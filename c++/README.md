# C++ app

# Building app

I used Microsoft Visual Studio Enterprise 2017, VisualStudio.15.Release/15.5.7+27130.2036 (see your Help About).

A solution and project file are in [$/C++/app](/C++/app) which should build and run everything you need.

If you want to make it work on another platform, the source code should compile fine on any C++17 compiler.

# Running

You should find a $(SolutionDir) relative path is set up for debugging already for all the platforms/configurations available.

This points to the smallest of the the workshop json files that all the flavours of *app* use. You can change the filename using the project properties debugging tab command line arguments if you want to focus on another file or to process all the json files in the directory.