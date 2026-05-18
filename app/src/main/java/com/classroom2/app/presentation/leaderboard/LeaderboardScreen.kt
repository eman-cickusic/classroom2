package com.classroom2.app.presentation.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.domain.model.Badge
import com.classroom2.app.presentation.components.BadgeCard
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.LeaderboardRow
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatCard
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun LeaderboardScreen(onBack: () -> Unit) {
    val leaderboard by InMemoryStore.leaderboard.collectAsState()
    val student by InMemoryStore.currentStudent.collectAsState()
    val ranked = remember(leaderboard, student) {
        // Ensure current student is reflected from currentStudent state
        val withMe = leaderboard.toMutableList()
        val idx = withMe.indexOfFirst { it.studentId == student.id }
        if (idx >= 0) {
            withMe[idx] = withMe[idx].copy(points = student.points, streak = student.streak, badges = student.badges)
        }
        withMe.sortedByDescending { it.points }
    }
    val myRank = ranked.indexOfFirst { it.studentId == student.id } + 1

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Leaderboard", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    label = "Your rank",
                    value = if (myRank > 0) "#$myRank" else "—",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Points",
                    value = student.points.toString(),
                    modifier = Modifier.weight(1f),
                    accent = ClassroomGreen,
                    softBackground = ClassroomGreenSoft
                )
                StatCard(
                    label = "Streak",
                    value = "🔥 ${student.streak}",
                    modifier = Modifier.weight(1f),
                    accent = ClassroomOrange,
                    softBackground = ClassroomOrangeSoft
                )
            }

            SectionHeader(title = "Top of the class")
            ranked.forEachIndexed { index, entry ->
                LeaderboardRow(
                    rank = index + 1,
                    name = entry.studentName,
                    points = entry.points,
                    streak = entry.streak,
                    highlight = entry.studentId == student.id
                )
            }

            SectionHeader(title = "Badges")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Badge.all.forEach { badge ->
                    val earned = student.badges.contains(badge.title)
                    BadgeCard(
                        badge = badge,
                        earned = earned,
                        modifier = Modifier.fillMaxWidth(0.31f)
                    )
                }
            }

            Spacer(Modifier.size(16.dp))
        }
    }
}

