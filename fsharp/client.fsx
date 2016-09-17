#r "packages/FSharp.Data/lib/net40/FSharp.Data.dll"

open System
open FSharp.Data
open FSharp.Data.JsonExtensions

type Event<'T> = {
    Id: string
    Timestamp: DateTime
    Payload: 'T
}

type PlayerHasRegistered = JsonProvider<"""{"player_id":1,"last_name":"Coopman","first_name":"Thomas"}""">
type QuizWasCreated = JsonProvider<"""{"quiz_title":"Cities of Europe","quiz_id":1,"owner_id":1}""">
type QuestionAddedToQuiz = JsonProvider<"""{"quiz_id":1,"question_id":1,"question":"What is the capital of France?","answer":"Paris"}""">
type QuizWasPublished = JsonProvider<"""{"quiz_id":1}""">

type Events = 
    | PlayerHasRegistered of PlayerHasRegistered.Root
    | QuizWasCreated of QuizWasCreated.Root
    | QuestionAddedToQuiz of QuestionAddedToQuiz.Root
    | QuizWasPublished of QuizWasPublished.Root
    | IgnoredEvent of string

let parsePayload payload = function
    | "PlayerHasRegistered" -> PlayerHasRegistered (PlayerHasRegistered.Parse(payload))
    | "QuizWasCreated" -> QuizWasCreated (QuizWasCreated.Parse(payload))
    | "QuestionAddedToQuiz" -> QuestionAddedToQuiz (QuestionAddedToQuiz.Parse(payload))
    | "QuizWasPublished" -> QuizWasPublished (QuizWasPublished.Parse(payload))
    | e -> IgnoredEvent e

let parseEvent (event:JsonValue) =
    { 
        Id = event?id.AsString()
        Timestamp = event?timestamp.AsDateTime()
        Payload = parsePayload (event?payload.ToString()) (event.["type"].AsString())
    }

let parseEvents json = 
    match json with
    | JsonValue.Array events -> events |> Seq.map parseEvent
    | _ -> failwith "Unrecognized stream"

let fetchStream streamId =
    Http.RequestString (sprintf "https://playing-with-projections.herokuapp.com/stream/%i" streamId) 
    |> JsonValue.Parse

let readStreamFromFile streamId = 
    System.IO.File.ReadAllText(__SOURCE_DIRECTORY__ + (sprintf "/%d.json" streamId))
    |> JsonValue.Parse

let stream0 = fetchStream 0 |> parseEvents
stream0 |> printfn "%A"
