package io.tripled

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.elasticsearch.client.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResourceLoader
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.EntityMapper
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Arrays.asList
import javax.annotation.PostConstruct

@SpringBootApplication
open class ElkApplication

fun main(args: Array<String>) {
    SpringApplication.run(ElkApplication::class.java, *args)
}

@Component
class EventLoader @Autowired constructor(val mapper: ObjectMapper, val events: Events) {

    @PostConstruct
    fun load(){
        events.deleteAll()
        events.save(loadEvents("2"))
    }

    @Throws(IOException::class)
    fun loadEvents(stream: String): Iterable<Event>
            = asList(*mapper.readValue(buildStreamFilePath(stream), Array<Event>::class.java))

    fun buildStreamFilePath(streamId: String): InputStream {
        val currentAbsolutePath = File("").toPath().toAbsolutePath().toString()
        val rootPath = currentAbsolutePath.split("elk")[0]
        return FileSystemResourceLoader()
                .getResource("file://$rootPath/data/$streamId.json")
                .inputStream
    }
}

interface Events : ElasticsearchRepository<Event, String>

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "events")
data class Event constructor(
        @JsonProperty("name") var id: String?,
        @JsonProperty("type") var type: String?,
        @JsonProperty("timestamp") var timestamp: LocalDateTime?,
        @JsonProperty("payload") var payload: Map<String, String>?
)

@Configuration
open class Configuration {
    @Bean
    open fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    open fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper.dateFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'ZZZ (z)")
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        mapper.registerModule(JavaTimeModule())
        mapper.findAndRegisterModules()
        return mapper
    }

    @Bean
    open fun elasticsearchTemplate(client: Client, objectMapper: ObjectMapper): ElasticsearchTemplate = ElasticsearchTemplate(client, object : EntityMapper {
        @Throws(IOException::class)
        override fun mapToString(obj: Any): String = objectMapper.writeValueAsString(obj)

        @Throws(IOException::class)
        override fun <T> mapToObject(source: String, clazz: Class<T>): T = objectMapper.readValue(source, clazz)
    })
}