using System;
using System.Linq;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO;

namespace projections
{
    public class JsonEventParser
    {
      public IEnumerable<Event> Parse(string data)
      {
          return JsonConvert.DeserializeObject<IEnumerable<Event>>(data);
      }
    }
}
