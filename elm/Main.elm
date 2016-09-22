module Main exposing (..)

import Html exposing (..)
import Html.App exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Html.Attributes exposing (..)
import Http
import Task exposing (Task)

import Events exposing (Event, decodeEvents)

type Msg
    = ErrorOccurred String
    | EventsFetched (List Event)

fetchEvents : String -> Cmd Msg
fetchEvents stream =
    Http.get decodeEvents ("https://playing-with-projections.herokuapp.com/stream/" ++ stream)
        |> Task.mapError toString
        |> Task.perform ErrorOccurred EventsFetched

type alias Model =
    { events : List Event
    }


init : ( Model, Cmd Msg )
init =
    { events = [] } ! [ (fetchEvents "0") ]


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
        ErrorOccurred error ->
            Debug.log error (model ! [])

        EventsFetched events ->
            { model | events = events } ! []


view : Model -> Html Msg
view model =
    div [] (List.map (text << toString) model.events)
