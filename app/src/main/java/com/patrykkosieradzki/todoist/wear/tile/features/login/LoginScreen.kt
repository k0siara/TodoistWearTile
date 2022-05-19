package com.patrykkosieradzki.todoist.wear.tile.features.login

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
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler
import com.patrykkosieradzki.todoist.wear.tile.extensions.showConfirmationOverlay

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navigateToHome: () -> Unit
) {
    val currentNavigateToHome by rememberUpdatedState(newValue = navigateToHome)

    val context = LocalContext.current
    val listState = rememberScalingLazyListState()

    ComposerFlowEventHandler(
        event = viewModel.navigateToHomeEvent,
        handleEvent = { _, _ ->
            currentNavigateToHome.invoke()
        }
    )

    Scaffold(
        timeText = {
            Text(
                textAlign = TextAlign.Center,
                color = Color.White,
                text = "time?"
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item {
                Text(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    text = "First, login to Todoist"
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
}
