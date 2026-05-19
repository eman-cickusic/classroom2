package com.classroom2.app.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

@Serializable
data class AttendanceQrPayload(
    val sessionId: String,
    val professorId: String,
    val classId: String,
    val expiresAt: Long
) {
    /**
     * Encoded as a `classroom2://` URL so iOS Camera doesn't misread the
     * 13-digit `expiresAt` timestamp as a phone number. Android's ML Kit
     * scanner returns the raw value either way; we strip the scheme on the
     * way back in.
     */
    fun toJson(): String {
        val json = Json.encodeToString(serializer(), this)
        return SCHEME + URLEncoder.encode(json, Charsets.UTF_8.name())
    }

    companion object {
        private const val SCHEME = "classroom2://attend?p="
        private val parser = Json { ignoreUnknownKeys = true; isLenient = true }

        fun fromJson(raw: String): AttendanceQrPayload? = runCatching {
            val trimmed = raw.trim()
            val body = if (trimmed.startsWith(SCHEME)) {
                URLDecoder.decode(trimmed.removePrefix(SCHEME), Charsets.UTF_8.name())
            } else {
                trimmed // accept bare JSON for backwards compatibility
            }
            parser.decodeFromString(serializer(), body)
        }.getOrNull()
    }
}
