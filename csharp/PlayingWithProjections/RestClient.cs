using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;

namespace PlayingWithProjections
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
