#!/bin/bash

echo "==== Building and Running Java Landing Website ===="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is required but not installed. Please install Maven."
    exit 1
fi

echo "Building the application..."
mvn clean package

if [ $? -eq 0 ]; then
    echo "Build successful. Starting the application..."
    java -jar target/landing-website-1.0-SNAPSHOT.jar
else
    echo "Build failed. Please check the logs above for errors."
    exit 1
fi