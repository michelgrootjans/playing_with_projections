package be.playing.with.projections.model

interface Projection <T> {

    fun project(eventStream: List<Event>): T

    fun buildResultMessage(projectResult: T): String

    infix fun print(events: List<Event>) {
        println(buildResultMessage( project(events)))
    }
}
