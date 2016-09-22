Client requirement:
java 1.8

To build and run all tests:
> ./gradlew build

To run the unit tests only:
> ./gradlew test

To run the client, running all projections:
> ./gradlew bootRun
The b.t.ProjectionsCommandLineRunner will print out the projections result message


By default the eventstream is retrieved from the server. In case of network problems run the application with the spring profile fs.
Add -Dspring.profiles.active=fs to your VM Options

There are different streams of events. If no stream is specified the system will default to zero. 
The stream id is taken as the first argument pass to the application.
Add 1 to the program arguments.
