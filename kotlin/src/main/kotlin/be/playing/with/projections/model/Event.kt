package be.playing.with.projections.model
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDateTime

data class Event(val id: String, val type: EventType, val timestamp: LocalDateTime, val payload: Map<String, String>)

enum class EventType(@get:JsonValue val representation: String) {

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
    QUIZ_WAS_PUBLISHED("QuizWasPublished");


    companion object {
        fun ofRepresentation(representation: String): EventType = EventType.values().first { it.representation == representation }
    }
}
