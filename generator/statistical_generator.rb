require 'faker'
require 'rubystats'
require_relative 'modules'
require_relative 'models'

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
end
