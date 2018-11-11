defmodule QuizzyClient.MixProject do
  use Mix.Project

  def project do
    [
      app: :quizzy_client,
      version: "0.1.0",
      elixir: "~> 1.7",
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:logger]
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      {:poison, "~> 2.0"},
      {:credo, "~> 0.10", only: [:dev, :test]}
    ]
  end
end
