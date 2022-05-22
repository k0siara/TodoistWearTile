package com.patrykkosieradzki.todoist.wear.tile.features.home

sealed class FavoriteLabelComponent {
    data class Item(
        val id: String,
        val name: String,
        val taskCount: Int
    ) : FavoriteLabelComponent()

    object ShowAllButton : FavoriteLabelComponent()
}

sealed class FavoriteProjectComponent {
    data class Item(
        val id: String,
        val name: String,
        val taskCount: Int
    ) : FavoriteProjectComponent()

    object ShowAllButton : FavoriteProjectComponent()
}
