package com.example.neettoper.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.neettoper.features.auth.LoginScreen
import com.example.neettoper.features.auth.SignUpScreen
import com.example.neettoper.features.community.AskQuestionScreen
import com.example.neettoper.features.community.DoubtDetailScreen
import com.example.neettoper.features.main.MainScreen
import com.example.neettoper.features.onboarding.OnboardingScreen
import com.example.neettoper.features.onboarding.TopicMasteryScreen

// ... (AppRoutes object is unchanged) ...
object AppRoutes {
    const val SIGN_UP = "signup"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val TOPIC_MASTERY = "topic_mastery/{subject}"
    const val MAIN = "main_app"
    const val DOUBT_DETAIL = "doubt_detail/{doubtId}"
    const val ASK_QUESTION = "ask_question"

    fun topicMasteryScreen(subject: String) = "topic_mastery/$subject"
    fun doubtDetailScreen(doubtId: String) = "doubt_detail/$doubtId"
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SIGN_UP
    ) {
        // ... (SignUp, Login, Onboarding, TopicMastery screens are unchanged) ...
        composable(AppRoutes.SIGN_UP) {
            SignUpScreen(
                onSignUpClicked = {
                    navController.navigate(AppRoutes.ONBOARDING) {
                        popUpTo(AppRoutes.SIGN_UP) { inclusive = true }
                    }
                },
                onLoginClicked = {
                    navController.navigate(AppRoutes.LOGIN)
                }
            )
        }

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginClicked = {
                    navController.navigate(AppRoutes.MAIN) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onSignUpClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.ONBOARDING) {
            OnboardingScreen(
                onPlanCreated = { favoriteSubject, _ ->
                    navController.navigate(AppRoutes.topicMasteryScreen(favoriteSubject))
                }
            )
        }

        composable(
            route = AppRoutes.TOPIC_MASTERY,
            arguments = listOf(navArgument("subject") { type = NavType.StringType })
        ) { backStackEntry ->
            val subject = backStackEntry.arguments?.getString("subject") ?: "Physics"

            TopicMasteryScreen(
                subjectName = subject,
                onContinueClicked = { selectedTopics ->
                    navController.navigate(AppRoutes.MAIN) {
                        popUpTo(AppRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // --- THIS IS THE UPDATED PART ---
        composable(AppRoutes.MAIN) {
            MainScreen(
                onNavigateToDoubt = { doubtId ->
                    navController.navigate(AppRoutes.doubtDetailScreen(doubtId))
                },
                onAskQuestionClicked = {
                    navController.navigate(AppRoutes.ASK_QUESTION)
                },
                // Add the new logout logic
                onLogoutClicked = {
                    navController.navigate(AppRoutes.SIGN_UP) {
                        // Clear the entire app stack so the user can't go "back"
                        popUpTo(AppRoutes.MAIN) { inclusive = true }
                    }
                }
            )
        }
        // --- END OF UPDATED PART ---

        // ... (DoubtDetail and AskQuestion screens are unchanged) ...
        composable(
            route = AppRoutes.DOUBT_DETAIL,
            arguments = listOf(navArgument("doubtId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doubtId = backStackEntry.arguments?.getString("doubtId") ?: "unknown"
            DoubtDetailScreen(
                doubtId = doubtId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.ASK_QUESTION) {
            AskQuestionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPostQuestion = { subject, title, photoUri ->
                    navController.popBackStack()
                }
            )
        }
    }
}