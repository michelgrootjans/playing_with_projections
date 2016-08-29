# Playing with Projections
Preparation of a workshop

## Server

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
