package com.classroom2.app.data.remote

import com.classroom2.app.domain.model.AttendanceRecord
import com.classroom2.app.domain.model.ClassSession
import com.classroom2.app.domain.model.LeaderboardEntry
import com.classroom2.app.domain.model.Quiz
import com.classroom2.app.domain.model.QuizAnswer
import com.classroom2.app.domain.model.User
import com.classroom2.app.util.DemoData
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Single source of truth for the in-memory backend. Mirrors the Firestore schema
 * so the local repos and Firestore repos can swap behind the same interfaces.
 */
object InMemoryStore {
    val activeSession: MutableStateFlow<ClassSession?> = MutableStateFlow(null)
    val sessionHistory: MutableStateFlow<List<ClassSession>> = MutableStateFlow(emptyList())
    val attendance: MutableStateFlow<Map<String, List<AttendanceRecord>>> =
        MutableStateFlow(emptyMap())

    val activeQuiz: MutableStateFlow<Quiz?> = MutableStateFlow(null)
    val quizHistory: MutableStateFlow<List<Quiz>> = MutableStateFlow(emptyList())
    val quizAnswers: MutableStateFlow<Map<String, List<QuizAnswer>>> =
        MutableStateFlow(emptyMap())

    val leaderboard: MutableStateFlow<List<LeaderboardEntry>> =
        MutableStateFlow(DemoData.seedLeaderboard.sortedByDescending { it.points })

    val currentStudent: MutableStateFlow<User> = MutableStateFlow(DemoData.student)
    val currentProfessor: MutableStateFlow<User> = MutableStateFlow(DemoData.professor)
}
