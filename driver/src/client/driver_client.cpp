#include <winsock2.h>
//#include <winsock.h>
#include <Ws2tcpip.h> // Ws2_32.lib
#include <string>
#include <cstring>
#include <iostream>
#include <tchar.h>
#include "driver_client.hpp"
#include "client_data.hpp"

int inet_pton(int af, const char *src, void *dst)
{
  struct sockaddr_storage ss;
  int size = sizeof(ss);
  char src_copy[INET6_ADDRSTRLEN+1];

  ZeroMemory(&ss, sizeof(ss));
  /* stupid non-const API */
  strncpy (src_copy, src, INET6_ADDRSTRLEN+1);
  src_copy[INET6_ADDRSTRLEN] = 0;

  if (WSAStringToAddress(src_copy, af, NULL, (struct sockaddr *)&ss, &size) == 0) {
    switch(af) {
      case AF_INET:
    *(struct in_addr *)dst = ((struct sockaddr_in *)&ss)->sin_addr;
    return 1;
      case AF_INET6:
    *(struct in6_addr *)dst = ((struct sockaddr_in6 *)&ss)->sin6_addr;
    return 1;
    }
  }
  return 0;
} 

void DriverClient::connectClient(std::string ip, int port) {
    std::cout << "connecting..." << std::endl;

    WSADATA wsaData;
    WSAStartup(0x202, &wsaData);

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        std::cout << "socket creation failed: " << sockfd << std::endl;
    }
    
    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = inet_addr("127.0.0.1");//ip.c_str()
    addr.sin_port = htons(port);

    int addr_status = inet_pton(AF_INET, ip.c_str(), &addr.sin_addr.s_addr);
    if (addr_status <= 0) {
        std::cout << "invalid address: " << addr_status << std::endl;
    }
    
    int conn_status = connect(sockfd, (struct sockaddr*)&addr, sizeof(addr));
    if (conn_status < 0) {
        std::cout << "connection failed: " << conn_status << std::endl;
    }
}

void DriverClient::sendData(ClientData data) {
    std::cout << "sending data..." << std::endl;
    const char* serializedData = data.serializeData();
    send(sockfd, serializedData, strlen(serializedData), 0);
}