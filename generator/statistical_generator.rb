require 'faker'
require 'rubystats'
require_relative 'modules'
require_relative 'models'

module Statistics
  class Generator
    def generate_history
      startup_date = DateTime.parse('2016-1-1T08:00:00')
      top_date = startup_date + 150
      puts top_date
      players = (1..2).map { Player.generate(Rubystats::NormalDistribution.new(top_date)) }
      quizzes = (1..2).map{ Quiz.generate(Rubystats::NormalDistribution.new(top_date))}
      [players, quizzes].flatten
          .map(&:events).flatten
          .select{|e| e[:timestamp] > startup_date && e[:timestamp] < DateTime.now }
          .each{|e| e[:timestamp] = e[:timestamp].strftime("%DT%T.%L")}
          .sort_by { |e| e[:timestamp] }
    end
  end
end
