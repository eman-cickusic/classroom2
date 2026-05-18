package com.classroom2.app.presentation.history

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyStateCard
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing
import com.classroom2.app.util.DemoData
import com.classroom2.app.util.TimeUtil

@Composable
fun AttendanceHistoryScreen(onBack: () -> Unit) {
    val history by InMemoryStore.sessionHistory.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Attendance history", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            if (history.isEmpty()) {
                EmptyStateCard(
                    title = "No saved sessions yet",
                    message = "Your completed attendance sessions will appear here.",
                    emoji = "🗂️"
                )
                return@Column
            }

            SectionHeader(title = "Past sessions")
            history.forEachIndexed { index, session ->
                val percent = if (DemoData.EXPECTED_CLASS_SIZE > 0)
                    (session.presentCount * 100) / DemoData.EXPECTED_CLASS_SIZE else 0
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(280, delayMillis = index * 50)) +
                        slideInVertically(tween(280, delayMillis = index * 50)) { it / 8 }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = ClassroomShapes.Card,
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(ClassroomSpacing.md),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        session.classTitle,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        TimeUtil.formatDateTime(session.createdAt),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                StatusChip(
                                    label = "$percent%",
                                    accent = ClassroomGreen,
                                    softBackground = ClassroomGreenSoft,
                                    showDot = false
                                )
                            }
                            Text(
                                "${session.presentCount} of ${DemoData.EXPECTED_CLASS_SIZE} present",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}
