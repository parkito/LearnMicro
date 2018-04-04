FROM openjdk:8-jre-alpine
RUN mkdir /var/lib/user-service
COPY /target/user-1.0-SNAPSHOT.jar /var/lib/user-service
WORKDIR /var/lib/user-service
EXPOSE 2144
CMD ["java", "-jar", "user-1.0-SNAPSHOT.jar"]