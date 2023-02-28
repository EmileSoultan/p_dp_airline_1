FROM adoptopenjdk:11-jre-hotspot
COPY ./target/airline-project.jar airline-project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "airline-project.jar"]