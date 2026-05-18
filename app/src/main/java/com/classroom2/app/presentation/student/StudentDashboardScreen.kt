package com.classroom2.app.presentation.student

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.presentation.components.ActionCard
import com.classroom2.app.presentation.components.AnimatedCounter
import com.classroom2.app.presentation.components.BadgePill
import com.classroom2.app.presentation.components.DashboardHeroCard
import com.classroom2.app.presentation.components.DemoModeBanner
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomPurple
import com.classroom2.app.ui.theme.ClassroomPurpleSoft
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun StudentDashboardScreen(
    onScan: () -> Unit,
    onJoinQuiz: () -> Unit,
    onAIExplainer: () -> Unit,
    onLeaderboard: () -> Unit,
    onSwitchRole: () -> Unit
) {
    val student by InMemoryStore.currentStudent.collectAsState()
    val activeSession by InMemoryStore.activeSession.collectAsState()
    val activeQuiz by InMemoryStore.activeQuiz.collectAsState()

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // Progress to next "milestone": every 50 pts unlocks a badge tier.
    val nextMilestone = ((student.points / 50) + 1) * 50
    val progress = (student.points % 50) / 50f
    val pointsToNext = nextMilestone - student.points

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = ClassroomSpacing.lg)
    ) {
        AnimatedVisibility(visible, enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 12 }) {
            Column(
                modifier = Modifier.padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
            ) {
                DashboardHeroCard(
                    eyebrow = "Hi there 👋",
                    title = student.name,
                    subtitle = "You're on a ${student.streak}-class learning streak.",
                    trailing = { DemoModeBanner() },
                    metrics = {
                        Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        "POINTS",
                                        color = Color.White.copy(alpha = 0.8f),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    AnimatedCounter(
                                        value = student.points,
                                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    StatusChip(
                                        label = "🔥 ${student.streak} days",
                                        accent = Color.White,
                                        softBackground = Color.White.copy(alpha = 0.2f),
                                        showDot = false
                                    )
                                    if (student.badges.isNotEmpty()) {
                                        Text(
                                            "Latest: ${student.badges.last()}",
                                            color = Color.White.copy(alpha = 0.85f),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Next milestone $nextMilestone pts",
                                    color = Color.White.copy(alpha = 0.85f),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Text(
                                    "$pointsToNext to go",
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = progress.coerceIn(0f, 1f))
                                        .height(8.dp)
                                        .background(Color.White, RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)
                ) {
                    if (activeSession != null) {
                        StatusChip(label = "Scan now", accent = ClassroomGreen, softBackground = ClassroomGreenSoft)
                    }
                    if (activeQuiz != null) {
                        StatusChip(label = "Quiz live", accent = ClassroomOrange, softBackground = ClassroomOrangeSoft)
                    }
                    Spacer(Modifier.weight(1f))
                    SecondaryActionButton(
                        text = "👩‍🏫 Pro view",
                        onClick = onSwitchRole,
                        modifier = Modifier.fillMaxWidth(0.42f)
                    )
                }

                SectionHeader(title = "Today")

                ActionCard(
                    title = "Scan attendance QR",
                    subtitle = if (activeSession != null) "Session live — tap to check in"
                               else "Open scanner when professor starts attendance",
                    icon = Icons.Filled.QrCodeScanner,
                    onClick = onScan
                )
                ActionCard(
                    title = "Join live quiz",
                    subtitle = if (activeQuiz != null) "Quiz is open right now"
                               else "Wait for professor to start a quiz",
                    icon = Icons.Filled.QuestionAnswer,
                    accent = ClassroomPurple,
                    accentContainer = ClassroomPurpleSoft,
                    onClick = onJoinQuiz
                )
                ActionCard(
                    title = "Ask AI explainer",
                    subtitle = "Get a clearer explanation of any concept",
                    icon = Icons.Filled.AutoAwesome,
                    accent = ClassroomGreen,
                    accentContainer = ClassroomGreenSoft,
                    onClick = onAIExplainer
                )
                ActionCard(
                    title = "Leaderboard",
                    subtitle = "See how you rank in the class",
                    icon = Icons.Filled.EmojiEvents,
                    accent = ClassroomOrange,
                    accentContainer = ClassroomOrangeSoft,
                    onClick = onLeaderboard
                )

                if (student.badges.isNotEmpty()) {
                    SectionHeader(title = "Badges earned")
                    Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                        student.badges.takeLast(3).forEach { name ->
                            BadgePill(emoji = badgeEmoji(name), label = name)
                        }
                    }
                }
            }
        }
    }
}

private fun badgeEmoji(name: String): String =
    com.classroom2.app.domain.model.Badge.emojiFor(name)
