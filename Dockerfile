FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 48080

ENTRYPOINT ["java", "-jar", "app.jar"]