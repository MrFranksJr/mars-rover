FROM eclipse-temurin:latest
COPY webapp/target/webapp-0.0.1-SNAPSHOT.jar marsrover.jar

ENTRYPOINT ["java","-jar","/marsrover.jar"]