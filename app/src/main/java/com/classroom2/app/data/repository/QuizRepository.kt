package com.classroom2.app.data.repository

import com.classroom2.app.data.remote.FirestorePaths
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.domain.model.Quiz
import com.classroom2.app.domain.model.QuizAnswer
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

interface QuizRepository {
    suspend fun createQuiz(quiz: Quiz): Quiz
    fun observeActiveQuiz(classId: String = DemoData.CLASS_ID): Flow<Quiz?>
    fun observeAnswers(quizId: String): Flow<List<QuizAnswer>>
    suspend fun endQuiz(quizId: String)
    suspend fun submitAnswer(quiz: Quiz, student: User, selectedIndex: Int): AppResult<QuizAnswer>
}

/* ---------- Local impl ---------- */

class LocalQuizRepository : QuizRepository {

    override suspend fun createQuiz(quiz: Quiz): Quiz {
        val now = System.currentTimeMillis()
        val saved = quiz.copy(
            id = quiz.id.ifBlank { UUID.randomUUID().toString() },
            classId = DemoData.CLASS_ID,
            createdAt = now,
            active = true
        )
        InMemoryStore.activeQuiz.value = saved
        InMemoryStore.quizAnswers.value = InMemoryStore.quizAnswers.value + (saved.id to emptyList())
        return saved
    }

    override fun observeActiveQuiz(classId: String): Flow<Quiz?> = InMemoryStore.activeQuiz

    override fun observeAnswers(quizId: String): Flow<List<QuizAnswer>> =
        InMemoryStore.quizAnswers.map { it[quizId].orEmpty() }

    override suspend fun endQuiz(quizId: String) {
        val current = InMemoryStore.activeQuiz.value
        if (current?.id == quizId) {
            val ended = current.copy(active = false)
            InMemoryStore.quizHistory.value = listOf(ended) + InMemoryStore.quizHistory.value
            InMemoryStore.activeQuiz.value = null
        }
    }

    override suspend fun submitAnswer(
        quiz: Quiz,
        student: User,
        selectedIndex: Int
    ): AppResult<QuizAnswer> {
        if (!quiz.active) return AppResult.Error("This quiz has ended. Wait for the next live question.")
        if (selectedIndex !in quiz.options.indices) return AppResult.Error("Pick one of the four options to submit.")
        val current = InMemoryStore.quizAnswers.value[quiz.id].orEmpty()
        if (current.any { it.studentId == student.id }) {
            return AppResult.Error("You already answered. Wait for the next question.")
        }
        val answer = QuizAnswer(
            id = student.id,
            quizId = quiz.id,
            studentId = student.id,
            studentName = student.name,
            selectedIndex = selectedIndex,
            correct = selectedIndex == quiz.correctAnswerIndex,
            submittedAt = System.currentTimeMillis()
        )
        InMemoryStore.quizAnswers.value =
            InMemoryStore.quizAnswers.value + (quiz.id to (current + answer))
        return AppResult.Success(answer)
    }
}

/* ---------- Firestore impl ---------- */

class FirestoreQuizRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : QuizRepository {

    override suspend fun createQuiz(quiz: Quiz): Quiz {
        val doc = db.collection(FirestorePaths.QUIZZES).document()
        val saved = quiz.copy(
            id = doc.id,
            classId = DemoData.CLASS_ID,
            createdAt = System.currentTimeMillis(),
            active = true
        )
        doc.set(saved).await()
        return saved
    }

    override fun observeActiveQuiz(classId: String): Flow<Quiz?> = callbackFlow {
        val reg = db.collection(FirestorePaths.QUIZZES)
            .whereEqualTo("classId", classId)
            .whereEqualTo("active", true)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snap, _ ->
                trySend(snap?.documents?.firstOrNull()?.toObject(Quiz::class.java))
            }
        awaitClose { reg.remove() }
    }

    override fun observeAnswers(quizId: String): Flow<List<QuizAnswer>> = callbackFlow {
        val reg = db.collection(FirestorePaths.QUIZZES)
            .document(quizId)
            .collection(FirestorePaths.ANSWERS)
            .orderBy("submittedAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, _ ->
                trySend(snap?.documents?.mapNotNull { it.toObject(QuizAnswer::class.java) }.orEmpty())
            }
        awaitClose { reg.remove() }
    }

    override suspend fun endQuiz(quizId: String) {
        db.collection(FirestorePaths.QUIZZES)
            .document(quizId)
            .update("active", false)
            .await()
    }

    override suspend fun submitAnswer(
        quiz: Quiz,
        student: User,
        selectedIndex: Int
    ): AppResult<QuizAnswer> {
        return try {
            if (!quiz.active) return AppResult.Error("This quiz has ended. Wait for the next live question.")
            if (selectedIndex !in quiz.options.indices) return AppResult.Error("Pick one of the four options to submit.")
            val answerRef = db.collection(FirestorePaths.QUIZZES)
                .document(quiz.id)
                .collection(FirestorePaths.ANSWERS)
                .document(student.id)
            if (answerRef.get().await().exists()) {
                return AppResult.Error("You already answered. Wait for the next question.")
            }
            val answer = QuizAnswer(
                id = student.id,
                quizId = quiz.id,
                studentId = student.id,
                studentName = student.name,
                selectedIndex = selectedIndex,
                correct = selectedIndex == quiz.correctAnswerIndex,
                submittedAt = System.currentTimeMillis()
            )
            answerRef.set(answer).await()
            AppResult.Success(answer)
        } catch (t: Throwable) {
            AppResult.Error(t.message ?: "Live sync is unavailable, but demo mode is still ready.")
        }
    }
}
