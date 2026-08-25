# Use Java 21 runtime
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/*.jar app.jar

# Expose the Spring Boot application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]