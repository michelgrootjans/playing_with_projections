package be.tothepoint.projections;

import be.tothepoint.Event;

import java.util.List;

public interface Projection<T> {
    T project(List<Event> eventStream);

    String buildResultMessage(T projectResult);
}
