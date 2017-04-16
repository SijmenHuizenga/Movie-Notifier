FROM java:8
EXPOSE 80
ADD /target/notifierservice.fat.jar notifierservice.fat.jar
ENTRYPOINT ["java", "-jar", "notifierservice.fat.jar"]