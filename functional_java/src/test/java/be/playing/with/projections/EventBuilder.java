package be.playing.with.projections;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import be.playing.with.projections.model.Event;
import be.playing.with.projections.model.EventType;

public class EventBuilder {

  private String id;
  private EventType type;
  private LocalDateTime timestamp;
  private Map<String, String> payload = new HashMap<>();

  private EventBuilder() {
  }

  public static EventBuilder newBuilder() {
    return new EventBuilder();
  }

  public EventBuilder withId(String val) {
    id = val;
    return this;
  }

  public EventBuilder withType(EventType val) {
    type = val;
    return this;
  }

  public EventBuilder withTimestamp(LocalDateTime val) {
    timestamp = val;
    return this;
  }

  public EventBuilder withProperty(String key, String val) {
    payload.put(key, val);
    return this;
  }

  public Event build() {
    return Event.of(id, type, timestamp, payload);
  }
}
