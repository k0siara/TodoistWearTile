package com.patrykkosieradzki.todoist.wear.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scalingLazyColumnComposable
import com.patrykkosieradzki.todoist.wear.tile.features.home.HomeScreen
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginScreen
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginViewModel
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashScreen
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashViewModel

private object AppRoutes {
    const val splashScreen = "/splash"
    const val loginScreen = "/login"
    const val homeScreen = "/home"
}

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun AppNavGraph() {
    val navController = rememberSwipeDismissableNavController()

    WearNavScaffold(
        navController = navController,
        startDestination = AppRoutes.splashScreen,
        timeText = { timeTextModifier ->
            when (navController.currentDestination?.route) {
                AppRoutes.splashScreen -> {
                    // Nothing
                }
                else -> {
                    TimeText(modifier = timeTextModifier)
                }
            }
        }
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

        scalingLazyColumnComposable(
            route = AppRoutes.loginScreen,
            scrollStateBuilder = { ScalingLazyListState() }
        ) {
            val viewModel = hiltViewModel<LoginViewModel>()

            LoginScreen(
                viewModel = viewModel,
                listState = it.scrollableState,
                navigateToHome = {
                    navController.navigate(AppRoutes.homeScreen) {
                        popUpTo(AppRoutes.loginScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        scalingLazyColumnComposable(
            route = AppRoutes.homeScreen,
            scrollStateBuilder = { ScalingLazyListState() }
        ) {
            HomeScreen(
                listState = it.scrollableState
            )
        }
    }
}

@Preview
@Composable
private fun AppContentPreview() {
    AppNavGraph()
}
