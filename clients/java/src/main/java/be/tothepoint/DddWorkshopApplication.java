package be.tothepoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@SpringBootApplication
public class DddWorkshopApplication {

    private static final Logger log = LoggerFactory.getLogger(DddWorkshopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DddWorkshopApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {

            ResponseEntity<List<be.tothepoint.Response>> response =
                    restTemplate.exchange(RequestEntity.get(URI.create("http://localhost:8080")).build(),
                            new ParameterizedTypeReference<List<be.tothepoint.Response>>() {
                            });

            List<be.tothepoint.Response> responses = response.getBody();

            responses
                    .stream()
                    .map(be.tothepoint.Response::toString)
                    .forEach(log::info);
        };
    }
}
