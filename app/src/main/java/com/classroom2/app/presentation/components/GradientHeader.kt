package com.classroom2.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.ui.theme.ClassroomGradientEnd
import com.classroom2.app.ui.theme.ClassroomGradientEndDark
import com.classroom2.app.ui.theme.ClassroomGradientStart
import com.classroom2.app.ui.theme.ClassroomGradientStartDark
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun classroomGradient(darkTheme: Boolean = isAppInDarkTheme()): Brush {
    val start = if (darkTheme) ClassroomGradientStartDark else ClassroomGradientStart
    val end = if (darkTheme) ClassroomGradientEndDark else ClassroomGradientEnd
    return Brush.linearGradient(listOf(start, end))
}

@Composable
fun isAppInDarkTheme(): Boolean = androidx.compose.foundation.isSystemInDarkTheme()

@Composable
fun GradientHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = classroomGradient(),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.lg)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            if (onBack != null) {
                IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            Column(modifier = Modifier.weight(1f).padding(top = if (onBack != null) 4.dp else 0.dp, start = 4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
            if (trailing != null) trailing()
        }
    }
}

