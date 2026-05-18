package com.classroom2.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

object TimeUtil {
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val shortTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("MMM d • HH:mm", Locale.getDefault())

    fun formatTime(millis: Long): String = timeFormat.format(Date(millis))
    fun formatShortTime(millis: Long): String = shortTimeFormat.format(Date(millis))
    fun formatDate(millis: Long): String = dateFormat.format(Date(millis))
    fun formatDateTime(millis: Long): String = dateTimeFormat.format(Date(millis))

    fun formatCountdown(remainingMillis: Long): String {
        val total = max(remainingMillis / 1000, 0L)
        val mm = total / 60
        val ss = total % 60
        return String.format(Locale.getDefault(), "%02d:%02d", mm, ss)
    }
}
