require 'json'
require 'securerandom'
require 'date'

require_relative 'history_generator'
require_relative 'statistical_generator'


stream_name = ARGV.first || '1'
file_name = "../data/#{stream_name}.json"

puts "Generating #{file_name}"

# history = HistoryGenerator.new.generate_history(100 + Random.new.rand(100))
history = Statistics::Generator.new.generate_history

open(file_name, 'w') do |file|
  file.truncate(0)
  file.puts JSON.pretty_generate history
end
