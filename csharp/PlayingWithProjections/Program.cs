using System;
using System.Collections.Generic;
using System.Linq;

namespace PlayingWithProjections
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var streamId = GetStreamId(args);
            var rawData = ReadData(streamId);
            var events = new JsonEventParser().Parse(rawData);
            var projector = new Projector(events);

            Console.WriteLine("Number of events: {0}", projector.NumberOfEvents);
        }

        private static string ReadData(string streamId)
        {
            return new FileEventReader().Read($"../../../../data/{streamId}.json");
        }

        private static string GetStreamId(string[] args)
        {
            return args.Any() ? args[0] : "0";
        }
    }

    public class Projector
    {
        readonly IEnumerable<Event> events;

        public Projector(IEnumerable<Event> events)
        {
            this.events = events;
        }

        public int NumberOfEvents
        {
            get { return events.Count(); }
        }
    }
}