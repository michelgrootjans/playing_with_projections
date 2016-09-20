using System;
using System.Linq;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace projections
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var client = new RestClient();

            var projector = new Projector(client.Get("http://playing-with-projections.herokuapp.com/stream/0").Result);
        }
    }

    public class Projector
    {
      public Projector(JArray events)
      {
        var numberOfEvents = events.Count();
        Console.WriteLine("Number of events: {0}", numberOfEvents);
      }
    }
}
