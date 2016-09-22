package be.tothepoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DddWorkshopApplication {

	private static final Logger log = LoggerFactory.getLogger(DddWorkshopApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DddWorkshopApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ResponseProvider responseProvider) throws Exception {
		return args -> {
			responseProvider.loadResponses("0")
					.stream()
					.map(be.tothepoint.Response::toString)
					.forEach(log::info);
		};
	}

}
