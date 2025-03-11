FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG EUREKA_SERVER=localhost

# Definir variáveis de ambiente para o banco
ENV POSTGRES_DB=clientedb
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=123456

ENTRYPOINT java -jar app.jar