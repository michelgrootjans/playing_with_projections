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
           var stream_number = 0;
           if(args.Count() > 0) stream_number = int.Parse(args[0]);
           var data = new RestEventReader().Read($"http://playing-with-projections.herokuapp.com/stream/{stream_number}");
           var json = new JsonEventParser().Parse(data);
           var projector = new Projector(json);
        }
    }

    public class Projector
    {
      public Projector(IEnumerable<Event> events)
      {
        Console.WriteLine("Number of events: {0}", events.Count());
      }
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
