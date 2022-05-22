package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.scrollableColumn
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    listState: ScalingLazyListState,
    viewModel: HomeViewModel,
    navigateToAddTask: () -> Unit,
    navigateToTasks: () -> Unit,
    navigateToConfirmLogout: () -> Unit
) {
    val viewState by viewModel.viewState.asLifecycleAwareState()

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .scrollableColumn(focusRequester, listState),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
        contentPadding = PaddingValues(start = 15.dp, top = 20.dp, end = 15.dp)
    ) {
        item("title") {
            ListHeader(
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillParentMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Hello, ${viewState.firstName},",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "anything TODO?",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        item("add-task") {
            Chip(
                modifier = Modifier.padding(top = 10.dp),
                colors = ChipDefaults.primaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Add task",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = navigateToAddTask
            )
        }

        item("all-tasks") {
            Chip(
                modifier = Modifier.padding(top = 10.dp),
                colors = ChipDefaults.secondaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "All tasks",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = navigateToTasks
            )
        }

        if (viewState.favoriteLabelItems.isNotEmpty()) {
            item("favorite-labels-header") {
                ListHeader(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Favorites",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            items(
                items = viewState.favoriteProjects,
                key = { it.id }
            ) { project ->
                Chip(
                    colors = ChipDefaults.secondaryChipColors(),
                    label = {
                        Text(
                            modifier = Modifier.fillParentMaxWidth(),
                            text = project.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    onClick = {}
                )
            }

            items(
                items = viewState.favoriteLabelItems,
                key = { it.id }
            ) { labelItem ->
                Chip(
                    colors = ChipDefaults.secondaryChipColors(),
                    label = {
                        Row(
                            modifier = Modifier.fillParentMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = labelItem.name,
                                maxLines = 1,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = "${labelItem.taskCount}",
                                maxLines = 1,
                                textAlign = TextAlign.End,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    onClick = {}
                )
            }
        }

        item("account-header") {
            ListHeader(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillParentMaxWidth(),
                    text = "Account",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        item("settings") {
            Chip(
                colors = ChipDefaults.secondaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Settings (in progress)",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {},
                enabled = false
            )
        }

        item("logout") {
            Chip(
                colors = ChipDefaults.secondaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Logout",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = navigateToConfirmLogout
            )
        }

        item("app-version") {
            ListHeader(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillParentMaxWidth(),
                    text = "Version: ${viewState.appVersion}",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
