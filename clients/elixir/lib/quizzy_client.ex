defmodule QuizzyClient do
    alias QuizzyClient.Events.{QuizWasCreated, PlayerHasRegistered, QuestionAddedToGame}
    alias QuizzyClient.Projections.Players

    def main do
        %{body: body} = HTTPotion.get "http://localhost:4000/api/stream/1"

        events = body
        |> Poison.Parser.parse!(keys: :atoms!)
        |> Enum.map(&(parse(&1)))

        players = Enum.reduce(events, Players.new, fn event, state -> Players.project(state, event) end)

        Players.player_id_exists?(players, 1)
    end

    def parse(%{event: name, data: data}) do
        case name do
            "QuizWasCreated" -> parse_quiz_was_created(data)
            "PlayerHasRegistered" -> parse_player_has_registered(data)
            "QuestionAddedToGame" -> parse_question_added_to_game(data)
        end
    end

    def parse_quiz_was_created(%{
        event_id: event_id,
        created_at: created_at,
        owner_id: owner_id,
        quiz_id: quiz_id,
        quiz_title: quiz_title
    }) do
        %QuizWasCreated{
            event_id: event_id,
            created_at: created_at,
            owner_id: owner_id,
            quiz_id: quiz_id,
            quiz_title: quiz_title
        }
    end

    def parse_player_has_registered(%{
        created_at: created_at,
        event_id: event_id,
        player_id: player_id,
        first_name: first_name,
        last_name: last_name
    } = event) do
        %PlayerHasRegistered{
            event_id: event_id,
            created_at: created_at,
            player_id: player_id,
            first_name: first_name,
            last_name: last_name
        }
    end

    def parse_question_added_to_game(%{
        created_at: created_at,
        event_id: event_id,
        quiz_id: quiz_id,
        question_id: question_id,
        question: question,
        answer: answer
    } = event) do
        %QuestionAddedToGame{
            created_at: created_at,
            event_id: event_id,
            quiz_id: quiz_id,
            question_id: question_id,
            question: question,
            answer: answer
        }
    end
end
