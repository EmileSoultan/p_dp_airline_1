FROM adoptopenjdk:11-jre-hotspot
COPY ./target/airline-project.jar airline-project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "airline-project.jar"]