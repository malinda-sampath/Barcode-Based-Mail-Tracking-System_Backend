FROM openjdk:21-jdk-slim

# Install DejaVu fonts
RUN apt-get update && apt-get install -y \
    fonts-dejavu-core \
    fonts-dejavu-extra \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
