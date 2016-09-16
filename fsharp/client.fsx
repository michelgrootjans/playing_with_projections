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

type Events = 
    | PlayerHasRegistered of PlayerHasRegistered.Root
    | UnknownEvent

let parsePayload (payload:JsonValue) = function
    | "PlayerHasRegistered" -> 
        PlayerHasRegistered (PlayerHasRegistered.Parse(payload.ToString()))
    | _ -> UnknownEvent

let parseEvent (event:JsonValue) =
    { 
        Id = event?id.AsString()
        Timestamp = event?timestamp.AsDateTime()
        Payload = parsePayload event?payload (event.["type"].AsString())
    }

let fetchEvents streamId = 
    let json = Http.RequestString (sprintf "https://playing-with-projections.herokuapp.com/stream/%i" streamId) |> JsonValue.Parse
    match json with
    | JsonValue.Array events -> events |> Seq.map parseEvent
    | _ -> failwith "Unrecognized stream"

fetchEvents 0 |> printfn "%A"
