module Main exposing (..)

import Html exposing (..)
import Html.App exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Html.Attributes exposing (..)
import Http
import Json.Encode
import Json.Decode exposing ((:=), at)
import Json.Decode.Extra exposing ((|:))
import Task exposing (Task)


type Event
    = EPlayerHasRegistered PlayerHasRegistered
    | EQuizWasCreated QuizWasCreated
    | EQuestionAddedToGame QuestionAddedToGame


type alias PlayerHasRegistered =
    { player_id : Int
    , last_name : String
    , first_name : String
    , event_id : String
    , created_at : String
    }


type alias QuizWasCreated =
    { quiz_title : String
    , quiz_id : Int
    , owner_id : Int
    , event_id : String
    , created_at : String
    }


type alias QuestionAddedToGame =
    { quiz_id : Int
    , question_id : Int
    , question : String
    , event_id : String
    , created_at : String
    , answer : String
    }


type Msg
    = FetchEvents
    | ErrorOccurred String
    | EventsFetched (List Event)


decodeEvents : Json.Decode.Decoder (List Event)
decodeEvents =
    Json.Decode.list decodeEvent


decodeEvent : Json.Decode.Decoder Event
decodeEvent =
    ("event" := Json.Decode.string) `Json.Decode.andThen` eventInfo


eventInfo : String -> Json.Decode.Decoder Event
eventInfo eventName =
    case eventName of
        "PlayerHasRegistered" ->
            Json.Decode.map (\e -> EPlayerHasRegistered e) decodePlayerHasRegistered

        "QuizWasCreated" ->
            Json.Decode.map (\e -> EQuizWasCreated e) decodeQuizWasCreated

        "QuestionAddedToGame" ->
            Json.Decode.map (\e -> EQuestionAddedToGame e) decodeQuestionAddedToGame

        _ ->
            Json.Decode.fail (eventName ++ " is not a recognized event")


decodePlayerHasRegistered : Json.Decode.Decoder PlayerHasRegistered
decodePlayerHasRegistered =
    Json.Decode.succeed PlayerHasRegistered
        |: (at ["data", "player_id"] Json.Decode.int)
        |: (at ["data", "last_name"] Json.Decode.string)
        |: (at ["data", "first_name"] Json.Decode.string)
        |: (at ["data", "event_id"] Json.Decode.string)
        |: (at ["data", "created_at"] Json.Decode.string)


decodeQuizWasCreated : Json.Decode.Decoder QuizWasCreated
decodeQuizWasCreated =
    Json.Decode.succeed QuizWasCreated
        |: (at ["data", "quiz_title"] Json.Decode.string)
        |: (at ["data", "quiz_id"] Json.Decode.int)
        |: (at ["data", "owner_id"] Json.Decode.int)
        |: (at ["data", "event_id"] Json.Decode.string)
        |: (at ["data", "created_at"] Json.Decode.string)


decodeQuestionAddedToGame : Json.Decode.Decoder QuestionAddedToGame
decodeQuestionAddedToGame =
    Json.Decode.succeed QuestionAddedToGame
        |: (at ["data", "quiz_id"] Json.Decode.int)
        |: (at ["data", "question_id"] Json.Decode.int)
        |: (at ["data", "question"] Json.Decode.string)
        |: (at ["data", "event_id"] Json.Decode.string)
        |: (at ["data", "created_at"] Json.Decode.string)
        |: (at ["data", "answer"] Json.Decode.string)


fetchEvents : String -> Cmd Msg
fetchEvents stream =
    Http.get decodeEvents ("http://localhost:4000/api/stream/" ++ stream)
        |> Task.mapError toString
        |> Task.perform ErrorOccurred EventsFetched


type alias Model =
    { events : List Event
    }


init : ( Model, Cmd Msg )
init =
    { events = [] } ! [ (fetchEvents "1") ]


main =
    Html.App.program
        { init = init
        , update = update
        , view = view
        , subscriptions = \_ -> Sub.none
        }


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        FetchEvents ->
            model ! [ (fetchEvents "1") ]

        ErrorOccurred error ->
            Debug.log error (model ! [])

        EventsFetched events ->
            { model | events = events } ! []


view : Model -> Html Msg
view model =
    div [] (List.map (text << toString) model.events)
