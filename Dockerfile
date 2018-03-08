FROM openjdk:9-slim

WORKDIR /app

COPY ./ ./

RUN ./gradlew clean jar

CMD java -jar build/libs/lucasvalente-0.1.0.jar

