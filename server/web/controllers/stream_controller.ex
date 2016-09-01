defmodule Quizzy.StreamController do
    @moduledoc """
    Controller module for event streams
    """

    use Quizzy.Web, :controller
    use Timex

    alias Quizzy.Events

    def show(conn, %{"id" => id}) do
        {id, _} = Integer.parse(id)
        repo = init()
        stream = Map.fetch!(repo, id)
        render conn, "show.json", stream: stream
    end

    defp init do
        %{1 => [
            %Events.PlayerHasRegistered{
                meta: meta(),
                player_id: 1,
                first_name: "Thomas",
                last_name: "Coopman"},
            %Events.QuizWasCreated{
                meta: meta(),
                quiz_id: 1,
                quiz_title: "Cities of Europe",
                owner_id: 1},
            %Events.QuestionAddedToQuiz{
                meta: meta(),
                question_id: 1,
                quiz_id: 1,
                question: "What is the capital of France?",
                answer: "Paris"
            },
            %Events.QuizWasPublished{
                meta: meta(),
                quiz_id: 1
            }
        ]}
    end

    defp meta do
        %Events.Meta{
            id: UUID.uuid4(),
            timestamp: Timex.now
        }
    end

end
