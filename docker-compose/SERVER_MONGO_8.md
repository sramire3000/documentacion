# SERVIDOR MONGO VER. 7

## Archivo ".env"
```
# ============================================================
# MongoDB - entorno de desarrollo
# Todos los valores del docker-compose.yml salen de este archivo.
# ============================================================

# ---------- Proyecto / imagen ----------
COMPOSE_PROJECT_NAME=mongo-dev
MONGO_VERSION=8.0

# ---------- Contenedor ----------
MONGO_CONTAINER_NAME=mongo-dev
RESTART_POLICY=unless-stopped
TZ=UTC

# ---------- Credenciales / base de datos ----------
# Cambiar la contraseña si el puerto se expone fuera de localhost.
MONGO_ROOT_USERNAME=devroot
MONGO_ROOT_PASSWORD=dev_password_123
MONGO_DATABASE=app_dev

# ---------- Red / puerto ----------
# 127.0.0.1 = solo accesible desde esta máquina (recomendado en dev).
MONGO_BIND_IP=127.0.0.1
MONGO_PORT=27017
MONGO_NETWORK_NAME=mongo-dev-net

# ---------- Volúmenes persistentes ----------
MONGO_VOLUME_NAME=mongo-dev-data
MONGO_CONFIG_VOLUME_NAME=mongo-dev-config

# ---------- Límites de recursos (dev) ----------
MONGO_CPU_LIMIT=1.0
MONGO_MEMORY_LIMIT=512M
MONGO_CPU_RESERVATION=0.25
MONGO_MEMORY_RESERVATION=256M

# Caché de WiredTiger en GB (debe ser menor que MONGO_MEMORY_LIMIT).
MONGO_WIREDTIGER_CACHE_GB=0.25

# ---------- Healthcheck MongoDB ----------
HEALTHCHECK_INTERVAL=10s
HEALTHCHECK_TIMEOUT=5s
HEALTHCHECK_RETRIES=10
HEALTHCHECK_START_PERIOD=20s

# ============================================================
# Mongo Express (interfaz web)
# ============================================================

# ---------- Imagen / contenedor ----------
ME_VERSION=1.0.2
ME_CONTAINER_NAME=mongo-express-dev

# ---------- Red / puerto ----------
ME_BIND_IP=127.0.0.1
ME_PORT=8081
ME_SITE_BASEURL=/

# ---------- Login de la interfaz ----------
ME_BASICAUTH_USERNAME=devadmin
ME_BASICAUTH_PASSWORD=dev_express_123

# Secretos de sesión/cookies de la interfaz (cambiar en entornos compartidos).
ME_COOKIE_SECRET=dev_cookie_secret_change_me
ME_SESSION_SECRET=dev_session_secret_change_me

# ---------- Límites de recursos (dev) ----------
ME_CPU_LIMIT=0.5
ME_MEMORY_LIMIT=256M
ME_CPU_RESERVATION=0.1
ME_MEMORY_RESERVATION=64M

# ---------- Healthcheck Mongo Express ----------
ME_HEALTHCHECK_INTERVAL=15s
ME_HEALTHCHECK_TIMEOUT=5s
ME_HEALTHCHECK_RETRIES=10
ME_HEALTHCHECK_START_PERIOD=20s

# ---------- Logs ----------
LOG_MAX_SIZE=10m
LOG_MAX_FILES=3
```

## Archivo "docker-compose.yml"
```
name: ${COMPOSE_PROJECT_NAME:-mongo-dev}

services:
  mongo:
    image: mongo:${MONGO_VERSION:-8.0}
    container_name: ${MONGO_CONTAINER_NAME:-mongo-dev}
    restart: ${RESTART_POLICY:-unless-stopped}
    environment:
      MONGO_INITDB_ROOT_USERNAME: ${MONGO_ROOT_USERNAME}
      MONGO_INITDB_ROOT_PASSWORD: ${MONGO_ROOT_PASSWORD}
      MONGO_INITDB_DATABASE: ${MONGO_DATABASE}
      TZ: ${TZ:-UTC}
    command:
      - mongod
      - --bind_ip_all
      # WiredTiger usa por defecto ~50% de la RAM vista; se fija explícitamente
      # para no chocar con el límite de memoria del contenedor.
      - --wiredTigerCacheSizeGB
      - ${MONGO_WIREDTIGER_CACHE_GB:-0.25}
    ports:
      - "${MONGO_BIND_IP:-127.0.0.1}:${MONGO_PORT:-27017}:27017"
    volumes:
      - mongo-data:/data/db
      - mongo-config:/data/configdb
    healthcheck:
      test:
        - CMD-SHELL
        - >-
          mongosh --quiet --host 127.0.0.1 --port 27017
          --username "$$MONGO_INITDB_ROOT_USERNAME"
          --password "$$MONGO_INITDB_ROOT_PASSWORD"
          --authenticationDatabase admin
          --eval "db.adminCommand('ping').ok" | grep -q 1
      interval: ${HEALTHCHECK_INTERVAL:-10s}
      timeout: ${HEALTHCHECK_TIMEOUT:-5s}
      retries: ${HEALTHCHECK_RETRIES:-10}
      start_period: ${HEALTHCHECK_START_PERIOD:-20s}
    deploy:
      resources:
        limits:
          cpus: "${MONGO_CPU_LIMIT:-1.0}"
          memory: ${MONGO_MEMORY_LIMIT:-512M}
        reservations:
          cpus: "${MONGO_CPU_RESERVATION:-0.25}"
          memory: ${MONGO_MEMORY_RESERVATION:-256M}
    logging:
      driver: json-file
      options:
        max-size: ${LOG_MAX_SIZE:-10m}
        max-file: "${LOG_MAX_FILES:-3}"
    networks:
      - mongo-net

  mongo-express:
    image: mongo-express:${ME_VERSION:-1.0.2}
    container_name: ${ME_CONTAINER_NAME:-mongo-express-dev}
    restart: ${RESTART_POLICY:-unless-stopped}
    depends_on:
      mongo:
        condition: service_healthy
    environment:
      # Conexión interna al servicio "mongo" (misma red de Compose).
      ME_CONFIG_MONGODB_URL: mongodb://${MONGO_ROOT_USERNAME}:${MONGO_ROOT_PASSWORD}@mongo:27017/?authSource=admin
      ME_CONFIG_MONGODB_ENABLE_ADMIN: "true"
      # Login de la interfaz web (no confundir con las credenciales de Mongo).
      ME_CONFIG_BASICAUTH: "true"
      ME_CONFIG_BASICAUTH_USERNAME: ${ME_BASICAUTH_USERNAME}
      ME_CONFIG_BASICAUTH_PASSWORD: ${ME_BASICAUTH_PASSWORD}
      ME_CONFIG_SITE_BASEURL: ${ME_SITE_BASEURL:-/}
      ME_CONFIG_SITE_COOKIESECRET: ${ME_COOKIE_SECRET}
      ME_CONFIG_SITE_SESSIONSECRET: ${ME_SESSION_SECRET}
      TZ: ${TZ:-UTC}
    ports:
      - "${ME_BIND_IP:-127.0.0.1}:${ME_PORT:-8081}:8081"
    healthcheck:
      test:
        - CMD-SHELL
        # mongo-express responde 401 sin credenciales; cualquier respuesta HTTP
        # (200/302/401) confirma que el servidor está escuchando.
        - >-
          wget -S -O /dev/null http://127.0.0.1:8081/
          2>&1 | grep -qE "HTTP/[0-9.]+ (200|302|401)"
      interval: ${ME_HEALTHCHECK_INTERVAL:-15s}
      timeout: ${ME_HEALTHCHECK_TIMEOUT:-5s}
      retries: ${ME_HEALTHCHECK_RETRIES:-10}
      start_period: ${ME_HEALTHCHECK_START_PERIOD:-20s}
    deploy:
      resources:
        limits:
          cpus: "${ME_CPU_LIMIT:-0.5}"
          memory: ${ME_MEMORY_LIMIT:-256M}
        reservations:
          cpus: "${ME_CPU_RESERVATION:-0.1}"
          memory: ${ME_MEMORY_RESERVATION:-64M}
    logging:
      driver: json-file
      options:
        max-size: ${LOG_MAX_SIZE:-10m}
        max-file: "${LOG_MAX_FILES:-3}"
    networks:
      - mongo-net

volumes:
  mongo-data:
    name: ${MONGO_VOLUME_NAME:-mongo-dev-data}
  mongo-config:
    name: ${MONGO_CONFIG_VOLUME_NAME:-mongo-dev-config}

networks:
  mongo-net:
    name: ${MONGO_NETWORK_NAME:-mongo-dev-net}
    driver: bridge
```
