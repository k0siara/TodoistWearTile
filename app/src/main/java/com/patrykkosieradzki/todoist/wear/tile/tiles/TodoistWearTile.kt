package com.patrykkosieradzki.todoist.wear.tile.tiles

import androidx.core.content.ContextCompat.getColor
import androidx.wear.tiles.ActionBuilders
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.LayoutElementBuilders.Column
import androidx.wear.tiles.LayoutElementBuilders.FontStyles
import androidx.wear.tiles.LayoutElementBuilders.Layout
import androidx.wear.tiles.LayoutElementBuilders.LayoutElement
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.ModifiersBuilders.Clickable
import androidx.wear.tiles.ModifiersBuilders.Modifiers
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.google.android.horologist.tiles.CoroutinesTileService
import com.patrykkosieradzki.todoist.wear.tile.R

private const val RESOURCES_VERSION = "1"

class TodoistWearTile : CoroutinesTileService() {
    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        return TileBuilders.Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setTimeline(
                Timeline.Builder()
                    .addTimelineEntry(
                        TimelineEntry.Builder()
                            .setLayout(
                                Layout.Builder()
                                    .setRoot(layout(requestParams.deviceParameters!!))
                                    .build()
                            ).build()
                    ).build()
            ).build()
    }

    private fun layout(
        deviceParameters: DeviceParametersBuilders.DeviceParameters
    ): LayoutElement {
        return Column.Builder()
            .setModifiers(
                Modifiers.Builder()
                    .setClickable(
                        Clickable.Builder()
                            .setOnClick(ActionBuilders.LoadAction.Builder().build())
                            .build()
                    )
                    .build()
            )
            .addContent(
                Text.Builder()
                    .setText("Hello Tile")
                    .setFontStyle(
                        FontStyles
                            .display2(deviceParameters)
                            .setColor(argb(getColor(baseContext, R.color.white))).build()
                    )
                    .build()
            ).build()
    }

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): Resources {
        return Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }
}
