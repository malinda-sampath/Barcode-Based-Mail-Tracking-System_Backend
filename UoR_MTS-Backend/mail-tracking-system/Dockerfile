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
    && rm -rf /var/lib/apt/lists/*

# Install Microsoft core fonts (using wget to download the font installer)
RUN mkdir -p /usr/share/fonts/truetype/msttcorefonts \
    && cd /usr/share/fonts/truetype/msttcorefonts \
    && wget http://excellmedia.dl.sourceforge.net/project/corefonts/the%20fonts/final/andale32.exe?viasf=1 \
    && apt-get install -y cabextract \
    && cabextract andale32.exe \
    && rm andale32.exe

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Command to run the application with headless mode enabled
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
