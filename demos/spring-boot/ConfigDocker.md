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

