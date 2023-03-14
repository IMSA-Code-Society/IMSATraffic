#ifndef config_reader
#define config_reader

#include <string>

class ConfigReader {
    public:
        std::string ip;
        int port;
        int interval;
        void readConfig();
};

#endif