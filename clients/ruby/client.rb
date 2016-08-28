require 'json'
require 'rest-client'

base_uri = 'http://localhost:4567'
stream = "#{base_uri}/#{ARGV.first}"
puts "Reading from '#{stream}'"

response = RestClient.get stream
hashed_response = JSON.parse(response)
puts hashed_response.inspect
