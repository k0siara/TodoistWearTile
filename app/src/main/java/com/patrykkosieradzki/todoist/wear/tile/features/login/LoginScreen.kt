package com.patrykkosieradzki.todoist.wear.tile.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler
import com.patrykkosieradzki.composer.composables.UiStateView
import com.patrykkosieradzki.todoist.wear.tile.extensions.showConfirmationOverlay

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    listState: ScalingLazyListState,
    navigateToHome: () -> Unit
) {
    val currentNavigateToHome by rememberUpdatedState(newValue = navigateToHome)

    ComposerFlowEventHandler(
        event = viewModel.navigateToHomeEvent,
        handleEvent = { _, _ ->
            currentNavigateToHome.invoke()
        }
    )

    UiStateView(
        uiStateManager = viewModel,
        renderOnLoading = {
            // move this to separate screen so user can go back to login

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    indicatorColor = Color.White,
                    trackColor = Color.Red
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Waiting for authorization...",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary
                )
            }

        },
        renderOnFailure = { throwable ->
            FailureState(listState = listState, throwable = throwable)
        },
        renderOnSuccess = {
            SuccessState(listState = listState, viewModel = viewModel)
        }
    )
}

@Composable
private fun FailureState(
    listState: ScalingLazyListState,
    throwable: Throwable
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            Text(
                text = "Something went wrong :<",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun SuccessState(
    listState: ScalingLazyListState,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            Text(
                text = "First, login to Todoist",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
        item {
            Chip(
                modifier = Modifier.padding(top = 10.dp),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Login",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {
                    viewModel.onLoginClicked()
                    context.showConfirmationOverlay(
                        message = "Open your phone to authorize"
                    )
                }
            )
        }
    }
}