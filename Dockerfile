FROM openjdk:11-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} shallwe.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/shallwe.jar"]