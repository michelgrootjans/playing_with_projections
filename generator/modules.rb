module Statistics
  module HashToFields
    def method_missing *args
      @options[args.first]
    end
  end

  module EventGenerator
    def generate_event(type, timestamp, payload)
      {
          id: SecureRandom.uuid,
          type: type,
          timestamp: timestamp,
          payload: payload
      }
    end
  end

  module TimeHelpers
    def a_few_seconds
      (1.0 + Random.new.rand(60.0)) /(24*60*60)
    end

    def a_few_minutes
      (1.0 + Random.new.rand(10.0)) /(24*60)
    end

    def a_few_days
      1.0 + Random.new.rand(15.0)
    end
  end
end
