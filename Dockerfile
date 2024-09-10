ARG JAVA_VERSION=17

FROM openjdk:${JAVA_VERSION}

WORKDIR /usr/api 

COPY ./target/*.jar api.jar

CMD ["java", "-jar", "api.jar"]

EXPOSE 8080
