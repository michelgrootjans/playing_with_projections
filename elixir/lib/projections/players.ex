defmodule QuizzyClient.Projections.Players do
  @moduledoc """
  A projection containing the players.
  How do you handle unique user names in a es/cqrs system?

  The event registered could fail if the player_id is not unique. Only happening
  if the 2 users register the same username at the same time...
  What's the chance of this happening?
  See also: http://codebetter.com/gregyoung/2010/08/12/eventual-consistency-and-set-validation/


  So in this projection we don't do anything.
  We could send a command (ResolveDuplicatePlayerId)
  or have we could have a process manager detect duplicate user names and do something with it.
  We do realize that this projection could be wrong because of this
  """

  alias QuizzyClient.Events.PlayerHasRegistered

  defmodule Player do
    defstruct [:player_id, :name]
  end

  def new, do: %{}

  def project(%{} = state, %PlayerHasRegistered{player_id: p_id, first_name: first_name, last_name: last_name}) do
    case player_id_exists?(state, p_id) do
      true -> state
      false -> Map.put(state, p_id, %Player{player_id: p_id, name: first_name})
    end
  end

  def project(%{} = state, event), do: state

  def player_id_exists?(%{} = state, player_id) do
    Map.has_key?(state, player_id)
  end
end
