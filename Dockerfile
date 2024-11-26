FROM ubuntu:latest
LABEL authors="USER"

# Add docker official image (add variable name "AS build")
# Function to run maven project
FROM maven:3.8.5-openjdk-17 AS build
# COPY all the project into docker image
COPY . .
# Run command to install application package - jar file
RUN mvn clean package -DskipTests

# Container use to run application
FROM openjdk:17.0.1-jdk-slim
COPY  --from=build /target/financialhub-0.0.1-SNAPSHOT.jar financialhub-1.0.0-beta.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "financialhub-1.0.0-beta.jar"]