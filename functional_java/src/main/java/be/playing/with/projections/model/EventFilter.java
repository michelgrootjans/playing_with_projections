package be.playing.with.projections.model;

import java.time.LocalDateTime;
import java.util.function.Function;

public interface EventFilter extends Function<Event, Boolean> {
    static EventFilter byType(EventType type){
        return event -> event.type.equals(type);
    }

    static EventFilter byId(String id){
        return event -> event.id.equals(id);
    }

    static EventFilter before(LocalDateTime date){
        return event -> event.timestamp.isBefore(date);
    }

    static EventFilter after(LocalDateTime date){
        return event -> event.timestamp.isAfter(date);
    }

    default EventFilter and(EventFilter other){
        return event -> this.apply(event) && other.apply(event);
    }

    default EventFilter or(EventFilter other){
        return event -> this.apply(event) || other.apply(event);
    }
}
