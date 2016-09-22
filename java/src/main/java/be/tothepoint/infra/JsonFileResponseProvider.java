package be.tothepoint.infra;

import be.tothepoint.Event;
import be.tothepoint.ResponseProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JsonFileResponseProvider implements ResponseProvider {

    private ObjectMapper mapper;
    private ApplicationContext applicationContext;

    public JsonFileResponseProvider(ObjectMapper mapper, ApplicationContext applicationContext) {
        this.mapper = mapper;
        this.applicationContext = applicationContext;
    }
//TODO ugly code
    @Override
    public List<Event> loadResponses(String stream) {
        try {
            InputStream jsonFileStream = applicationContext
                    .getResource(getDataDirectory(stream))
                    .getInputStream();

            return Arrays.asList(mapper.readValue(jsonFileStream, Event[].class));
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
