FROM openjdk:8-jre-alpine
RUN mkdir /var/lib/eureka_server
COPY /target/eureka-1.0-SNAPSHOT.jar /var/lib/eureka_server
WORKDIR /var/lib/eureka_server
EXPOSE 9091
CMD ["java", "-jar", "eureka-1.0-SNAPSHOT.jar"]