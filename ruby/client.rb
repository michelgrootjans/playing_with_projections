require 'json'
require 'rest-client'

def read_from_uri stream_id
  # stream = "https://playing-with-projections.herokuapp.com/stream/#{stream_id}"
 stream = "https://raw.githubusercontent.com/tcoopman/playing_with_projections_server/master/data/#{stream_id}.json"
  puts "Reading from '#{stream}'"
  RestClient.get stream
end

def read_from_file stream_id
  file_path = "../data/#{stream_id}.json"
  puts "Reading from '#{file_path}'"
  file_content = File.read(file_path)
end

stream_id = ARGV.first || 0

raw_data = read_from_uri(stream_id)
# raw_data = read_from_file(stream_id)

events = JSON.parse(raw_data)
puts "Number of events: #{events.count}"
