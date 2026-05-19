package com.classroom2.app.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.HowToReg
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Toll
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.ui.graphics.vector.ImageVector
import com.classroom2.app.domain.model.Badge
import com.classroom2.app.domain.model.UserRole

/**
 * Single source of truth mapping every domain concept to an [ImageVector].
 * Screens reference this instead of `Icons.Outlined.*` so swaps are centralized.
 */
object ClassroomIcons {

    fun role(role: UserRole): ImageVector = when (role) {
        UserRole.PROFESSOR -> Icons.Outlined.School
        UserRole.STUDENT -> Icons.Outlined.Backpack
    }

    fun badge(badge: Badge): ImageVector = when (badge.id) {
        "first_check_in" -> Icons.Outlined.Key
        "quiz_starter" -> Icons.Outlined.MyLocation
        "quick_thinker" -> Icons.Outlined.Bolt
        "perfect_answer" -> Icons.Outlined.Verified
        "streak_builder" -> Icons.Outlined.LocalFireDepartment
        "top_three" -> Icons.Outlined.EmojiEvents
        else -> Icons.Outlined.MilitaryTech
    }

    fun badgeByTitle(title: String): ImageVector =
        Badge.all.firstOrNull { it.title == title }?.let(::badge) ?: Icons.Outlined.MilitaryTech

    val streak: ImageVector = Icons.Outlined.LocalFireDepartment
    val points: ImageVector = Icons.Outlined.Toll
    val trophy: ImageVector = Icons.Outlined.EmojiEvents
    val sparkle: ImageVector = Icons.Outlined.AutoAwesome
    val lightbulb: ImageVector = Icons.Outlined.Lightbulb
    val insight: ImageVector = Icons.Outlined.Insights
    val queryStats: ImageVector = Icons.Outlined.QueryStats
    val barChart: ImageVector = Icons.Outlined.BarChart
    val attendance: ImageVector = Icons.Outlined.HowToReg
    val cameraOff: ImageVector = Icons.Outlined.CameraAlt
    val scanner: ImageVector = Icons.Outlined.QrCodeScanner
    val emptyAttendance: ImageVector = Icons.Outlined.GroupAdd
    val folder: ImageVector = Icons.Outlined.FolderOpen
    val medalGeneric: ImageVector = Icons.Outlined.MilitaryTech
}
