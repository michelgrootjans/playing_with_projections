package be.tothepoint.projections;

import be.tothepoint.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class NrOfPlayersProjection implements Projection<Long> {

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

