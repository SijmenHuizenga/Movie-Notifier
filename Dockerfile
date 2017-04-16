FROM java:8
EXPOSE 80
ADD service.jar service.jar
ENTRYPOINT ["java", "-jar", "service.jar"]