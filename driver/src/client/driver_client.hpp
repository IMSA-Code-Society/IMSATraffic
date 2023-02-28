#include <string>;
#include "client_data.hpp";

class DriverClient {
    public:
        void connectClient(std::string ip, int port);
        void sendData(ClientData data);
    private:
        int sockfd;
        int clientfd;
};