FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

EXPOSE 8080

ADD target/projetofinal-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]