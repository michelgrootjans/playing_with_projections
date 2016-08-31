defmodule Quizzy.StreamView do
    use Quizzy.Web, :view

    alias Quizzy.Events.{PlayerHasRegistered, QuizWasCreated, QuestionAddedToGame}

    def render("show.json", %{stream: stream}) do
        render_one(stream, Quizzy.StreamView, "event.json")
    end

    def render("event.json", %{stream: stream}) do
        stream
        |> Enum.map &(render_event &1)
    end

    def render_event(%PlayerHasRegistered{} = event) do
        %{event: "PlayerHasRegistered", data: event}
    end

    def render_event(%QuizWasCreated{} = event) do
        %{event: "QuizWasCreated", data: event}
    end

    def render_event(%QuestionAddedToGame{} = event) do
        %{event: "QuestionAddedToGame", data: event}
    end
end
