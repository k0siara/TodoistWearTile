package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.scrollableColumn
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState
import com.patrykkosieradzki.todoist.wear.tile.R

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

        if (viewState.favoriteProjectComponents.isNotEmpty()) {
            item("projects-header") {
                ListHeader(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Projects",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            items(
                items = viewState.favoriteProjectComponents,
                key = { fpc ->
                    when (fpc) {
                        is FavoriteProjectComponent.Item -> fpc.id
                        FavoriteProjectComponent.ShowAllButton -> "fpc-show-all-button"
                    }
                }
            ) { fpc ->
                when (fpc) {
                    is FavoriteProjectComponent.Item -> {
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
                                        text = fpc.name,
                                        maxLines = 1,
                                        textAlign = TextAlign.Start,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = "${fpc.taskCount}",
                                        maxLines = 1,
                                        textAlign = TextAlign.End,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(ChipDefaults.IconSize),
                                    painter = painterResource(id = R.drawable.ic_project),
                                    contentDescription = "label"
                                )
                            },
                            onClick = {}
                        )
                    }
                    FavoriteProjectComponent.ShowAllButton -> {
                        Chip(
                            colors = ChipDefaults.gradientBackgroundChipColors(),
                            label = {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Show all",
                                    maxLines = 1,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            onClick = {}
                        )
                    }
                }
            }
        }

        if (viewState.favoriteLabelComponents.isNotEmpty()) {
            item("labels-header") {
                ListHeader(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Labels",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            items(
                items = viewState.favoriteLabelComponents,
                key = { flc ->
                    when (flc) {
                        is FavoriteLabelComponent.Item -> flc.id
                        FavoriteLabelComponent.ShowAllButton -> "flc-show-all-button"
                    }
                }
            ) { flc ->
                when (flc) {
                    is FavoriteLabelComponent.Item -> {
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
                                        text = flc.name,
                                        maxLines = 1,
                                        textAlign = TextAlign.Start,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = "${flc.taskCount}",
                                        maxLines = 1,
                                        textAlign = TextAlign.End,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(ChipDefaults.IconSize),
                                    painter = painterResource(id = R.drawable.ic_label),
                                    contentDescription = "label"
                                )
                            },
                            onClick = {}
                        )
                    }
                    FavoriteLabelComponent.ShowAllButton -> {
                        Chip(
                            colors = ChipDefaults.gradientBackgroundChipColors(),
                            label = {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Show all",
                                    maxLines = 1,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            onClick = {}
                        )
                    }
                }
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
