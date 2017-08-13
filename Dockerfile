FROM java:8-jre
MAINTAINER Sijmen Huizenga

ENTRYPOINT ["/usr/bin/java", "-jar", "service.jar", "--spring.config.location=movie-notifier.properties"]

ADD target/movie-notifier.jar service.jar
