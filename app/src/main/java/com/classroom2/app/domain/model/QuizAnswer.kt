package com.classroom2.app.domain.model

data class QuizAnswer(
    val id: String = "",
    val quizId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val selectedIndex: Int = 0,
    val correct: Boolean = false,
    val submittedAt: Long = System.currentTimeMillis()
)
