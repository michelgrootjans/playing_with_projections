package be.playing.with.projections.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Projection<T> extends Function<List<Event>, Optional<T>> {

    static Projection<List<Event>> events(){
        return ProjectionSupport::emptyOrEvents;
    }

    @Override default Optional<T> apply(List<Event> events){
        return project(events);
    }

    Optional<T> project(List<Event> eventStream);

    default Projection<List<Event>> filter(Function<Event, Boolean> function) {
        return events -> Optional.of(events.stream().filter(function::apply).collect(Collectors.toList()));
    }

    default Projection<Integer> count(){
        return map(value -> {
            if(value instanceof Collection) {
                return ((Collection) value).size();
            }else if(value instanceof Map){
                return ((Map) value).size();
            }else{
                return 1;
            }
        });
    }

    default <S> Projection<S> map(Function<T, S> mapper){
        return events -> this.apply(events).map(mapper);
    }

    final class ProjectionSupport {
        private static Optional<List<Event>> emptyOrEvents(List<Event> events) {
            return events.isEmpty() ? Optional.empty() : Optional.of(events);
        }
    }
}
