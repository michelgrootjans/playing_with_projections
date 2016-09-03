require 'time'

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
          timestamp: timestamp.to_time.utc.iso8601,
          payload: payload
      }
    end
  end
end
