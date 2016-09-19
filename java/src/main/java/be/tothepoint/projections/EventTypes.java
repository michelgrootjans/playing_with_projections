package be.tothepoint.projections;

public class EventTypes {
    public static final String PLAYER_JOINED_GAME = "PlayerJoinedGame";
    public static final String GAME_WAS_STARTED = "GameWasStarted";
    public static final String QUESTION_WAS_ASKED = "QuestionWasAsked";
    public static final String ANSWER_WAS_GIVEN = "AnswerWasGiven";
    public static final String QUESTION_WAS_COMPLETED = "QuestionWasCompleted";
    public static final String GAME_WAS_OPENED = "GameWasOpened";
    public static final String GAME_WAS_FINISHED = "GameWasFinished";

    private EventTypes() {
    }
}
