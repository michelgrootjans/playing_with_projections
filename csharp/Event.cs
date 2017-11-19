using System;
using System.Collections.Generic;

namespace PlayingWithProjections
{
    public class Event
    {
      public string id {get; set;}
      public string type {get; set;}
      public DateTime timestamp {get; set;}
      public Dictionary<string, string> payload{get;set;}
    }
}
