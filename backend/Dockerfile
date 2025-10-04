# JDK base image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Install curl
RUN apk add --no-cache curl

# Download dependencies first
RUN ./mvnw dependency:go-offline -B

COPY src ./src

# Run tests and build JAR 
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/backend-0.0.1-SNAPSHOT.jar"]