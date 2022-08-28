FROM openjdk:11
COPY target/SmartContactManager-0.0.1-SNAPSHOT.jar /
WORKDIR /
CMD ["java", "-jar", "SmartContactManager-0.0.1-SNAPSHOT.jar"]
