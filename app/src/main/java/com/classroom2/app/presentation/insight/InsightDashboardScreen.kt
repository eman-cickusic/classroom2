package com.classroom2.app.presentation.insight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.domain.model.InsightSummary
import com.classroom2.app.presentation.components.AnimatedCounter
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyStateCard
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.presentation.quiz.QuizViewModel
import com.classroom2.app.ui.icons.ClassroomIcons
import com.classroom2.app.ui.theme.ClassroomGradientEnd
import com.classroom2.app.ui.theme.ClassroomGradientStart
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomPurple
import com.classroom2.app.ui.theme.ClassroomPurpleSoft
import com.classroom2.app.ui.theme.ClassroomRed
import com.classroom2.app.ui.theme.ClassroomRedSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun InsightDashboardScreen(
    onBack: () -> Unit,
    vm: QuizViewModel = viewModel()
) {
    val state by vm.resultsState.collectAsState()
    val insight = state.insight

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Teaching insight", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            if (insight == null || state.quiz == null) {
                EmptyStateCard(
                    title = "No quiz to analyze",
                    message = "Run a live quiz to see the teaching insight here.",
                    icon = ClassroomIcons.insight
                )
                return@Column
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusChip(
                    label = "Teaching insight",
                    accent = ClassroomPurple,
                    softBackground = ClassroomPurpleSoft
                )
                StatusChip(
                    label = "${insight.totalAnswers} answers",
                    accent = ClassroomGreen,
                    softBackground = ClassroomGreenSoft,
                    showDot = false
                )
            }

            AnimatedVisibility(visible, enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 8 }) {
                HeadlineCard(insight)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                AnimatedMetricChip(
                    label = "Participation",
                    value = insight.participationRate,
                    accent = ClassroomPurple,
                    soft = ClassroomPurpleSoft,
                    modifier = Modifier.weight(1f)
                )
                AnimatedMetricChip(
                    label = "Correct",
                    value = insight.correctPercentage,
                    accent = ClassroomGreen,
                    soft = ClassroomGreenSoft,
                    modifier = Modifier.weight(1f)
                )
            }

            SectionHeader(title = "Students struggled most with")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = ClassroomShapes.Card,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(ClassroomSpacing.md), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "“${insight.mostMissedAnswer.ifBlank { "—" }}”",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = ClassroomOrange
                    )
                    Text(
                        "Topic: ${insight.confusingTopic}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            SectionHeader(title = "Recommended next step")
            AIRecommendationCard(text = insight.recommendedExplanation)

            SectionHeader(title = "Suggested follow-up question")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = ClassroomShapes.Card,
                color = ClassroomGreenSoft
            ) {
                Row(modifier = Modifier.padding(ClassroomSpacing.md), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                    Icon(
                        imageVector = ClassroomIcons.lightbulb,
                        contentDescription = null,
                        tint = ClassroomGreen,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(insight.suggestedFollowUp, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(Modifier.size(ClassroomSpacing.md))
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
    val toneSoft = when {
        insight.correctPercentage >= 80 -> ClassroomGreenSoft
        insight.correctPercentage >= 50 -> ClassroomOrangeSoft
        else -> ClassroomRedSoft
    }
    val pct by animateFloatAsState(
        targetValue = (insight.correctPercentage / 100f).coerceIn(0f, 1f),
        animationSpec = tween(600),
        label = "understanding-bar"
    )
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
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Text(
                        "Class understanding",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AnimatedCounter(
                        value = insight.correctPercentage,
                        style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text("%", style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.SemiBold)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = pct)
                            .height(10.dp)
                            .background(toneSoft, RoundedCornerShape(8.dp))
                    )
                }
                Text(
                    "${insight.totalAnswers} of ${insight.expectedClassSize} students answered",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodyMedium
                )
                // tone marker pill
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(modifier = Modifier.size(10.dp).background(tone, CircleShape))
                    Text(
                        when {
                            insight.correctPercentage >= 80 -> "Strong understanding"
                            insight.correctPercentage >= 50 -> "Mixed understanding"
                            else -> "Re-teach recommended"
                        },
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedMetricChip(
    label: String,
    value: Int,
    accent: Color,
    soft: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = ClassroomShapes.Card,
        color = soft
    ) {
        Row(
            modifier = Modifier.padding(horizontal = ClassroomSpacing.md, vertical = ClassroomSpacing.sm + 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm + 4.dp)
        ) {
            Box(modifier = Modifier.size(10.dp).background(accent, CircleShape))
            Column {
                Text(
                    label.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = accent,
                    fontWeight = FontWeight.SemiBold
                )
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    AnimatedCounter(
                        value = value,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text("%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = accent)
                }
            }
        }
    }
}

@Composable
private fun AIRecommendationCard(text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = ClassroomShapes.LargeCard,
        color = ClassroomPurpleSoft
    ) {
        Column(modifier = Modifier.padding(ClassroomSpacing.md), verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(ClassroomPurple, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
                Text(
                    "AI suggestion",
                    style = MaterialTheme.typography.titleSmall,
                    color = ClassroomPurple,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
