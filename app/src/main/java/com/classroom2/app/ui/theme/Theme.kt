package com.classroom2.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = ClassroomBlue,
    onPrimary = Color.White,
    primaryContainer = ClassroomBlueSoft,
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
    surfaceVariant = ClassroomSurfaceSoft,
    onSurfaceVariant = ClassroomTextSecondary,
    outline = ClassroomBorder,
    error = ClassroomRed,
    onError = Color.White,
    errorContainer = ClassroomRedSoft,
    onErrorContainer = ClassroomRed
)

private val DarkColors = darkColorScheme(
    primary = ClassroomBlue,
    onPrimary = Color.White,
    primaryContainer = ClassroomBlueDarkSoft,
    onPrimaryContainer = ClassroomBlueSoft,
    secondary = ClassroomPurple,
    onSecondary = Color.White,
    secondaryContainer = ClassroomPurpleDarkSoft,
    onSecondaryContainer = ClassroomPurpleSoft,
    tertiary = ClassroomOrange,
    onTertiary = Color.White,
    tertiaryContainer = ClassroomOrangeDarkSoft,
    onTertiaryContainer = ClassroomOrangeSoft,
    background = ClassroomDarkBackground,
    onBackground = ClassroomDarkTextPrimary,
    surface = ClassroomDarkSurface,
    onSurface = ClassroomDarkTextPrimary,
    surfaceVariant = ClassroomDarkSurfaceSoft,
    onSurfaceVariant = ClassroomDarkTextSecondary,
    outline = ClassroomDarkBorder,
    error = ClassroomRed,
    onError = Color.White,
    errorContainer = ClassroomRedDarkSoft,
    onErrorContainer = ClassroomRedSoft
)

@Composable
fun Classroom2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = ClassroomTypography,
        shapes = ClassroomMaterialShapes,
        content = content
    )
}
