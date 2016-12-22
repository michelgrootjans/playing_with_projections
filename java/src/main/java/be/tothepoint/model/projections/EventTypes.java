package be.tothepoint.model.projections;

class EventTypes {

  static final String PLAYER_JOINED_GAME = "PlayerJoinedGame";
  static final String PLAYER_HAS_REGISTERED = "PlayerHasRegistered";
  static final String GAME_WAS_STARTED = "GameWasStarted";
  static final String QUESTION_WAS_ASKED = "QuestionWasAsked";
  static final String ANSWER_WAS_GIVEN = "AnswerWasGiven";
  static final String QUESTION_WAS_COMPLETED = "QuestionWasCompleted";
  static final String GAME_WAS_OPENED = "GameWasOpened";
  static final String GAME_WAS_FINISHED = "GameWasFinished";

  private EventTypes() {
  }
}
