package com.classroom2.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun DashboardHeroCard(
    eyebrow: String,
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null,
    metrics: @Composable (() -> Unit)? = null
) {
    val dark = androidx.compose.foundation.isSystemInDarkTheme()
    val gradient = Brush.linearGradient(
        listOf(
            if (dark) ClassroomGradientStartDark else ClassroomGradientStart,
            if (dark) ClassroomGradientEndDark else ClassroomGradientEnd
        )
    )
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = ClassroomShapes.Hero,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient, ClassroomShapes.Hero)
                .padding(ClassroomSpacing.lg)
        ) {
            Column {
                Row(verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = eyebrow.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.78f),
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(ClassroomSpacing.xs))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        if (subtitle != null) {
                            Spacer(Modifier.height(ClassroomSpacing.sm))
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }
                    if (trailing != null) trailing()
                }
                if (metrics != null) {
                    Spacer(Modifier.height(ClassroomSpacing.md))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.14f)
                    ) {
                        Box(modifier = Modifier.padding(ClassroomSpacing.sm)) { metrics() }
                    }
                }
            }
        }
    }
}
