package com.classroom2.app.domain.model

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String
) {
    companion object {
        val FirstCheckIn = Badge("first_check_in", "First Check-In", "First attendance scan", "🔑")
        val QuizStarter = Badge("quiz_starter", "Quiz Starter", "Answered your first quiz", "🎯")
        val QuickThinker = Badge("quick_thinker", "Quick Thinker", "Answered in under 10 seconds", "⚡")
        val PerfectAnswer = Badge("perfect_answer", "Perfect Answer", "Got a quiz answer correct", "💯")
        val StreakBuilder = Badge("streak_builder", "Streak Builder", "Three classes in a row", "🔥")
        val TopThree = Badge("top_three", "Top 3", "Top three on the leaderboard", "🏆")

        val all: List<Badge> = listOf(FirstCheckIn, QuizStarter, QuickThinker, PerfectAnswer, StreakBuilder, TopThree)

        fun emojiFor(title: String): String = all.firstOrNull { it.title == title }?.emoji ?: "🏅"
    }
}
