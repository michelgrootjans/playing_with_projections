package be.playing.with.projections;

import be.playing.with.projections.infra.FileEventStreamProvider;
import be.playing.with.projections.model.EventStreamProvider;
import be.playing.with.projections.model.projections.CountEventsProjection;

class Main {

  public static void main(String[] args) throws Exception {
    // Switch between File based access and Remote rest here!
    EventStreamProvider streamProvider = new FileEventStreamProvider();
//    EventStreamProvider streamProvider = new RestEventStreamProvider();

    CountEventsProjection countEventsProjection = new CountEventsProjection();

    int result = countEventsProjection.project(streamProvider.loadResponses(getStream(args)));
    System.out.println(countEventsProjection.buildResultMessage(result));
  }

  private static String getStream(String[] args) {
    if (args.length < 1) {
      return getDefaultStream();
    } else {
      return args[0];
    }
  }

  private static String getDefaultStream() {
    System.out.println("A stream id was expected. Defaulting to Zero.");
    return "0";
  }
}

