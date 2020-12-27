FROM maven:3.6.3-openjdk-15 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:15-jdk-alpine
COPY --from=build /usr/src/app/target/TaskService-1.0.jar /usr/app/TaskService-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/TaskService-1.0.jar"]