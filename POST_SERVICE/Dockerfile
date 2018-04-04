FROM openjdk:8-jre-alpine
RUN mkdir /var/lib/post-service
COPY /target/post-1.0-SNAPSHOT.jar /var/lib/post-service
WORKDIR /var/lib/post-service
EXPOSE 2142
CMD ["java", "-jar", "post-1.0-SNAPSHOT.jar"]