FROM openjdk:17-jdk-alpine
COPY target/http-log-search-0.0.1-SNAPSHOT.jar http-log-search-1.0.0.jar
ENTRYPOINT ["java","-jar","/http-log-search-1.0.0.jar"]
EXPOSE 8081