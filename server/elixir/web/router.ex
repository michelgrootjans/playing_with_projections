defmodule Quizzy.Router do
  use Quizzy.Web, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/api", Quizzy do
    pipe_through :api
  end
end
