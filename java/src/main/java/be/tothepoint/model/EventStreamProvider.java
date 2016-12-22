package be.tothepoint.model;

import java.util.List;

public interface EventStreamProvider {
	List<Event> loadResponses(String stream);
}
