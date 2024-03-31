FROM openjdk:11
COPY target/CafeUserService-1.0.0-SNAPSHOT.jar /CafeUserServivce-1.0.0-SNAPSHOT.jar
CMD ["java", "-jar", "-noverify", "/CafeUserServivce-1.0.0-SNAPSHOT.jar"]
EXPOSE 8090