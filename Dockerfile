FROM eclipse-temurin:latest
COPY main/target/main-0.0.1-SNAPSHOT.jar marsrover.jar

ENTRYPOINT ["java","-jar","/marsrover.jar"]