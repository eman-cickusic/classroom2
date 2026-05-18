package com.classroom2.app.presentation.student

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
import com.classroom2.app.presentation.components.ClassroomCard
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatCard
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft

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
                    "Hi there 👋",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    student.name,
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
                text = "👩‍🏫 Pro view",
                onClick = onSwitchRole,
                modifier = Modifier.fillMaxWidth(0.4f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                label = "Points",
                value = student.points.toString(),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Streak",
                value = "🔥 ${student.streak}",
                modifier = Modifier.weight(1f),
                accent = ClassroomOrange,
                softBackground = ClassroomOrangeSoft
            )
            StatCard(
                label = "Badges",
                value = student.badges.size.toString(),
                modifier = Modifier.weight(1f),
                accent = ClassroomGreen,
                softBackground = ClassroomGreenSoft
            )
        }

        Spacer(Modifier.size(4.dp))
        SectionHeader(title = "Next action")

        ClassroomCard(
            title = "Scan attendance QR",
            subtitle = if (activeSession != null) "Session live — tap to check in"
                       else "Open scanner when professor starts attendance",
            leadingEmoji = "📷",
            onClick = onScan
        )
        ClassroomCard(
            title = "Join live quiz",
            subtitle = if (activeQuiz != null) "Quiz is open right now"
                       else "Wait for professor to start a quiz",
            leadingEmoji = "🧠",
            onClick = onJoinQuiz
        )
        ClassroomCard(
            title = "Ask AI explainer",
            subtitle = "Get a clearer explanation of any concept",
            leadingEmoji = "🤖",
            onClick = onAIExplainer
        )
        ClassroomCard(
            title = "Leaderboard",
            subtitle = "See how you rank in the class",
            leadingEmoji = "🏆",
            onClick = onLeaderboard
        )
    }
}
