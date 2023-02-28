#include <string>;

class ClientData {
    public:
        std::string ip;
        int port;
        std::string device_name;
        std::string bssid;
        const char* serializeData();
        ClientData(std::string ip, int port, std::string device_name, std::string bssid);
};