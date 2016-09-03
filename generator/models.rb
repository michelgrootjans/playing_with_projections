module Statistics
  class Player
    include HashToFields
    include EventGenerator

    def initialize(options)
      @options = options
    end

    def self.generate date_generator
      Player.new(
          player_id: SecureRandom.uuid,
          first_name: Faker::Name.first_name,
          last_name: Faker::Name.last_name,
          registered_at: date_generator.rng
      )
    end

    def events
      [
          generate_event('PlayerHasRegistered', registered_at, @options.except(:registered_at ))
      ]
    end

    def create_quizzes
      (1..6).map{ Quiz.generate(self) }
    end
  end

  class Quiz
    include HashToFields
    include EventGenerator

    def initialize(options)
      @options = options
    end

    def self.generate author
      Quiz.new(
          quiz_id: SecureRandom.uuid,
          quiz_title: Faker::Lorem.sentence,
          owner_id: author.player_id,
          created_at: author.registered_at + 1.0 * Random.new.rand(1000)/10
      )
    end

    def events
      [
          generate_event('QuizWasCreated', created_at, @options.except(:created_at ))
      ]
    end
  end
end