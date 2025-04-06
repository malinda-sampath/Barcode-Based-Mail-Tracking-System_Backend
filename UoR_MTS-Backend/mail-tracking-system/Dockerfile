# Use the official OpenJDK image
FROM openjdk:21-jdk-slim

# Install necessary dependencies for font rendering and Microsoft core fonts
RUN apt-get update && apt-get install -y \
    fontconfig \
    fonts-dejavu \
    fonts-liberation \
    libfreetype6 \
    libfontconfig1 \
    wget \
    cabextract \
    ttf-mscorefonts-installer \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Command to run the application with headless mode enabled
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
