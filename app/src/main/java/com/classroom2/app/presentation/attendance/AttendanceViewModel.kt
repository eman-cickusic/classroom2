package com.classroom2.app.presentation.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.data.remote.ScanOutcomeState
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.domain.model.AttendanceQrPayload
import com.classroom2.app.domain.model.AttendanceRecord
import com.classroom2.app.domain.model.ClassSession
import com.classroom2.app.util.AppResult
import com.classroom2.app.util.DemoData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AttendanceUiState(
    val activeSession: ClassSession? = null,
    val records: List<AttendanceRecord> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

class AttendanceViewModel : ViewModel() {

    private val attendanceRepo = ServiceLocator.attendance
    private val authRepo = ServiceLocator.auth
    private val gamification = ServiceLocator.gamification

    val activeSession: StateFlow<ClassSession?> =
        attendanceRepo.observeActiveSession()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val attendanceRecords: StateFlow<List<AttendanceRecord>> =
        activeSession
            .flatMapLatest { s ->
                if (s == null) flowOf(emptyList()) else attendanceRepo.observeAttendance(s.id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val professorUiState: StateFlow<AttendanceUiState> =
        combine(activeSession, attendanceRecords) { s, r ->
            AttendanceUiState(activeSession = s, records = r)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AttendanceUiState())

    val scanOutcome: StateFlow<ScanOutcomeState?> = InMemoryStore.lastScanOutcome

    fun startSession() {
        if (activeSession.value != null) return
        viewModelScope.launch {
            attendanceRepo.createAttendanceSession(authRepo.professor().id)
        }
    }

    fun endSession() {
        val s = activeSession.value ?: return
        viewModelScope.launch { attendanceRepo.endSession(s.id) }
    }

    fun currentQrPayload(): String? {
        val s = activeSession.value ?: return null
        return AttendanceQrPayload(
            sessionId = s.id,
            professorId = s.professorId,
            classId = s.classId,
            expiresAt = s.expiresAt
        ).toJson()
    }

    fun demoScan() {
        val s = activeSession.value
        if (s == null) {
            InMemoryStore.lastScanOutcome.value = ScanOutcomeState(
                error = "Ask the professor to start attendance, then tap Demo scan again."
            )
            return
        }
        markAttendance(s.id)
    }

    fun handleScannedPayload(raw: String) {
        val payload = AttendanceQrPayload.fromJson(raw)
        if (payload == null) {
            InMemoryStore.lastScanOutcome.value = ScanOutcomeState(
                error = "That QR code isn't a Classroom 2.0 session — point at the professor's QR."
            )
            return
        }
        if (payload.expiresAt < System.currentTimeMillis()) {
            InMemoryStore.lastScanOutcome.value = ScanOutcomeState(
                error = "This QR code expired. Ask your professor to generate a fresh one."
            )
            return
        }
        markAttendance(payload.sessionId)
    }

    private fun markAttendance(sessionId: String) {
        viewModelScope.launch {
            val student = authRepo.student()
            when (val result = attendanceRepo.markAttendance(sessionId, student)) {
                is AppResult.Success -> {
                    val updated = gamification.awardAttendance(student)
                    InMemoryStore.lastScanOutcome.value = ScanOutcomeState(
                        recordId = result.data.id,
                        studentName = updated.name,
                        totalPoints = updated.points,
                        streak = updated.streak
                    )
                }
                is AppResult.Error ->
                    InMemoryStore.lastScanOutcome.value = ScanOutcomeState(error = result.message)
                AppResult.Loading -> Unit
            }
        }
    }

    fun consumeScanOutcome() {
        InMemoryStore.lastScanOutcome.value = null
    }

    val expectedClassSize: Int get() = DemoData.EXPECTED_CLASS_SIZE
}
