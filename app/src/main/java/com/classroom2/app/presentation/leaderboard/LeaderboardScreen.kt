package com.classroom2.app.presentation.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.domain.model.Badge
import com.classroom2.app.domain.model.LeaderboardEntry
import com.classroom2.app.presentation.components.BadgeCard
import com.classroom2.app.util.TimeUtil
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.LeaderboardRow
import com.classroom2.app.presentation.components.MetricCard
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.ui.theme.ClassroomGradientEnd
import com.classroom2.app.ui.theme.ClassroomGradientStart
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun LeaderboardScreen(onBack: () -> Unit) {
    val leaderboard by InMemoryStore.leaderboard.collectAsState()
    val student by InMemoryStore.currentStudent.collectAsState()
    val ranked = remember(leaderboard, student) {
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
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                MetricCard(
                    label = "Your rank",
                    value = if (myRank > 0) "#$myRank" else "—",
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    label = "Points",
                    value = student.points.toString(),
                    modifier = Modifier.weight(1f),
                    accent = ClassroomGreen,
                    softBackground = ClassroomGreenSoft
                )
                MetricCard(
                    label = "Streak",
                    value = TimeUtil.pluralize(student.streak, "day"),
                    modifier = Modifier.weight(1f),
                    accent = ClassroomOrange,
                    softBackground = ClassroomOrangeSoft
                )
            }

            if (ranked.size >= 3) {
                SectionHeader(title = "Podium")
                PodiumCard(top3 = ranked.take(3), currentId = student.id)
            }

            SectionHeader(title = "Class ranking")
            ranked.forEachIndexed { index, entry ->
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn(
                        androidx.compose.animation.core.tween(260, delayMillis = index * 40)
                    ) + androidx.compose.animation.slideInVertically(
                        androidx.compose.animation.core.tween(260, delayMillis = index * 40)
                    ) { it / 8 }
                ) {
                    LeaderboardRow(
                        rank = index + 1,
                        name = entry.studentName,
                        points = entry.points,
                        streak = entry.streak,
                        highlight = entry.studentId == student.id
                    )
                }
            }

            SectionHeader(title = "Badges")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm),
                verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)
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

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}

@Composable
private fun PodiumCard(top3: List<LeaderboardEntry>, currentId: String) {
    // Visual order on podium: 2nd, 1st, 3rd (left to right)
    val first = top3.getOrNull(0)
    val second = top3.getOrNull(1)
    val third = top3.getOrNull(2)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = ClassroomShapes.LargeCard,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(listOf(ClassroomGradientStart, ClassroomGradientEnd)),
                    shape = ClassroomShapes.LargeCard
                )
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (second != null) PodiumColumn(rank = 2, height = 78.dp, entry = second, currentId = currentId)
                if (first != null) PodiumColumn(rank = 1, height = 108.dp, entry = first, currentId = currentId)
                if (third != null) PodiumColumn(rank = 3, height = 60.dp, entry = third, currentId = currentId)
            }
        }
    }
}

@Composable
private fun PodiumColumn(rank: Int, height: androidx.compose.ui.unit.Dp, entry: LeaderboardEntry, currentId: String) {
    val medal = when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        else -> "🥉"
    }
    val isMe = entry.studentId == currentId

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.width(96.dp)
    ) {
        Text(medal, style = MaterialTheme.typography.headlineMedium)
        Text(
            entry.studentName + if (isMe) " (you)" else "",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = if (isMe) FontWeight.Bold else FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            "${entry.points} pts",
            color = Color.White.copy(alpha = 0.9f),
            style = MaterialTheme.typography.labelMedium
        )
        Box(
            modifier = Modifier
                .height(height)
                .fillMaxWidth(0.72f)
                .background(
                    color = if (isMe) Color.White else Color.White.copy(alpha = 0.32f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "#$rank",
                color = if (isMe) ClassroomGradientStart else Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
