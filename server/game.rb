class Game
  attr_reader :id, :title, :theme, :created_at, :published_at

  def initialize
    @id = SecureRandom.uuid
    @created_at = Faker::Time.between(DateTime.now - 365, DateTime.now)
    @published_at = created_at + Random.new.rand(60*60*24)
    @theme = GameOfThronesTheme.new
    @title = @theme.title
  end

  def new_question
    Question.new(self)
  end

  def new_quiz
    Quiz.new(self)
  end
end

class Question
  attr_reader :id, :game_id, :text, :right_answer, :created_at
  def initialize(game)
    @id = SecureRandom.uuid
    @game_id = game.id
    @created_at = game.created_at + Random.new.rand(60*60)

    @text, @right_answer = game.theme.question_and_answer
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

class GameOfThronesTheme
  def title
    [
        lambda{ "Do you know the house of #{Faker::GameOfThrones.house}" },
        lambda{ "All about #{Faker::GameOfThrones.character}" },
        lambda{ "What happened in #{Faker::GameOfThrones.city}" },
    ].sample.call
  end

  def question_and_answer
    [
        lambda{ ["Who killed #{Faker::GameOfThrones.character}?", Faker::GameOfThrones.character] },
        lambda{ ["What color is the banner of the house of #{Faker::GameOfThrones.house}?", Faker::Color.color_name] },
        lambda{ ["Where was #{Faker::GameOfThrones.character} killed?", Faker::GameOfThrones.city] },
        lambda{ ["Where was #{Faker::GameOfThrones.character} born?", Faker::GameOfThrones.city] },
        lambda{ ["Where did #{Faker::GameOfThrones.character} first meet  #{Faker::GameOfThrones.character}?", Faker::GameOfThrones.city] },
    ].sample.call
  end
end

