#include <cstdio>
#include <memory>
#include <stdexcept>
#include <string>
#include <array>
#include <iostream>

#include "bssid_logger.hpp"

std::string exec(const char* cmd) {
    std::array<char, 128> buffer;
    std::string result;
    std::unique_ptr<FILE, decltype(&_pclose)> pipe(_popen(cmd, "r"), _pclose);
    if (!pipe) {
        throw std::runtime_error("popen() failed!");
    }
    while (fgets(buffer.data(), buffer.size(), pipe.get()) != nullptr) {
        result += buffer.data();
    }
    return result;
}

std::string BSSIDLogger::getBSSID()
{
    std::string netshOut = exec("netsh wlan show interface | find \"BSSID\"");

    if (netshOut.length() != 0) {
        return netshOut.substr(29, 17);
    } else {
        return "00:00:00:00:00:00";
    }
}