package be.tothepoint;

import java.util.List;

public interface EventStreamProvider {
	List<Event> loadResponses(String stream);
}
