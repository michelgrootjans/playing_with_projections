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

### Ruby

The server implementation is a sinatra app.

To prepare the server:
```
cd server/ruby
bundle install
```

To generate some data
```
ruby server/ruby/generate.rb
```

To run the server:
```
ruby server/ruby/app.rb
```

## Ruby client
To prepare the client:
```
cd client/ruby
bundle install
```

To run the client
```
ruby clients/ruby/client.rb
```
or to read from a specific stream
```
ruby clients/ruby/client.rb stream1
```
