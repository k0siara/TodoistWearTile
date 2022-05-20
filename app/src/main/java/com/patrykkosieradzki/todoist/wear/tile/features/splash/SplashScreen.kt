package com.patrykkosieradzki.todoist.wear.tile.features.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val currentNavigateToLogin by rememberUpdatedState(newValue = navigateToLogin)
    val currentNavigateToHome by rememberUpdatedState(newValue = navigateToHome)

    LaunchedEffect(Unit) {
        viewModel.checkLoginStatus()
    }

    ComposerFlowEventHandler(
        event = viewModel.navigateToLogin,
        handleEvent = { _, _ ->
            currentNavigateToLogin.invoke()
        }
    )

    ComposerFlowEventHandler(
        event = viewModel.navigateToHome,
        handleEvent = { _, _ ->
            currentNavigateToHome.invoke()
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "splash screen")
    }
}
