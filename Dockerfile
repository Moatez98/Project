FROM openjdk:17-jdk
MAINTAINER moatezrehouma
COPY target/Project-0.0.1-SNAPSHOT.jar /
ENTRYPOINT java -jar /Project-0.0.1-SNAPSHOT.jar
