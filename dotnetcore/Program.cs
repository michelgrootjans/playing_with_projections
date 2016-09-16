using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace projections
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var client = new RestClient();

            Console.WriteLine(client.Get("http://playing-with-projections.herokuapp.com/stream/0").Result);

        }
    }
}
