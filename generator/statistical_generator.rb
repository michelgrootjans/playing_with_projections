require 'faker'

module Statistics
  class Generator
    def generate_history
      (1..10).map { Player.generate() }.map(&:events).flatten.sort_by { |e| e[:timestamp] }
    end
  end


  module HashToFields
    def method_missing *args
      @options[args.first]
    end
  end

  module EventGenerator
    def generate_event(type, timestamp, payload)
      {
          id: SecureRandom.uuid,
          type: type,
          timestamp: timestamp.strftime('%FT%T.%L'),
          payload: payload
      }
    end
  end

  class Player
    include HashToFields
    include EventGenerator

    def self.generate
      Player.new(
          id: SecureRandom.uuid,
          first_name: Faker::Name.first_name,
          last_name: Faker::Name.last_name,
          registered_at: DateTime.now
      )
    end

    def initialize(options)
      @options = options
    end

    def events
      [
          generate_event(
              'PlayerHasRegistered',
              registered_at,
              player_id: id,
              first_name: first_name,
              last_name: last_name
          )
      ]
    end

  end
end
