package com.patrykkosieradzki.todoist.wear.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginScreen
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginViewModel
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashScreen
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashViewModel

private object AppRoutes {
    const val splashScreen = "/splash"
    const val loginScreen = "/login"
}

@Composable
fun AppNavGraph() {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = AppRoutes.splashScreen,
    ) {
        composable(
            route = AppRoutes.splashScreen
        ) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                viewModel = viewModel,
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
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
private fun AppContentPreview() {
    AppNavGraph()
}
