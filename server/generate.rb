require 'json'
require 'securerandom'
require 'date'

def generate_game
  data = {
    event: {
      type: 'game_created',
      id: SecureRandom.uuid,
      timestamp: DateTime.now
    },
    payload: {
      game: {
        id: SecureRandom.uuid,
        title: 'Game of Thrones quiz'
      }
    }
  }
end

stream_name = ARGV.first || 'stream1'
file_name = "streams/#{stream_name}.json"

puts "Generating #{file_name}"

data = []
data << generate_game

open(file_name, 'w') do |file|
  file.truncate(0)
  file.puts data.to_json
end
