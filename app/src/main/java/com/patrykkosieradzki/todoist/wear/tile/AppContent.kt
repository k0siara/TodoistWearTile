package com.patrykkosieradzki.todoist.wear.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginScreen
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashScreen

private object AppRoutes {
    const val splashScreen = "/splash"
    const val loginScreen = "/login"
}

@Composable
fun AppContent() {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = AppRoutes.splashScreen,
    ) {
        composable(
            route = AppRoutes.splashScreen
        ) {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(AppRoutes.loginScreen) {
                        popUpTo(AppRoutes.splashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = AppRoutes.loginScreen
        ) {
            LoginScreen()
        }
    }
}

@Preview
@Composable
private fun AppContentPreview() {
    AppContent()
}
