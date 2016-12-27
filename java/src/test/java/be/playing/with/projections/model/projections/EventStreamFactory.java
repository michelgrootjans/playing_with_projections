package be.playing.with.projections.model.projections;

import be.playing.with.projections.model.Event;
import be.playing.with.projections.EventBuilder;
import be.playing.with.projections.model.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventStreamFactory {

    private EventStreamFactory() {
    }

    public static List<Event> buildSingleQuestionEventSream() {
        final List<Event> events = new ArrayList<>();
        int eventId = 1;
        events.add(buildPlayerHasRegistered(eventId++));
        events.add(buildGameWasOpened(eventId++));
        events.add(buildPlayerJoinedTeamEvent(eventId++));
        events.add(buildGameStarted(eventId++));
        events.add(buildQuestionWasAsked(eventId++));
        events.add(buildAnswerGiven(eventId++));
        events.add(buildQuestionWasCompleted(eventId++));
        events.add(buildGameWasFinished(eventId++));
        return events;
    }

    public static List<Event> buildBasicQuizEventSream() {
        final List<Event> events = new ArrayList<>();
        int eventId = 1;
        events.add(buildPlayerHasRegistered(eventId++));
        events.add(buildPlayerHasRegistered(eventId++));
        events.add(buildPlayerHasRegistered(eventId++));
        events.add(buildPlayerJoinedTeamEvent(eventId++));
        events.add(buildPlayerJoinedTeamEvent(eventId++));
        events.add(buildPlayerJoinedTeamEvent(eventId++));
        events.add(buildGameStarted(eventId++));
        events.add(buildQuestionWasAsked(eventId++));
        events.add(buildAnswerGiven(eventId++));
        events.add(buildAnswerGiven(eventId++));
        events.add(buildQuestionWasCompleted(eventId++));
        events.add(buildQuestionWasAsked(eventId++));
        events.add(buildAnswerGiven(eventId++));
        events.add(buildQuestionWasCompleted(eventId++));
        events.add(buildQuestionWasAsked(eventId++));
        events.add(buildAnswerGiven(eventId++));
        events.add(buildQuestionWasCompleted(eventId++));
        return events;
    }

    private static Event buildPlayerJoinedTeamEvent(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.PLAYER_JOINED_GAME)
                .build();
    }

    private static Event buildPlayerHasRegistered(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.PLAYER_HAS_REGISTERED)
                .build();
    }

    private static Event buildQuestionWasAsked(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.QUESTION_WAS_ASKED)
                .build();
    }

    private static Event buildGameStarted(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.GAME_WAS_STARTED)
                .build();
    }

    private static Event buildAnswerGiven(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.ANSWER_WAS_GIVEN)
                .build();
    }

    private static Event buildQuestionWasCompleted(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.QUESTION_WAS_COMPLETED)
                .build();
    }

    private static Event buildGameWasOpened(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.GAME_WAS_OPENED)
                .build();
    }

    private static Event buildGameWasFinished(int id) {
        return EventBuilder.newBuilder()
                .withId("Event_" + id)
                .withTimestamp(LocalDateTime.now())
                .withType(EventType.GAME_WAS_FINISHED)
                .build();
    }
}
