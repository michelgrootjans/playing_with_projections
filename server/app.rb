require 'sinatra'
require 'json'

set :port, 4567

get '/' do
  redirect to('/stream1')
end

get '/:file_name' do |file_name|
  file = File.read ("#{file_name}.json")
  JSON.parse(file).to_json
end
