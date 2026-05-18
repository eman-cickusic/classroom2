package com.classroom2.app.domain.model

data class AttendanceRecord(
    val id: String = "",
    val sessionId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
