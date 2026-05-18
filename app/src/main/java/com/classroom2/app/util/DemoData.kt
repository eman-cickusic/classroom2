package com.classroom2.app.util

import com.classroom2.app.domain.model.LeaderboardEntry
import com.classroom2.app.domain.model.Quiz
import com.classroom2.app.domain.model.User
import com.classroom2.app.domain.model.UserRole

object DemoData {

    const val CLASS_ID = "demo-class"
    const val CLASS_TITLE = "Computer Science 101"
    const val EXPECTED_CLASS_SIZE = 6

    val professor = User(
        id = "prof-demo",
        name = "Professor Ada",
        role = UserRole.PROFESSOR
    )

    val student = User(
        id = "student-demo",
        name = "Eman",
        role = UserRole.STUDENT,
        points = 120,
        streak = 3,
        badges = listOf("First Check-In", "Quiz Starter")
    )

    val classmates: List<User> = listOf(
        User(id = "student-1", name = "Lejla", role = UserRole.STUDENT, points = 160, streak = 5),
        User(id = "student-2", name = "Amar", role = UserRole.STUDENT, points = 145, streak = 4),
        User(id = "student-3", name = "Sara", role = UserRole.STUDENT, points = 132, streak = 3),
        User(id = "student-4", name = "Tarik", role = UserRole.STUDENT, points = 115, streak = 2),
        User(id = "student-5", name = "Hana", role = UserRole.STUDENT, points = 105, streak = 2)
    )

    val demoQuiz = Quiz(
        question = "What does polymorphism mean in programming?",
        options = listOf(
            "A variable changing value",
            "One interface having many forms",
            "A loop inside another loop",
            "Storing data permanently"
        ),
        correctAnswerIndex = 1
    )

    val seedLeaderboard: List<LeaderboardEntry> = (classmates + student).map { u ->
        LeaderboardEntry(
            studentId = u.id,
            studentName = u.name,
            points = u.points,
            streak = u.streak,
            badges = u.badges
        )
    }
}
