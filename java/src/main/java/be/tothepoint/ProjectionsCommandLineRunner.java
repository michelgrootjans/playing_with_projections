package be.tothepoint;

import be.tothepoint.projections.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ProjectionsCommandLineRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ProjectionsCommandLineRunner.class);
    private final ResponseProvider responseProvider;
    private final List<Projection> projections;

    public ProjectionsCommandLineRunner(ResponseProvider responseProvider, List<Projection> projections) {
        this.responseProvider = responseProvider;
        this.projections = projections;
    }

    @Override
    public void run(String... args) throws Exception {
        final String stream = getStreamId(args);
        final List<Event> events = responseProvider.loadResponses(stream);
        projections.forEach(x -> projectEventStream(events, x));
    }

    private String getStreamId(String[] args) {
        if (args.length < 1) {
            log.error("A stream id was expected. Defaulting to Zero.");
            return "0";
        }
        return args[0];
    }

    @SuppressWarnings("unchecked")
    private void projectEventStream(List<Event> events, Projection x) {
        final Object project = x.project(events);
        final String resultMessage = x.buildResultMessage(project);
        log.info(resultMessage);
    }





}

