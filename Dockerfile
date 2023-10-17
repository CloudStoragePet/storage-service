FROM openjdk:17-jdk-slim
LABEL authors="Vitalii Seniuk"

ARG JAR_FILE=target/*.jar
ARG DB_HOST
ARG DB_USER
ARG DB_PASSWORD
ARG EUREKA_URL

# Set environment variables from build-time arguments
ENV DB_HOST=$DB_HOST \
    DB_USER=$DB_USER \
    DB_PASSWORD=$DB_PASSWORD \
    EUREKA_URL=$EUREKA_URL

COPY ${JAR_FILE} storage-service.jar

ENTRYPOINT ["java", "-jar", "/storage-service.jar"]