# QuizzyClient

There are two choices to be made. First, you have to decide whether you want to install Elixir and dependencies locally or whether you prefer to rely on a docker based setup, avoiding the need for any local installations in case you have docker already setup on your machine.

Secondly, in order to verify your solutions, you can choose to build tests for your production code in the form of ExUnit tests. Alternatively, you can implement a verification technique (e.g. via `IO.inspect` or similar) in the production code directly.

Both approaches are supported by this code base and briefly described in the following sections.

## run with a local installation
This assumes that you have Elixir setup on your machine, the minimum required version is Elixir v1.7

From within this folder, install the required dependencies:

`mix deps.get`

Then, either go for the ExUnit test based approach and run

`mix test`

Or, run the production code and verify its output like so:

`mix run -e QuizzyClient.main`

## run with docker-compose
Provided you have docker installed and running in a reasonably recent version, you can use `docker-compose` to fetch the latest, official Elixir image and run the production code (QuizzyClient.main) like so:

`docker-compose up`

In case you want to switch to an ExUnit based approach, please see the comments in the `docker-compose.yml` file that resides next to this README in the same folder.
