package be.playing.with.projections.infra

import be.playing.with.projections.infra.ObjectMapperProvider.mapper
import be.playing.with.projections.model.Event
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.net.URL

interface EventStreamProvider {
    fun loadResponses(stream: String): List<Event>
}

class FileEventStreamProvider : EventStreamProvider {
    override fun loadResponses(stream: String): List<Event> = mapper.readValue(File( stream ))
}

class RestEventStreamProvider : EventStreamProvider {
    override fun loadResponses(stream: String): List<Event> = mapper.readValue(URL("https://playing-with-projections.herokuapp.com/stream/$stream"))
}

internal object ObjectMapperProvider {
    val mapper= jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)!!
}
