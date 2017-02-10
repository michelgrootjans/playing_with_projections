package be.playing.with.projections;

import be.playing.with.projections.infra.FileEventStreamProvider;
import be.playing.with.projections.model.Event;
import be.playing.with.projections.model.EventStreamProvider;
import static be.playing.with.projections.model.Projection.events;

import java.util.List;
import java.util.stream.Collectors;

class Main {

  public static void main(String[] args) throws Exception {
    // Switch between File based access and Remote rest here!
    EventStreamProvider streamProvider = new FileEventStreamProvider();
    // EventStreamProvider streamProvider = new RestEventStreamProvider();

    final List<Event> events = streamProvider.loadResponses(getStream(args));

    events().count().project(events).ifPresent(it -> System.out.printf("%s events counted in the stream\n", it));

    events().map(ev -> ev.stream().collect(Collectors.groupingBy(e -> e.type, Collectors.counting())))
            .count()
            .project(events)
            .ifPresent(it-> System.out.printf("%s different types counted in the stream\n", it));
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
    return "../data/0.json";
  }
}
