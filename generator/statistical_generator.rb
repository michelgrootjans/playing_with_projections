require 'faker'
require 'rubystats'
require 'time'
require_relative 'modules'
require_relative 'models'


module Statistics
  class Generator
    def generate_history
      startup_date = DateTime.parse('2016-1-1T08:00:00')
      top_date = startup_date + 150
      puts top_date
      players = (1..2).map { Player.generate(Rubystats::NormalDistribution.new(top_date)) }
      quizzes = players.map(&:create_quizzes)
      [players, quizzes].flatten
          .map(&:events).flatten
          .select{|e| e[:timestamp] > startup_date && e[:timestamp] < DateTime.now }
          .sort_by { |e| e[:timestamp] }
          .each{|e| e[:timestamp] = e[:timestamp].to_time.utc.iso8601}
    end
  end
end
