#include <winsock2.h>
#include <Ws2tcpip.h> // Ws2_32.lib
#include <string>
#include <cstring>
#include "driver_client.hpp"
#include "client_data.hpp"

void DriverClient::connectClient(std::string ip, int port) {
    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_port = htons(port);
    addr.sin_addr.s_addr = inet_addr(ip.c_str());

    this->sockfd = socket(AF_INET, SOCK_STREAM, 0);
    
    connect(clientfd, (struct sockaddr*)&addr, sizeof(addr));
}

void DriverClient::sendData(ClientData data) {
    const char* serializedData = data.serializeData();
    send(clientfd, serializedData, strlen(serializedData), 0);
}