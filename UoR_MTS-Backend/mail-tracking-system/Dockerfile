# Use a base image with Java (make sure it matches your Java version)
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy your JAR file into the container
COPY target/mts-backend-app.jar /app/mts-backend-app.jar

# Set the Java headless mode and run the application
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "/app/mts-backend-app.jar"]

# Expose the port the app runs on
EXPOSE 8081
