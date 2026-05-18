package com.classroom2.app.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft

@Composable
fun DemoModeBanner(modifier: Modifier = Modifier) {
    if (!ServiceLocator.isUsingLocalBackend) return
    StatusChip(
        modifier = modifier,
        label = "Demo Mode",
        accent = ClassroomOrange,
        softBackground = ClassroomOrangeSoft,
        showDot = true
    )
}

/** Variant for use over gradient backgrounds (white frosted pill). */
@Composable
fun DemoModeBannerOnGradient(modifier: Modifier = Modifier) {
    if (!ServiceLocator.isUsingLocalBackend) return
    StatusChip(
        modifier = modifier,
        label = "Demo Mode",
        accent = MaterialTheme.colorScheme.surface,
        softBackground = MaterialTheme.colorScheme.surface.copy(alpha = 0.22f),
        showDot = true
    )
}
