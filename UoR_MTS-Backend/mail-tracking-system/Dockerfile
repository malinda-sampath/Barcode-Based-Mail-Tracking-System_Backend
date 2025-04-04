FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port (change this if your app runs on a different port)
EXPOSE 8081

# Set environment variables (can be overridden at runtime)
ENV \
    SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/uor_mts?createDatabaseIfNotExist=true" \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=root

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
