<?php

/**
 * Created by PhpStorm.
 * User: peterpacket
 * Date: 26/01/2017
 * Time: 00:17
 */
class EventStream
{
    const STREAM_URL = 'https://playing-with-projections.herokuapp.com/stream/';

    const STREAM_DIRECTORY = DATA_DIR . '/';

    public function fromUrl($streamId)
    {
        $jsonStream = file_get_contents(self::STREAM_URL . $streamId);
        return $this->parseEvents($jsonStream);
    }

    public function fromFile($streamId)
    {
        $jsonStream = file_get_contents(self::STREAM_DIRECTORY . $streamId . '.json');
        return $this->parseEvents($jsonStream);
    }

    private function parseEvents(string $json)
    {
        $unparsedEvents = json_decode($json, true);
        $events = array_map(function($eventArray) {
            return Event::fromArray($eventArray);
        }, $unparsedEvents);

        return $events;
    }

}