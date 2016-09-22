package be.tothepoint.infra;

import be.tothepoint.Event;
import be.tothepoint.ResponseProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

class JsonFileResponseProvider implements ResponseProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileResponseProvider.class);
    private ObjectMapper mapper;
    private ApplicationContext applicationContext;

    public JsonFileResponseProvider(ObjectMapper mapper, ApplicationContext applicationContext) {
        this.mapper = mapper;
        this.applicationContext = applicationContext;
    }

    //TODO ugly code
    @Override
    public List<Event> loadResponses(String streamId) {
        logStart(streamId);
        try {
            InputStream jsonFileStream = applicationContext
                    .getResource(getDataDirectory(streamId))
                    .getInputStream();

            return Arrays.asList(mapper.readValue(jsonFileStream, Event[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void logStart(String stream) {
        LOGGER.info("Loading event stream with id " + stream + " from file system.");
    }

    private String getDataDirectory(String stream) {
        String currentAbsolutePath = new File("").toPath().toAbsolutePath().toString();
        String rootPath = currentAbsolutePath.split("java")[0];

        return "file://" + rootPath + "data/" + stream + ".json";
    }
}
