#include <winsock.h>;
#include <Ws2tcpip.h>; // Ws2_32.lib
#include <string>;
#include <cstring>;
#include "driver_client.hpp";

void DriverClient::connectClient(std::string ip, int port) {
    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_port = htons(1234);
    inet_pton(AF_INET, ip.c_str(), &addr.sin_addr);

    this->sockfd = socket(AF_INET, SOCK_STREAM, 0);
    
    connect(clientfd, (struct sockaddr*)&addr, sizeof(addr));
}

void DriverClient::sendData(ClientData data) {
    const char* serializedData = data.serializeData();
    send(clientfd, serializedData, strlen(serializedData), 0);
}