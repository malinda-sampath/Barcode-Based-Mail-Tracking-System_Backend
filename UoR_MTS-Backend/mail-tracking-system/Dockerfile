FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Command to run the application with headless mode enabled
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
