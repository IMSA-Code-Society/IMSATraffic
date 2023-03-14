#include <iostream>
#include <fstream>
#include <string>

#include "config_reader.hpp"

void ConfigReader::readConfig() {
    std::ifstream in("TrafficDriver.config");

    getline(in, this->ip);

    std::cout << this->ip << std::endl;

    std::string port_str;
    getline(in, port_str);
    this->port = std::stoi(port_str);

    std::string interval_str;
    getline(in, interval_str);
    this->interval = std::stoi(interval_str);
}