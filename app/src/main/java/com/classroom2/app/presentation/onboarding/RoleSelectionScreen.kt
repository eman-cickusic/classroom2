package com.classroom2.app.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.domain.model.UserRole
import com.classroom2.app.presentation.components.DemoModeBannerOnGradient
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.theme.ClassroomGradientEnd
import com.classroom2.app.ui.theme.ClassroomGradientEndDark
import com.classroom2.app.ui.theme.ClassroomGradientStart
import com.classroom2.app.ui.theme.ClassroomGradientStartDark
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun RoleSelectionScreen(
    onContinue: (UserRole) -> Unit
) {
    var selected by remember { mutableStateOf<UserRole?>(null) }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val dark = isSystemInDarkTheme()
    val gradient = Brush.verticalGradient(
        listOf(
            if (dark) ClassroomGradientStartDark else ClassroomGradientStart,
            if (dark) ClassroomGradientEndDark else ClassroomGradientEnd
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = gradient,
                    shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp)
                )
                .padding(horizontal = ClassroomSpacing.lg, vertical = 40.dp)
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(450)) + slideInVertically(tween(450)) { it / 6 }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Color.White.copy(alpha = 0.22f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🎓", style = MaterialTheme.typography.headlineSmall)
                            }
                            Text(
                                "Classroom 2.0",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        DemoModeBannerOnGradient()
                    }

                    Text(
                        "Turn every class into a live learning experience.",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "QR attendance, live quizzes, AI explanations, and real-time teaching insights.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(Modifier.height(ClassroomSpacing.sm))

                    // Floating mini-metric cards
                    Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                        FloatingMini("92%", "attendance captured", Modifier.weight(1f))
                        FloatingMini("Live", "quiz active", Modifier.weight(1f))
                        FloatingMini("AI", "insight ready", Modifier.weight(1f))
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(550, delayMillis = 120)) + slideInVertically(tween(550, delayMillis = 120)) { it / 8 }
        ) {
            Column(
                modifier = Modifier.padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
            ) {
                Text(
                    "Continue as",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                RoleOption(
                    emoji = "👩‍🏫",
                    title = "Professor",
                    description = "Start attendance, run live quizzes, see real-time insights",
                    selected = selected == UserRole.PROFESSOR,
                    onClick = { selected = UserRole.PROFESSOR }
                )

                RoleOption(
                    emoji = "🎒",
                    title = "Student",
                    description = "Scan QR, join quizzes, ask AI, earn points",
                    selected = selected == UserRole.STUDENT,
                    onClick = { selected = UserRole.STUDENT }
                )

                Text(
                    "Choose a role to explore the live classroom flow.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(ClassroomSpacing.sm))

                PrimaryActionButton(
                    text = "Continue",
                    onClick = { selected?.let(onContinue) },
                    enabled = selected != null
                )
            }
        }

        Spacer(Modifier.height(ClassroomSpacing.lg))
    }
}

@Composable
private fun FloatingMini(headline: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = Color.White.copy(alpha = 0.18f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(headline, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(label, color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun RoleOption(
    emoji: String,
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = ClassroomShapes.LargeCard,
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        tonalElevation = if (selected) 0.dp else 1.dp
    ) {
        Row(modifier = Modifier.padding(ClassroomSpacing.md + 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, style = MaterialTheme.typography.headlineSmall)
            }
            Spacer(Modifier.size(ClassroomSpacing.md))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (selected) {
                StatusChip(label = "Selected")
            }
        }
    }
}
