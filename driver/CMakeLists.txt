cmake_minimum_required(VERSION 3.9.1)
set(CMAKE_CXX_STANDARD 14)
project(IMSATrafficDriver)

if(" ${CMAKE_SOURCE_DIR}" STREQUAL " ${CMAKE_BINARY_DIR}")
	message(FATAL_ERROR "
FATAL: In-source builds are not allowed.
		You should create a separate directory for build files.
")
endif()

if(${CMAKE_SYSTEM_NAME} MATCHES Windows)
	message("windwos")
elseif(${CMAKE_SYSTEM_NAME} MATCHES Linux)
	message("linux")
endif()

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)

add_executable(driver src/main.cpp)