// app.cpp : Defines the entry point for the console application.
//

#include "json/json.h"

#include <iostream>
#include <functional>
#include <fstream>
#include <sstream>

#include <experimental/filesystem>

namespace fs = std::experimental::filesystem;

void EnumerateFiles(int argc, char** argv, const std::function<void(std::istream&)>& streamReader)
{
    fs::path path{ fs::current_path() };

    if (argc == 2)
    {
        path = argv[1];
    }

    const auto fileOpener = [&](const fs::path& path)
    {
        std::ifstream ifstream(path.native());

        streamReader(ifstream);
    };

    if (fs::is_directory(path))
    {
        for (auto& file : fs::directory_iterator(path))
        {
            if (file.path().native().find(L".json") == std::wstring::npos)
            {
                continue;
            }

            std::wcout << file << std::endl;

            if (fs::is_regular_file(file.path()))
            {
                fileOpener(file);
            }
        }
    }
    else if (fs::is_regular_file(path))
    {
        fileOpener(path);
    }
}

bool EatWhitespace(std::istream& stream)
{
    if (stream.eof())
    {
        return false;
    }

    switch (auto peek = stream.peek(); peek)
    {
        case ' ':
        case '\0x0d':
        case '\0x0a':
        {
            char throwAway;
            stream >> throwAway;
        }

        default:
        {
            return true;
        }
    }
}

bool EatOptionalComma(std::istream& stream)
{
    if (stream.eof())
    {
        return false;
    }

    switch (auto peek = stream.peek(); peek)
    {
        case ',':
        {
            char throwAway;
            stream >> throwAway;
        }

        default:
        {
            return true;
        }
    }
}

bool EatJsonObject(std::istream& jsonStream, const std::function<void(const Json::Value&)>& jsonValueConsumer)
{
    Json::CharReaderBuilder jsonCharReaderBuilder;
    jsonCharReaderBuilder["collectComments"] = false;

    while (!jsonStream.eof())
    {
        if (!EatWhitespace(jsonStream))
        {
            return false;
        }

        char openObject{ 0 };
        jsonStream >> openObject;

        if (openObject != '{')
        {
            std::wcout << "expected start of object at position " << jsonStream.tellg();
            return false;
        }

        std::stringstream jsonObjectStream;
        jsonObjectStream << openObject;

        if (!EatWhitespace(jsonStream))
        {
            return false;
        }

        int braceDepth = 1;

        char octet{ 0 };

        while (!jsonStream.eof())
        {
            jsonStream >> octet; 
            if (octet == '{')
            {
                ++braceDepth;
            }
            else if (octet == '}')
            {
                if (--braceDepth == 0)
                {
                    break;
                }
            }

            jsonObjectStream << octet;
        }

        jsonObjectStream << '}' << std::ends;

        Json::Value jsonValue;
        JSONCPP_STRING errors;

        if (!Json::parseFromStream(jsonCharReaderBuilder, jsonObjectStream, &jsonValue, &errors))
        {
            std::wcout << "recieved >> " << errors.c_str() << " << from json object at position " << jsonStream.tellg();
            return false;
        }

        jsonValueConsumer(jsonValue);

        if (!EatWhitespace(jsonStream))
        {
            return false;
        }

        EatOptionalComma(jsonStream);
    }

    return true;
}

void EnumerateEvents(std::istream& jsonStream, const std::function<void (const Json::Value&)>& jsonValueConsumer)
{
    EatWhitespace(jsonStream);

    char openArray{ 0 };
    jsonStream >> openArray;
    if (openArray != '[')
    {
        std::wcout << "file needs to contain an array of events" << std::endl;
        return;
    }

    while (EatJsonObject(jsonStream, jsonValueConsumer))
    {
        EatWhitespace(jsonStream);
    }
}

int main(int argc, char** argv)
{ 
    EnumerateFiles(argc, argv, [&](std::istream& jsonStream)
    {
        EnumerateEvents(jsonStream, [&](const Json::Value& jsonValue) 
        {
            if (jsonValue["type"].asString() == "PlayerHasRegistered")
            {
                const auto& timeStamp = jsonValue["timestamp"].asString();

                std::wcout << timeStamp.c_str() << std::endl;
            }
        });
    });

    return 0;
}

