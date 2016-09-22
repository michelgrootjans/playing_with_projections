using System;
using System.Collections.Generic;
using System.Linq;

namespace PlayingWithProjections
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
}
