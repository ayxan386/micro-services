FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080
ENV POSTGRES_IP=localhost
ENV POSTGRES_PORT=5432
ENV POSTGRES_DB=postgres
ENV CACHE_IP=0.0.0.0
ENV CACHE_PORT=6379

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]