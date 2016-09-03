module Statistics
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

  class StarWarsTheme
    def title
      [
          lambda{ "Vehicles in #{Faker::StarWars.planet}" },
          lambda{ "The battle of #{Faker::StarWars.planet}" },
          lambda{ "#{Faker::StarWars.character}'s quiz" },
          lambda{ "The #{Faker::StarWars.planet} quiz" },
          lambda{ "Only for #{Faker::StarWars.species}s" },
          lambda{ Faker::StarWars.quote },
      ].sample.call
    end

    def question_and_answer
      [
          lambda{ ["Who owns #{Faker::StarWars.droid}?", Faker::StarWars.character] },
          lambda{ ["What vehicle does #{Faker::StarWars.character} use?", Faker::StarWars.vehicle] },
          lambda{ ["What species is #{Faker::StarWars.character}?", Faker::StarWars.specie] },
          lambda{ ["Who said '#{Faker::StarWars.quote}'?", Faker::StarWars.character] },
          lambda{ ["Where is #{Faker::StarWars.character} from?", Faker::StarWars.planet] },
      ].sample.call
    end
  end

  class SuperheroTheme
    def title
      [
          lambda{ "Superhero quiz" },
          lambda{ "Superpowers!" },
      ].sample.call
    end

    def question_and_answer
      [
          lambda{ ["What power does #{Faker::Superhero.name} have?", Faker::Superhero.power] },
          lambda{ ["Who has #{Faker::Superhero.power}?", Faker::Superhero.name] },
      ].sample.call
    end
  end

  class PokemonTheme
    def title
      [
          lambda{ "Pokemon!" },
          lambda{ "Gotta catch em all" },
      ].sample.call
    end

    def question_and_answer
      [
          lambda{ ["Where can I find #{Faker::Pokemon.name}?", Faker::Pokemon.location] },
          lambda{ ["What pokemon can I find in #{Faker::Pokemon.location}?", Faker::Pokemon.name] },
      ].sample.call
    end
  end
end