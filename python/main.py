import json


def parse_events_from_file(stream):
    with open("../data/" + stream + ".json") as json_file:
        events = json.load(json_file)

    return events


events = parse_events_from_file("0")
print(len(events))
