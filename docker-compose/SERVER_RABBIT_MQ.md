# Docker Composer RabbitMQ

### File .env
```
# ============================================================
# RabbitMQ - entorno de desarrollo
# Todos los valores del docker-compose.yml y de config/rabbitmq.conf
# salen de este archivo (rabbitmq.conf los interpola con $(VARIABLE)).
# ============================================================

# ---------- Proyecto / imagen ----------
COMPOSE_PROJECT_NAME=rabbitmq-dev
# Usar imagen "-management" para tener la interfaz web en el puerto 15672.
# Alternativas: rabbitmq:4.4-management (Debian) o rabbitmq:4.4-management-alpine
RABBITMQ_IMAGE=rabbitmq:4.4-management-alpine

# ---------- Contenedor ----------
RABBITMQ_CONTAINER_NAME=rabbitmq-dev
# Forma parte del nombre del nodo (rabbit@rabbitmq-dev). Si se cambia con
# datos ya persistidos, hay que recrear el volumen o renombrar la carpeta
# mnesia dentro de él.
RABBITMQ_HOSTNAME=rabbitmq-dev
RESTART_POLICY=unless-stopped
TZ=UTC

# ---------- Credenciales / vhost ----------
# El usuario se crea solo la primera vez (base de datos vacía).
# Evitar comillas simples (') en la contraseña: rabbitmq.conf la entrecomilla.
RABBITMQ_DEFAULT_USER=devuser
RABBITMQ_DEFAULT_PASS=dev_password_123
RABBITMQ_DEFAULT_VHOST=/

# ---------- Red / puertos ----------
# 127.0.0.1 = solo accesible desde esta máquina (recomendado en dev).
RABBITMQ_BIND_IP=127.0.0.1
# AMQP (clientes: aplicaciones).
RABBITMQ_AMQP_PORT=5672
# Interfaz web de administración.
RABBITMQ_MANAGEMENT_PORT=15672
RABBITMQ_NETWORK_NAME=rabbitmq-dev-net

# ---------- Volumen persistente ----------
# Contiene /var/lib/rabbitmq: mnesia, definiciones y cookie de Erlang.
RABBITMQ_VOLUME_NAME=rabbitmq-dev-data

# ---------- Límites de recursos (dev) ----------
RABBITMQ_CPU_LIMIT=1.0
RABBITMQ_MEMORY_LIMIT=512M
RABBITMQ_CPU_RESERVATION=0.25
RABBITMQ_MEMORY_RESERVATION=256M

# ---------- Umbrales internos de RabbitMQ ----------
# IMPORTANTE: en contenedores se usa un límite ABSOLUTO (no relativo) porque
# RabbitMQ no siempre detecta el límite de memoria del cgroup.
# Debe ser menor que RABBITMQ_MEMORY_LIMIT para que RabbitMQ active el flow
# control (memoria) antes de que el kernel mate el contenedor por OOM.
RABBITMQ_MEMORY_WATERMARK=300MB
# Espacio libre mínimo en el volumen; por debajo se activa la alarma de disco.
RABBITMQ_DISK_FREE_LIMIT=100MB
# error | warning | info | debug
RABBITMQ_LOG_LEVEL=info

# ---------- Healthcheck ----------
RABBITMQ_HEALTHCHECK_INTERVAL=15s
RABBITMQ_HEALTHCHECK_TIMEOUT=10s
RABBITMQ_HEALTHCHECK_RETRIES=10
RABBITMQ_HEALTHCHECK_START_PERIOD=45s

# ---------- Apagado ordenado ----------
RABBITMQ_STOP_GRACE_PERIOD=30s

# ---------- Logs ----------
LOG_MAX_SIZE=10m
LOG_MAX_FILES=3
```

### Create File docker-compose.yaml
```
name: ${COMPOSE_PROJECT_NAME:-rabbitmq-dev}

services:
  rabbitmq:
    image: ${RABBITMQ_IMAGE:-rabbitmq:4.4-management-alpine}
    container_name: ${RABBITMQ_CONTAINER_NAME:-rabbitmq-dev}
    # El nombre del nodo deriva del hostname; si cambia, RabbitMQ no encuentra
    # los datos previos del volumen (mnesia/rabbit@<hostname>).
    hostname: ${RABBITMQ_HOSTNAME:-rabbitmq-dev}
    restart: ${RESTART_POLICY:-unless-stopped}
    environment:
      RABBITMQ_NODENAME: rabbit@${RABBITMQ_HOSTNAME:-rabbitmq-dev}
      # Estas variables no las consume el entrypoint de la imagen: las usa
      # config/rabbitmq.conf mediante interpolación $(VAR). Así los valores
      # reales viven únicamente en .env.
      RABBITMQ_DEFAULT_USER: ${RABBITMQ_DEFAULT_USER}
      RABBITMQ_DEFAULT_PASS: ${RABBITMQ_DEFAULT_PASS}
      RABBITMQ_DEFAULT_VHOST: ${RABBITMQ_DEFAULT_VHOST:-/}
      RABBITMQ_MEMORY_WATERMARK: ${RABBITMQ_MEMORY_WATERMARK:-300MB}
      RABBITMQ_DISK_FREE_LIMIT: ${RABBITMQ_DISK_FREE_LIMIT:-100MB}
      RABBITMQ_LOG_LEVEL: ${RABBITMQ_LOG_LEVEL:-info}
      TZ: ${TZ:-UTC}
    ports:
      # AMQP 0-9-1 / AMQP 1.0
      - "${RABBITMQ_BIND_IP:-127.0.0.1}:${RABBITMQ_AMQP_PORT:-5672}:5672"
      # Interfaz web (requiere imagen -management)
      - "${RABBITMQ_BIND_IP:-127.0.0.1}:${RABBITMQ_MANAGEMENT_PORT:-15672}:15672"
    volumes:
      # Datos, mnesia y cookie de Erlang: todo el estado del nodo.
      - rabbitmq-data:/var/lib/rabbitmq
      - ./config/rabbitmq.conf:/etc/rabbitmq/rabbitmq.conf:ro
    healthcheck:
      test: ["CMD-SHELL", "rabbitmq-diagnostics -q ping"]
      interval: ${RABBITMQ_HEALTHCHECK_INTERVAL:-15s}
      timeout: ${RABBITMQ_HEALTHCHECK_TIMEOUT:-10s}
      retries: ${RABBITMQ_HEALTHCHECK_RETRIES:-10}
      start_period: ${RABBITMQ_HEALTHCHECK_START_PERIOD:-45s}
    deploy:
      resources:
        limits:
          cpus: "${RABBITMQ_CPU_LIMIT:-1.0}"
          memory: ${RABBITMQ_MEMORY_LIMIT:-512M}
        reservations:
          cpus: "${RABBITMQ_CPU_RESERVATION:-0.25}"
          memory: ${RABBITMQ_MEMORY_RESERVATION:-256M}
    logging:
      driver: json-file
      options:
        max-size: ${LOG_MAX_SIZE:-10m}
        max-file: "${LOG_MAX_FILES:-3}"
    # RabbitMQ necesita cerrar conexiones y persistir de forma ordenada.
    stop_grace_period: ${RABBITMQ_STOP_GRACE_PERIOD:-30s}
    networks:
      - rabbitmq-net

volumes:
  rabbitmq-data:
    name: ${RABBITMQ_VOLUME_NAME:-rabbitmq-dev-data}

networks:
  rabbitmq-net:
    name: ${RABBITMQ_NETWORK_NAME:-rabbitmq-dev-net}
    driver: bridge
```

### Create file "config/rabbitmq.conf"
```
# Configuración de RabbitMQ.
#
# Los valores NO se escriben aquí: se interpolan desde las variables de
# entorno definidas en .env / docker-compose.yml con la sintaxis $(VARIABLE)
# (soportada por RabbitMQ 3.9+). Así todo lo ajustable vive en .env.
#
# Los cambios en este archivo o en .env requieren reiniciar el contenedor.

# --- Usuario, contraseña y vhost creados con una base de datos vacía ---
default_user  = $(RABBITMQ_DEFAULT_USER)
default_pass  = '$(RABBITMQ_DEFAULT_PASS)'
default_vhost = $(RABBITMQ_DEFAULT_VHOST)

# --- Control de flujo por memoria ---
# Absoluto a propósito: RabbitMQ no siempre detecta el límite del cgroup,
# con lo que un porcentaje relativo se calcularía sobre la RAM del host.
vm_memory_high_watermark.absolute = $(RABBITMQ_MEMORY_WATERMARK)

# --- Alarma de disco ---
disk_free_limit.absolute = $(RABBITMQ_DISK_FREE_LIMIT)

# --- Logs a stdout (docker logs) ---
log.console       = true
log.console.level = $(RABBITMQ_LOG_LEVEL)
```

## URL WEB

-[Admin RabbitMQ](http://localhost:15672/)

