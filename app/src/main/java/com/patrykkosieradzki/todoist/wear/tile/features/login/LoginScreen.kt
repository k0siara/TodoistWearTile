package com.patrykkosieradzki.todoist.wear.tile.features.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.activity.ConfirmationActivity
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.phone.interactions.authentication.CodeChallenge
import androidx.wear.phone.interactions.authentication.CodeVerifier
import androidx.wear.phone.interactions.authentication.OAuthRequest
import androidx.wear.phone.interactions.authentication.OAuthResponse
import androidx.wear.phone.interactions.authentication.RemoteAuthClient
import androidx.wear.widget.ConfirmationOverlay
import com.patrykkosieradzki.todoist.wear.tile.findActivity


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val listState = rememberScalingLazyListState()

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
                    label = {
                        Text(
                            modifier = Modifier.fillParentMaxWidth(),
                            text = "Login on phone",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    onClick = {
                        context.findActivity()?.let { activity ->
                            val message: CharSequence = "Continue on phone..."

                            ConfirmationOverlay()
                                .setType(ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION)
                                .setDuration(1500)
                                .setMessage(message)
                                .setOnAnimationFinishedListener {
                                    // What should be done here?
                                }.showOn(activity)
                        }


                    }
                )
            }
        }
    }
}
