ARG JAR_NAME=user-0.0.1-SNAPSHOT.jar
FROM openjdk:17-jdk-alpine
ARG JAR_NAME
WORKDIR /app
COPY target/$JAR_NAME ./
EXPOSE 8000
ENV GREETINGS=hello_there
CMD ["java", "-jar","user-0.0.1-SNAPSHOT.jar"]