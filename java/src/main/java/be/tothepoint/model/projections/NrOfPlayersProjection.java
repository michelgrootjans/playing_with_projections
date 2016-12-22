package be.tothepoint.model.projections;

import java.util.List;

import be.tothepoint.model.Event;

public class NrOfPlayersProjection implements Projection<Long> {

  @Override
  public Long project(List<Event> events) {
    return events
        .stream()
        .filter(x -> x.getType().equals(EventTypes.PLAYER_HAS_REGISTERED))
        .count();
  }

  @Override
  public String buildResultMessage(Long projectResult) {
    return projectResult + " players have registered";
  }
}

