using System.Collections.Generic;
using Newtonsoft.Json;

namespace PlayingWithProjections
{
    public class JsonEventParser
    {
      public IEnumerable<Event> Parse(string data)
      {
          return JsonConvert.DeserializeObject<IEnumerable<Event>>(data);
      }
    }
}
