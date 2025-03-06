FROM maven:3.9.8-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar gerenciador-tarefas.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "gerenciador-tarefas.jar" ]
