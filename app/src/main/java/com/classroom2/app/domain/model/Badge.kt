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
        val SharpMind = Badge("sharp_mind", "Sharp Mind", "Got a quiz answer correct", "🧠")
        val StreakHero = Badge("streak_hero", "Streak Hero", "Three classes in a row", "🔥")
        val TopThree = Badge("top_three", "Top 3", "Top three on the leaderboard", "🏆")

        val all: List<Badge> = listOf(FirstCheckIn, QuizStarter, SharpMind, StreakHero, TopThree)
    }
}
