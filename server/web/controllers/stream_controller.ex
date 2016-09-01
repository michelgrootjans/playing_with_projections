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
        |> Enum.map(&(Events.json_to_event(&1)))
    end

end
