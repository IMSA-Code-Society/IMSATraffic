#include <string>;
#include "client_data.hpp";

class DriverClient {
    public:
        void connect(std::string ip, int port);
        void sendData(ClientData data);
};