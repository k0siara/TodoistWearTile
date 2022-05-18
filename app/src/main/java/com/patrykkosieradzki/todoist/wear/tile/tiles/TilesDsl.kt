package com.patrykkosieradzki.todoist.wear.tile.tiles

import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TimelineBuilders

object TilesDsl {

    object TileBuilder {

        @DslMarker
        annotation class TileBuilderScope

        fun buildTile(
            @TileBuilderScope builder: TileBuilders.Tile.Builder.() -> Unit
        ) = TileBuilders.Tile.Builder().apply(builder).build()

        @TileBuilderScope
        fun TileBuilders.Tile.Builder.setResourcesVersion(
            version: () -> String
        ) = setResourcesVersion(version())

        @TileBuilderScope
        fun TileBuilders.Tile.Builder.setTimeline(
            timelineBuilder: () -> TimelineBuilders.Timeline
        ) = setTimeline(timelineBuilder())
    }

    object TimelineBuilder {

        @DslMarker
        annotation class TimelineBuilderScope

        fun buildTimeline(
            @TimelineBuilderScope builder: TimelineBuilders.Timeline.Builder.() -> Unit
        ) = TimelineBuilders.Timeline.Builder().apply(builder).build()

        @TimelineBuilderScope
        fun TimelineBuilders.Timeline.Builder.addTimelineEntry(
            timelineEntryBuilder: () -> TimelineBuilders.TimelineEntry
        ) = addTimelineEntry(timelineEntryBuilder())


    }

    object TimelineEntryBuilder {

        @DslMarker
        annotation class TimelineEntryBuilderScope

        fun buildTimelineEntry(
            @TimelineEntryBuilderScope builder: TimelineBuilders.TimelineEntry.Builder.() -> Unit
        ) = TimelineBuilders.TimelineEntry.Builder().apply(builder).build()

        @TimelineEntryBuilderScope
        fun TimelineBuilders.TimelineEntry.Builder.setLayout(
            layoutBuilder: () -> LayoutElementBuilders.Layout
        ) = setLayout(layoutBuilder())
    }

    object LayoutBuilder {

        @DslMarker
        annotation class LayoutBuilderScope

        fun buildLayout(
            @LayoutBuilderScope layoutBuilder: LayoutElementBuilders.Layout.Builder.() -> Unit
        ) = LayoutElementBuilders.Layout.Builder().apply(layoutBuilder).build()
    }

}
