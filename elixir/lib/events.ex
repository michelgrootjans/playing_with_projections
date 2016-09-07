defmodule QuizzyClient.Events do
  @moduledoc """
  List of the quizzy domain events
  """
  defmodule Meta do
      @derive [Poison.Encoder]
      defstruct [:id, :timestamp]
  end
  defmodule QuizWasCreated do
    @derive [Poison.Encoder]
    defstruct [:meta, :quiz_id, :quiz_title, :owner_id]
  end

  defmodule PlayerHasRegistered do
    @derive [Poison.Encoder]
    defstruct [:meta, :player_id, :first_name, :last_name]
  end

  defmodule QuestionAddedToQuiz do
    @derive [Poison.Encoder]
    defstruct [:meta, :question_id, :quiz_id, :question, :answer]
  end

  defmodule QuizWasPublished do
      @derive [Poison.Encoder]
      defstruct [:meta, :quiz_id]
  end
end
