require 'json'
require 'rest-client'

base_uri = 'http://localhost:4000'
stream_id = ARGV.first || 0
stream = "#{base_uri}/stream/#{stream_id}"
puts "Reading from '#{stream}'"

response = RestClient.get stream
hashed_response = JSON.parse(response)
puts hashed_response.inspect
