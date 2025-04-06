# Use the official OpenJDK image
FROM openjdk:21-jdk-slim

# Install necessary dependencies for font rendering
RUN apt-get update && apt-get install -y \
    fontconfig \
    fonts-dejavu \
    fonts-liberation \
    libfreetype6 \
    libfontconfig1 \
    wget \
    cabextract \
    && rm -rf /var/lib/apt/lists/*

# Manually install Microsoft core fonts
RUN mkdir -p /usr/share/fonts/truetype/msttcorefonts \
    && cd /usr/share/fonts/truetype/msttcorefonts \
    && wget http://downloads.sourceforge.net/corefonts/andale32.exe \
    && cabextract andale32.exe \
    && rm andale32.exe \
    && fc-cache -f -v

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Command to run the application with headless mode enabled
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
