package com.patrykkosieradzki.todoist.wear.tile.tiles

import androidx.core.content.ContextCompat.getColor
import androidx.wear.tiles.ActionBuilders
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.LayoutElementBuilders.Column
import androidx.wear.tiles.LayoutElementBuilders.FontStyles
import androidx.wear.tiles.LayoutElementBuilders.LayoutElement
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.ModifiersBuilders.Clickable
import androidx.wear.tiles.ModifiersBuilders.Modifiers
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.tiles.CoroutinesTileService
import com.patrykkosieradzki.todoist.wear.tile.R
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.LayoutBuilder.buildLayout
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TileBuilder.buildTile
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TileBuilder.setResourcesVersion
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TileBuilder.setTimeline
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TimelineBuilder.addTimelineEntry
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TimelineBuilder.buildTimeline
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TimelineEntryBuilder.buildTimelineEntry
import com.patrykkosieradzki.todoist.wear.tile.tiles.TilesDsl.TimelineEntryBuilder.setLayout

private const val RESOURCES_VERSION = "1"

class TodoistWearTile : CoroutinesTileService() {
    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        return buildTile {
            setResourcesVersion { RESOURCES_VERSION }
            setTimeline {
                buildTimeline {
                    addTimelineEntry {
                        buildTimelineEntry {
                            setLayout {
                                buildLayout { setRoot(layout(requestParams.deviceParameters!!)) }
                            }
                        }
                    }
                }
            }
        }
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
