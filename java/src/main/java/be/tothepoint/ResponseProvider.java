package be.tothepoint;

import java.util.List;

public interface ResponseProvider {
	List<Response> loadResponses(String stream);
}
