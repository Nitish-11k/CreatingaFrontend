package com.example.neettoper.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.neettoper.features.community.CommunityFeedScreen
import com.example.neettoper.features.dashboard.DashboardScreen
import com.example.neettoper.features.profile.ProfileScreen // <-- Import
import com.example.neettoper.features.syllabus.SyllabusTrackerScreen

// ... (MainScreens sealed class is unchanged) ...
sealed class MainScreens(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : MainScreens("dashboard", "Home", Icons.Default.Home)
    object Syllabus : MainScreens("syllabus", "Syllabus", Icons.Default.Checklist)
    object Community : MainScreens("community", "Community", Icons.Default.People)
    object Profile : MainScreens("profile", "Profile", Icons.Default.AccountCircle)
}


@Composable
fun MainScreen(
    onNavigateToDoubt: (String) -> Unit,
    onAskQuestionClicked: () -> Unit,
    onLogoutClicked: () -> Unit // <-- ADD THIS NEW LAMBDA
) {
    val navController = rememberNavController()
    val navItems = listOf(
        MainScreens.Dashboard,
        MainScreens.Syllabus,
        MainScreens.Community,
        MainScreens.Profile
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, items = navItems)
        },
        floatingActionButton = {
            if (currentRoute == MainScreens.Community.route) {
                FloatingActionButton(
                    onClick = onAskQuestionClicked,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Ask a Question"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainAppNavHost(
                navController = navController,
                onNavigateToDoubt = onNavigateToDoubt,
                onLogoutClicked = onLogoutClicked // <-- Pass it down
            )
        }
    }
}

// ... (BottomNavBar composable is unchanged) ...
@Composable
fun BottomNavBar(navController: NavHostController, items: List<MainScreens>) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


// --- THIS IS THE UPDATED PART ---
@Composable
fun MainAppNavHost(
    navController: NavHostController,
    onNavigateToDoubt: (String) -> Unit,
    onLogoutClicked: () -> Unit // <-- Receive the lambda
) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.Dashboard.route
    ) {
        // ... (Dashboard, Syllabus, Community composables are unchanged) ...
        composable(MainScreens.Dashboard.route) {
            DashboardScreen()
        }
        composable(MainScreens.Syllabus.route) {
            SyllabusTrackerScreen()
        }

        composable(MainScreens.Community.route) {
            CommunityFeedScreen(
                onDoubtClicked = { doubtId ->
                    onNavigateToDoubt(doubtId)
                }
            )
        }

        // --- THIS IS UPDATED ---
        composable(MainScreens.Profile.route) {
            ProfileScreen(
                onLogoutClicked = onLogoutClicked // Pass it to the screen
            )
        }
        // --- END OF UPDATE ---
    }
}