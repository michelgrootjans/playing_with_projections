package be.tothepoint.projections;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventTypeProjectionTest {

    @Test
    public void project() throws Exception {
        final EventTypeProjection projection = new EventTypeProjection();
        final List<String> eventTypes = projection.project(EventStreamFactory.buildSingleQuestionEventSream());

        assertEquals(7, eventTypes.size());
        assertEquals(EventTypes.GAME_WAS_OPENED, eventTypes.get(0));
        assertEquals(EventTypes.PLAYER_JOINED_GAME, eventTypes.get(1));
        assertEquals(EventTypes.GAME_WAS_STARTED, eventTypes.get(2));
        assertEquals(EventTypes.QUESTION_WAS_ASKED, eventTypes.get(3));
        assertEquals(EventTypes.ANSWER_WAS_GIVEN, eventTypes.get(4));
        assertEquals(EventTypes.QUESTION_WAS_COMPLETED, eventTypes.get(5));
        assertEquals(EventTypes.GAME_WAS_FINISHED, eventTypes.get(6));

    }


}