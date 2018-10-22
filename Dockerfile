FROM maven:3-jdk-8 as builder

COPY . /project
WORKDIR /project
RUN mvn package

FROM java:8-jre
COPY --from=builder /project/target/movie-notifier.jar /service.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/service.jar", "--spring.config.location=/movie-notifier.properties"]
EXPOSE 80