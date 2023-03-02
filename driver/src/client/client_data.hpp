#ifndef client_data
#define client_data

#include <string>

class ClientData {
    public:
        std::string device_name;
        std::string bssid;
        const char* serializeData();
        ClientData(std::string device_name, std::string bssid);
};

#endif