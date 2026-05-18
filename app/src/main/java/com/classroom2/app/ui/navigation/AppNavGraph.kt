package com.classroom2.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.classroom2.app.data.remote.ServiceLocator
import com.classroom2.app.domain.model.UserRole
import com.classroom2.app.presentation.ai.AIExplainerScreen
import com.classroom2.app.presentation.attendance.AttendanceSuccessScreen
import com.classroom2.app.presentation.attendance.ProfessorAttendanceScreen
import com.classroom2.app.presentation.attendance.StudentScannerScreen
import com.classroom2.app.presentation.history.AttendanceHistoryScreen
import com.classroom2.app.presentation.insight.InsightDashboardScreen
import com.classroom2.app.presentation.leaderboard.LeaderboardScreen
import com.classroom2.app.presentation.onboarding.RoleSelectionScreen
import com.classroom2.app.presentation.professor.ProfessorDashboardScreen
import com.classroom2.app.presentation.quiz.CreateQuizScreen
import com.classroom2.app.presentation.quiz.QuizResultsScreen
import com.classroom2.app.presentation.quiz.StudentQuizScreen
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
        composable(Routes.CREATE_QUIZ) {
            CreateQuizScreen(
                onBack = { navController.popBackStack() },
                onStarted = {
                    navController.navigate(Routes.QUIZ_RESULTS) {
                        popUpTo(Routes.CREATE_QUIZ) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.STUDENT_QUIZ) {
            StudentQuizScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.QUIZ_RESULTS) {
            QuizResultsScreen(
                onBack = { navController.popBackStack() },
                onOpenInsight = { navController.navigate(Routes.INSIGHT_DASHBOARD) }
            )
        }
        composable(Routes.INSIGHT_DASHBOARD) {
            InsightDashboardScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.AI_EXPLAINER) {
            AIExplainerScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.LEADERBOARD) {
            LeaderboardScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.ATTENDANCE_HISTORY) {
            AttendanceHistoryScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.POINTS) {
            LeaderboardScreen(onBack = { navController.popBackStack() })
        }
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
