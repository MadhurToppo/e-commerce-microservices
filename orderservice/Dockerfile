FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew gradlew
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/
COPY src/ src/

# Ensure gradlew is executable
RUN chmod +x gradlew

# Build the Spring Boot fat jar (skip tests for Docker build)
RUN ./gradlew bootJar --no-daemon -x test

# Create a minimal runtime image
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built Spring Boot fat jar from the builder stage
COPY --from=builder /app/build/libs/orderservice-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
