# Step 1: Use Java 21 to build the project
FROM gradle:8.8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar -x test

# Step 2: Run the built app in a lightweight Java environment
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]