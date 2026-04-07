# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the entire workspace so Maven can resolve all parent-child relationships perfectly
COPY . .

# Build JUST this specific module
RUN mvn clean package -pl eureka-server -am -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR from the specific module's target folder
COPY --from=build /app/eureka-server/target/*.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]