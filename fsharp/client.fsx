#r "packages/FSharp.Data/lib/net40/FSharp.Data.dll"

open System
open FSharp.Data

let streamId = 0 
let result = Http.RequestString (sprintf "https://playing-with-projections.herokuapp.com/stream/%i" streamId)

type Event<'T> = {
    Id: string
    Timestamp: DateTime
    Payload: 'T
}

type Events = 
    | PlayerHasRegistered of PlayerHasRegistered
    | UnknownEvent
and PlayerHasRegistered = {
    PlayerId: int
    LastName: string
    FirstName: string
}

let parsePayload (payload:JsonValue) = function
    | _ -> UnknownEvent

let parseEvent (event:JsonValue) =
    { 
        Id = event.GetProperty("id").AsString()
        Timestamp = event.GetProperty("timestamp").AsDateTime()
        Payload = parsePayload (event.GetProperty("payload")) (event.GetProperty("payload")) 
    }

let parse events =
    events
    |> Seq.map parseEvent

let json = JsonValue.Parse result
let events =
    match json with
    | JsonValue.Array events -> parse events
    | _ -> failwith "Unrecognized stream"
printfn "%A" events
