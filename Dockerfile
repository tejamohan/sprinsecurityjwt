FROM openjdk:8-jdk-alpine
MAINTAINER tejamunakala@gmail.com
COPY target/springsecurityjwt.jar springsecurityjwt.jar
ENTRYPOINT ["java","-jar","/springsecurityjwt.jar"]