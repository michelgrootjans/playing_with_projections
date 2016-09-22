package be.tothepoint.infra;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.tothepoint.Response;
import be.tothepoint.ResponseProvider;

public class JsonFileResponseProvider implements ResponseProvider {

	private ObjectMapper mapper;
	private ApplicationContext applicationContext;

	public JsonFileResponseProvider(ObjectMapper mapper, ApplicationContext applicationContext) {
		this.mapper = mapper;
		this.applicationContext = applicationContext;
	}

	@Override
	public List<Response> loadResponses(String stream) {
		try {
			InputStream jsonFileStream = applicationContext
					.getResource(getDataDirectory(stream))
					.getInputStream();

			return Arrays.asList(mapper.readValue(jsonFileStream, Response[].class));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getDataDirectory(String stream) {
		String currentAbsolutePath = new File("").toPath().toAbsolutePath().toString();
		String rootPath = currentAbsolutePath.split("java")[0];

		return "file://" + rootPath + "data/" + stream + ".json";
	}
}
