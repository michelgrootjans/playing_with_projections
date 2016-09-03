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
          timestamp: timestamp.strftime('%FT%T.%L'),
          payload: payload
      }
    end
  end
end