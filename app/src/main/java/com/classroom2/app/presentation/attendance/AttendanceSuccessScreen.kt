package com.classroom2.app.presentation.attendance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.BadgePill
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SuccessCheck
import com.classroom2.app.ui.theme.ClassroomGradientEnd
import com.classroom2.app.ui.theme.ClassroomGradientStart
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun AttendanceSuccessScreen(
    onBackToDashboard: () -> Unit,
    vm: AttendanceViewModel = viewModel()
) {
    val outcome by vm.scanOutcome.collectAsState()
    val pointsEarned = 15 // attendance 10 + streak 5

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    DisposableEffect(Unit) {
        onDispose { vm.consumeScanOutcome() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = ClassroomSpacing.lg)
    ) {
        Spacer(Modifier.size(ClassroomSpacing.xxl))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(350)) + scaleIn(tween(350), initialScale = 0.7f)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SuccessCheck()
            }
        }

        Spacer(Modifier.size(ClassroomSpacing.lg))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 120)) + slideInVertically(tween(400, delayMillis = 120)) { it / 6 }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)
            ) {
                Text(
                    "You're checked in!",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Attendance confirmed for Computer Science 101.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.size(ClassroomSpacing.lg))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(450, delayMillis = 240)) + slideInVertically(tween(450, delayMillis = 240)) { it / 6 }
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = ClassroomShapes.LargeCard,
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(listOf(ClassroomGradientStart, ClassroomGradientEnd)),
                            shape = ClassroomShapes.LargeCard
                        )
                        .padding(ClassroomSpacing.lg)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "+$pointsEarned points earned",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "10 attendance · 5 streak bonus",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        outcome?.let { o ->
                            Spacer(Modifier.size(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                BadgePill(
                                    emoji = "💰",
                                    label = "${o.totalPoints} pts total",
                                    accent = Color.White,
                                    softBackground = Color.White.copy(alpha = 0.18f)
                                )
                                BadgePill(
                                    emoji = "🔥",
                                    label = "${o.streak} day streak",
                                    accent = Color.White,
                                    softBackground = Color.White.copy(alpha = 0.18f)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(500, delayMillis = 360))
        ) {
            PrimaryActionButton(
                text = "Back to dashboard",
                onClick = onBackToDashboard
            )
        }

        Spacer(Modifier.size(ClassroomSpacing.lg))
    }
}

