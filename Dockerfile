FROM maven:3.6.3-jdk-8 AS builder
WORKDIR /build/
COPY . .
RUN mvn package

FROM openjdk:8-jre-alpine3.9
WORKDIR /app/
COPY --from=builder  /build/fatjar/target/litnetbot-fatjar.jar .
CMD java -jar litnetbot-fatjar.jar
