package be.playing.with.projections.model.projections;

import java.util.List;

import be.playing.with.projections.model.Event;
import be.playing.with.projections.model.Projection;

public class CountEventsProjection implements Projection<Integer> {

  @Override
  public Integer project(List<Event> events) {
    return events.size();
  }

  @Override
  public String buildResultMessage(Integer projectResult) {
    return projectResult + " events counted in the stream";
  }
}

