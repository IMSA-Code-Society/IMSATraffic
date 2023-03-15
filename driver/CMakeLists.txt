cmake_minimum_required(VERSION 3.20.0)
set(CMAKE_CXX_STANDARD 14)
project(TrafficDriver VERSION 0.0.0 LANGUAGES C CXX)

set(CMAKE_BINARY_DIR ${CMAKE_SOURCE_DIR}/out)
set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}/cmake" ${CMAKE_MODULE_PATH})

include_directories(${CMAKE_SOURCE_DIR})

message("Starting CMake...")

if(${CMAKE_SYSTEM_NAME} MATCHES Windows)
	message("Windows system found")
else()
	message(FATAL_ERROR "FATAL: ${CMAKE_SYSTEM_NAME} builds are currently unsupported")
endif()

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)

file(GLOB_RECURSE SRC src/*.cpp)
add_executable(TrafficDriver ${SRC})

target_include_directories(TrafficDriver PRIVATE ${CMAKE_SOURCE_DIR}/include)

message("Linking ws2_32...")
target_compile_options(TrafficDriver PRIVATE -fexceptions)
target_link_libraries(TrafficDriver wsock32 ws2_32)

function(create_zip output_file input_files working_dir target_name)
	add_custom_command(#TARGET TrafficDriver POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E tar "cf" "${output_file}" --format=zip -- ${input_files}
        WORKING_DIRECTORY "${working_dir}"
		OUTPUT "${output_file}"
        DEPENDS ${input_files}
		COMMENT "Zipping ${input_files}"
    )
endfunction()

function(copy_file input_file output_file target_name)
	add_custom_target(${target_name} ALL
		COMMAND ${CMAKE_COMMAND} -E copy ${input_file} ${output_file}
		WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
		DEPENDS ${input_file}
	)
endfunction()

function(make_dir dir target_name)
	add_custom_target(${target_name} ALL
		COMMAND ${CMAKE_COMMAND} -E make_directory ${dir}
	)
endfunction()

if(${CMAKE_BUILD_TYPE} MATCHES Debug)
	message("Debug mode on")
	add_custom_command(TARGET TrafficDriver POST_BUILD
		COMMAND ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/TrafficDriver.exe
		WORKING_DIRECTORY ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}
		COMMENT "Running build..."
	)
else()
	message("Release mode on")
	
	message("Hiding console window...")
	set(CMAKE_CXX_FLAGS "-mwindows")

	message("Copying installation scripts...")

	make_dir("${CMAKE_BINARY_DIR}/TrafficDriver/bin" zip-dir)

	copy_file("${CMAKE_CURRENT_SOURCE_DIR}/scripts/install.bat" "${CMAKE_BINARY_DIR}/TrafficDriver/" install-script)
	copy_file("${CMAKE_CURRENT_SOURCE_DIR}/scripts/uninstall.bat" "${CMAKE_BINARY_DIR}/TrafficDriver/" uninstall-script)
	copy_file("${CMAKE_CURRENT_SOURCE_DIR}/scripts/traffic_updater.bat" "${CMAKE_BINARY_DIR}/TrafficDriver/bin/" updater-script)
	copy_file("${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/TrafficDriver.exe" "${CMAKE_BINARY_DIR}/TrafficDriver/bin/" driver-executable)

	include(ziplist)
	create_zip("${CMAKE_BINARY_DIR}/TrafficDriver.zip" "${ZIP_FILES}" "${CMAKE_BINARY_DIR}/TrafficDriver" zip)
	add_custom_target(zip ALL DEPENDS "${CMAKE_BINARY_DIR}/TrafficDriver.zip")
endif()