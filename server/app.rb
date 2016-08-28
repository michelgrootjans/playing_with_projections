require 'sinatra'

set :port, 4567

get '/' do
  redirect to('/stream1')
end

get '/:file_name' do |file_name|
  File.read ("#{file_name}.json")
end
