defmodule Quizzy.StreamController do
    @moduledoc """
    Controller module for event streams
    """

    use Quizzy.Web, :controller
    use Timex

    alias Quizzy.Events

    def show(conn, %{"id" => id}) do
        repo = init()
        stream = Map.fetch!(repo, id)
        render conn, "show.json", stream: stream
    end

    defp init do
        is_json = fn file ->
            Path.extname(file) == ".json"
        end

        path = "../data"

        path
        |> File.ls!
        |> Enum.filter(&(is_json.(&1)))
        |> Enum.map(&({Path.rootname(&1), parse_json_file(Path.join(path, &1))}))
        |> Map.new
    end

    defp parse_json_file(file) do
        {:ok, file} = File.read file

        file
        |> Poison.Parser.parse!(keys: :atoms!)
        |> Enum.map(&(json_to_struct(&1)))
    end

    defp json_to_struct(%{type: type, id: id, timestamp: timestamp, payload: payload}) do
        case type do
            "PlayerHasRegistered" ->
                %Events.PlayerHasRegistered{
                    meta: meta(id, timestamp),
                    first_name: payload.first_name,
                    last_name: payload.last_name,
                    player_id: payload.player_id
                }
            "QuizWasCreated" ->
                %Events.QuizWasCreated{
                    meta: meta(id, timestamp),
                    quiz_id: payload.quiz_id,
                    quiz_title: payload.quiz_title,
                    owner_id: payload.owner_id
                }
            "QuizWasPublished" ->
                %Events.QuizWasPublished{
                    meta: meta(id, timestamp),
                    quiz_id: payload.quiz_id,
                }
            "QuestionAddedToQuiz" ->
                %Events.QuestionAddedToQuiz{
                    meta: meta(id, timestamp),
                    quiz_id: payload.quiz_id,
                    question_id: payload.question_id,
                    question: payload.question,
                    answer: payload.answer
                }
        end
    end

    defp meta(id, timestamp) do
        %Events.Meta{
            id: UUID.uuid4(),
            timestamp: Timex.now
        }
    end
end
