cmake_minimum_required(VERSION 3.9.1)
set(CMAKE_CXX_STANDARD 14)
project(IMSATrafficDriver VERSION 0.0.0 LANGUAGES C CXX)

set(CMAKE_BINARY_DIR ${CMAKE_SOURCE_DIR}/out)

include_directories(${CMAKE_SOURCE_DIR})

message("Starting CMake...")

if(" ${CMAKE_SOURCE_DIR}" STREQUAL " ${CMAKE_BINARY_DIR}")
	message(FATAL_ERROR "
FATAL: In-source builds are not allowed.
		You should create a separate directory for build files.
")
endif()

if(${CMAKE_SYSTEM_NAME} MATCHES Windows)
	message("Windows system found")
else()
	message(FATAL_ERROR "FATAL: ${CMAKE_SYSTEM_NAME} builds are currently unsupported")
endif()

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)

file(GLOB_RECURSE SRC src/*.cpp)
add_executable(driver ${SRC})