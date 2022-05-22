package com.patrykkosieradzki.todoist.wear.tile.features.home.confirmlogout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.Text
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler
import com.patrykkosieradzki.todoist.wear.tile.R

@Composable
fun ConfirmLogoutScreen(
    modifier: Modifier = Modifier,
    viewModel: ConfirmLogoutViewModel,
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val currentNavigateToLogin by rememberUpdatedState(newValue = navigateToLogin)

    ComposerFlowEventHandler(
        event = viewModel.navigateToLogin,
        handleEvent = { _, _ -> currentNavigateToLogin.invoke() }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListHeader {
            Text(
                text = "Are you sure?",
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
                onClick = navigateBack,
                colors = ButtonDefaults.secondaryButtonColors(),
                enabled = true,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "close",
                    modifier = Modifier
                        .size(ButtonDefaults.LargeIconSize)
                        .wrapContentSize(align = Alignment.Center),
                )
            }

            Button(
                modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
                onClick = viewModel::onLogoutConfirmed,
                colors = ButtonDefaults.primaryButtonColors(),
                enabled = true,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "close",
                    modifier = Modifier
                        .size(ButtonDefaults.LargeIconSize)
                        .wrapContentSize(align = Alignment.Center),
                )
            }
        }
    }
}
