ELK stack:
![Screenshot](kibana-quiz-dash.png?raw=true)

Client requirement:
java 1.8

To build and run all tests:
> ./gradlew build

To run the server, that will load the event stream into an embedded elasticsearch:
> ./gradlew bootRun

Grab a kibana < 5.0.0 since it's not longer compatible
https://www.elastic.co/downloads/past-releases/kibana-4-6-2
unpack & cd 
> ./bin/kibana

There are different streams of events. If no stream is specified the system will default to zero. 
The stream id is taken as the first argument pass to the application.
Add the stream id (0,1,2) to the program arguments.

To load a different set of parameters, adjust this line of code inside the ElkApplication.kt:
> events.save(loadEvents("2"))

After the application has started a basic kibana dashboard can be loaded:

> npm install elasticdump -g

> cd src/main/kibana

> ./load_dashboard.sh

Kibana should be available at http://localhost:5601. It's possible that the first time the query/index needs to be defined. This is 'events' and then select the timestamp field (there is only one option).