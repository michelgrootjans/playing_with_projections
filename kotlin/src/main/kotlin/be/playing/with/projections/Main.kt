package be.playing.with.projections

import be.playing.with.projections.infra.FileEventStreamProvider
import be.playing.with.projections.model.projections.CountEventsProjection


fun main(args: Array<String>) {
    val events = FileEventStreamProvider().loadResponses(jsonFile(args))

    CountEventsProjection() print events
}

private fun jsonFile(args: Array<String>)= when  {
    args.isEmpty() -> "../data/0.json"
    else -> args[0]
}