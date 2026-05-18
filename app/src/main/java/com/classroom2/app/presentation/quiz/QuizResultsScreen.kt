package com.classroom2.app.presentation.quiz

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.ClassroomCard
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyState
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatCard
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft

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
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (quiz == null) {
                EmptyState(
                    title = "No quiz yet",
                    message = "Start a quiz to see live results here.",
                    emoji = "📊"
                )
                return@Column
            }

            Text(quiz.question, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    label = "Answers",
                    value = state.totalAnswers.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Correct",
                    value = "${state.correctPercentage}%",
                    modifier = Modifier.weight(1f),
                    accent = ClassroomGreen,
                    softBackground = ClassroomGreenSoft
                )
                StatCard(
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
                ClassroomCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${('A' + index)}. $opt",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Medium,
                            color = if (isCorrect) ClassroomGreen else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "$count",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.size(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction = count / maxCount.toFloat())
                                .height(10.dp)
                                .background(
                                    if (isCorrect) ClassroomGreen else MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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

            Spacer(Modifier.size(16.dp))
        }
    }
}

