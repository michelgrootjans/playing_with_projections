package be.tothepoint.infra;

import be.tothepoint.Event;
import be.tothepoint.ResponseProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

class RestResponseProvider implements ResponseProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseProvider.class);
    private static final String URL = "https://playing-with-projections.herokuapp.com/stream/";
    private final RestTemplate restTemplate;

    public RestResponseProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Event> loadResponses(String streamId) {
        logStart(streamId);
        final ResponseEntity<List<Event>> response = getResponse(streamId);
        return response.getBody();
    }

    private void logStart(String streamId) {
        LOGGER.info("Loading event stream with id " + streamId + " from Url " + URL);
    }


    private ResponseEntity<List<Event>> getResponse(String streamId) {
        return restTemplate.exchange(buildRequestEntity(streamId), buildExpectedResponseType());
    }

    private ParameterizedTypeReference<List<Event>> buildExpectedResponseType() {
        return new ParameterizedTypeReference<List<Event>>() {
        };
    }

    private RequestEntity<Void> buildRequestEntity(String streamId) {
        return RequestEntity.get(URI.create(URL + streamId)).build();
    }
}
