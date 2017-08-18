FROM java:8-jre
MAINTAINER Sijmen Huizenga

ENTRYPOINT ["/usr/bin/java", "-jar", "service.jar", "--spring.config.location=movie-notifier.properties"]
EXPOSE 80

ADD target/movie-notifier.jar service.jar