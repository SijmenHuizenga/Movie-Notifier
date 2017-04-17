FROM java:8
EXPOSE 80
ENTRYPOINT ["java", "-jar", "service.jar"]
ADD service.jar service.jar
