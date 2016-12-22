package be.tothepoint.application;

import java.util.List;

import be.tothepoint.model.Event;
import be.tothepoint.model.EventStreamProvider;
import be.tothepoint.model.projections.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectionRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectionRunner.class);
  private final EventStreamProvider eventStreamProvider;
  private final List<Projection> projections;

  public ProjectionRunner(EventStreamProvider eventStreamProvider, List<Projection> projections) {
    this.eventStreamProvider = eventStreamProvider;
    this.projections = projections;
  }

  public void run(String stream) throws Exception {
    final List<Event> events = eventStreamProvider.loadResponses(stream);
    runAllProjections(events);
  }

  private void runAllProjections(List<Event> events) {
    logStart(events);
    projections.forEach(x -> projectEventStream(events, x));
    logEnd();
  }

  private void logStart(List<Event> events) {
    LOGGER.info("Event stream loaded with " + events.size() + " events.");
    LOGGER.info("Starting projections ");
    LOGGER.info("---------------------------------------------------------------");
  }

  @SuppressWarnings("unchecked")
  private void projectEventStream(List<Event> events, Projection x) {
    final Object project = x.project(events);
    final String resultMessage = x.buildResultMessage(project);
    LOGGER.info(x.getClass().getSimpleName() + " says [" + resultMessage + "]");
  }

  private void logEnd() {
    LOGGER.info("---------------------------------------------------------------");
    LOGGER.info("End projections ");
  }
}

