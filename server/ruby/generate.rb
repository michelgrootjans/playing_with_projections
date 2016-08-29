require 'json'
require 'securerandom'
require 'date'

require_relative 'history_generator.rb'


stream_name = ARGV.first || 'stream1'
file_name = "streams/#{stream_name}.json"

puts "Generating #{file_name}"

history = HistoryGenerator.new.generate_history(100 + Random.new.rand(100))

open(file_name, 'w') do |file|
  file.truncate(0)
  file.puts history.to_json
end
