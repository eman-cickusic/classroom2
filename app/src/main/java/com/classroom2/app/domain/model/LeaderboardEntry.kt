package com.classroom2.app.domain.model

data class LeaderboardEntry(
    val studentId: String = "",
    val studentName: String = "",
    val points: Int = 0,
    val streak: Int = 0,
    val badges: List<String> = emptyList(),
    val updatedAt: Long = System.currentTimeMillis()
)
