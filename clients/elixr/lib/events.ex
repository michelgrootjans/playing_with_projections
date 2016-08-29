defmodule QuizzyClient.Events do
  @moduledoc """
  List of the quizzy domain events
  """
  defmodule QuizWasCreated do
    @derive [Poison.Encoder]
    defstruct [:event_id, :created_at, :quiz_id, :quiz_title, :owner_id]
  end

  defmodule PlayerHasRegistered do
    @derive [Poison.Encoder]
    defstruct [:event_id, :created_at, :player_id, :first_name, :last_name]
  end

  defmodule QuestionAddedToGame do
    @derive [Poison.Encoder]
    defstruct [:event_id, :created_at, :question_id, :quiz_id, :question, :answer]
  end
end
