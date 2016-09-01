defmodule Quizzy.Router do
  use Quizzy.Web, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", Quizzy do
    pipe_through :api

    resources "/stream", StreamController, only: [:show]
  end
end
