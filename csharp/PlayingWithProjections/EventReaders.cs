using System;

namespace PlayingWithProjections
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
