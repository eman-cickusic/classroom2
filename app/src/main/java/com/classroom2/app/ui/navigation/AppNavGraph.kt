package com.classroom2.app.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.domain.model.UserRole
import com.classroom2.app.presentation.attendance.AttendanceSuccessScreen
import com.classroom2.app.presentation.attendance.ProfessorAttendanceScreen
import com.classroom2.app.presentation.attendance.StudentScannerScreen
import com.classroom2.app.presentation.onboarding.RoleSelectionScreen
import com.classroom2.app.presentation.professor.ProfessorDashboardScreen
import com.classroom2.app.presentation.student.StudentDashboardScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.ROLE_SELECTION
    ) {
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(onContinue = { role ->
                ServiceLocator.auth.selectRole(role)
                navController.navigate(role.dashboardRoute()) {
                    popUpTo(Routes.ROLE_SELECTION) { inclusive = true }
                }
            })
        }

        composable(Routes.PROFESSOR_DASHBOARD) {
            ProfessorDashboardScreen(
                onStartAttendance = { navController.navigate(Routes.PROFESSOR_ATTENDANCE) },
                onStartQuiz = { navController.navigate(Routes.CREATE_QUIZ) },
                onOpenInsights = { navController.navigate(Routes.INSIGHT_DASHBOARD) },
                onOpenLeaderboard = { navController.navigate(Routes.LEADERBOARD) },
                onOpenHistory = { navController.navigate(Routes.ATTENDANCE_HISTORY) },
                onSwitchRole = { navController.switchTo(UserRole.STUDENT) }
            )
        }

        composable(Routes.STUDENT_DASHBOARD) {
            StudentDashboardScreen(
                onScan = { navController.navigate(Routes.STUDENT_SCANNER) },
                onJoinQuiz = { navController.navigate(Routes.STUDENT_QUIZ) },
                onAIExplainer = { navController.navigate(Routes.AI_EXPLAINER) },
                onLeaderboard = { navController.navigate(Routes.LEADERBOARD) },
                onSwitchRole = { navController.switchTo(UserRole.PROFESSOR) }
            )
        }

        composable(Routes.PROFESSOR_ATTENDANCE) {
            ProfessorAttendanceScreen(
                onBack = { navController.popBackStack() },
                onEnded = { navController.popBackStack() }
            )
        }
        composable(Routes.STUDENT_SCANNER) {
            StudentScannerScreen(
                onBack = { navController.popBackStack() },
                onScanned = {
                    navController.navigate(Routes.ATTENDANCE_SUCCESS) {
                        popUpTo(Routes.STUDENT_SCANNER) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ATTENDANCE_SUCCESS) {
            AttendanceSuccessScreen(
                onBackToDashboard = {
                    navController.navigate(Routes.STUDENT_DASHBOARD) {
                        popUpTo(Routes.STUDENT_DASHBOARD) { inclusive = true }
                    }
                }
            )
        }
        comingSoon(Routes.CREATE_QUIZ, "Create quiz")
        comingSoon(Routes.STUDENT_QUIZ, "Student quiz")
        comingSoon(Routes.QUIZ_RESULTS, "Quiz results")
        comingSoon(Routes.INSIGHT_DASHBOARD, "Insight dashboard")
        comingSoon(Routes.AI_EXPLAINER, "AI explainer")
        comingSoon(Routes.LEADERBOARD, "Leaderboard")
        comingSoon(Routes.ATTENDANCE_HISTORY, "Attendance history")
        comingSoon(Routes.POINTS, "Points")
    }
}

private fun UserRole.dashboardRoute(): String = when (this) {
    UserRole.PROFESSOR -> Routes.PROFESSOR_DASHBOARD
    UserRole.STUDENT -> Routes.STUDENT_DASHBOARD
}

private fun NavHostController.switchTo(role: UserRole) {
    ServiceLocator.auth.selectRole(role)
    val target = role.dashboardRoute()
    navigate(target) {
        popUpTo(graph.startDestinationId) { inclusive = false }
        launchSingleTop = true
    }
}

private fun androidx.navigation.NavGraphBuilder.comingSoon(route: String, label: String) {
    composable(route) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(label, style = MaterialTheme.typography.headlineMedium)
                Text("Wired in a later phase.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
