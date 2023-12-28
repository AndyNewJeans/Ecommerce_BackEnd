FROM eclipse-temurin:17-jdk-focal
VOLUME /tmp
ARG JAR_FILE
ENTRYPOINT ["java","-jar","/Project_Backend.jar"] a