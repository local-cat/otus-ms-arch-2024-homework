FROM maven:3.8.3-openjdk-17 as builder

WORKDIR /app

COPY ./ /app

RUN mvn clean deploy

RUN mvn clean install -X

FROM openjdk:17.0-jdk-slim as app

COPY --from=build /app/target/*.jar /app.jar

CMD java -jar /app.jar