package com.classroom2.app.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.presentation.components.ClassroomCard
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyState
import com.classroom2.app.presentation.components.SectionHeader
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
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (history.isEmpty()) {
                EmptyState(
                    title = "No saved sessions",
                    message = "Your completed attendance sessions will appear here.",
                    emoji = "🗂️"
                )
                return@Column
            }

            SectionHeader(title = "Past sessions")
            history.forEach { session ->
                val percent = if (DemoData.EXPECTED_CLASS_SIZE > 0)
                    (session.presentCount * 100) / DemoData.EXPECTED_CLASS_SIZE else 0
                ClassroomCard(
                    title = session.classTitle,
                    subtitle = TimeUtil.formatDateTime(session.createdAt),
                    leadingEmoji = "📅"
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${session.presentCount} of ${DemoData.EXPECTED_CLASS_SIZE} present",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "$percent% attendance",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
