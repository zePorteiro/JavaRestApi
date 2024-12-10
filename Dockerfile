# Etapa de construção
FROM maven:3-openjdk-17 AS builder

WORKDIR /build

# Copia o código-fonte para o contêiner
COPY . .

# Executa o Maven para construir a aplicação (sem rodar os testes)
RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

# Etapa final: Imagem para rodar a aplicação
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven para o contêiner
COPY --from=builder /build/target/*.jar app.jar

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
