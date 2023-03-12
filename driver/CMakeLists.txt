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

target_include_directories(driver PRIVATE ${CMAKE_SOURCE_DIR}/src/include)

if(WIN32)
	message("Linking ws2_32...")
	target_compile_options(driver PRIVATE -fexceptions)
	target_link_libraries(driver wsock32 ws2_32)
endif()

if(debug)
	message("Debug mode on")
	add_custom_command(TARGET driver POST_BUILD
		COMMAND ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/driver.exe
		WORKING_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}
		COMMENT "Running build..."
	)
endif()