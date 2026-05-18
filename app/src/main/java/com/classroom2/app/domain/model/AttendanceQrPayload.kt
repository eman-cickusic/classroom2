package com.classroom2.app.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class AttendanceQrPayload(
    val sessionId: String,
    val professorId: String,
    val classId: String,
    val expiresAt: Long
) {
    fun toJson(): String = Json.encodeToString(serializer(), this)

    companion object {
        private val json = Json { ignoreUnknownKeys = true; isLenient = true }

        fun fromJson(raw: String): AttendanceQrPayload? = runCatching {
            json.decodeFromString(serializer(), raw.trim())
        }.getOrNull()
    }
}
