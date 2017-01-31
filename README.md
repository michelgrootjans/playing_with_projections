# Important information for DDDEurope.

If you follow this workshop at [DDDEurope](https://dddeurope.com/2017/), please read this README.
It would be nice if you already clone this repo and check a client in your favorite technology.
If the client runs that is enough to get started. We will complete the first steps together in the workshop.

# Getting started
Clone or fork this repo.

# Choose your preferred technology
Look in the client directories to get started in your preferred technology:
- [Java](/java)
- [C# (classic)](/csharp)
- [C# (dotnet core)](/dotnetcore)
- [F#](/fshap)
- [javascript](/js)
- [Ruby](/ruby)
- [Elixir](/elixir)
- [Elm](/elm)
- [Php](/php)

# Make it run
Read the README.md of the choosen technology, and make it run.

# First step
Modify the code to print:
- The number of events in the stream.
- The distinct type of events in the stream.

# Short description
The event stream contains several types of events. The event stream is a JSON array of events. An event is structured like this:
```
{
  'id': 'a unique id for this event'
  'type': 'the type of the event',
  'timestamp': 'the moment the event happened',
  'payload': {
    // key-value pairs specific to the event type
    'key1': 'value1',
    'key2': 'value2';
    ...
  }
}
```
