package be.tothepoint;

import be.tothepoint.projections.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ProjectionsCommandLineRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectionsCommandLineRunner.class);
    private final EventStreamProvider eventStreamProvider;
    private final List<Projection> projections;

    public ProjectionsCommandLineRunner(EventStreamProvider eventStreamProvider, List<Projection> projections) {
        this.eventStreamProvider = eventStreamProvider;
        this.projections = projections;
    }

    @Override
    public void run(String... args) throws Exception {
        final String stream = getStreamId(args);
        final List<Event> events = eventStreamProvider.loadResponses(stream);
        runAllProjections(events);
    }

    private void runAllProjections(List<Event> events) {
        logStart(events);
        projections.forEach(x -> projectEventStream(events, x));
        logEnd();
    }

    private void logEnd() {
        LOGGER.info("---------------------------------------------------------------");
        LOGGER.info("End projections ");
    }

    private void logStart(List<Event> events) {
        LOGGER.info("Event stream loaded with " + events.size() + " events.");
        LOGGER.info("Starting projections ");
        LOGGER.info("---------------------------------------------------------------");
    }

    private String getStreamId(String[] args) {
        if (args.length < 1)
            return getDefaultStream();
        else
            return args[0];

    }

    private String getDefaultStream() {
        LOGGER.warn("A stream id was expected. Defaulting to Zero.");
        return "0";
    }

    @SuppressWarnings("unchecked")
    private void projectEventStream(List<Event> events, Projection x) {
        final Object project = x.project(events);
        final String resultMessage = x.buildResultMessage(project);
        LOGGER.info(x.getClass().getSimpleName() + " says [" + resultMessage + "]");
    }


}

