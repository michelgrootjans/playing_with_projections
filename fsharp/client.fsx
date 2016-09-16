#r "packages/FSharp.Data/lib/net40/FSharp.Data.dll"

open System
open FSharp.Data
open FSharp.Data.JsonExtensions

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
        Id = event?id.AsString()
        Timestamp = event?timestamp.AsDateTime()
        Payload = parsePayload event?payload (event.["type"].AsString())
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
