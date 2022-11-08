FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} airline_1.jar
ENTRYPOINT ["java", "-jar", "airline_1.jar"]