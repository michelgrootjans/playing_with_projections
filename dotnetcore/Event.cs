using System;
using System.Linq;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO;

namespace projections
{
    public class Event
    {
      public string id {get; set;}
      public string type {get; set;}
      public DateTime timestamp {get; set;}
      public Dictionary<string, string> payload{get;set;}
    }
}
