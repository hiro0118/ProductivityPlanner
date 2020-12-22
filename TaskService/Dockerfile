FROM openjdk:15-jdk-alpine
VOLUME /tmp
COPY target/TaskService-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]