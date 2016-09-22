module Events exposing (Event, decodeEvents)

import Json.Encode
import Json.Decode exposing ((:=), at)
import Json.Decode.Extra exposing ((|:))

type Event
    = EPlayerHasRegistered PlayerHasRegistered
    | EQuizWasCreated QuizWasCreated
    | EQuizWasPublished QuizWasPublished
    | EQuestionAddedToQuiz QuestionAddedToQuiz


type alias PlayerHasRegistered =
    { player_id : Int
    , last_name : String
    , first_name : String
    , id: String
    , timestamp : String
    }


type alias QuizWasCreated =
    { quiz_title : String
    , quiz_id : Int
    , owner_id : Int
    , id : String
    , timestamp : String
    }

type alias QuizWasPublished =
    { quiz_id : Int
    , id : String
    , timestamp : String
    }


type alias QuestionAddedToQuiz =
    { quiz_id : Int
    , question_id : Int
    , question : String
    , id : String
    , timestamp : String
    , answer : String
    }


decodeEvents : Json.Decode.Decoder (List Event)
decodeEvents =
    Json.Decode.list decodeEvent


decodeEvent : Json.Decode.Decoder Event
decodeEvent =
    ("type" := Json.Decode.string) `Json.Decode.andThen` eventInfo


eventInfo : String -> Json.Decode.Decoder Event
eventInfo eventName =
    case eventName of
        "PlayerHasRegistered" ->
            Json.Decode.map (\e -> EPlayerHasRegistered e) decodePlayerHasRegistered

        "QuizWasCreated" ->
            Json.Decode.map (\e -> EQuizWasCreated e) decodeQuizWasCreated

        "QuizWasPublished" ->
            Json.Decode.map (\e -> EQuizWasPublished e) decodeQuizWasPublished

        "QuestionAddedToQuiz" ->
            Json.Decode.map (\e -> EQuestionAddedToQuiz e) decodeQuestionAddedToQuiz

        _ ->
            Json.Decode.fail (eventName ++ " is not a recognized event")


decodePlayerHasRegistered : Json.Decode.Decoder PlayerHasRegistered
decodePlayerHasRegistered =
    Json.Decode.succeed PlayerHasRegistered
        |: (at ["payload", "player_id"] Json.Decode.int)
        |: (at ["payload", "last_name"] Json.Decode.string)
        |: (at ["payload", "first_name"] Json.Decode.string)
        |: (at ["id"] Json.Decode.string)
        |: (at ["timestamp"] Json.Decode.string)


decodeQuizWasCreated : Json.Decode.Decoder QuizWasCreated
decodeQuizWasCreated =
    Json.Decode.succeed QuizWasCreated
        |: (at ["payload", "quiz_title"] Json.Decode.string)
        |: (at ["payload", "quiz_id"] Json.Decode.int)
        |: (at ["payload", "owner_id"] Json.Decode.int)
        |: (at ["id"] Json.Decode.string)
        |: (at ["timestamp"] Json.Decode.string)

decodeQuizWasPublished : Json.Decode.Decoder QuizWasPublished
decodeQuizWasPublished =
    Json.Decode.succeed QuizWasPublished
        |: (at ["payload", "quiz_id"] Json.Decode.int)
        |: (at ["id"] Json.Decode.string)
        |: (at ["timestamp"] Json.Decode.string)

decodeQuestionAddedToQuiz : Json.Decode.Decoder QuestionAddedToQuiz
decodeQuestionAddedToQuiz =
    Json.Decode.succeed QuestionAddedToQuiz
        |: (at ["payload", "quiz_id"] Json.Decode.int)
        |: (at ["payload", "question_id"] Json.Decode.int)
        |: (at ["payload", "question"] Json.Decode.string)
        |: (at ["id"] Json.Decode.string)
        |: (at ["timestamp"] Json.Decode.string)
        |: (at ["payload", "answer"] Json.Decode.string)


