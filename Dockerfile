FROM maven:3.9.8-eclipse-temurin-17-alpine AS builder

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean verify

FROM eclipse-temurin-17-alpine

COPY --from=build /target/*.jar gerenciador-tarefas.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "gerenciador-tarefas.jar" ]