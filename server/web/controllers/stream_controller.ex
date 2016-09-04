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

        sort_events = fn(event1, event2) ->
            t1 = Timex.parse!(event1.meta.timestamp, "{ISO:Extended}")
            t2 = Timex.parse!(event2.meta.timestamp, "{ISO:Extended}")
            Timex.compare(t1, t2) < 1
        end

        file
        |> Poison.Parser.parse!(keys: :atoms!)
        |> Enum.map(&(Events.json_to_event(&1)))
        |> Enum.sort(&(sort_events.(&1, &2)))
    end
end
