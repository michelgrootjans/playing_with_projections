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
To your VM Options add 
>-Dspring.profiles.active=fs 

There are different streams of events. If no stream is specified the system will default to zero. 
The stream id is taken as the first argument pass to the application.
Add the stream id (0,1,2) to the program arguments.
