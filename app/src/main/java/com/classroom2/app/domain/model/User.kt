package com.classroom2.app.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val role: UserRole = UserRole.STUDENT,
    val points: Int = 0,
    val streak: Int = 0,
    val badges: List<String> = emptyList()
)
