#include <string>;
#include "client_data.hpp";

ClientData::ClientData(std::string ip, int port, std::string device_name, std::string bssid) {
    this->ip = ip;
    this->port = port;
    this->device_name = device_name;
    this->bssid = bssid;
}

const char* ClientData::serializeData() {
    const char* ip_data = ip.c_str();
    const char* port_data = (const char*)port;
    const char* device_data = device_name.c_str();
    const char* bssid_data = bssid.c_str();

    int data_size = 37 + ip.length(); // port + bssid + device_name + ip
    char* serialized_data = new char[data_size];
    strcat(serialized_data, ip_data);
    strcat(serialized_data, port_data);
    strcat(serialized_data, device_data);
    strcat(serialized_data, bssid_data);

    return (const char*)serialized_data;
}