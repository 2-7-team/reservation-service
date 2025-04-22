FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/reservation-service-0.0.1-SNAPSHOT.jar reservation-service.jar
ENTRYPOINT ["java", "-jar", "reservation-service.jar"]