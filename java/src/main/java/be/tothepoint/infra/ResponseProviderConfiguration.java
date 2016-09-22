package be.tothepoint.infra;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.tothepoint.EventStreamProvider;

@Configuration
public class ResponseProviderConfiguration {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Profile("fs")
	public EventStreamProvider jsonFileResponseProvider(ObjectMapper objectMapper) {
		return new JsonFileEventStreamProvider(objectMapper);
	}

	@Bean
	@Profile("!fs")
	public EventStreamProvider herokuRestResponseProvider(RestTemplate restTemplate) {
		return new RestEventStreamProvider(restTemplate);
	}

}
