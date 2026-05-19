package com.classroom2.app.presentation.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.BadgePill
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyStateCard
import com.classroom2.app.presentation.components.OptionCard
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.presentation.components.SuccessCheck
import com.classroom2.app.ui.icons.ClassroomIcons
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun StudentQuizScreen(
    onBack: () -> Unit,
    vm: QuizViewModel = viewModel()
) {
    val quiz by vm.activeQuiz.collectAsState()
    val submission by vm.submission.collectAsState()
    var selected by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Live quiz", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            val q = quiz
            when {
                q == null -> EmptyStateCard(
                    title = "No live quiz",
                    message = "Waiting for the next live quiz.",
                    icon = ClassroomIcons.queryStats
                )

                submission?.isSuccess == true ->
                    AnswerConfirmation(
                        correct = submission?.correct == true,
                        totalPoints = submission?.totalPoints ?: 0
                    )

                else -> {
                    StatusChip(
                        label = "Live, one answer only",
                        accent = ClassroomOrange,
                        softBackground = ClassroomOrangeSoft
                    )
                    Text(
                        q.question,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                        q.options.forEachIndexed { index, opt ->
                            OptionCard(
                                letter = ('A' + index).toString(),
                                text = opt,
                                selected = selected == index,
                                onClick = { selected = index }
                            )
                        }
                    }
                    submission?.error?.let { msg ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = ClassroomShapes.Card,
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                msg,
                                modifier = Modifier.padding(ClassroomSpacing.md),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                    PrimaryActionButton(
                        text = "Submit answer",
                        onClick = { selected?.let(vm::submitAnswer) },
                        enabled = selected != null
                    )
                }
            }

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}

@Composable
private fun AnswerConfirmation(correct: Boolean, totalPoints: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
    ) {
        Spacer(Modifier.size(ClassroomSpacing.lg))
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(300)) + scaleIn(tween(300), initialScale = 0.7f)
        ) {
            SuccessCheck()
        }
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(350, delayMillis = 120)) + slideInVertically(tween(350, delayMillis = 120)) { it / 6 }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    if (correct) "Correct" else "Answer submitted",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BadgePill(
                        icon = ClassroomIcons.points,
                        label = if (correct) "+20 pts" else "+5 pts",
                        accent = ClassroomGreen,
                        softBackground = ClassroomGreenSoft
                    )
                    BadgePill(
                        icon = ClassroomIcons.trophy,
                        label = "$totalPoints total"
                    )
                }
            }
        }
    }
}
