package com.classroom2.app.domain.model

data class InsightSummary(
    val quizId: String = "",
    val question: String = "",
    val totalAnswers: Int = 0,
    val expectedClassSize: Int = 0,
    val participationRate: Int = 0,
    val correctPercentage: Int = 0,
    val mostSelectedAnswer: String = "",
    val mostMissedAnswer: String = "",
    val confusingTopic: String = "",
    val recommendedExplanation: String = "",
    val suggestedFollowUp: String = ""
)
