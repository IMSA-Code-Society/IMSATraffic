#include <winsock2.h>
#include <Ws2tcpip.h>
#include <string>
#include <cstring>
#include <iostream>
#include <tchar.h>
#include "driver_client.hpp"
#include "client_data.hpp"
#include "inet_pton.hpp"
#include "bssid_logger.hpp"

void DriverClient::connectClient(std::string ip, int port) {
    WSADATA wsaData;
    WSAStartup(0x202, &wsaData);

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        std::cout << "socket creation failed: " << sockfd << std::endl;
        return;
    }
    
    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = inet_addr(ip.c_str());
    addr.sin_port = htons(port);

    int addr_status = InetPton(AF_INET, ip.c_str(), &addr.sin_addr.s_addr);
    if (addr_status <= 0) {
        std::cout << "invalid address: " << addr_status << std::endl;
        return;
    }
    
    int conn_status = connect(sockfd, (struct sockaddr*)&addr, sizeof(addr));
    if (conn_status < 0) {
        std::cout << "connection failed: " << conn_status << std::endl;
        return;
    }

    this->connected = true;
}

void DriverClient::sendData(ClientData data) {
    if (this->connected) {
        const char* serializedData = data.serializeData();
        send(sockfd, serializedData, strlen(serializedData), 0);
    }
}