defmodule Quizzy.Events do
  @moduledoc """
  List of the quizzy domain events
  """
  defmodule QuizWasCreated do
    @enforce_keys [:event_id, :created_at, :quiz_id, :quiz_title, :owner_id]
    defstruct [:event_id, :created_at, :quiz_id, :quiz_title, :owner_id]
  end

  defmodule QuizWasPublished do
    @enforce_keys [:event_id, :created_at, :quiz_id]
    defstruct [:event_id, :created_at, :quiz_id]
  end

  defmodule PlayerHasRegistered do
    @enforce_keys [:event_id, :created_at, :player_id, :first_name, :last_name]
    defstruct [:event_id, :created_at, :player_id, :first_name, :last_name]
  end

  defmodule QuestionAddedToGame do
    @enforce_keys [:event_id, :created_at, :question_id, :quiz_id, :question, :answer]
    defstruct [:event_id, :created_at, :question_id, :quiz_id, :question, :answer]
  end

  defmodule GameWasOpened do
    @enforce_keys [:event_id, :created_at, :quiz_id, :game_id]
    defstruct [:event_id, :created_at, :quiz_id, :game_id]
  end

  defmodule GameWasStarted do
    @enforce_keys [:event_id, :created_at, :game_id]
    defstruct [:event_id, :created_at, :game_id]
  end

  defmodule PlayerJoinedGame do
    @enforce_keys [:event_id, :created_at, :game_id, :player_id]
    defstruct [:event_id, :created_at, :game_id, :player_id]
  end

  defmodule PlayerLeftGame do
    @enforce_keys [:event_id, :created_at, :game_id, :player_id]
    defstruct [:event_id, :created_at, :game_id, :player_id]
  end

  defmodule QuestionWasOpened do
    @enforce_keys [:event_id, :created_at, :game_id, :question_id]
    defstruct [:event_id, :created_at, :game_id, :question_id]
  end

  defmodule QuestionWasAnswered do
    @enforce_keys [:event_id, :created_at, :game_id, :question_id, :answer, :answer_time]
    defstruct [:event_id, :created_at, :game_id, :question_id, :answer, :answer_time]
  end

  defmodule QuestionWasClosed do
    @enforce_keys [:event_id, :created_at, :game_id, :question_id]
    defstruct [:event_id, :created_at, :game_id, :question_id]
  end

  defmodule GameWasFinished do
    @enforce_keys [:event_id, :created_at, :game_id, :question_id]
    defstruct [:event_id, :created_at, :game_id, :question_id]
  end
end
