FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /build
COPY pom.xml .
COPY src /build/src
RUN mvn -f /build/pom.xml clean package

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /build/target/airline-project.jar airline-project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "airline-project.jar"]