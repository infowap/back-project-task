# Etapa 1: Construção da aplicação
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Imagem final
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/gerenciador-tarefas-0.0.1-SNAPSHOT.jar gerenciador-tarefas.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "gerenciador-tarefas.jar"]
