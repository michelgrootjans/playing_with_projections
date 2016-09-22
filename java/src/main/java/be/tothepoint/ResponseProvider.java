package be.tothepoint;

import java.util.List;

public interface ResponseProvider {
	List<Event> loadResponses(String stream);
}
