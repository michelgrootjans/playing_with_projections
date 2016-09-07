defmodule QuizzyClient do
    alias QuizzyClient.Events.{Meta, QuizWasCreated, PlayerHasRegistered, QuestionAddedToQuiz, QuizWasPublished}
    alias QuizzyClient.Projections.Players

    def main do
        %{body: body} = HTTPotion.get "https://playing-with-projections.herokuapp.com/stream/0"

        events = body
        |> Poison.Parser.parse!(keys: :atoms)
        |> Enum.map(&(parse(&1)))

        players = Enum.reduce(events, Players.new, fn event, state -> Players.project(state, event) end)

        Players.player_id_exists?(players, 1)
    end

    def parse(%{type: type} = event) do
        case type do
            "QuizWasCreated" -> parse_event(QuizWasCreated, event)
            "PlayerHasRegistered" -> parse_event(PlayerHasRegistered, event)
            "QuestionAddedToQuiz" -> parse_event(QuestionAddedToQuiz, event)
            "QuizWasPublished" -> parse_event(QuizWasPublished, event)
        end
    end

    def parse_event(event, %{id: id, timestamp: timestamp, payload: payload}) do
        e = struct(event, payload)
        %{e | meta: %Meta{
            id: id,
            timestamp: timestamp
        }}
    end
end
