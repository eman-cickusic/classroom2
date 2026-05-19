package com.classroom2.app.presentation.quiz

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.OptionCard
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.icons.ClassroomIcons
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@Composable
fun CreateQuizScreen(
    onBack: () -> Unit,
    onStarted: () -> Unit,
    vm: QuizViewModel = viewModel()
) {
    val state by vm.createState.collectAsState()
    val activeQuiz by vm.activeQuiz.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Start a live quiz", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            if (activeQuiz != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = ClassroomShapes.Card,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Column(modifier = Modifier.padding(ClassroomSpacing.md), verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                        Row {
                            StatusChip(
                                label = "Quiz in progress",
                                accent = ClassroomOrange,
                                softBackground = ClassroomOrangeSoft
                            )
                        }
                        Text(
                            "A quiz is already running",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "End it from the results screen before starting a new one.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        SecondaryActionButton(text = "View results", onClick = onStarted)
                    }
                }
            }

            SectionHeader(title = "Question")
            OutlinedTextField(
                value = state.question,
                onValueChange = vm::onQuestionChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. What does polymorphism mean in programming?") },
                minLines = 2,
                shape = ClassroomShapes.Card
            )

            SectionHeader(title = "Options")
            Text(
                "Type each option, then tap one to mark the correct answer.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            state.options.forEachIndexed { index, value ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = { vm.onOptionChange(index, it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Option ${letterFor(index)}") },
                        shape = ClassroomShapes.Card
                    )
                    OptionCard(
                        letter = letterFor(index),
                        text = if (value.isBlank()) "Tap to mark as correct" else value,
                        selected = state.correctIndex == index,
                        onClick = { vm.onCorrectIndexChange(index) }
                    )
                }
            }

            state.errorMessage?.let { msg ->
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

            SecondaryActionButton(
                text = "Use demo question",
                icon = ClassroomIcons.sparkle,
                onClick = vm::useDemoQuestion
            )
            PrimaryActionButton(
                text = if (state.isSubmitting) "Starting…" else "Start quiz",
                onClick = { vm.startQuiz(onStarted) },
                enabled = state.canStart && !state.isSubmitting && activeQuiz == null
            )

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}

private fun letterFor(index: Int): String = ('A' + index).toString()
