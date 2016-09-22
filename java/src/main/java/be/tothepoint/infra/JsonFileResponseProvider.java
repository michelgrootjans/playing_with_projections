package be.tothepoint.infra;

import be.tothepoint.Event;
import be.tothepoint.ResponseProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

class JsonFileResponseProvider implements ResponseProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileResponseProvider.class);
    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public JsonFileResponseProvider(ObjectMapper mapper) {
        this.mapper = mapper;
        this.resourceLoader = new FileSystemResourceLoader();
    }

    @Override
    public List<Event> loadResponses(String eventStreamId) {
        logStart(eventStreamId);
        return loadEventsFromFileStream(eventStreamId);
    }

    private List<Event> loadEventsFromFileStream(String eventStreamId) {
        try {
            final InputStream jsonFileStream = createInputStream(eventStreamId);
            return mapStream(jsonFileStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Event> mapStream(InputStream jsonFileStream) throws IOException {
        final Event[] events = mapper.readValue(jsonFileStream, Event[].class);
        return Arrays.asList(events);
    }

    private InputStream createInputStream(String streamId) throws IOException {
        final String location = buildStreamFilePath(streamId);
        return loadFile(location);
    }

    private InputStream loadFile(String location) throws IOException {
        return resourceLoader
                .getResource(location)
                .getInputStream();
    }

    private void logStart(String stream) {
        LOGGER.info("Loading event stream with id " + stream + " from file system.");
    }

    private String buildStreamFilePath(String streamId) {
        final String currentAbsolutePath = new File("").toPath().toAbsolutePath().toString();
        final String rootPath = currentAbsolutePath.split("java")[0];
        return "file://" + rootPath + "data/" + streamId + ".json";
    }
}
