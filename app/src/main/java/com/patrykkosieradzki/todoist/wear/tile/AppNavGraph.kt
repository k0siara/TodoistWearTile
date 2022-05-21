package com.patrykkosieradzki.todoist.wear.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scalingLazyColumnComposable
import com.patrykkosieradzki.todoist.wear.tile.features.addtask.AddTaskScreen
import com.patrykkosieradzki.todoist.wear.tile.features.home.HomeScreen
import com.patrykkosieradzki.todoist.wear.tile.features.home.HomeViewModel
import com.patrykkosieradzki.todoist.wear.tile.features.home.confirmlogout.ConfirmLogoutScreen
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginScreen
import com.patrykkosieradzki.todoist.wear.tile.features.login.LoginViewModel
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashScreen
import com.patrykkosieradzki.todoist.wear.tile.features.splash.SplashViewModel
import com.patrykkosieradzki.todoist.wear.tile.features.tasks.TaskListScreen
import com.patrykkosieradzki.todoist.wear.tile.features.tasks.TaskListViewModel

private object AppRoutes {
    const val splashScreen = "/splash"
    const val loginScreen = "/login"

    const val homeScreen = "/home"
    const val confirmLogoutScreen = "/confirmLogout"

    const val tasksScreen = "/tasks"
    const val addTaskScreen = "/add-task"
}

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun AppNavGraph() {
    val navController = rememberSwipeDismissableNavController()

    WearNavScaffold(
        navController = navController,
        startDestination = AppRoutes.splashScreen,
        timeText = { timeTextModifier ->
            if (navController.currentDestination?.route != AppRoutes.splashScreen) {
                TimeText(modifier = timeTextModifier)
            }
        }
    ) {
        includeSplashScreen(navController)
        includeLoginScreen(navController)

        includeHomeScreen(navController)
        includeConfirmLogoutScreen()

        includeAddTaskScreen()
        includeTaskListScreen()
    }
}

fun NavGraphBuilder.includeSplashScreen(navController: NavController) = composable(
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
        },
        navigateToHome = {
            navController.navigate(AppRoutes.homeScreen) {
                popUpTo(AppRoutes.splashScreen) {
                    inclusive = true
                }
            }
        }
    )
}

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
fun NavGraphBuilder.includeLoginScreen(navController: NavController) = scalingLazyColumnComposable(
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

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
fun NavGraphBuilder.includeHomeScreen(
    navController: NavController
) = scalingLazyColumnComposable(
    route = AppRoutes.homeScreen,
    scrollStateBuilder = { ScalingLazyListState() }
) {
    val viewModel = hiltViewModel<HomeViewModel>()

    HomeScreen(
        focusRequester = it.viewModel.focusRequester,
        listState = it.scrollableState,
        viewModel = viewModel,
        navigateToAddTask = {
            navController.navigate(AppRoutes.addTaskScreen)
        },
        navigateToTasks = {
            navController.navigate(AppRoutes.tasksScreen)
        },
        navigateToConfirmLogout = {
            navController.navigate(AppRoutes.confirmLogoutScreen)
        }
    )
}

fun NavGraphBuilder.includeConfirmLogoutScreen() = composable(
    route = AppRoutes.confirmLogoutScreen
) {
    ConfirmLogoutScreen()
}

fun NavGraphBuilder.includeAddTaskScreen() = composable(
    route = AppRoutes.addTaskScreen
) {
    AddTaskScreen()
}

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
fun NavGraphBuilder.includeTaskListScreen() = scalingLazyColumnComposable(
    route = AppRoutes.tasksScreen,
    scrollStateBuilder = { ScalingLazyListState() }
) {
    val viewModel = hiltViewModel<TaskListViewModel>()

    TaskListScreen(
        focusRequester = it.viewModel.focusRequester,
        listState = it.scrollableState,
        viewModel = viewModel
    )
}

@Preview
@Composable
private fun AppContentPreview() {
    AppNavGraph()
}
