package be.tothepoint.infra;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import be.tothepoint.Response;
import be.tothepoint.ResponseProvider;

public class RestResponseProvider implements ResponseProvider {

	private static final String URL = "https://playing-with-projections.herokuapp.com/stream/";

	private RestTemplate restTemplate;

	public RestResponseProvider(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public List<Response> loadResponses(String stream) {
		ResponseEntity<List<Response>> response =
				restTemplate.exchange(RequestEntity.get(URI.create(URL + stream)).build(),
						new ParameterizedTypeReference<List<Response>>() {
						});

		return response.getBody();
	}
}
