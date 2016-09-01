# Playing with Projections
Preparation of a workshop

## Server

The server implementation is a [phoenix](http://www.phoenixframework.org/) app.

background info (to create the project): `mix phoenix.new . --no-brunch --no-ecto --no-html --app quizzy`

To prepare the server:
```bash
cd server/elixir
mix deps.get
```

To run the server:

```bash
mix phoenix.server
```

to run the tests:
```bash
mix test
```
to run the test continuously:
```bash
mix test.watch
```

## Generator

To prepare the generator:
```
cd generator
bundle install
```

To generate some data
```
ruby generator/generate.rb
```

## Clients

Look in the client directories
