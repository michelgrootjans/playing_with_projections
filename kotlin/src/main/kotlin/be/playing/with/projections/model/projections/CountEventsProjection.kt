package be.playing.with.projections.model.projections

import be.playing.with.projections.model.Event
import be.playing.with.projections.model.Projection

class CountEventsProjection : Projection<Int> {

    override fun buildResultMessage(projectResult: Int): String = "$projectResult events counted in the stream"

    override fun project(events: List<Event>): Int = events.size

}

