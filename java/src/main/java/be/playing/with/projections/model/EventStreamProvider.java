package be.playing.with.projections.model;

import java.util.List;

public interface EventStreamProvider {
	List<Event> loadResponses(String stream);
}
