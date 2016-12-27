package be.playing.with.projections.model;

import java.util.List;

public interface Projection<T> {

  T project(List<Event> eventStream);

  String buildResultMessage(T projectResult);
}
