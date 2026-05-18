package com.classroom2.app.data.remote

import com.classroom2.app.data.repository.AttendanceRepository
import com.classroom2.app.data.repository.AuthRepository
import com.classroom2.app.data.repository.FirestoreAttendanceRepository
import com.classroom2.app.data.repository.FirestoreQuizRepository
import com.classroom2.app.data.repository.GamificationRepository
import com.classroom2.app.data.repository.InsightRepository
import com.classroom2.app.data.repository.LocalAttendanceRepository
import com.classroom2.app.data.repository.LocalAuthRepository
import com.classroom2.app.data.repository.LocalQuizRepository
import com.classroom2.app.data.repository.QuizRepository

object ServiceLocator {

    val auth: AuthRepository by lazy { LocalAuthRepository() }

    val attendance: AttendanceRepository by lazy {
        if (FirebaseInitializer.firestoreAvailable) FirestoreAttendanceRepository()
        else LocalAttendanceRepository()
    }

    val quiz: QuizRepository by lazy {
        if (FirebaseInitializer.firestoreAvailable) FirestoreQuizRepository()
        else LocalQuizRepository()
    }

    val insight: InsightRepository by lazy { InsightRepository() }

    val gamification: GamificationRepository by lazy { GamificationRepository() }

    val isUsingLocalBackend: Boolean
        get() = !FirebaseInitializer.firestoreAvailable

    fun init() {
        // Force lazy initialization so any wiring errors surface early.
        listOf(auth, attendance, quiz, insight, gamification)
    }
}
