require 'faker'
require 'pry'

require_relative 'game.rb'


class HistoryGenerator
  def generate_history(number_of_games)
    (1..number_of_games).map { generate_game }.flatten.sort_by { |message| message[:event][:timestamp] }
  end

  private

  def generate_game
    game = Game.new
    events = []
    events << game_created(game)

    ([5, 10, 20].sample).times do
      question = game.new_question
      events << question_added(question)
    end

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

  def question_added(question)
    {
        event: event_created('question_added_to_game', question.created_at),
        payload: {
            game_id: question.game_id,
            question: {
                id: question.id,
                text: question.text,
                right_answer: question.right_answer
            }
        }
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