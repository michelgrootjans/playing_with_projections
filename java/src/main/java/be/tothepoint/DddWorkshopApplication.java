package be.tothepoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DddWorkshopApplication {

    static final String FIRST_DATA_STREAM_URL = "https://playing-with-projections.herokuapp.com/stream/1";
    static final String THOMAS_URL = "https://raw.githubusercontent.com/tcoopman/playing_with_projections_server/master/data/1.json";
    static final String KRIS_URL = "http://bbox.rotate-it.be/~blacky/streams/2.json";

    public static void main(String[] args) {
        SpringApplication.run(DddWorkshopApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}
