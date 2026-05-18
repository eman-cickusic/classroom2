package com.classroom2.app.data.repository

import com.classroom2.app.domain.model.InsightSummary
import com.classroom2.app.domain.model.Quiz
import com.classroom2.app.domain.model.QuizAnswer
import com.classroom2.app.util.DemoData
import kotlin.math.roundToInt

class InsightRepository {

    fun generate(
        quiz: Quiz,
        answers: List<QuizAnswer>,
        expectedClassSize: Int = DemoData.EXPECTED_CLASS_SIZE
    ): InsightSummary {
        if (quiz.options.isEmpty()) return InsightSummary(quizId = quiz.id, question = quiz.question)

        val total = answers.size
        val participation = if (expectedClassSize > 0)
            ((total.toFloat() / expectedClassSize) * 100).roundToInt().coerceIn(0, 100)
        else 0

        val correctCount = answers.count { it.correct }
        val correctPct = if (total > 0) ((correctCount.toFloat() / total) * 100).roundToInt() else 0

        val distribution = IntArray(quiz.options.size)
        answers.forEach { a ->
            if (a.selectedIndex in quiz.options.indices) distribution[a.selectedIndex]++
        }
        val mostSelectedIndex = distribution.indices.maxByOrNull { distribution[it] } ?: 0
        val mostSelected = quiz.options.getOrNull(mostSelectedIndex).orEmpty()

        val wrongIndices = quiz.options.indices.filter { it != quiz.correctAnswerIndex }
        val mostMissedIndex = wrongIndices.maxByOrNull { distribution.getOrElse(it) { 0 } } ?: 0
        val mostMissed = quiz.options.getOrNull(mostMissedIndex).orEmpty()

        val confusingTopic = extractTopic(quiz.question)

        val recommendation = when {
            total == 0 -> "Waiting for answers — share the quiz and results appear here live."
            correctPct >= 80 ->
                "Most students understood this concept. Continue with a harder follow-up question."
            correctPct in 50..79 ->
                "Some confusion exists. Give one concrete example and ask a simpler follow-up."
            else ->
                "Pause and reteach the core concept using a visual example before moving on."
        }

        val followUp = when {
            correctPct >= 80 -> "Try a deeper variant: \"When does $confusingTopic break down?\""
            correctPct in 50..79 -> "Ask: \"Can someone give a real-world example of $confusingTopic?\""
            else -> "Reteach using a 30-second analogy, then ask the same question again."
        }

        return InsightSummary(
            quizId = quiz.id,
            question = quiz.question,
            totalAnswers = total,
            expectedClassSize = expectedClassSize,
            participationRate = participation,
            correctPercentage = correctPct,
            mostSelectedAnswer = mostSelected,
            mostMissedAnswer = mostMissed,
            confusingTopic = confusingTopic,
            recommendedExplanation = recommendation,
            suggestedFollowUp = followUp
        )
    }

    private fun extractTopic(question: String): String {
        val stop = setOf(
            "what", "does", "is", "are", "the", "a", "an", "of", "in", "to", "do",
            "how", "why", "which", "and", "or", "for", "with", "on", "by", "be",
            "this", "that", "mean", "means", "programming"
        )
        val words = question
            .lowercase()
            .replace("[^a-z0-9 ]".toRegex(), " ")
            .split(" ")
            .filter { it.isNotBlank() && it !in stop && it.length > 3 }
        return words.firstOrNull()?.replaceFirstChar { it.uppercase() } ?: "this concept"
    }
}
