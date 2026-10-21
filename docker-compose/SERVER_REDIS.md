# SERVIDOR SYBASE 15.7

## Archivo ".env"
```
# =============================================================================
# Configuración centralizada de Redis para desarrollo.
# Todo lo ajustable vive aquí; docker-compose.yml solo consume estas variables.
# =============================================================================

# ---- Proyecto / contenedor ----
COMPOSE_PROJECT_NAME=redis-dev
REDIS_CONTAINER_NAME=redis-dev
REDIS_IMAGE=redis:8-alpine
REDIS_RESTART_POLICY=unless-stopped

# ---- Red ----
# Interfaz del host donde se publica el puerto. 127.0.0.1 = solo local.
REDIS_BIND_IP=127.0.0.1
# Puerto publicado en el host.
REDIS_PORT=6379
# Interfaz de escucha dentro del contenedor.
REDIS_BIND_ADDR=0.0.0.0
# Puerto interno de Redis (debe coincidir con el destino del mapeo).
REDIS_INTERNAL_PORT=6379
# Con contraseña activa, "yes" rechaza conexiones no autenticadas.
REDIS_PROTECTED_MODE=yes
REDIS_PASSWORD=devredis

# ---- Persistencia ----
REDIS_VOLUME_NAME=redis_dev_data
REDIS_DATA_DIR=/data
REDIS_APPENDONLY=yes
REDIS_APPENDSYNC=everysec
# Snapshots RDB: pares "<segundos> <cambios>". Con persistencia AOF es opcional.
REDIS_SAVE=900 1 300 10 60 10000

# ---- Memoria interna de Redis ----
# IMPORTANTE: REDIS_MAXMEMORY debe ser menor que REDIS_MEM_LIMIT para que Redis
# libere/gestione memoria antes de que el kernel mate el contenedor (OOM).
REDIS_MAXMEMORY=384mb
# noeviction: no perder datos en silencio. Para caché pura usar allkeys-lru.
REDIS_MAXMEMORY_POLICY=noeviction
REDIS_LOGLEVEL=notice

# ---- Límites del contenedor ----
REDIS_CPUS_LIMIT=0.50
REDIS_CPUS_RESERVATION=0.10
REDIS_MEM_LIMIT=512m
REDIS_MEM_RESERVATION=128m

# ---- Healthcheck / apagado ----
REDIS_HEALTHCHECK_INTERVAL=10s
REDIS_HEALTHCHECK_TIMEOUT=5s
REDIS_HEALTHCHECK_RETRIES=5
REDIS_HEALTHCHECK_START_PERIOD=10s
REDIS_STOP_GRACE_PERIOD=30s
```

## Archivo "docker-compose.yml"
```
name: ${COMPOSE_PROJECT_NAME:-redis-dev}

services:
  redis:
    image: ${REDIS_IMAGE:-redis:8-alpine}
    container_name: ${REDIS_CONTAINER_NAME:-redis-dev}
    restart: ${REDIS_RESTART_POLICY:-unless-stopped}
    environment:
      REDIS_BIND_ADDR: ${REDIS_BIND_ADDR:-0.0.0.0}
      REDIS_INTERNAL_PORT: ${REDIS_INTERNAL_PORT:-6379}
      REDIS_PROTECTED_MODE: ${REDIS_PROTECTED_MODE:-yes}
      REDIS_DATA_DIR: ${REDIS_DATA_DIR:-/data}
      REDIS_PASSWORD: ${REDIS_PASSWORD:-devredis}
      REDIS_MAXMEMORY: ${REDIS_MAXMEMORY:-384mb}
      REDIS_MAXMEMORY_POLICY: ${REDIS_MAXMEMORY_POLICY:-noeviction}
      REDIS_APPENDONLY: ${REDIS_APPENDONLY:-yes}
      REDIS_APPENDSYNC: ${REDIS_APPENDSYNC:-everysec}
      REDIS_LOGLEVEL: ${REDIS_LOGLEVEL:-notice}
      REDIS_SAVE: ${REDIS_SAVE:-900 1 300 10 60 10000}
    command:
      - sh
      - -c
      - >-
        exec redis-server
        --bind "$$REDIS_BIND_ADDR"
        --port "$$REDIS_INTERNAL_PORT"
        --protected-mode "$$REDIS_PROTECTED_MODE"
        --dir "$$REDIS_DATA_DIR"
        --requirepass "$$REDIS_PASSWORD"
        --maxmemory "$$REDIS_MAXMEMORY"
        --maxmemory-policy "$$REDIS_MAXMEMORY_POLICY"
        --appendonly "$$REDIS_APPENDONLY"
        --appendfsync "$$REDIS_APPENDSYNC"
        --loglevel "$$REDIS_LOGLEVEL"
        --save $$REDIS_SAVE
    ports:
      # Host:contenedor. Solo accesible desde 127.0.0.1 por defecto.
      - "${REDIS_BIND_IP:-127.0.0.1}:${REDIS_PORT:-6379}:${REDIS_INTERNAL_PORT:-6379}"
    volumes:
      - type: volume
        source: redis_data
        target: ${REDIS_DATA_DIR:-/data}
    # Límites para desarrollo (Compose v2 los aplica al contenedor).
    cpus: "${REDIS_CPUS_LIMIT:-0.50}"
    mem_limit: "${REDIS_MEM_LIMIT:-512m}"
    mem_reservation: "${REDIS_MEM_RESERVATION:-128m}"
    # Equivalentes para Swarm / compatibilidad con deploy.
    deploy:
      resources:
        limits:
          cpus: "${REDIS_CPUS_LIMIT:-0.50}"
          memory: "${REDIS_MEM_LIMIT:-512m}"
        reservations:
          cpus: "${REDIS_CPUS_RESERVATION:-0.10}"
          memory: "${REDIS_MEM_RESERVATION:-128m}"
    healthcheck:
      test: ["CMD-SHELL", "redis-cli -p \"$$REDIS_INTERNAL_PORT\" --no-auth-warning -a \"$$REDIS_PASSWORD\" ping | grep -q PONG"]
      interval: ${REDIS_HEALTHCHECK_INTERVAL:-10s}
      timeout: ${REDIS_HEALTHCHECK_TIMEOUT:-5s}
      retries: ${REDIS_HEALTHCHECK_RETRIES:-5}
      start_period: ${REDIS_HEALTHCHECK_START_PERIOD:-10s}
    stop_grace_period: ${REDIS_STOP_GRACE_PERIOD:-30s}

volumes:
  redis_data:
    name: ${REDIS_VOLUME_NAME:-redis_dev_data}
```


