package com.classroom2.app.domain.model

data class Quiz(
    val id: String = "",
    val sessionId: String = "",
    val classId: String = "demo-class",
    val professorId: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = 0,
    val active: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
