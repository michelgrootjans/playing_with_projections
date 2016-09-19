package be.tothepoint;

import be.tothepoint.projections.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static be.tothepoint.DddWorkshopApplication.KRIS_URL;

@Component
class ProjectionsCommandLineRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ProjectionsCommandLineRunner.class);
    private final RestTemplate restTemplate;
    private final List<Projection> projections;

    public ProjectionsCommandLineRunner(RestTemplate restTemplate, List<Projection> projections) {
        this.restTemplate = restTemplate;
        this.projections = projections;
    }

    @Override
    public void run(String... args) throws Exception {
        final List<Event> events = getEventStream(restTemplate);
        projections.forEach(x -> projectEventStream(events, x));
    }

    @SuppressWarnings("unchecked")
    private void projectEventStream(List<Event> events, Projection x) {
        final Object project = x.project(events);
        final String resultMessage = x.buildResultMessage(project);
        log.info(resultMessage);
    }


    private List<Event> getEventStream(RestTemplate restTemplate) {
        final ResponseEntity<List<Event>> response = getResponse(restTemplate);
        return response.getBody();
    }


    private ResponseEntity<List<Event>> getResponse(RestTemplate restTemplate) {
        return restTemplate.exchange(buildRequestEntity(), buildExpectedResponseType());
    }

    private ParameterizedTypeReference<List<Event>> buildExpectedResponseType() {
        return new ParameterizedTypeReference<List<Event>>() {
        };
    }

    private RequestEntity<Void> buildRequestEntity() {
        return RequestEntity.get(URI.create(KRIS_URL)).build();
    }


}

