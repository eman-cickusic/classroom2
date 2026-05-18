package com.classroom2.app.data.repository

import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.domain.model.Badge
import com.classroom2.app.domain.model.LeaderboardEntry
import com.classroom2.app.domain.model.User
import kotlinx.coroutines.flow.Flow

class GamificationRepository {

    companion object {
        const val POINTS_ATTENDANCE = 10
        const val POINTS_QUIZ_PARTICIPATION = 5
        const val POINTS_CORRECT_ANSWER = 15
        const val POINTS_STREAK_BONUS = 5
    }

    fun observeLeaderboard(): Flow<List<LeaderboardEntry>> = InMemoryStore.leaderboard

    fun observeCurrentStudent(): Flow<User> = InMemoryStore.currentStudent

    fun awardAttendance(student: User): User {
        val newPoints = student.points + POINTS_ATTENDANCE + POINTS_STREAK_BONUS
        val newBadges = student.badges.toMutableList().also {
            if (Badge.FirstCheckIn.title !in it) it += Badge.FirstCheckIn.title
        }
        val updated = student.copy(points = newPoints, streak = student.streak + 1, badges = newBadges)
        commit(updated)
        return updated
    }

    fun awardQuizAnswer(student: User, correct: Boolean): User {
        var added = POINTS_QUIZ_PARTICIPATION
        if (correct) added += POINTS_CORRECT_ANSWER
        val newBadges = student.badges.toMutableList().also {
            if (Badge.QuizStarter.title !in it) it += Badge.QuizStarter.title
            if (correct && Badge.SharpMind.title !in it) it += Badge.SharpMind.title
        }
        val updated = student.copy(points = student.points + added, badges = newBadges)
        commit(updated)
        return updated
    }

    private fun commit(updated: User) {
        InMemoryStore.currentStudent.value = updated
        val current = InMemoryStore.leaderboard.value.toMutableList()
        val idx = current.indexOfFirst { it.studentId == updated.id }
        val entry = LeaderboardEntry(
            studentId = updated.id,
            studentName = updated.name,
            points = updated.points,
            streak = updated.streak,
            badges = updated.badges
        )
        if (idx >= 0) current[idx] = entry else current += entry
        InMemoryStore.leaderboard.value = current.sortedByDescending { it.points }
    }
}
