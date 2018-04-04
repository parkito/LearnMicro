FROM openjdk:8-jre-alpine
RUN mkdir /var/lib/document-service
COPY /target/documents-1.0-SNAPSHOT.jar /var/lib/document-service
WORKDIR /var/lib/document-service
EXPOSE 2143
CMD ["java", "-jar", "documents-1.0-SNAPSHOT.jar"]