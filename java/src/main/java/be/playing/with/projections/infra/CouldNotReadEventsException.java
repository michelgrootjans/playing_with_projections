package be.playing.with.projections.infra;

class CouldNotReadEventsException extends RuntimeException {

  CouldNotReadEventsException(Exception cause) {
    super(cause);
  }
}
