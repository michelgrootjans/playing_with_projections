using System;
using System.Linq;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO;

namespace projections
{
    public class Program
    {
        public static void Main(string[] args)
        {
           var stream_id = 0;
           if(args.Count() > 0) stream_id = int.Parse(args[0]);
           var raw_data = new RestEventReader().Read($"http://playing-with-projections.herokuapp.com/stream/{stream_id}");
           // var raw_data = new RestEventReader().Read($"https://raw.githubusercontent.com/tcoopman/playing_with_projections_server/master/data/{stream_id}.json");
           // var raw_data = new FileEventReader().Read($"../data/{stream_id}.json");
           var events = new JsonEventParser().Parse(raw_data);
           var projector = new Projector(events);

           Console.WriteLine("Number of events: {0}", projector.NumberOfEvents);
        }
    }

    public class Projector
    {
      readonly IEnumerable<Event> events;
      public Projector(IEnumerable<Event> events)
      {
        this.events = events;
      }
      public int NumberOfEvents{get{ return events.Count();}}
    }

    public class Event
    {
      public string id {get; set;}
      public string type {get; set;}
      public DateTime timestamp {get; set;}
      public Dictionary<string, string> payload{get;set;}
    }

    public class JsonEventParser
    {
      public IEnumerable<Event> Parse(string data)
      {
          return JsonConvert.DeserializeObject<IEnumerable<Event>>(data);
      }
    }

    public class FileEventReader
    {
      public string Read(string filePath)
      {
          Console.WriteLine($"Reading '{filePath}'");
          return System.IO.File.ReadAllText(filePath);
      }
    }

    public class RestEventReader
    {
      public string Read(string uri)
      {
        Console.WriteLine($"Reading '{uri}'");
        return new RestClient().Get(uri).Result.ToString();
      }
    }

}
