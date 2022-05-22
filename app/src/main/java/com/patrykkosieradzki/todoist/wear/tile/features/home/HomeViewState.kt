package com.patrykkosieradzki.todoist.wear.tile.features.home

import com.patrykkosieradzki.todoist.wear.tile.domain.VersionProvider
import com.patrykkosieradzki.todoist.wear.tile.domain.model.Label
import com.patrykkosieradzki.todoist.wear.tile.domain.model.Project
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.domain.model.User

data class HomeViewState(
    val firstName: String,
    val favoriteLabelComponents: List<FavoriteLabelComponent>,
    val favoriteProjectComponents: List<FavoriteProjectComponent>,
    val appVersion: String
) {
    companion object {
        val Empty = HomeViewState(
            firstName = "",
            favoriteProjectComponents = emptyList(),
            favoriteLabelComponents = emptyList(),
            appVersion = ""
        )

        fun resolveFrom(
            user: User?,
            tasks: List<TodoistTask>?,
            labels: List<Label>?,
            projects: List<Project>?,
            versionProvider: VersionProvider
        ) = HomeViewState(
            firstName = user?.firstName ?: "...",
            favoriteProjectComponents = resolveProjectComponents(
                projects = projects,
                tasks = tasks
            ),
            favoriteLabelComponents = resolveLabelComponents(
                labels = labels,
                tasks = tasks
            ),
            appVersion = versionProvider.versionName
        )
    }
}

private fun resolveProjectComponents(
    projects: List<Project>?,
    tasks: List<TodoistTask>?
): List<FavoriteProjectComponent> {
    val favoriteProjects = projects
        ?.filter { it.isFavorite }
        ?: emptyList()

    val threeFavoriteProjects = favoriteProjects.take(3)

    val favoriteProjectComponentItems = threeFavoriteProjects.map { project ->
        // TODO: Fix taskCount for projects. Something's messed up here
        val taskCount = tasks?.count { it.projectId == project.id } ?: 0

        FavoriteProjectComponent.Item(
            id = project.id,
            name = project.name,
            taskCount = taskCount
        )
    }

    return when {
        favoriteProjects.isEmpty() -> emptyList()
        favoriteProjects.size <= 3 -> favoriteProjectComponentItems
        else -> favoriteProjectComponentItems + FavoriteProjectComponent.ShowAllButton
    }
}

private fun resolveLabelComponents(
    labels: List<Label>?,
    tasks: List<TodoistTask>?
): List<FavoriteLabelComponent> {
    val favoriteLabels = labels
        ?.filter { it.isFavorite }
        ?: emptyList()

    val threeFavoriteLabels = favoriteLabels.take(3)

    val favoriteLabelComponentItems = threeFavoriteLabels.map { label ->
        val taskCount = tasks?.count { it.labels.contains(label.id) } ?: 0

        FavoriteLabelComponent.Item(
            id = label.id,
            name = label.name,
            taskCount = taskCount
        )
    }

    return when {
        favoriteLabels.isEmpty() -> emptyList()
        favoriteLabels.size <= 3 -> favoriteLabelComponentItems
        else -> favoriteLabelComponentItems + FavoriteLabelComponent.ShowAllButton
    }
}
