package be.playing.with.projections.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static be.playing.with.projections.model.EventFilter.*;
import static be.playing.with.projections.model.Projection.events;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ProjectionShould {

    private final List<Event> basicQuizEventStream = Collections.unmodifiableList(EventStreamFactory.buildBasicQuizEventSream());
    private final List<Event> singleQuestions = EventStreamFactory.buildSingleQuestionEventSream();

    @Test
    public void projectSingleQuestionQuiz() {
        final int nrOfPlayers = events().count().project(singleQuestions).get();
        assertEquals(8, nrOfPlayers);
    }

    @Test
    public void projectBasicQuiz() {
        final int nrOfPlayers = events().count().project(basicQuizEventStream).get();
        assertEquals(17, nrOfPlayers);
    }

    @Test
    public void result_into_empty_list_if_no_event_passes_filter(){
        Optional<List<Event>> empty = events().filter(ev -> false).project(basicQuizEventStream);

        assertThat(empty.isPresent(), is(true));
        assertThat(empty.get().size(), is(0));
    }

    @Test
    public void provide_means_to_filter_events(){
        final Function<EventType, Function<Event, Boolean>> byType = type -> event -> event.type.equals(type);
        final Function<Event, Boolean> isPlayerJoinedGame = byType.apply(EventType.PLAYER_JOINED_GAME);

        final Projection<List<Event>> projection = events().filter(isPlayerJoinedGame);
        final Optional<List<Event>> playerJoinedGameEvents = projection.project(basicQuizEventStream);

        assertThat(playerJoinedGameEvents.get().size(), is(3));
    }

    @Test
    public void provide_means_to_filter_by_type_and_id(){
        final Function<EventType, Function<Event, Boolean>> byType = type -> event -> event.type.equals(type);
        final Function<Event, Boolean> isPlayerJoinedGame = byType.apply(EventType.PLAYER_JOINED_GAME);

        final Function<String, Function<Event, Boolean>> byId = id -> event -> event.id.equals(id);
        final Function<Event, Boolean> isEvent4 = byId.apply("Event_4");

        final Projection<List<Event>> projection = events().filter(event -> {
            return isEvent4.apply(event) && isPlayerJoinedGame.apply(event);
        });

        final Optional<List<Event>> playerJoinedGameEventsWithId1 = projection.project(basicQuizEventStream);
        assertThat(playerJoinedGameEventsWithId1.get().size(), is(1));
    }

    @Test
    public void provide_means_to_filer_by_type_and_and_id_with_combinator(){
        final EventFilter isPlayerJoinedGameAndEvent4 = byType(EventType.PLAYER_JOINED_GAME).and(byId("Event_4"));

        final Optional<List<Event>> result = events().filter(isPlayerJoinedGameAndEvent4).project(basicQuizEventStream);

        assertThat(result.get().size(), is(1));
    }

    @Test
    public void filter_player_joined_or_game_was_started(){
        final EventFilter playerJoinedOrGameWasStarted = byType(EventType.PLAYER_JOINED_GAME)
                                                            .or(byType(EventType.GAME_WAS_STARTED));

        final Optional<List<Event>> result = events().filter(playerJoinedOrGameWasStarted).project(basicQuizEventStream);

        assertThat(result.get().size(), is(4));
    }

    @Test
    public void filter_events_before_specific_date(){
        final LocalDateTime in2Days = LocalDateTime.now().plusDays(2);

        final Optional<List<Event>> result = events().filter(before(in2Days)).project(basicQuizEventStream);

        assertThat(result.get().size(), is(17));
    }

    @Test
    public void filter_events_after_specific_date(){
        final LocalDateTime in2Days = LocalDateTime.now().plusDays(2);

        final Optional<List<Event>> result = events().filter(after(in2Days)).project(basicQuizEventStream);

        assertThat(result.get().size(), is(0));
    }

    private static <T> T todo(String what) {
        throw new UnsupportedOperationException(what);
    }
}
