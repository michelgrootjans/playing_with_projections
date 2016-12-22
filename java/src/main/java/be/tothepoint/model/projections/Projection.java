package be.tothepoint.model.projections;

import be.tothepoint.model.Event;

import java.util.List;

public interface Projection<T> {
    T project(List<Event> eventStream);

    String buildResultMessage(T projectResult);
}
