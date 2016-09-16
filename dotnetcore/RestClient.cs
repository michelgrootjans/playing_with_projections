using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace projections
{
    public class RestClient{
        public RestClient(){}

        public async Task<JArray> Get(string url){


            var client = new HttpClient();

            var response = await client.GetAsync(url);

            response.EnsureSuccessStatusCode();

            var result = await response.Content.ReadAsStringAsync();

            return JArray.Parse(result);
        }

    }
}