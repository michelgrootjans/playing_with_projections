defmodule Quizzy.Events do
  @moduledoc """
  List of the quizzy domain events
  """
  defmodule Meta do
    @enforce_keys [:id, :timestamp]
    defstruct [:id, :timestamp]
  end

  defmodule QuizWasCreated do
    @enforce_keys [:meta, :quiz_id, :quiz_title, :owner_id]
    defstruct [:meta, :quiz_id, :quiz_title, :owner_id]
  end

  defmodule QuizWasPublished do
    @enforce_keys [:meta, :quiz_id]
    defstruct [:meta, :quiz_id]
  end

  defmodule PlayerHasRegistered do
    @enforce_keys [:meta, :player_id, :first_name, :last_name]
    defstruct [:meta, :player_id, :first_name, :last_name]
  end

  defmodule QuestionAddedToQuiz do
    @enforce_keys [:meta, :question_id, :quiz_id, :question, :answer]
    defstruct [:meta, :question_id, :quiz_id, :question, :answer]
  end

  defmodule GameWasOpened do
    @enforce_keys [:meta, :quiz_id, :game_id]
    defstruct [:meta, :quiz_id, :game_id]
  end

  defmodule GameWasStarted do
    @enforce_keys [:meta, :game_id]
    defstruct [:meta, :game_id]
  end

  defmodule PlayerJoinedGame do
    @enforce_keys [:meta, :game_id, :player_id]
    defstruct [:meta, :game_id, :player_id]
  end

  defmodule PlayerLeftGame do
    @enforce_keys [:meta, :game_id, :player_id]
    defstruct [:meta, :game_id, :player_id]
  end

  defmodule QuestionWasOpened do
    @enforce_keys [:meta, :game_id, :question_id]
    defstruct [:meta, :game_id, :question_id]
  end

  defmodule AnswerWasGiven do
    @enforce_keys [:meta, :game_id, :question_id, :answer, :answer_time]
    defstruct [:meta, :game_id, :question_id, :answer, :answer_time]
  end

  defmodule QuestionTimedOut do
    @enforce_keys [:meta, :game_id, :question_id]
    defstruct [:meta, :game_id, :question_id]
  end

  defmodule QuestionWasClosed do
    @enforce_keys [:meta, :game_id, :question_id]
    defstruct [:meta, :game_id, :question_id]
  end

  defmodule GameWasFinished do
    @enforce_keys [:meta, :game_id, :question_id]
    defstruct [:meta, :game_id, :question_id]
  end
end
