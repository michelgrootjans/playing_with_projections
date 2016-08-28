require 'faker'
require 'pry'

class GameOfThronesTheme
  def title
    Faker::Book.title
  end
end

class Game
  attr_reader :id, :title, :created_at, :published_at
  def initialize
    @id = SecureRandom.uuid
    @created_at = Faker::Time.between(DateTime.now - 365, DateTime.now)
    @published_at = created_at + Random.new.rand(60*60*24)
    @theme = GameOfThronesTheme.new
    @title = @theme.title
  end

  def new_quiz
    Quiz.new(self)
  end
end

class Quiz
  attr_reader :id, :game_id, :started_at, :ended_at
  def initialize(game)
    @id = SecureRandom.uuid
    @game_id = game.id
    @started_at = Faker::Time.between(game.published_at, DateTime.now)
    @ended_at = @started_at + 10 + Random.new.rand(60*60)
  end

  def ended?
    ended_at < Time.now
  end
end

class HistoryGenerator
  def generate_history(number_of_games)
    (1..number_of_games).map { generate_game }.flatten.sort_by { |message| message[:event][:timestamp] }
  end

  private

  def generate_game
    game = Game.new
    events = []
    events << game_created(game)
    events << game_published(game)
    (2..Random.new.rand(20)).each do
      quiz = game.new_quiz
      events << quiz_started(quiz)
      events << quiz_ended(quiz) if quiz.ended?
    end
    events.flatten.compact

  end

  def game_created(game)
    {
        event: event_created('game_created', game.created_at),
        payload: {game: {
            id: game.id,
            title: game.title
        }}
    }
  end

  def game_published(game)
    {
        event: event_created('game_published', game.published_at),
        payload: {game_id: game.id}
    }
  end

  def quiz_started(quiz)
    {
        event: event_created('quiz_started', quiz.started_at),
        payload: {game_id: quiz.game_id, quiz_id: quiz.id}
    }
  end

  def quiz_ended(quiz)
    {
        event: event_created('quiz_ended', quiz.ended_at),
        payload: {game_id: quiz.game_id, quiz_id: quiz.id}
    }
  end

  def event_created(type, timestamp)
    {
        type: type,
        id: SecureRandom.uuid,
        timestamp: timestamp
    }
  end

end