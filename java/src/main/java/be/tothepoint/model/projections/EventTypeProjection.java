package be.tothepoint.model.projections;

import java.util.List;
import java.util.stream.Collectors;

import be.tothepoint.model.Event;

public class EventTypeProjection implements Projection<List<String>> {

  @Override
  public List<String> project(List<Event> events) {
    return events.stream()
        .map(Event::getType)
        .collect(Collectors.toList());
  }

  @Override
  public String buildResultMessage(List<String> eventTypes) {
    return "The even stream contained " + eventTypes.size() + " events.";
  }
}

