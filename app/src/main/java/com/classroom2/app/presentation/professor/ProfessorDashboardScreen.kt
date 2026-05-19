package com.classroom2.app.presentation.professor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.QuestionAnswer
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
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.domain.model.UserRole
import com.classroom2.app.ui.icons.ClassroomIcons
import com.classroom2.app.util.DemoData
import com.classroom2.app.presentation.components.ActionCard
import com.classroom2.app.presentation.components.AnimatedCounter
import com.classroom2.app.presentation.components.DashboardHeroCard
import com.classroom2.app.presentation.components.DemoModeBanner
import com.classroom2.app.presentation.components.MetricCard
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
import com.classroom2.app.util.TimeUtil

@Composable
fun ProfessorDashboardScreen(
    onStartAttendance: () -> Unit,
    onStartQuiz: () -> Unit,
    onOpenInsights: () -> Unit,
    onOpenLeaderboard: () -> Unit,
    onOpenHistory: () -> Unit,
    onSwitchRole: () -> Unit
) {
    val professor = ServiceLocator.auth.professor()
    val activeSession by InMemoryStore.activeSession.collectAsState()
    val attendance by InMemoryStore.attendance.collectAsState()
    val activeQuiz by InMemoryStore.activeQuiz.collectAsState()
    val sessionHistory by InMemoryStore.sessionHistory.collectAsState()

    val presentCount = activeSession?.let { attendance[it.id]?.size } ?: 0

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

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
                    eyebrow = "Today",
                    title = professor.name,
                    subtitle = "${DemoData.CLASS_TITLE} · ${DemoData.EXPECTED_CLASS_SIZE} students",
                    trailing = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            DemoModeBanner()
                        }
                    },
                    metrics = {
                        Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                            HeroMetric(
                                label = "Present",
                                modifier = Modifier.weight(1f)
                            ) {
                                AnimatedCounter(
                                    value = presentCount,
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                            HeroMetricText(
                                label = "Active quiz",
                                value = if (activeQuiz != null) "Live" else "—",
                                modifier = Modifier.weight(1f)
                            )
                            HeroMetricText(
                                label = "Engagement",
                                value = "92%",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (activeSession != null) {
                        StatusChip(
                            label = "Attendance open",
                            accent = ClassroomGreen,
                            softBackground = ClassroomGreenSoft
                        )
                    }
                    if (activeQuiz != null) {
                        StatusChip(
                            label = "Quiz in progress",
                            accent = ClassroomOrange,
                            softBackground = ClassroomOrangeSoft
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    SecondaryActionButton(
                        text = "Student view",
                        icon = ClassroomIcons.role(UserRole.STUDENT),
                        onClick = onSwitchRole,
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                }

                SectionHeader(title = "Quick actions")

                ActionCard(
                    title = "Start attendance",
                    subtitle = if (activeSession != null) "Session open. View QR."
                               else "Generate a QR for students to scan.",
                    icon = Icons.Outlined.QrCode2,
                    onClick = onStartAttendance
                )
                ActionCard(
                    title = "Start live quiz",
                    subtitle = if (activeQuiz != null) "Quiz in progress. View answers."
                               else "Ask one question. See live answers.",
                    icon = Icons.Outlined.QuestionAnswer,
                    accent = ClassroomPurple,
                    accentContainer = ClassroomPurpleSoft,
                    onClick = onStartQuiz
                )
                ActionCard(
                    title = "Teaching insight",
                    subtitle = "Class understanding and recommended next step.",
                    icon = Icons.Outlined.AutoAwesome,
                    accent = ClassroomGreen,
                    accentContainer = ClassroomGreenSoft,
                    onClick = onOpenInsights
                )
                ActionCard(
                    title = "Leaderboard",
                    subtitle = "Class ranking and badges.",
                    icon = Icons.Outlined.EmojiEvents,
                    accent = ClassroomOrange,
                    accentContainer = ClassroomOrangeSoft,
                    onClick = onOpenLeaderboard
                )
                ActionCard(
                    title = "Attendance history",
                    subtitle = "Past sessions.",
                    icon = Icons.Outlined.History,
                    onClick = onOpenHistory
                )

                SectionHeader(title = "Recent activity")

                Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                    val lastSession = sessionHistory.firstOrNull() ?: activeSession
                    MetricCard(
                        label = "Last session",
                        value = lastSession?.let { TimeUtil.formatShortTime(it.createdAt) } ?: "—",
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        label = "Quiz answers",
                        value = (InMemoryStore.quizAnswers.value.values.flatten().size).toString(),
                        modifier = Modifier.weight(1f),
                        accent = ClassroomPurple,
                        softBackground = ClassroomPurpleSoft
                    )
                    MetricCard(
                        label = "Sessions",
                        value = sessionHistory.size.toString(),
                        modifier = Modifier.weight(1f),
                        accent = ClassroomGreen,
                        softBackground = ClassroomGreenSoft
                    )
                }

                if (sessionHistory.isEmpty() && activeSession == null) {
                    Text(
                        "Start your first attendance session to see activity here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.size(ClassroomSpacing.sm))
            }
        }
    }
}

@Composable
private fun HeroMetric(
    label: String,
    modifier: Modifier = Modifier,
    valueContent: @Composable () -> Unit
) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            label.uppercase(),
            color = Color.White.copy(alpha = 0.78f),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )
        valueContent()
    }
}

@Composable
private fun HeroMetricText(label: String, value: String, modifier: Modifier = Modifier) {
    HeroMetric(label = label, modifier = modifier) {
        Text(
            value,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}
