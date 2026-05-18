package com.classroom2.app.presentation.insight

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.domain.model.InsightSummary
import com.classroom2.app.presentation.components.ClassroomCard
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyState
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.quiz.QuizViewModel
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomPurple
import com.classroom2.app.ui.theme.ClassroomRed

@Composable
fun InsightDashboardScreen(
    onBack: () -> Unit,
    vm: QuizViewModel = viewModel()
) {
    val state by vm.resultsState.collectAsState()
    val insight = state.insight

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Teaching insight", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (insight == null || state.quiz == null) {
                EmptyState(
                    title = "No quiz to analyze",
                    message = "Run a quiz and the insight summary will appear here.",
                    emoji = "📈"
                )
                return@Column
            }

            HeadlineCard(insight)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricChip(
                    label = "Participation",
                    value = "${insight.participationRate}%",
                    accent = ClassroomPurple,
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    label = "Correct",
                    value = "${insight.correctPercentage}%",
                    accent = ClassroomGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            SectionHeader(title = "Students struggled most with")
            ClassroomCard {
                Text(
                    "“${insight.mostMissedAnswer.ifBlank { "—" }}”",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = ClassroomOrange
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Topic: ${insight.confusingTopic}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            SectionHeader(title = "Recommended next step")
            ClassroomCard(leadingEmoji = "💡") {
                Text(
                    insight.recommendedExplanation,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            SectionHeader(title = "Suggested follow-up question")
            ClassroomCard(leadingEmoji = "🧪") {
                Text(insight.suggestedFollowUp, style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.size(16.dp))
        }
    }
}

@Composable
private fun HeadlineCard(insight: InsightSummary) {
    val tone = when {
        insight.correctPercentage >= 80 -> ClassroomGreen
        insight.correctPercentage >= 50 -> ClassroomOrange
        else -> ClassroomRed
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "Class understanding",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                "${insight.correctPercentage}%",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = tone
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = (insight.correctPercentage / 100f).coerceIn(0f, 1f))
                        .height(8.dp)
                        .background(tone, RoundedCornerShape(8.dp))
                )
            }
            Text(
                "${insight.totalAnswers} of ${insight.expectedClassSize} students answered",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun MetricChip(
    label: String,
    value: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = accent.copy(alpha = 0.12f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(accent, CircleShape)
            )
            Column {
                Text(
                    label.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = accent,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
