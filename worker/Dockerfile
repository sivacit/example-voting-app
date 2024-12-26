# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY build/libs/worker-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for application properties
ENV SPRING_APPLICATION_NAME=demo \
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/learning \
    SPRING_DATASOURCE_USERNAME=teable \
    SPRING_DATASOURCE_PASSWORD=teable \
    SPRING_JPA_HIBERNATE_DDL_AUTO=update

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]