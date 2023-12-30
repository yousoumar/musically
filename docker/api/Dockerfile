FROM eclipse-temurin:21-jdk-alpine AS builder

# Install inotify-tools and dos2unix for live reload
RUN apk add --no-cache inotify-tools dos2unix

WORKDIR /app

COPY pom.xml .

# Copy maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Resolve dependencies
RUN ./mvnw dependency:go-offline -B

COPY src src

FROM builder AS dev

# Copy entrypoint script
COPY docker/api/entrypoint.sh /usr/local/bin/entrypoint.sh

# Make entrypoint script executable
RUN chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

FROM builder AS prod

# Build application
RUN ./mvnw clean package -DskipTests

# Run application
CMD ["java", "-jar", "target/musically-0.0.1-SNAPSHOT.jar"]


FROM builder AS test

RUN ./mvnw test
