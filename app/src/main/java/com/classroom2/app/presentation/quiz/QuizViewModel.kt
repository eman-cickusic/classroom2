package com.classroom2.app.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.data.remote.QuizSubmissionState
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.domain.model.InsightSummary
import com.classroom2.app.domain.model.Quiz
import com.classroom2.app.domain.model.QuizAnswer
import com.classroom2.app.util.AppResult
import com.classroom2.app.util.DemoData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CreateQuizUiState(
    val question: String = "",
    val options: List<String> = listOf("", "", "", ""),
    val correctIndex: Int = 0,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
) {
    val canStart: Boolean
        get() = question.isNotBlank() &&
            options.all { it.isNotBlank() } &&
            correctIndex in options.indices
}

data class QuizResultsUiState(
    val quiz: Quiz? = null,
    val answers: List<QuizAnswer> = emptyList(),
    val insight: InsightSummary? = null
) {
    val totalAnswers: Int get() = answers.size
    val correctCount: Int get() = answers.count { it.correct }
    val correctPercentage: Int
        get() = if (answers.isNotEmpty()) ((correctCount * 100f) / answers.size).toInt() else 0
}

class QuizViewModel : ViewModel() {

    private val quizRepo = ServiceLocator.quiz
    private val authRepo = ServiceLocator.auth
    private val gamification = ServiceLocator.gamification
    private val insightRepo = ServiceLocator.insight

    private val _createState = kotlinx.coroutines.flow.MutableStateFlow(CreateQuizUiState())
    val createState: StateFlow<CreateQuizUiState> = _createState

    val activeQuiz: StateFlow<Quiz?> =
        quizRepo.observeActiveQuiz()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val answers: StateFlow<List<QuizAnswer>> =
        activeQuiz
            .flatMapLatest { q ->
                if (q == null) flowOf(emptyList()) else quizRepo.observeAnswers(q.id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val resultsState: StateFlow<QuizResultsUiState> =
        combine(activeQuiz, answers) { q, a ->
            QuizResultsUiState(
                quiz = q,
                answers = a,
                insight = if (q != null) insightRepo.generate(q, a) else null
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), QuizResultsUiState())

    val submission: StateFlow<QuizSubmissionState?> = InMemoryStore.lastQuizOutcome

    /* ---------- Create flow ---------- */

    fun onQuestionChange(value: String) {
        _createState.value = _createState.value.copy(question = value, errorMessage = null)
    }

    fun onOptionChange(index: Int, value: String) {
        val current = _createState.value.options.toMutableList()
        if (index in current.indices) {
            current[index] = value
            _createState.value = _createState.value.copy(options = current, errorMessage = null)
        }
    }

    fun onCorrectIndexChange(index: Int) {
        _createState.value = _createState.value.copy(correctIndex = index)
    }

    fun useDemoQuestion() {
        _createState.value = CreateQuizUiState(
            question = DemoData.demoQuiz.question,
            options = DemoData.demoQuiz.options,
            correctIndex = DemoData.demoQuiz.correctAnswerIndex
        )
    }

    fun startQuiz(onStarted: () -> Unit) {
        val state = _createState.value
        if (!state.canStart) {
            _createState.value = state.copy(errorMessage = "Fill the question and all four options.")
            return
        }
        viewModelScope.launch {
            _createState.value = state.copy(isSubmitting = true)
            quizRepo.createQuiz(
                Quiz(
                    professorId = authRepo.professor().id,
                    question = state.question.trim(),
                    options = state.options.map { it.trim() },
                    correctAnswerIndex = state.correctIndex,
                    active = true
                )
            )
            _createState.value = CreateQuizUiState()
            onStarted()
        }
    }

    /* ---------- Student flow ---------- */

    fun submitAnswer(selectedIndex: Int) {
        val q = activeQuiz.value ?: return
        viewModelScope.launch {
            val student = authRepo.student()
            when (val result = quizRepo.submitAnswer(q, student, selectedIndex)) {
                is AppResult.Success -> {
                    val updated = gamification.awardQuizAnswer(student, result.data.correct)
                    InMemoryStore.lastQuizOutcome.value = QuizSubmissionState(
                        quizId = q.id,
                        correct = result.data.correct,
                        totalPoints = updated.points
                    )
                }
                is AppResult.Error ->
                    InMemoryStore.lastQuizOutcome.value =
                        QuizSubmissionState(error = result.message)
                AppResult.Loading -> Unit
            }
        }
    }

    fun consumeSubmission() { InMemoryStore.lastQuizOutcome.value = null }

    /* ---------- Results / end ---------- */

    fun endActiveQuiz() {
        val q = activeQuiz.value ?: return
        viewModelScope.launch { quizRepo.endQuiz(q.id) }
    }
}
