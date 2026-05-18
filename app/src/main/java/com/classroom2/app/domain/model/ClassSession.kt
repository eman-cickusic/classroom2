package com.classroom2.app.domain.model

data class ClassSession(
    val id: String = "",
    val professorId: String = "",
    val classId: String = "demo-class",
    val classTitle: String = "Computer Science 101",
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + 5 * 60 * 1000,
    val active: Boolean = true,
    val presentCount: Int = 0
) {
    val isExpired: Boolean
        get() = expiresAt < System.currentTimeMillis()
}
