package be.tothepoint.infra;

class CouldNotReadEventsException extends RuntimeException {

  CouldNotReadEventsException(Exception cause) {
    super(cause);
  }
}
