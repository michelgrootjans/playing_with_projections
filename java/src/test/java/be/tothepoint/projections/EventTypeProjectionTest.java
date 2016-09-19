package be.tothepoint.projections;


import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventTypeProjectionTest {

    @Test
    public void project() throws Exception {
        final EventTypeProjection projection = new EventTypeProjection();
        final List<String> eventTypes = projection.project(EventStreamFactory.buildSingleQuestionEventSream());

        assertEquals(8, eventTypes.size());
        final Iterator<String> iterator = eventTypes.iterator();
        assertEquals(EventTypes.PLAYER_HAS_REGISTERED, iterator.next());
        assertEquals(EventTypes.GAME_WAS_OPENED, iterator.next());
        assertEquals(EventTypes.PLAYER_JOINED_GAME, iterator.next());
        assertEquals(EventTypes.GAME_WAS_STARTED, iterator.next());
        assertEquals(EventTypes.QUESTION_WAS_ASKED,iterator.next());
        assertEquals(EventTypes.ANSWER_WAS_GIVEN, iterator.next());
        assertEquals(EventTypes.QUESTION_WAS_COMPLETED, iterator.next());
        assertEquals(EventTypes.GAME_WAS_FINISHED, iterator.next());

    }


}