defmodule QuizzyClientTest do
  @moduledoc """
  This is a working ExUnit test. It serves as a
  placeholder and starting point in case you decide
  to derive your solutions guided by tests.
  """
  use ExUnit.Case

  @doc """
  Please note that in this test we make use of raw
  QuizzyClient features and also make use of a hard
  coded file path. You may find value in extracting
  some of these aspects into your production code
  eventually.
  """
  test "that number of events in stream 0 is 4" do
    file = File.read!("../data/0.json")

    events = file
    |> Poison.Parser.parse!(%{keys: :atoms})
    |> Enum.map(&(QuizzyClient.parse(&1)))

    assert length(events) == 4
  end
end