package com.classroom2.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ClassroomColorScheme = lightColorScheme(
    primary = ClassroomIndigo,
    onPrimary = Color.White,
    primaryContainer = ClassroomIndigoSoft,
    onPrimaryContainer = ClassroomIndigo,
    secondary = ClassroomPurple,
    onSecondary = Color.White,
    secondaryContainer = ClassroomPurpleSoft,
    onSecondaryContainer = ClassroomPurple,
    tertiary = ClassroomOrange,
    onTertiary = Color.White,
    tertiaryContainer = ClassroomOrangeSoft,
    onTertiaryContainer = ClassroomOrange,
    background = ClassroomBackground,
    onBackground = ClassroomTextPrimary,
    surface = ClassroomSurface,
    onSurface = ClassroomTextPrimary,
    surfaceVariant = ClassroomSurfaceMuted,
    onSurfaceVariant = ClassroomTextSecondary,
    outline = ClassroomBorder,
    error = ClassroomRed,
    onError = Color.White,
    errorContainer = ClassroomRedSoft,
    onErrorContainer = ClassroomRed
)

@Composable
fun Classroom2Theme(
    @Suppress("UNUSED_PARAMETER") darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ClassroomColorScheme,
        typography = ClassroomTypography,
        content = content
    )
}
