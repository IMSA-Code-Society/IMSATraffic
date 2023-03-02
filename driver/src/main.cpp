#include <iostream>
#include <string>
#include "client/driver_client.hpp"
#include "client/client_data.hpp"

std::string IP = "127.0.0.1";
int PORT = 3003;

int main() {
    std::cout << "Starting client" << std::endl;

    DriverClient client;
    client.connectClient(IP, PORT);

    ClientData data("test", "12:45:78:01:34:67");
    client.sendData(data);

    return 0;
}