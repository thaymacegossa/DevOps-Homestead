# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copiar arquivos do projeto
COPY pom.xml .
COPY src/ src/
COPY mvnw mvnw.cmd ./

# Build do projeto <-DskipTests>
RUN mvn clean package

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Criar usuário não-root
RUN useradd -m -u 1000 appuser

# Copiar JAR do stage anterior
COPY --from=builder /build/target/DevOps-Homestead-*.jar app.jar

# Definir permissões
RUN chown -R appuser:appuser /app

# Usar usuário não-root
USER appuser

# Expor porta
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
