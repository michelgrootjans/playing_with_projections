defmodule QuizzyClient do
    alias QuizzyClient.Events.{Meta, QuizWasCreated, PlayerHasRegistered, QuestionAddedToQuiz, QuizWasPublished}

    def main do
        file = File.read!("../data/0.json")

        events = file
        |> Poison.Parser.parse!(keys: :atoms)
        |> Enum.map(&(parse(&1)))

        IO.inspect events
    end

    def parse(%{type: type} = event) do
        case type do
            "QuizWasCreated"      -> parse_event(QuizWasCreated, event)
            "PlayerHasRegistered" -> parse_event(PlayerHasRegistered, event)
            "QuestionAddedToQuiz" -> parse_event(QuestionAddedToQuiz, event)
            "QuizWasPublished"    -> parse_event(QuizWasPublished, event)
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
