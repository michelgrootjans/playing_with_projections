package be.tothepoint.infra;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.tothepoint.ResponseProvider;

@Configuration
public class ResponseProviderConfiguration {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Profile("fs")
	public ResponseProvider jsonFileResponseProvider(ObjectMapper objectMapper, ApplicationContext applicationContext) {
		return new JsonFileResponseProvider(objectMapper, applicationContext);
	}

	@Bean
	@Profile("!fs")
	public ResponseProvider herokuRestResponseProvider(RestTemplate restTemplate) {
		return new RestResponseProvider(restTemplate);
	}

}
