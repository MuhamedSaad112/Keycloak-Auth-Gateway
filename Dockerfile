# Use a slim OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Install Maven and system dependencies in a single layer
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy only necessary files for dependency resolution first
COPY pom.xml .
# Run dependency download separately to leverage Docker layer caching
RUN mvn dependency:go-offline -B

# Copy the entire source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Copy the built JAR file
COPY target/Keycloak-Security-Gateway-0.0.1-SNAPSHOT.jar app.jar

# Use a non-root user for security
RUN addgroup --system spring && adduser --system --group spring
USER spring

# Expose port if needed (add this if your app has a specific port)
# EXPOSE 8080

# Use ENTRYPOINT with CMD for more flexibility
ENTRYPOINT ["java", "-jar", "app.jar"]