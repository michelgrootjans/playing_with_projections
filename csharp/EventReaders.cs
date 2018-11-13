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

}
