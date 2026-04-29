# Configuraciones de Docker

## Archivo "Dockerfile" con JDK 21
```
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

# El Salvador time zone and JVM memory settings based on container limits.
ENV TZ=America/El_Salvador
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70.0 -Duser.timezone=America/El_Salvador"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
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
