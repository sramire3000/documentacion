# Docker Composer Trivy

### File .env
```
# Directorio de cache de Trivy en el host
TRIVY_CACHE_VOLUME=./trivy-cache

# Directorio que contiene los archivos a escanear
HOST_SCAN_DIR=./scan

# Configuración de Trivy
TRIVY_CACHE_DIR=/tmp/trivy
TRIVY_TIMEOUT=10m
TRIVY_QUIET=false
TRIVY_FORMAT=table
TRIVY_SEVERITY=HIGH,CRITICAL
TRIVY_IGNORE_UNFIXED=false

# Comando por defecto al ejecutar el contenedor
TRIVY_DEFAULT_COMMAND=--help

# Credenciales opcionales para registros privados
# TRIVY_TOKEN=your-token-here
# TRIVY_USERNAME=your-username
# TRIVY_PASSWORD=your-password

# Archivos y directorios a excluir
# TRIVY_SKIP_FILES=/etc/ssl/certs/ca-certificates.crt
# TRIVY_SKIP_DIRS=/proc,/sys,/dev
```

### Create folder
```
mkdir -p scan
sudo chmod 777 scan

mkdir -p trivy-cache
sudo chmod 777 trivy-cache
```

### File docker-compose.yaml
```
version: '3.8'

services:
  trivy:
    image: aquasec/trivy:latest
    container_name: trivy-scanner
    environment:
      - TRIVY_CACHE_DIR=${TRIVY_CACHE_DIR:-/tmp/trivy}
      - TRIVY_TIMEOUT=${TRIVY_TIMEOUT:-5m}
      - TRIVY_QUIET=${TRIVY_QUIET:-false}
      - TRIVY_FORMAT=${TRIVY_FORMAT:-table}
      - TRIVY_SEVERITY=${TRIVY_SEVERITY:-HIGH,CRITICAL}
      - TRIVY_IGNORE_UNFIXED=${TRIVY_IGNORE_UNFIXED:-false}
      - TRIVY_SKIP_FILES=${TRIVY_SKIP_FILES}
      - TRIVY_SKIP_DIRS=${TRIVY_SKIP_DIRS}
      - TRIVY_TOKEN=${TRIVY_TOKEN}
      - TRIVY_USERNAME=${TRIVY_USERNAME}
      - TRIVY_PASSWORD=${TRIVY_PASSWORD}
    volumes:
      - ${TRIVY_CACHE_VOLUME:-./trivy-cache}:/tmp/trivy
      - ${HOST_SCAN_DIR:-./scan}:/scan:ro
      - /var/run/docker.sock:/var/run/docker.sock
    working_dir: /scan
    command: ${TRIVY_DEFAULT_COMMAND:---help}
    restart: unless-stopped
    profiles:
      - trivy
```
## Ejemplos de uso:

### 1. Escanear una imagen de Docker:
```
# Crear el directorio de scan si no existe
mkdir -p scan

# Ejecutar escaneo de imagen
docker-compose --profile trivy run --rm trivy image alpine:latest
```

### 2. Escanear un sistema de archivos:
```
# Copiar archivos al directorio de escaneo
cp -r tu-proyecto/ scan/

# Escanear el filesystem
docker-compose --profile trivy run --rm trivy filesystem --skip-update /scan
```

### 3. Escanear un repositorio Git:
```
docker-compose --profile trivy run --rm trivy repo https://github.com/username/repo.git
```

### 4. Comando personalizado con variables override:
```
TRIVY_FORMAT=json TRIVY_SEVERITY=CRITICAL,HIGH,MEDIUM docker-compose --profile trivy run --rm trivy image nginx:latest
```

### 5, Comando usando bitbucket
```
# Escanear repositorio público
docker-compose --profile trivy run --rm trivy repo https://bitbucket.org/usuario/repositorio.git

# Escanear repositorio privado con variables de entorno
TRIVY_USERNAME=tu_usuario TRIVY_PASSWORD=tu_token docker-compose --profile trivy run --rm trivy repo https://bitbucket.org/usuario/repositorio.git
```
