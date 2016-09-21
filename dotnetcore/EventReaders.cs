using System;
using System.Linq;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO;

namespace projections
{
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
