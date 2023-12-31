FROM eclipse-temurin:21-jdk-alpine AS builder

# Install inotify-tools and dos2unix for live reload
RUN apk add --no-cache inotify-tools dos2unix curl jq


WORKDIR /app

COPY pom.xml .

# Copy maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Ensure Unix line endings in case the Maven wrapper was copied from a Windows environment
RUN dos2unix mvnw && chmod +x mvnw

# Resolve dependencies
RUN ./mvnw dependency:go-offline -B

COPY src src

FROM builder AS dev

# Copy entrypoint script
COPY docker/api/entrypoint.sh /usr/local/bin/entrypoint.sh

# Make entrypoint script executable
RUN dos2unix /usr/local/bin/entrypoint.sh && chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

FROM builder AS prod

# Build application
RUN ./mvnw clean package

# Run application
CMD ["java", "-jar", "target/musically-0.0.1-SNAPSHOT.jar"]
