using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace projections
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var events = GetEvents(args);

            Console.WriteLine("Number of events: {0}", EventCounter.Count(events));
        }

        private static IEnumerable<Event> GetEvents(string[] args)
        {
            return FileNames(args)
                .Select(file => new FileEventReader().Read(file))
                .Select(events => new JsonEventParser().    Parse(events))
                .Aggregate((accu, events) => accu.Union(events));
        }

        private static IEnumerable<string> FileNames(string[] args)
        {
            if (!args.Any()) throw new ArgumentException("Expected a file or directory as parameter");
            if (args[0].EndsWith(".json")) return new[] {args[0]};

            return Directory.GetFiles(args[0])
                .Where(file => file.EndsWith(".json"));
        }
    }

    public class EventCounter
    {
        public static int Count(IEnumerable<Event> events)
        {
            return events.Count();
        }
    }
}
