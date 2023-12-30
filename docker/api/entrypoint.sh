#!/bin/bash
dos2unix mvnw  # Ensure Unix line endings in case the Maven wrapper was copied from a Windows environment

# Run the Spring Boot application with remote debugging enabled
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" &

# Monitor changes to source files and recompile the application
while true; do
  inotifywait -e modify,create,delete,move -r ./src/ && ./mvnw compile
done
