package be.tothepoint.infra;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import be.tothepoint.model.Event;
import be.tothepoint.model.EventStreamProvider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestEventStreamProvider implements EventStreamProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestEventStreamProvider.class);
  private static final String URL = "https://playing-with-projections.herokuapp.com/stream/";
  private ObjectMapper objectMapper;

  public RestEventStreamProvider() {
    objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Override
  public List<Event> loadResponses(String stream) {
    logStart(stream);
    Event[] events = retrieveEvents(stream);
    if (events == null) {
      return Collections.emptyList();
    } else {
      return Arrays.asList(events);
    }
  }

  private void logStart(String streamId) {
    LOGGER.info("Loading event stream with id " + streamId + " from Url " + URL);
  }

  private Event[] retrieveEvents(String stream) {
    Event[] events;
    try {
      events = objectMapper.readValue(new URL(URL + stream),
          Event[].class);
    } catch (IOException e) {
      throw new CouldNotReadEventsException(e);
    }
    return events;
  }
}
