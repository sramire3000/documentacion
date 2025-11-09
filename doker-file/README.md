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

### Contruir Imagen jdk 1.8
```
docker build -t jdk8-base:latest .
```

## OpenJdk17

### Dockerfile
```
# Usar imagen oficial mínima y específica con versión fixed
FROM eclipse-temurin:17.0.11_9-jdk-jammy

# Metadatos de seguridad
LABEL maintainer="tu-equipo@empresa.com"
LABEL description="Imagen segura con JDK 17"
LABEL security.scan="true"
LABEL update.policy="weekly"

# Instalar solo lo esencial sin versiones fijas problemáticas
RUN apt-get update && \
    apt-get upgrade -y --no-install-recommends && \
    apt-get install -y --no-install-recommends \
    curl \
    ca-certificates \
    && \
    # Limpieza de seguridad en la MISMA capa
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
    # Hardening del sistema de archivos
    chmod 700 /tmp /var/tmp

# Crear usuario y grupo no-privilegiados con UID/GID específicos
RUN groupadd -r -g 1001 javaapp && \
    useradd -r -u 1001 -g javaapp -s /bin/false javaapp && \
    # Asegurar directorios del usuario
    mkdir -p /app /home/javaapp && \
    chown -R javaapp:javaapp /app /home/javaapp && \
    chmod 755 /app /home/javaapp

# Configurar directorio de trabajo seguro
WORKDIR /app

# Cambiar al usuario no-root
USER javaapp

# Variables de entorno seguras para JVM
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Health check seguro (sin herramientas externas)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -XshowSettings:system -version > /dev/null 2>&1 || exit 1

# Verificación de seguridad como entrypoint
CMD ["java", "-XshowSettings:security", "-version"]
```
### Contruir la imagen
```
docker build --no-cache -t jdk17-secure:latest .
```

## OpenJdk21

### File Dockerfile 21
```
# Usar imagen oficial con versión específica
FROM eclipse-temurin:21.0.2_13-jdk-jammy

# Metadatos de seguridad
LABEL maintainer="tu-equipo@empresa.com"
LABEL description="Imagen segura con JDK 21"
LABEL security.scan="true"
LABEL update.policy="weekly"

# Instalar solo actualizaciones de seguridad esenciales
RUN apt-get update && \
    apt-get upgrade -y --no-install-recommends && \
    # Instalar solo lo absolutamente necesario
    apt-get install -y --no-install-recommends \
    tzdata \
    && \
    # Limpieza de seguridad en la misma capa
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
    # Hardening del sistema de archivos
    chmod 700 /tmp /var/tmp

# Crear usuario y grupo no-privilegiados con UID/GID específicos
RUN groupadd -r -g 1001 javaapp && \
    useradd -r -u 1001 -g javaapp -s /bin/false javaapp && \
    # Asegurar directorios del usuario
    mkdir -p /app /home/javaapp && \
    chown -R javaapp:javaapp /app /home/javaapp && \
    chmod 755 /app /home/javaapp

# Configurar timezone de forma segura
ENV TZ=America/Mexico_City

# Configurar directorio de trabajo seguro
WORKDIR /app

# Cambiar al usuario no-root
USER javaapp

# Variables de entorno seguras para JVM
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0"

# Health check seguro (sin dependencias externas)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -XshowSettings:system -version > /dev/null 2>&1 || exit 1

# Verificación de seguridad como entrypoint
CMD ["java", "-XshowSettings:security", "-version"]
```

### Contruir la imagen
```
docker build -t jdk21-base:latest .
```

