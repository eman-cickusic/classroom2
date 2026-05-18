package com.classroom2.app.presentation.professor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.presentation.components.ClassroomCard
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatCard
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomPurple
import com.classroom2.app.ui.theme.ClassroomPurpleSoft

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

    val presentCount = activeSession?.let { attendance[it.id]?.size } ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Good morning, Professor",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    professor.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Computer Science 101",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            SecondaryActionButton(
                text = "🎒 Student view",
                onClick = onSwitchRole,
                modifier = Modifier.fillMaxWidth(0.4f)
            )
        }

        Text(
            "Ready to make today's class interactive?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                label = "Present",
                value = presentCount.toString(),
                modifier = Modifier.weight(1f),
                accent = ClassroomGreen,
                softBackground = ClassroomGreenSoft
            )
            StatCard(
                label = "Active quiz",
                value = if (activeQuiz != null) "Live" else "—",
                modifier = Modifier.weight(1f),
                accent = ClassroomOrange,
                softBackground = ClassroomOrangeSoft
            )
            StatCard(
                label = "Engagement",
                value = "92%",
                modifier = Modifier.weight(1f),
                accent = ClassroomPurple,
                softBackground = ClassroomPurpleSoft
            )
        }

        Spacer(Modifier.size(4.dp))
        SectionHeader(title = "Quick actions")

        ClassroomCard(
            title = "Start attendance",
            subtitle = if (activeSession != null) "Session live — tap to view QR"
                       else "Generate a QR for students to scan",
            leadingEmoji = "🟢",
            onClick = onStartAttendance
        )
        ClassroomCard(
            title = "Start live quiz",
            subtitle = if (activeQuiz != null) "Quiz active — view answers"
                       else "Ask one quick question, see live answers",
            leadingEmoji = "❓",
            onClick = onStartQuiz
        )
        ClassroomCard(
            title = "View insights",
            subtitle = "Class understanding + recommended next step",
            leadingEmoji = "📊",
            onClick = onOpenInsights
        )
        ClassroomCard(
            title = "Leaderboard",
            subtitle = "See who's leading the class",
            leadingEmoji = "🏆",
            onClick = onOpenLeaderboard
        )
        ClassroomCard(
            title = "Attendance history",
            subtitle = "Past sessions saved here",
            leadingEmoji = "🗂️",
            onClick = onOpenHistory
        )
    }
}
