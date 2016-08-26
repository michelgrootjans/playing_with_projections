require 'sinatra'
require 'json'

set :port, 4567

get '/' do
  redirect to('/stream1')
end

get '/stream1' do
  {
    'hello' => 'world',
    'foo' => 'bar',
    'game' => {
      name: 'my first game',
      id: 5
    }
  }.to_json
end
