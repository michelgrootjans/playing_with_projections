package be.playing.with.projections.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventType {

  PLAYER_JOINED_GAME("PlayerJoinedGame"),
  PLAYER_HAS_REGISTERED("PlayerHasRegistered"),
  GAME_WAS_STARTED("GameWasStarted"),
  QUESTION_WAS_ASKED("QuestionWasAsked"),
  ANSWER_WAS_GIVEN("AnswerWasGiven"),
  QUESTION_WAS_COMPLETED("QuestionWasCompleted"),
  GAME_WAS_OPENED("GameWasOpened"),
  GAME_WAS_FINISHED("GameWasFinished"),
  QUIZ_WAS_CREATED("QuizWasCreated"),
  QUESTION_ADDED_TO_QUIZ("QuestionAddedToQuiz"),
  QUIZ_WAS_PUBLISHED("QuizWasPublished"),
  TIMER_HAS_EXPIRED("TimerHasExpired");

  private String representation;

  EventType(String representation) {
    this.representation = representation;
  }

  public static EventType ofRepresentation(String representation) {
    for (EventType current : EventType.values()) {
      if (current.representation.equals(representation)) {
        return current;
      }
    }
    throw new IllegalArgumentException(String.format("No Enum found for %s", representation));
  }

  @JsonValue
  public String getRepresentation() {
    return representation;
  }
}
