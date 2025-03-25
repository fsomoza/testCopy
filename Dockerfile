# First stage
FROM maven:3.9-amazoncorretto-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application
RUN mvn clean package -DskipTests

# Final stage
FROM amazoncorretto:21-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose port 3000
EXPOSE 3000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]