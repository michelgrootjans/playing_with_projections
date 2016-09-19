package be.tothepoint.projections;

import be.tothepoint.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class EventTypeProjection implements Projection<List<String>> {

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

