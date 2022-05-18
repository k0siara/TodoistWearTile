package com.patrykkosieradzki.todoist.wear.tile.features.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat.startActivity
import androidx.wear.activity.ConfirmationActivity
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
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
                        val intent = Intent(context, ConfirmationActivity::class.java).apply {
                            putExtra(
                                ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                                ConfirmationActivity.SUCCESS_ANIMATION
                            )
                            putExtra(ConfirmationActivity.EXTRA_MESSAGE, "open on phone")
                        }
                        context.startActivity(intent)


                        val request = OAuthRequest.Builder(context.packageName)
                            .setAuthProviderUrl(Uri.parse("https://todoist.com/oauth/authorize?scope=data:read_write&state=123"))
                            .setClientId("2bca0375698b4ef393d19a88a024e66b")
                            .setCodeChallenge(CodeChallenge(CodeVerifier("123")))
                            .build()

                        val client = RemoteAuthClient.create(context)
                        client.sendAuthorizationRequest(
                            request,
                            object : RemoteAuthClient.Callback() {
                                override fun onAuthorizationError(errorCode: Int) {
                                    println("onAuthorizationError: $errorCode")
                                }

                                override fun onAuthorizationResponse(
                                    request: OAuthRequest,
                                    response: OAuthResponse
                                ) {
                                    println("onAuthorizationResponse: $request -> $response")
                                }
                            })
                    }
                )
            }
        }
    }
}
