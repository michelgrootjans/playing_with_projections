package be.tothepoint.projections;

import org.junit.Test;

import static be.tothepoint.projections.EventStreamFactory.buildBasicQuizEventSream;
import static be.tothepoint.projections.EventStreamFactory.buildSingleQuestionEventSream;
import static org.junit.Assert.assertEquals;

public class NrOfPlayersProjectionTest {
    @Test
    public void projectSingleQuestionQuiz() {
        final NrOfPlayersProjection projection = new NrOfPlayersProjection();
        final Long nrOfPlayers = projection.project(buildSingleQuestionEventSream());
        assertEquals(1L, nrOfPlayers.longValue());

    }

    @Test
    public void projectBasicQuiz() {
        final NrOfPlayersProjection projection = new NrOfPlayersProjection();
        final Long nrOfPlayers = projection.project(buildBasicQuizEventSream());
        assertEquals(3L, nrOfPlayers.longValue());

    }

}