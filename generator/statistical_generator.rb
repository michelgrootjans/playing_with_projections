require 'faker'
require 'rubystats'
require_relative 'modules'

module Statistics
  class Generator
    def generate_history
      registration_date_generator = Rubystats::NormalDistribution.new(DateTime.now, 100)
      players = (1..10).map { Player.generate(registration_date_generator) }
      [players].flatten
          .map(&:events).flatten
          .select{|e| DateTime.parse(e[:timestamp]) < DateTime.now }
          .sort_by { |e| e[:timestamp] }
    end
  end


  class Player
    def self.generate date_generator
      Player.new(
          id: SecureRandom.uuid,
          first_name: Faker::Name.first_name,
          last_name: Faker::Name.last_name,
          registered_at: date_generator.rng
      )
    end

    include HashToFields
    include EventGenerator

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
