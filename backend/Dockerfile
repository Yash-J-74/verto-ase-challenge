# JDK base image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Running tests and building img even if they fail
RUN ./mvnw clean install -Dmaven.test.failure.ignore=true

COPY src ./src

# Package the Spring Boot app
RUN ./mvnw package

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/your-app-name-0.0.1-SNAPSHOT.jar"]