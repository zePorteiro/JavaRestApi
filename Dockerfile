FROM maven:3-openjdk-17 AS builder

WORKDIR /build

COPY ...

RUN mvn clean package -DskipTests - Dcheckstyle.skip=true

# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo JAR gerado pelo Maven para o container
COPY --from=builder/build/target/**.jar app.jar

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
