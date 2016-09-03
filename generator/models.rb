module Statistics
  class Player
    def self.generate date_generator
      Player.new(
          player_id: SecureRandom.uuid,
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
          generate_event('PlayerHasRegistered', registered_at, @options.except(:registered_at ))
      ]
    end

    def create_quizzes
      (1..6).map{ Quiz.generate(self) }
    end
  end

  require_relative 'themes'
  class Quiz
    def self.generate author
      Quiz.new(
          quiz_id: SecureRandom.uuid,
          owner_id: author.player_id,
          created_at: author.registered_at + 1.0 * Random.new.rand(1000)/10
      )
    end

    include HashToFields
    include EventGenerator

    def initialize(options)
      @options = options
      @theme = choose_theme
      @options[:quiz_title] = @theme.title
      @questions = (1..5).map{ Question.generate(self, @theme) }
    end

    def choose_theme
      [
          GameOfThronesTheme,
          StarWarsTheme,
          SuperheroTheme,
          PokemonTheme,
      ].sample.new
    end

    def events
      [
          generate_event('QuizWasCreated', created_at, @options.except(:created_at ))
      ] + @questions.map(&:events)
    end
  end

  class Question
    def self.generate(quiz, theme)
      q_and_a = theme.question_and_answer
      Question.new(
          question_id: SecureRandom.uuid,
          quiz_id: quiz.quiz_id,
          question: q_and_a.first,
          answer: q_and_a.last,
          created_at: quiz.created_at + (1.0 + Random.new.rand(15)) /(24*60*60)
      )
    end

    include HashToFields
    include EventGenerator

    def initialize(options)
      @options = options
    end

    def events
      [
          generate_event('QuestionAddedToQuiz', created_at, @options.except(:created_at ))
      ]
    end
  end
end