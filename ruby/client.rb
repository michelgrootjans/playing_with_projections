require 'json'
require 'rest-client'

def read_from_uri stream_id
  stream = "https://playing-with-projections.herokuapp.com/stream/#{stream_id}"
  # stream = "https://raw.githubusercontent.com/tcoopman/playing_with_projections_server/master/data/#{stream_id}.json"
  puts "Reading from '#{stream}'"
  RestClient.get stream
end

def read_from_file file_path
  puts "Reading from '#{file_path}'"
  file_content = File.read(file_path)
end

def transform_date event
  event['timestamp'] = DateTime.parse(event['timestamp'])
  event
end

stream = ARGV.first || '../data/0.json'

# raw_data = read_from_uri(stream)
raw_data = read_from_file(stream)

events = JSON.parse(raw_data).map(&method(:transform_date))
puts "Number of events: #{events.count}"
