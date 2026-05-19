package com.classroom2.app.data.repository

import com.classroom2.app.data.remote.FirestorePaths
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.domain.model.AttendanceRecord
import com.classroom2.app.domain.model.ClassSession
import com.classroom2.app.domain.model.User
import com.classroom2.app.util.AppResult
import com.classroom2.app.util.DemoData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

interface AttendanceRepository {
    suspend fun createAttendanceSession(professorId: String): ClassSession
    fun observeActiveSession(classId: String = DemoData.CLASS_ID): Flow<ClassSession?>
    fun observeAttendance(sessionId: String): Flow<List<AttendanceRecord>>
    fun observeSessionHistory(classId: String = DemoData.CLASS_ID): Flow<List<ClassSession>>
    suspend fun endSession(sessionId: String)
    suspend fun markAttendance(sessionId: String, student: User): AppResult<AttendanceRecord>
}

/* ---------- Local impl ---------- */

class LocalAttendanceRepository : AttendanceRepository {

    override suspend fun createAttendanceSession(professorId: String): ClassSession {
        val now = System.currentTimeMillis()
        val session = ClassSession(
            id = UUID.randomUUID().toString(),
            professorId = professorId,
            classId = DemoData.CLASS_ID,
            classTitle = DemoData.CLASS_TITLE,
            createdAt = now,
            expiresAt = now + 5 * 60 * 1000,
            active = true
        )
        InMemoryStore.activeSession.value = session
        InMemoryStore.attendance.value = InMemoryStore.attendance.value + (session.id to emptyList())
        return session
    }

    override fun observeActiveSession(classId: String): Flow<ClassSession?> =
        InMemoryStore.activeSession

    override fun observeAttendance(sessionId: String): Flow<List<AttendanceRecord>> =
        InMemoryStore.attendance.map { it[sessionId].orEmpty() }

    override fun observeSessionHistory(classId: String): Flow<List<ClassSession>> =
        InMemoryStore.sessionHistory

    override suspend fun endSession(sessionId: String) {
        val current = InMemoryStore.activeSession.value
        if (current?.id == sessionId) {
            val ended = current.copy(
                active = false,
                presentCount = InMemoryStore.attendance.value[sessionId]?.size ?: 0
            )
            InMemoryStore.sessionHistory.value = listOf(ended) + InMemoryStore.sessionHistory.value
            InMemoryStore.activeSession.value = null
        }
    }

    override suspend fun markAttendance(
        sessionId: String,
        student: User
    ): AppResult<AttendanceRecord> {
        val session = InMemoryStore.activeSession.value
            ?: return AppResult.Error("This session is no longer active. Ask the professor to start a fresh one.")
        if (session.id != sessionId) return AppResult.Error("This session is no longer active. Ask the professor to start a fresh one.")
        if (!session.active) return AppResult.Error("The professor ended this session. Wait for the next one.")
        if (session.isExpired) return AppResult.Error("This QR code expired. Ask your professor to generate a fresh one.")

        val current = InMemoryStore.attendance.value[sessionId].orEmpty()
        if (current.any { it.studentId == student.id }) {
            return AppResult.Error("You're already checked in for this session.")
        }
        val record = AttendanceRecord(
            id = student.id,
            sessionId = sessionId,
            studentId = student.id,
            studentName = student.name,
            timestamp = System.currentTimeMillis()
        )
        InMemoryStore.attendance.value =
            InMemoryStore.attendance.value + (sessionId to (current + record))
        return AppResult.Success(record)
    }
}

/* ---------- Firestore impl ---------- */

class FirestoreAttendanceRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : AttendanceRepository {

    override suspend fun createAttendanceSession(professorId: String): ClassSession {
        val now = System.currentTimeMillis()
        val doc = db.collection(FirestorePaths.SESSIONS).document()
        val session = ClassSession(
            id = doc.id,
            professorId = professorId,
            classId = DemoData.CLASS_ID,
            classTitle = DemoData.CLASS_TITLE,
            createdAt = now,
            expiresAt = now + 5 * 60 * 1000,
            active = true
        )
        doc.set(session).await()
        return session
    }

    override fun observeActiveSession(classId: String): Flow<ClassSession?> = callbackFlow {
        val reg = db.collection(FirestorePaths.SESSIONS)
            .whereEqualTo("classId", classId)
            .whereEqualTo("active", true)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snap, _ ->
                trySend(snap?.documents?.firstOrNull()?.toObject(ClassSession::class.java))
            }
        awaitClose { reg.remove() }
    }

    override fun observeAttendance(sessionId: String): Flow<List<AttendanceRecord>> = callbackFlow {
        val reg = db.collection(FirestorePaths.SESSIONS)
            .document(sessionId)
            .collection(FirestorePaths.ATTENDANCE)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, _ ->
                trySend(snap?.documents?.mapNotNull { it.toObject(AttendanceRecord::class.java) }.orEmpty())
            }
        awaitClose { reg.remove() }
    }

    override fun observeSessionHistory(classId: String): Flow<List<ClassSession>> = callbackFlow {
        val reg = db.collection(FirestorePaths.SESSIONS)
            .whereEqualTo("classId", classId)
            .whereEqualTo("active", false)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, _ ->
                trySend(snap?.documents?.mapNotNull { it.toObject(ClassSession::class.java) }.orEmpty())
            }
        awaitClose { reg.remove() }
    }

    override suspend fun endSession(sessionId: String) {
        db.collection(FirestorePaths.SESSIONS)
            .document(sessionId)
            .update("active", false)
            .await()
    }

    override suspend fun markAttendance(
        sessionId: String,
        student: User
    ): AppResult<AttendanceRecord> {
        return try {
            val sessionRef = db.collection(FirestorePaths.SESSIONS).document(sessionId)
            val session = sessionRef.get().await().toObject(ClassSession::class.java)
                ?: return AppResult.Error("This session is no longer active. Ask the professor to start a fresh one.")
            if (!session.active) return AppResult.Error("The professor ended this session. Wait for the next one.")
            if (session.isExpired) return AppResult.Error("This QR code expired. Ask your professor to generate a fresh one.")

            val attendanceRef = sessionRef
                .collection(FirestorePaths.ATTENDANCE)
                .document(student.id)
            if (attendanceRef.get().await().exists()) {
                return AppResult.Error("You're already checked in for this session.")
            }
            val record = AttendanceRecord(
                id = student.id,
                sessionId = sessionId,
                studentId = student.id,
                studentName = student.name,
                timestamp = System.currentTimeMillis()
            )
            attendanceRef.set(record).await()
            AppResult.Success(record)
        } catch (t: Throwable) {
            AppResult.Error(t.message ?: "Live sync is unavailable, but demo mode is still ready.")
        }
    }
}
