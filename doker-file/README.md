# DOCKERFILE

## OpenJdk1.8

### Dockerfile
```
# Dockerfile para JDK 1.8
FROM ubuntu:20.04

# Evitar preguntas interactivas durante la instalación
ENV DEBIAN_FRONTEND=noninteractive

# Instalar dependencias necesarias
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    gnupg \
    software-properties-common \
    && rm -rf /var/lib/apt/lists/* \
    && apt-get clean

# Descargar e instalar JDK 8 de Oracle
RUN wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" \
    https://download.oracle.com/otn-pub/java/jdk/8u381-b09/jdk-8u381-linux-x64.tar.gz

# Crear directorio para JDK y extraer
RUN mkdir -p /usr/lib/jvm && \
    tar -xzf jdk-8u381-linux-x64.tar.gz -C /usr/lib/jvm && \
    rm jdk-8u381-linux-x64.tar.gz

# Configurar variables de entorno
ENV JAVA_HOME /usr/lib/jvm/jdk1.8.0_381
ENV PATH $JAVA_HOME/bin:$PATH

# Crear usuario no-root para seguridad
RUN groupadd -r javaapp && useradd -r -g javaapp javaapp

# Directorio de trabajo
WORKDIR /app

# Cambiar ownership al usuario no-root
RUN chown -R javaapp:javaapp /app

# Cambiar al usuario no-root
USER javaapp

# Verificar la instalación
CMD ["java", "-version"]
```

## OpenJdk21

### File Dockerfile 21
```
# Usar una imagen base minimalista y segura
FROM eclipse-temurin:21.0.2_13-jdk-jammy

# Metadatos del maintainer
LABEL maintainer="tu-equipo@empresa.com"
LABEL description="Imagen base con JDK 21"

# Instalar seguridad updates y herramientas básicas
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y --no-install-recommends \
    curl \
    ca-certificates \
    tzdata \
    && rm -rf /var/lib/apt/lists/* \
    && apt-get clean

# Crear usuario no-root para seguridad
RUN groupadd -r javaapp && useradd -r -g javaapp javaapp

# Configurar timezone (opcional, ajusta según necesidad)
ENV TZ=America/Mexico_City

# Directorio de trabajo
WORKDIR /app

# Cambiar ownership al usuario no-root
RUN chown -R javaapp:javaapp /app

# Cambiar al usuario no-root
USER javaapp

# Verificar la instalación del JDK
CMD ["java", "-version"]
```

### Contruir la imagen
```
docker build -t jdk21-base:latest .
```

### File DockerFile + App.jar
```
# Usar una imagen base minimalista y segura
FROM eclipse-temurin:21.0.2_13-jdk-jammy

# Metadatos del maintainer
LABEL maintainer="tu-equipo@empresa.com"
LABEL description="Aplicación Java con JDK 21 en base segura"

# Instalar seguridad updates y herramientas básicas
RUN apt-get update && \
apt-get upgrade -y && \
apt-get install -y --no-install-recommends \
curl \
ca-certificates \
tzdata \
&& rm -rf /var/lib/apt/lists/* \
&& apt-get clean

# Crear usuario no-root para seguridad
RUN groupadd -r javaapp && useradd -r -g javaapp javaapp

# Configurar timezone (opcional, ajusta según necesidad)
ENV TZ=America/Mexico_City

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR de la aplicación (ajusta el nombre)
COPY target/tu-aplicacion.jar app.jar

# Cambiar ownership al usuario no-root
RUN chown -R javaapp:javaapp /app

# Cambiar al usuario no-root
USER javaapp

# Exponer puerto (ajusta según tu aplicación)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
CMD curl -f http://localhost:8080/actuator/health || exit 1

# Entrypoint optimizado para contenedores
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Contruir Jdk21 con ti
```
docker build -t mi-app:jdk21-segura .
```
