FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port (change this if your app runs on a different port)
EXPOSE 8081

# Set environment variables (can be overridden at runtime)
ENV SPRING_APPLICATION_NAME="mail-tracking-system" \
    SERVER_PORT=8081 \
    SPRING_JPA_HIBERNATE_DDL_AUTO=update \
    SPRING_SQL_INIT_MODE=always \
    SPRING_DATASOURCE_DRIVER_CLASS_NAME="com.mysql.cj.jdbc.Driver" \
    SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/uor_mts?createDatabaseIfNotExist=true" \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=root \
    SPRING_JPA_DATABASE_PLATFORM="org.hibernate.dialect.MySQLDialect" \
    SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT="org.hibernate.dialect.MySQLDialect" \
    SPRING_JPA_GENERATE_DDL=true \
    SPRING_JPA_SHOW_SQL=true \
    SECURITY_JWT_SECRET_KEY="b52aa87ef75d6672e3f281d69c2952b13c08063949e3b43224f245b3d3d177aa" \
    SECURITY_JWT_EXPIRATION_TIME=43200000

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
