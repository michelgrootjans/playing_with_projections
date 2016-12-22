package be.tothepoint;

import java.util.Arrays;
import java.util.List;

import be.tothepoint.application.ProjectionRunner;
import be.tothepoint.infra.RestEventStreamProvider;
import be.tothepoint.model.EventStreamProvider;
import be.tothepoint.model.projections.EventTypeProjection;
import be.tothepoint.model.projections.NrOfPlayersProjection;
import be.tothepoint.model.projections.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    // Switch between File based access and Remote rest here!
    // EventStreamProvider streamProvider = new FileEventStreamProvider();
    EventStreamProvider streamProvider = new RestEventStreamProvider();
    List<Projection> projections = Arrays.asList(new EventTypeProjection(), new NrOfPlayersProjection());

    ProjectionRunner main = new ProjectionRunner(streamProvider, projections);
    main.run(getStream(args));
  }

  private static String getStream(String[] args) {
    if (args.length < 1) {
      return getDefaultStream();
    } else {
      return args[0];
    }
  }

  private static String getDefaultStream() {
    LOGGER.warn("A stream id was expected. Defaulting to Zero.");
    return "0";
  }
}

