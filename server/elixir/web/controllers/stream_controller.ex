defmodule Quizzy.StreamController do
    @moduledoc """
    Controller module for event streams
    """

    use Quizzy.Web, :controller
    use Timex

    alias Quizzy.Events

    @repo %{1 => [
        %Events.PlayerHasRegistered{
            event_id: UUID.uuid4(),
            created_at: Timex.now,
            player_id: 1,
            first_name: "Thomas",
            last_name: "Coopman"},
        %Events.QuizWasCreated{
            event_id: UUID.uuid4(),
            created_at: Timex.now,
            quiz_id: 1,
            quiz_title: "Cities of Europe",
            owner_id: 1},
        %Events.QuestionAddedToQuiz{
            event_id: UUID.uuid4(),
            created_at: Timex.now,
            question_id: 1,
            quiz_id: 1,
            question: "What is the capital of France?",
            answer: "Paris"
        },
    ]}

    def show(conn, %{"id" => id}) do
        {id, _} = Integer.parse(id)
        stream = Map.fetch!(@repo, id)
        render conn, "show.json", stream: stream
    end

end
