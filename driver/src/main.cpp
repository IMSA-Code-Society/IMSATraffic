#include <string>
#include <iostream>
#include <time.h>
#include <windows.h>
#include "driver_client.hpp"
#include "client_data.hpp"
#include "bssid_logger.hpp"

std::string IP = "127.0.0.1";
int PORT = 3003;
int INTERVAL = 3 * 60 * 1000;

std::string last_bssid;
std::string device_name;

int main() {
    std::cout << "Sending to: " << IP << ":" << PORT << std::endl;

    ::ShowWindow(::GetConsoleWindow(), SW_HIDE);

    srand(time(NULL));
    
    DriverClient client;
    client.connectClient(IP, PORT);

    while(true) {
        BSSIDLogger logger;
        std::string bssid = logger.getBSSID();

        Sleep(INTERVAL);

        if (bssid != last_bssid) {
            device_name = ClientData::generateDeviceName();
            last_bssid = bssid;
        }

        ClientData data(device_name, bssid); // "12:45:78:01:34:67"
        client.sendData(data);
    }

    return 0;
}