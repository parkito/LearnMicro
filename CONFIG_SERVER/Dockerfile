FROM openjdk:8-jre-alpine
RUN mkdir /var/lib/config_server
COPY /target/config-1.0.0-SNAPSHOT.jar /var/lib/config_server
WORKDIR /var/lib/config_server
EXPOSE 9090
CMD ["java", "-jar", "config-1.0.0-SNAPSHOT.jar"]