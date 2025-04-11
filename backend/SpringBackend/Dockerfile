# Stage 1: Custom build stage (no premade availeble for Java 21...)
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy Maven configuration to leverage caching of dependencies
COPY pom.xml .

# Pre-download the dependencies
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package

# Stage 2: Create a lightweight image for running the application
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Volume for images and DB
VOLUME ["/app/uploads", "/app/data"]

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]