require 'faker'

class HistoryGenerator
  def generate_history(number_of_games)
    (1..number_of_games).map { generate_game }.flatten.sort_by { |message| message[:event][:timestamp] }
  end

  private

  def generate_game
    game_id = SecureRandom.uuid
    game_created = Faker::Time.between(DateTime.now - 365, DateTime.now)
    events = []
    events << game_created(game_id, game_created)
    (2..Random.new.rand(20)).each do
      quiz_id = SecureRandom.uuid
      quiz_start = Faker::Time.between(game_created, DateTime.now)
      quiz_end = Faker::Time.between(quiz_start, quiz_start + (1.0/24))
      events << quiz_started(game_id, quiz_id, quiz_start)
      events << quiz_ended(game_id, quiz_id, quiz_end) if quiz_end < Time.now
    end
    events.flatten.compact

  end

  def game_created(game_id, timestamp)
    {
        event: event_created('game_created', timestamp),
        payload: {game: {id: game_id}}
    }
  end

  def quiz_started(game_id, quiz_id, timestamp)
    {
        event: event_created('quiz_started', timestamp),
        payload: {game_id: game_id, quiz_id: quiz_id}
    }
  end

  def quiz_ended(game_id, quiz_id, timestamp)
    {
        event: event_created('quiz_ended', timestamp),
        payload: {game_id: game_id, quiz_id: quiz_id}
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