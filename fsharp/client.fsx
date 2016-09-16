#r "packages/FSharp.Data/lib/net40/FSharp.Data.dll"

open System
open FSharp.Data
open FSharp.Data.JsonExtensions

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
    | "PlayerHasRegistered" -> 
        PlayerHasRegistered { 
            PlayerId = payload.["player_id"].AsInteger()
            LastName = payload.["last_name"].AsString()
            FirstName = payload.["first_name"].AsString()
        }
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
