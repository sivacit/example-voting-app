# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Install necessary debugging tools (optional: only if needed for debugging)
RUN apt-get update && apt-get install -y iputils-ping netcat telnet && rm -rf /var/lib/apt/lists/*

# Copy the application JAR file to the container
COPY build/libs/worker-0.0.1-SNAPSHOT.jar app.jar

# (Optional) Copy application.properties for fallback configuration
COPY src/main/resources/application.properties /app/config/application.properties

# Expose the port your application runs on
EXPOSE 8080

# Run the application with a fallback to application.properties
ENTRYPOINT ["java", "-Dspring.config.location=/app/config/application.properties", "-jar", "app.jar"]
