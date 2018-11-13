require 'json'
require 'date'

def read_from_file file_path
  puts "Reading from '#{file_path}'"
  file_content = File.read(file_path)
end

def transform_date event
  event['timestamp'] = DateTime.parse(event['timestamp'])
  event
end

stream = ARGV.first || '../data/0.json'

raw_data = read_from_file(stream)

events = JSON.parse(raw_data).map(&method(:transform_date))
puts "Number of events: #{events.count}"
