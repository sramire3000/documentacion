# Configuraciones de Docker

## Archivo "Dockerfile" con JDK 21
```
# ─── Stage 1: Build ───────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /workspace

# Copiar descriptor de dependencias primero para aprovechar la caché de capas
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Descargar dependencias sin compilar el código fuente
RUN ./mvnw dependency:go-offline -q

# Copiar código fuente y compilar
COPY src ./src
RUN ./mvnw package -DskipTests -q

# Spring Boot 3.3+ / 4.x: jarmode=tools extract genera lib/ + jar delgado
RUN java -Djarmode=tools -jar target/*.jar extract --destination extracted

# ─── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine AS runtime

# Usuario no-root por seguridad (OWASP A05)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# lib/ cambia raramente → capa separada para mejor cache de Docker
COPY --from=builder --chown=appuser:appgroup /workspace/extracted/lib/ ./lib/
# Jar delgado con solo el código de la aplicación
COPY --from=builder --chown=appuser:appgroup /workspace/extracted/*.jar ./app.jar

# Directorio de logs
RUN mkdir -p /app/logs && chown appuser:appgroup /app/logs

USER appuser

EXPOSE 8080

# -XX:MaxRAMPercentage=70.0  → JVM usa como máximo el 70% de la RAM del contenedor
# -XX:InitialRAMPercentage=50.0 → JVM inicia con el 50% para arranque estable
# -XX:+UseG1GC               → GC eficiente para cargas batch de larga duración
# -XX:+UseContainerSupport   → Activa detección automática de límites del contenedor
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=70.0", \
  "-XX:InitialRAMPercentage=50.0", \
  "-XX:+UseG1GC", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]

```

## Archivo "docker-compose.yml"
```
services:
  demo-ehcache:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: demo-ehcache
    ports:
      - "8080:8080"
    environment:
      TZ: America/El_Salvador
      JAVA_TOOL_OPTIONS: "-XX:MaxRAMPercentage=70.0 -Duser.timezone=America/El_Salvador"
    mem_limit: 1g
    cpus: 0.5
    restart: unless-stopped
```

## Archivo "subirDocker.cmd"
```
@echo off
setlocal

REM Script manual para construir y ejecutar el contenedor con la misma configuracion de docker-compose.
set IMAGE_NAME=demo-ehcache
set IMAGE_TAG=latest
set CONTAINER_NAME=demo-ehcache
set HOST_PORT=8080
set CONTAINER_PORT=8080

REM El Dockerfile compila el proyecto en la etapa de build.
REM No es necesario generar el JAR localmente antes de ejecutar este script.

echo [1/3] Construyendo imagen Docker...
docker build -t %IMAGE_NAME%:%IMAGE_TAG% .
if errorlevel 1 (
  echo Error al construir la imagen.
  exit /b 1
)

echo [2/3] Eliminando contenedor anterior (si existe)...
docker rm -f %CONTAINER_NAME% >nul 2>&1

echo [3/3] Iniciando contenedor con RAM=1G, CPU=0.5 y zona horaria El Salvador...
docker run -d ^
  --name %CONTAINER_NAME% ^
  -p %HOST_PORT%:%CONTAINER_PORT% ^
  --memory=1g ^
  --cpus=0.5 ^
  -e TZ=America/El_Salvador ^
  -e JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70.0 -Duser.timezone=America/El_Salvador" ^
  --restart unless-stopped ^
  %IMAGE_NAME%:%IMAGE_TAG%

if errorlevel 1 (
  echo Error al iniciar el contenedor.
  exit /b 1
)

echo Contenedor iniciado correctamente.
docker ps --filter "name=%CONTAINER_NAME%"

endlocal

```
