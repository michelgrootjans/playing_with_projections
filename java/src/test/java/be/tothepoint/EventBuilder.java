package be.tothepoint;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import be.tothepoint.model.Event;

public class EventBuilder {

  private String id;
  private String type;
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

  public EventBuilder withType(String val) {
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
    validate();
    return new Event(id, type, timestamp, payload);
  }

  private void validate() {
    if (id == null || id.isEmpty()) {
      throw new RuntimeException("Id is required");
    }
    if (type == null || type.isEmpty()) {
      throw new RuntimeException("Type is required");
    }
    if (timestamp == null) {
      throw new RuntimeException("Timestamp is required");
    }
  }
}
