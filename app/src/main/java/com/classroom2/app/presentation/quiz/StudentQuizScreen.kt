package com.classroom2.app.presentation.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyState
import com.classroom2.app.presentation.components.OptionCard
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SuccessCheck
import com.classroom2.app.ui.theme.ClassroomGreen

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
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val q = quiz
            if (q == null) {
                EmptyState(
                    title = "No live quiz",
                    message = "Hang tight — your professor will start one any moment.",
                    emoji = "⏳"
                )
            } else if (submission?.isSuccess == true) {
                AnswerConfirmation(correct = submission?.correct == true, totalPoints = submission?.totalPoints ?: 0)
            } else {
                Text(
                    q.question,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                q.options.forEachIndexed { index, opt ->
                    OptionCard(
                        letter = ('A' + index).toString(),
                        text = opt,
                        selected = selected == index,
                        onClick = { selected = index }
                    )
                }
                submission?.error?.let { msg ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(msg, modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
                PrimaryActionButton(
                    text = "Submit answer",
                    onClick = { selected?.let(vm::submitAnswer) },
                    enabled = selected != null
                )
            }

            Spacer(Modifier.size(16.dp))
        }
    }
}

@Composable
private fun AnswerConfirmation(correct: Boolean, totalPoints: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.size(24.dp))
        SuccessCheck()
        Text(
            if (correct) "Correct! 🎯" else "Answer submitted",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            if (correct) "+20 points (5 participation • 15 correct)"
            else "+5 participation points",
            style = MaterialTheme.typography.bodyLarge,
            color = ClassroomGreen,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            "Total: $totalPoints pts",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
