package be.playing.with.projections.model.projections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountEventsProjectionTest {
    @Test
    public void projectSingleQuestionQuiz() {
        final CountEventsProjection projection = new CountEventsProjection();
        final int nrOfPlayers = projection.project(EventStreamFactory.buildSingleQuestionEventSream());
        assertEquals(8, nrOfPlayers);

    }

    @Test
    public void projectBasicQuiz() {
        final CountEventsProjection projection = new CountEventsProjection();
        final int nrOfPlayers = projection.project(EventStreamFactory.buildBasicQuizEventSream());
        assertEquals(17, nrOfPlayers);

    }

}