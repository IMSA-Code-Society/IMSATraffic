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

    int data_size = device_name.length() + bssid.length();
    char* serialized_data = new char[data_size];
    serialized_data[0] = 0; 

    strcat(serialized_data, device_data);
    strcat(serialized_data, bssid_data);

    return (const char*)serialized_data;
}