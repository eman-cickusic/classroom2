package com.classroom2.app.presentation.quiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.AnimatedCounter
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyStateCard
import com.classroom2.app.presentation.components.MetricCard
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.icons.ClassroomIcons
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun QuizResultsScreen(
    onBack: () -> Unit,
    onOpenInsight: () -> Unit,
    vm: QuizViewModel = viewModel()
) {
    val state by vm.resultsState.collectAsState()
    val quiz = state.quiz

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Live quiz results", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            if (quiz == null) {
                EmptyStateCard(
                    title = "No quiz to summarize yet",
                    message = "Start a quiz to see live results here.",
                    icon = ClassroomIcons.barChart
                )
                return@Column
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)
            ) {
                StatusChip(
                    label = "Live results",
                    accent = ClassroomOrange,
                    softBackground = ClassroomOrangeSoft
                )
                StatusChip(
                    label = "${state.totalAnswers} answered",
                    accent = ClassroomGreen,
                    softBackground = ClassroomGreenSoft,
                    showDot = false
                )
            }

            Text(
                quiz.question,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = ClassroomShapes.LargeCard,
                color = ClassroomGreenSoft
            ) {
                Row(
                    modifier = Modifier.padding(ClassroomSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            "CORRECT",
                            style = MaterialTheme.typography.labelMedium,
                            color = ClassroomGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            AnimatedCounter(
                                value = state.correctPercentage,
                                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                                color = ClassroomGreen
                            )
                            Text("%", style = MaterialTheme.typography.headlineSmall, color = ClassroomGreen, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            "${state.correctCount} of ${state.totalAnswers}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "answered correctly",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                MetricCard(
                    label = "Answers",
                    value = state.totalAnswers.toString(),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    label = "Wrong",
                    value = "${(100 - state.correctPercentage).coerceAtLeast(0)}%",
                    modifier = Modifier.weight(1f),
                    accent = ClassroomOrange,
                    softBackground = ClassroomOrangeSoft
                )
            }

            SectionHeader(title = "Answer distribution")

            val distribution = IntArray(quiz.options.size)
            state.answers.forEach {
                if (it.selectedIndex in quiz.options.indices) distribution[it.selectedIndex]++
            }
            val maxCount = (distribution.maxOrNull() ?: 0).coerceAtLeast(1)

            quiz.options.forEachIndexed { index, opt ->
                val count = distribution[index]
                val isCorrect = index == quiz.correctAnswerIndex
                val target = (count.toFloat() / maxCount).coerceIn(0f, 1f)
                val animated by animateFloatAsState(
                    targetValue = target,
                    animationSpec = tween(durationMillis = 600),
                    label = "bar-$index"
                )
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = ClassroomShapes.Card,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(ClassroomSpacing.md), verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                                Text(
                                    "${('A' + index)}. $opt",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isCorrect) ClassroomGreen else MaterialTheme.colorScheme.onSurface
                                )
                                if (isCorrect) {
                                    StatusChip(
                                        label = "Correct",
                                        accent = ClassroomGreen,
                                        softBackground = ClassroomGreenSoft,
                                        showDot = false
                                    )
                                }
                            }
                            Text(
                                "$count",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) ClassroomGreen else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = animated)
                                    .height(12.dp)
                                    .background(
                                        if (isCorrect) ClassroomGreen else MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                SecondaryActionButton(
                    text = "End quiz",
                    onClick = { vm.endActiveQuiz() },
                    modifier = Modifier.weight(1f)
                )
                PrimaryActionButton(
                    text = "View insight",
                    onClick = onOpenInsight,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}
