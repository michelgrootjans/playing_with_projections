package be.playing.with.projections.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jan Van Rensbergen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

  public final String id;
  public final EventType type;
  public final LocalDateTime timestamp;
  public final Map<String, String> payload;

  private Event(String id, EventType type, LocalDateTime timestamp, Map<String, String> payload) {
    this.id = id;
    this.type = type;
    this.timestamp = timestamp;
    this.payload = payload;
  }

  @JsonCreator
  public static Event of(@JsonProperty("id") String id,
          @JsonProperty("type") EventType type,
          @JsonProperty("timestamp") LocalDateTime timestamp,
          @JsonProperty("payload") Map<String, String> payload){

    validate(id, type, timestamp);
    return new Event(id,type,timestamp,payload);
  }

  private static void validate(String id, EventType type, LocalDateTime timestamp) {
    if (id == null || id.isEmpty()) {
      throw new RuntimeException("Id is required");
    }
    if (type == null) {
      throw new RuntimeException("Type is required");
    }
    if (timestamp == null) {
      throw new RuntimeException("Timestamp is required");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof Event)) { return false; }
    Event event = (Event) o;
    return Objects.equals(id, event.id) &&
           type == event.type &&
           Objects.equals(timestamp, event.timestamp) &&
           Objects.equals(payload, event.payload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, timestamp, payload);
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
