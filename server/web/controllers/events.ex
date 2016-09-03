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
    @enforce_keys [:meta, :game_id]
    defstruct [:meta, :game_id]
  end

  defmodule GameWasCancelled do
    @enforce_keys [:meta, :game_id]
    defstruct [:meta, :game_id]
  end

    def json_to_event(%{type: type, id: id, timestamp: timestamp, payload: payload}) do
        case type do
            "QuizWasCreated" ->
                %QuizWasCreated{
                    meta: meta(id, timestamp),
                      quiz_id: payload.quiz_id,
                    quiz_title: payload.quiz_title,
                    owner_id: payload.owner_id
                }
            "QuizWasPublished" ->
                %QuizWasPublished{
                    meta: meta(id, timestamp),
                    quiz_id: payload.quiz_id,
                }
            "PlayerHasRegistered" ->
                %PlayerHasRegistered{
                    meta: meta(id, timestamp),
                    first_name: payload.first_name,
                    last_name: payload.last_name,
                    player_id: payload.player_id
                }
            "QuestionAddedToQuiz" ->
                %QuestionAddedToQuiz{
                    meta: meta(id, timestamp),
                    quiz_id: payload.quiz_id,
                    question_id: payload.question_id,
                    question: payload.question,
                    answer: payload.answer
                }
            "GameWasOpened" ->
                %GameWasOpened{
                    meta: meta(id, timestamp),
                    quiz_id: payload.quiz_id,
                    game_id: payload.game_id
                }
            "GameWasStarted" ->
                %GameWasStarted{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id
                }
            "PlayerJoinedGame" ->
                %PlayerJoinedGame{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                    player_id: payload.player_id
                }
            "PlayerLeftGame" ->
                %PlayerLeftGame{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                    player_id: payload.player_id
                }
            "QuestionWasOpened" ->
                %QuestionWasOpened{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                    question_id: payload.question_id

                }
            "AnswerWasGiven" ->
                %AnswerWasGiven{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                    question_id: payload.question_id,
                    answer: payload.answer,
                    answer_time: payload.answer_time
                }
            "QuestionTimedOut" ->
                %QuestionTimedOut{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                    question_id: payload.question_id,
                }
            "QuestionWasClosed" ->
                %QuestionWasClosed{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                    question_id: payload.question_id,
                }
            "GameWasFinished" ->
                %GameWasFinished{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                }
            "GameWasCancelled" ->
                %GameWasCancelled{
                    meta: meta(id, timestamp),
                    game_id: payload.game_id,
                }
        end
    end

    defp meta(id, timestamp) do
        %Meta{
            id: id,
            timestamp: timestamp
        }
    end
end
