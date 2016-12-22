package be.tothepoint.infra;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import be.tothepoint.model.Event;
import be.tothepoint.model.EventStreamProvider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileEventStreamProvider implements EventStreamProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileEventStreamProvider.class);
  private final ObjectMapper mapper;

  public FileEventStreamProvider() {
    mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    mapper.registerModule(new JavaTimeModule());
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
    return Files.newInputStream(Paths.get(location));
  }

  private void logStart(String stream) {
    LOGGER.info("Loading event stream with id " + stream + " from file system.");
  }

  private String buildStreamFilePath(String streamId) {
    final String currentAbsolutePath = new File("").toPath().toAbsolutePath().toString();
    final String rootPath = currentAbsolutePath.split("java")[0];
    return "/" + rootPath + "data/" + streamId + ".json";
  }
}
