Client requirement:
java 1.8

To build and run all tests:
> ./gradlew build --offline

To run the unit tests only:
> ./gradlew test --offline

To run the application, create a fatJar first, then execute it
> ./gradlew clean build fatJar --offline
> java -jar build/libs/ddd-workshop-0.0.1-SNAPSHOT-all.jar

To run with a specific stream, for example: data/1.json, run:
> java -jar build/libs/ddd-workshop-0.0.1-SNAPSHOT-all.jar ../data/1.json

By default the eventstream is retrieved from the server. In case of network problems Wire the FileEventStreamProvider 
in the Main class instead of the RestEventStreamProvider. 

There are different streams of events. If no stream is specified the system will default to zero. 
The stream id is taken as the first argument pass to the application.
Add the stream id (0,1,2) to the program arguments.
