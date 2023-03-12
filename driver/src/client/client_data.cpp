#include <string>
#include <string.h>
#include "client_data.hpp"

ClientData::ClientData(std::string device_name, std::string bssid) {
    this->device_name = device_name;
    this->bssid = bssid;
}

const char* ClientData::serializeData() {
    const char* device_data = device_name.c_str();
    const char* bssid_data = bssid.c_str();

    int data_size = bssid.length() + device_name.length();
    char* serialized_data = new char[data_size];
    serialized_data[0] = 0; 

    strcat(serialized_data, bssid_data);
    strcat(serialized_data, device_data);

    return (const char*)serialized_data;
}

std::string ClientData::generateDeviceName() {
    static const char alphanum[] =
        "0123456789"
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        "abcdefghijklmnopqrstuvwxyz";

    std::string device_name;
    device_name.reserve(8);

    for (int i = 0; i < 8; ++i) {
        device_name += alphanum[rand() % (sizeof(alphanum) - 1)];
    }
    
    return device_name;
}