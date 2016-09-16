using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace playing_with_projections
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            WebClient c;
            c = new WebClient();
            string json =  c.DownloadString(@"https://playing-with-projections.herokuapp.com/stream/0");

            dynamic deserializedObjects = JsonConvert.DeserializeObject(json);

            foreach (var deserobj in deserializedObjects)
            {
                Console.WriteLine(deserobj.type);
            }
        }

    }
}
