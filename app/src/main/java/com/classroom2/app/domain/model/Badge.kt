package com.classroom2.app.domain.model

/**
 * Achievement metadata. The [emoji] field is retained for backwards compatibility
 * with any old persisted state but is no longer rendered — screens look up the
 * matching [androidx.compose.ui.graphics.vector.ImageVector] via
 * `com.classroom2.app.ui.icons.ClassroomIcons.badge(this)`.
 */
data class Badge(
    val id: String,
    val title: String,
    val description: String,
    @Deprecated("Use ClassroomIcons.badge(this) instead.") val emoji: String = ""
) {
    companion object {
        val FirstCheckIn = Badge("first_check_in", "First Check-In", "First attendance scan")
        val QuizStarter = Badge("quiz_starter", "Quiz Starter", "Answered your first quiz")
        val QuickThinker = Badge("quick_thinker", "Quick Thinker", "Answered in under 10 seconds")
        val PerfectAnswer = Badge("perfect_answer", "Perfect Answer", "Got a quiz answer correct")
        val StreakBuilder = Badge("streak_builder", "Streak Builder", "Three classes in a row")
        val TopThree = Badge("top_three", "Top 3", "Top three on the leaderboard")

        val all: List<Badge> = listOf(FirstCheckIn, QuizStarter, QuickThinker, PerfectAnswer, StreakBuilder, TopThree)

        @Deprecated("Use ClassroomIcons.badgeByTitle(title)")
        fun emojiFor(title: String): String = ""
    }
}
