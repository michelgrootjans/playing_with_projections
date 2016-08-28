require 'sinatra'

set :port, 4567

get '/' do
  File.read ("streams/default.json")
end

get '/:file_name' do |file_name|
  File.read ("streams/#{file_name}.json")
end
