require 'json'
require 'rest-client'

response = RestClient.get 'http://localhost:4567/'
puts JSON.parse(response,:symbolize_names => true)
