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

let parsePayload payload = function
    | "PlayerHasRegistered" -> PlayerHasRegistered (PlayerHasRegistered.Parse(payload))
    | "QuizWasCreated" -> QuizWasCreated (QuizWasCreated.Parse(payload))
    | "QuestionAddedToQuiz" -> QuestionAddedToQuiz (QuestionAddedToQuiz.Parse(payload))
    | "QuizWasPublished" -> QuizWasPublished (QuizWasPublished.Parse(payload))
    | e -> failwithf "Unknown event %s" e

let parseEvent (event:JsonValue) =
    { 
        Id = event?id.AsString()
        Timestamp = event?timestamp.AsDateTime()
        Payload = parsePayload (event?payload.ToString()) (event.["type"].AsString())
    }

let fetchEvents streamId = 
    let json = Http.RequestString (sprintf "https://playing-with-projections.herokuapp.com/stream/%i" streamId) |> JsonValue.Parse
    match json with
    | JsonValue.Array events -> events |> Seq.map parseEvent
    | _ -> failwith "Unrecognized stream"

fetchEvents 0 |> printfn "%A"
