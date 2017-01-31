<?php


abstract class Event
{

    /**
     * @var string
     */
    private $id;
    /**
     * @var DateTimeImmutable
     */
    private $timestamp;
    /**
     * @var array
     */
    private $payload;

    final public function __construct(string $id, string $timestamp, array $payload)
    {
        $this->id = $id;
        $this->timestamp = new DateTimeImmutable($timestamp);
        $this->payload = $payload;
    }

    /**
     * @param array $data
     * @return Event
     */
    final public static function fromArray(array $data)
    {
        $class = $data['type'];
        if (!class_exists($class) || !is_subclass_of($class, Event::class)) {
            throw new InvalidArgumentException(sprintf("The given event type '%s' does not translate to a valid event class", $class));
        }

        $event = new $class($data['id'], $data['timestamp'], $data['payload']);

        return $event;
    }

    /**
     * @return string
     */
    public function getId(): string
    {
        return $this->id;
    }

    /**
     * @return DateTimeImmutable
     */
    public function getTimestamp(): DateTimeImmutable
    {
        return $this->timestamp;
    }

    /**
     * @return array
     */
    public function getPayload(): array
    {
        return $this->payload;
    }

    /**
     * @return string
     */
    public function __toString()
    {
        return json_encode([
            'type' => get_class($this),
            'id' => $this->getId(),
            'timestamp' => $this->getTimestamp(),
            'payload' => $this->getPayload()
        ]);
    }

}