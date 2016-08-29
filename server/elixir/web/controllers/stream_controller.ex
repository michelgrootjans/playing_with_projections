defmodule Quizzy.StreamController do
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
            owner_id: 1}
    ]}

    def show(conn, %{"id" => id}) do
        {_id, _} = Integer.parse(id)
        stream = Map.fetch!(@repo, _id)
        render conn, "show.json", stream: stream
    end

end
