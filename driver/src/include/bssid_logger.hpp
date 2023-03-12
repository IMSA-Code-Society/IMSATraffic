#ifndef bssid_logger
#define bssid_logger

#include <string>

class BSSIDLogger {
    public:
        std::string bssid;
        std::string getBSSID();
};

#endif