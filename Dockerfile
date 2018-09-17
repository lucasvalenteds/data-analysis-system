FROM gradle:4.10.1-jdk11-slim as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle --no-daemon shadowJar

FROM openjdk:11-jre-slim
COPY --from=builder /home/gradle/src/build/libs/lucasvalente-0.1.0.jar /app/app.jar
WORKDIR /app
VOLUME ["/tmp"]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
