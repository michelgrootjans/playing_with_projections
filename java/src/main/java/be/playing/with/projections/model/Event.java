package be.playing.with.projections.model;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Jan Van Rensbergen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

  private String id;
  private EventType type;
  private LocalDateTime timestamp;
  private Map<String, String> payload;

  public Event(String id, EventType type, LocalDateTime timestamp, Map<String, String> payload) {
    this.id = id;
    this.type = type;
    this.timestamp = timestamp;
    this.payload = payload;
  }

  private Event() {
    // for frameworks
  }

  public String getId() {
    return id;
  }

  public EventType getType() {
    return type;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public Map<String, String> getPayload() {
    return payload;
  }

  @Override
  public String toString() {
    return "Event{" +
           "id='" + id + '\'' +
           ", type='" + type + '\'' +
           ", timestamp=" + timestamp +
           ", payload=" + payload +
           '}';
  }
}
