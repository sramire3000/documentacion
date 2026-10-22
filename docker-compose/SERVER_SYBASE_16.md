# SERVIDOR SYBASE 15.7

## Archivo ".env"
```
# =============================================================================
# Configuración centralizada de Sybase ASE 16 para desarrollo.
# Todo lo ajustable vive aquí; docker-compose.yml solo consume estas variables.
# =============================================================================

# ---- Proyecto / contenedor ----
COMPOSE_PROJECT_NAME=sybase-16-dev
SYBASE_CONTAINER_NAME=sybase-16-dev
# Imagen de ASE 16 (amd64). Alternativas: nguoianphu/docker-sybase:latest,
# datagrip/sybase:15.7. La de datagrip es la base más usada y mantenida.
SYBASE_IMAGE=datagrip/sybase:16.0
SYBASE_RESTART_POLICY=unless-stopped

# ---- Credenciales de la aplicación ----
# El login y la base se crean si no existen (volumen nuevo). En un volumen con
# datos, cambiar SYBASE_PASSWORD no cambia la clave real del login: hay que
# cambiarla con sp_password.
SYBASE_USER=dev
SYBASE_PASSWORD=DevPassword123!
SYBASE_DB=dev_db

# Clave de la cuenta sa. La imagen la fija en "myPassword" durante el build;
# si se cambia dentro del contenedor con sp_password, actualizar este valor
# para que entrypoint.sh pueda seguir administrando el servidor.
SYBASE_SA_PASSWORD=myPassword

# ---- Red ----
# Interfaz del host donde se publica el puerto. 127.0.0.1 = solo local.
SYBASE_BIND_IP=127.0.0.1
# Puerto publicado en el host (el 5000 es el puerto ASE por defecto).
# El puerto interno del contenedor siempre es 5000.
SYBASE_PORT=5000

# ---- Idioma / zona horaria ----
TZ=America/El_Salvador

# ---- Tamaño inicial de la base (solo volumen vacío) ----
# Devices app_data.dat y app_log.dat, en MiB. Con 1024/512 sobra para
# desarrollo; el log se trunca en cada checkpoint.
SYBASE_DATA_SIZE_MB=1024
SYBASE_LOG_SIZE_MB=512
# Pool de locks del servidor. Más locks = más memoria de ASE.
SYBASE_NUMBER_OF_LOCKS=50000

# ---- Persistencia ----
# Volumen con nombre (recomendado). Borrarlo elimina todas las bases.
SYBASE_VOLUME_NAME=sybase_dev_data

# ---- Límites del contenedor ----
# ASE con la configuración de esta imagen usa ~200 MB en reposo; 2 GB dejan
# margen para el pool de locks y para tablas medianas en desarrollo.
SYBASE_CPUS_LIMIT=1.0
SYBASE_CPUS_RESERVATION=0.25
SYBASE_MEM_LIMIT=2g
SYBASE_MEM_RESERVATION=512m
# /dev/shm del contenedor: ASE usa memoria compartida al arrancar.
SYBASE_SHM_SIZE=512m
# Descriptores de archivo (ASE abre un fd por device/usuario activo).
SYBASE_NOFILE_SOFT=65535
SYBASE_NOFILE_HARD=65535

# ---- Healthcheck / apagado ----
# El primer arranque crea los devices y la base: puede tardar varios minutos.
SYBASE_HEALTHCHECK_INTERVAL=15s
SYBASE_HEALTHCHECK_TIMEOUT=10s
SYBASE_HEALTHCHECK_RETRIES=10
SYBASE_HEALTHCHECK_START_PERIOD=180s
# Segundos que espera entrypoint.sh a que ASE acepte conexiones al arrancar.
SYBASE_STARTUP_TIMEOUT=600
# Segundos de espera para el shutdown limpio antes de forzar el proceso.
SYBASE_SHUTDOWN_TIMEOUT=60
# ASE necesita tiempo para hacer checkpoint y cerrar limpio.
SYBASE_STOP_GRACE_PERIOD=120s

# ---- Logs ----
LOG_MAX_SIZE=10m
LOG_MAX_FILES=3
```

## Archivo "docker-compose.yml"
```
name: ${COMPOSE_PROJECT_NAME:-sybase-dev}

services:
  sybase:
    image: ${SYBASE_IMAGE:-datagrip/sybase:16.0}
    container_name: ${SYBASE_CONTAINER_NAME:-sybase-dev}
    restart: ${SYBASE_RESTART_POLICY:-unless-stopped}
    # El nombre del servidor ASE está fijado en la imagen (archivo interfaces);
    # el entrypoint y el healthcheck se conectan como MYSYBASE.
    hostname: MYSYBASE
    # Entrypoint propio (ver entrypoint.sh): el de la imagen inicializa la base
    # demasiado pronto y falla con contraseñas que llevan símbolos.
    entrypoint: ["/bin/bash", "/opt/sybase-dev/entrypoint.sh"]
    environment:
      # Login/base: se crean automáticamente si no existen (volumen nuevo).
      SYBASE_USER: ${SYBASE_USER:?Debes definir SYBASE_USER en .env}
      SYBASE_PASSWORD: ${SYBASE_PASSWORD:?Debes definir SYBASE_PASSWORD en .env}
      SYBASE_DB: ${SYBASE_DB:?Debes definir SYBASE_DB en .env}
      # Lo usa el healthcheck para conectarse por el archivo interfaces.
      SYBASE_HOSTNAME: MYSYBASE
      # Clave de sa (fijada en la imagen; solo cambia si se usa sp_password).
      SYBASE_SA_PASSWORD: ${SYBASE_SA_PASSWORD:-myPassword}
      # Capacidad de los devices de datos y log (MiB). Ver README.
      SYBASE_DATA_SIZE_MB: ${SYBASE_DATA_SIZE_MB:-1024}
      SYBASE_LOG_SIZE_MB: ${SYBASE_LOG_SIZE_MB:-512}
      SYBASE_NUMBER_OF_LOCKS: ${SYBASE_NUMBER_OF_LOCKS:-50000}
      SYBASE_STARTUP_TIMEOUT: ${SYBASE_STARTUP_TIMEOUT:-600}
      SYBASE_SHUTDOWN_TIMEOUT: ${SYBASE_SHUTDOWN_TIMEOUT:-60}
      TZ: ${TZ:-America/El_Salvador}
    ports:
      # Host:contenedor. El puerto interno de ASE es 5000 y está fijado en la
      # imagen (archivo interfaces). Solo accesible desde 127.0.0.1 por defecto.
      - "${SYBASE_BIND_IP:-127.0.0.1}:${SYBASE_PORT:-5000}:5000"
    volumes:
      # Script de arranque (solo lectura). z = etiqueta SELinux para el contenedor.
      - type: bind
        source: ./entrypoint.sh
        target: /opt/sybase-dev/entrypoint.sh
        read_only: true
        bind:
          selinux: z
      # Volumen con nombre para los devices (master.dat, app_data.dat, app_log.dat).
      # Un bind mount suele fallar por permisos del usuario sybase.
      - type: volume
        source: sybase_data
        target: /opt/sybase/data
    # Límites para desarrollo. cpus/mem_limit los aplica Compose v2 al contenedor;
    # deploy.resources se mantiene para compatibilidad con Swarm.
    cpus: "${SYBASE_CPUS_LIMIT:-1.0}"
    mem_limit: "${SYBASE_MEM_LIMIT:-2g}"
    mem_reservation: "${SYBASE_MEM_RESERVATION:-512m}"
    shm_size: "${SYBASE_SHM_SIZE:-512m}"
    deploy:
      resources:
        limits:
          cpus: "${SYBASE_CPUS_LIMIT:-1.0}"
          memory: "${SYBASE_MEM_LIMIT:-2g}"
        reservations:
          cpus: "${SYBASE_CPUS_RESERVATION:-0.25}"
          memory: "${SYBASE_MEM_RESERVATION:-512m}"
    ulimits:
      nofile:
        soft: ${SYBASE_NOFILE_SOFT:-65535}
        hard: ${SYBASE_NOFILE_HARD:-65535}
    healthcheck:
      # isql necesita las librerías de ASE, por eso se carga SYBASE.sh primero.
      # Se prueba con el usuario/base de la aplicación: así el "healthy" confirma
      # que el login y la base se crearon, no solo que el motor responde.
      # ASE isql no tiene opción de query: la sentencia se pasa por stdin.
      test:
        - CMD-SHELL
        - >-
          . /opt/sybase/SYBASE.sh >/dev/null 2>&1;
          printf 'select 1\ngo\n' | /opt/sybase/OCS-16_0/bin/isql
          -U"$$SYBASE_USER" -P"$$SYBASE_PASSWORD" -S"$$SYBASE_HOSTNAME"
          -D"$$SYBASE_DB" -l 5 -t 10 -b --retserverror >/dev/null 2>&1
      interval: ${SYBASE_HEALTHCHECK_INTERVAL:-15s}
      timeout: ${SYBASE_HEALTHCHECK_TIMEOUT:-10s}
      retries: ${SYBASE_HEALTHCHECK_RETRIES:-10}
      start_period: ${SYBASE_HEALTHCHECK_START_PERIOD:-180s}
    # ASE necesita tiempo para hacer checkpoint y cerrar limpio.
    stop_grace_period: ${SYBASE_STOP_GRACE_PERIOD:-60s}
    logging:
      driver: json-file
      options:
        max-size: ${LOG_MAX_SIZE:-10m}
        max-file: "${LOG_MAX_FILES:-3}"

volumes:
  sybase_data:
    name: ${SYBASE_VOLUME_NAME:-sybase_dev_data}
    driver: local
```

### Crear archivo "entrypoint.sh"
```
#!/bin/bash
# =============================================================================
# Arranque de Sybase ASE 16 para desarrollo.
#
# Sustituye al entrypoint de datagrip/sybase:16.0, que espera mensajes de un
# log que ya viene dentro de la imagen (por eso inicializa demasiado pronto),
# crea la base sobre el device "master" y falla si la contraseña lleva "!".
#
# Este script:
#   1. arranca dataserver,
#   2. espera a que ASE acepte conexiones (timeout configurable),
#   3. crea devices, base, login y permisos solo si no existen (idempotente),
#   4. aplica el tamaño del pool de locks,
#   5. se queda en primer plano y cierra ASE limpio al recibir SIGTERM.
#
# Toda la configuración llega por variables de entorno (ver docker-compose.yml
# y .env).
# =============================================================================

export SYBASE=/opt/sybase
# Ojo: SYBASE.sh usa variables opcionales (INCLUDE, LIB...), por eso se carga
# antes de activar el modo estricto de variables.
# shellcheck disable=SC1091
. "$SYBASE/SYBASE.sh" >/dev/null 2>&1

set -u

ISQL="$SYBASE/OCS-16_0/bin/isql"
DATA_DIR="$SYBASE/data"
LOG_FILE="$SYBASE/ASE-16_0/install/MYSYBASE.log"

: "${SYBASE_HOSTNAME:=MYSYBASE}"
: "${SYBASE_USER:=dev}"
: "${SYBASE_PASSWORD:=guest1234}"
: "${SYBASE_DB:=dev_db}"
: "${SYBASE_DATA_SIZE_MB:=1024}"
: "${SYBASE_LOG_SIZE_MB:=512}"
: "${SYBASE_NUMBER_OF_LOCKS:=50000}"
# Clave de sa fijada en la imagen durante el build; no es configurable.
: "${SYBASE_SA_PASSWORD:=myPassword}"
: "${SYBASE_STARTUP_TIMEOUT:=600}"
: "${SYBASE_SHUTDOWN_TIMEOUT:=60}"

SERVER_PID=""
SHUTTING_DOWN=0

log() { printf '[sybase] %s\n' "$*"; }
die() { printf '[sybase] ERROR: %s\n' "$*" >&2; exit 1; }

sql_quote() { printf '%s' "$1" | sed "s/'/''/g"; }

# Ejecuta SQL por stdin como sa. --retserverror hace que isql devuelva un
# código distinto de 0 si el servidor reporta un error.
run_sql() {
  "$ISQL" -Usa -P"$SYBASE_SA_PASSWORD" -S"$SYBASE_HOSTNAME" \
    -l 5 -t 30 -b --retserverror -w 300
}

# Devuelve el valor numérico de una consulta (vacío si falla o no hay filas).
query_scalar() {
  printf '%s\ngo\n' "$1" | run_sql -h 2>/dev/null |
    tr -d ' \t' | grep -Ex '[0-9]+' | tail -n 1
}

device_exists() {
  [ "$(query_scalar "select count(*) from master.dbo.sysdevices where name = '$(sql_quote "$1")'")" = "1" ]
}

login_exists() {
  [ "$(query_scalar "select count(*) from master.dbo.syslogins where name = '$(sql_quote "$SYBASE_USER")'")" = "1" ]
}

database_exists() {
  [ "$(query_scalar "select count(*) from master.dbo.sysdatabases where name = '$(sql_quote "$SYBASE_DB")'")" = "1" ]
}

db_user_exists() {
  [ "$(query_scalar "select count(*) from $SYBASE_DB.dbo.sysusers where name = '$(sql_quote "$SYBASE_USER")'")" = "1" ]
}

validate_config() {
  [[ "$SYBASE_USER" =~ ^[A-Za-z][A-Za-z0-9_]*$ ]] ||
    die "SYBASE_USER debe ser un identificador simple (letras, números, _)"
  [[ "$SYBASE_DB" =~ ^[A-Za-z][A-Za-z0-9_]*$ ]] ||
    die "SYBASE_DB debe ser un identificador simple (letras, números, _)"
  [[ "$SYBASE_DATA_SIZE_MB" =~ ^[1-9][0-9]*$ ]] ||
    die "SYBASE_DATA_SIZE_MB debe ser un entero positivo"
  [[ "$SYBASE_LOG_SIZE_MB" =~ ^[1-9][0-9]*$ ]] ||
    die "SYBASE_LOG_SIZE_MB debe ser un entero positivo"
}

ping_ase() {
  printf 'select 1\ngo\n' | run_sql >/dev/null 2>&1
}

start_server() {
  log "Iniciando ASE ($SYBASE_HOSTNAME)..."
  sh "$SYBASE/ASE-16_0/install/RUN_MYSYBASE" >/tmp/dataserver.out 2>&1 &
  SERVER_PID=$!

  local deadline=$((SECONDS + SYBASE_STARTUP_TIMEOUT))
  log "Esperando a que ASE acepte conexiones (máx. ${SYBASE_STARTUP_TIMEOUT}s)..."
  while (( SECONDS < deadline )); do
    if ! kill -0 "$SERVER_PID" 2>/dev/null; then
      tail -n 20 /tmp/dataserver.out >&2 2>/dev/null || true
      die "dataserver terminó durante el arranque; revisar $LOG_FILE"
    fi
    if ping_ase; then
      log "ASE acepta conexiones"
      return 0
    fi
    sleep 2
  done
  die "timeout esperando a que ASE arranque"
}

init_database() {
  if ! device_exists app_data; then
    log "Creando device app_data (${SYBASE_DATA_SIZE_MB} MiB)..."
    run_sql <<SQL || die "no se pudo crear el device app_data"
disk init name = 'app_data', physname = '$DATA_DIR/app_data.dat', size = '${SYBASE_DATA_SIZE_MB}M'
go
SQL
  fi

  if ! device_exists app_log; then
    log "Creando device app_log (${SYBASE_LOG_SIZE_MB} MiB)..."
    run_sql <<SQL || die "no se pudo crear el device app_log"
disk init name = 'app_log', physname = '$DATA_DIR/app_log.dat', size = '${SYBASE_LOG_SIZE_MB}M'
go
SQL
  fi

  log "Creando base de datos $SYBASE_DB..."
  run_sql <<SQL || die "no se pudo crear la base $SYBASE_DB"
create database $SYBASE_DB on app_data = '${SYBASE_DATA_SIZE_MB}M' log on app_log = '${SYBASE_LOG_SIZE_MB}M'
go
SQL

  log "Configurando opciones de $SYBASE_DB..."
  run_sql <<SQL || die "no se pudieron aplicar las opciones de $SYBASE_DB"
use master
go
exec sp_dboption '$SYBASE_DB', 'abort tran on log full', true
go
exec sp_dboption '$SYBASE_DB', 'allow nulls by default', true
go
exec sp_dboption '$SYBASE_DB', 'ddl in tran', true
go
exec sp_dboption '$SYBASE_DB', 'trunc log on chkpt', true
go
exec sp_dboption '$SYBASE_DB', 'select into', true
go
SQL
}

ensure_login() {
  if login_exists; then
    return 0
  fi
  log "Creando login $SYBASE_USER..."
  run_sql <<SQL || die "no se pudo crear el login $SYBASE_USER"
create login $SYBASE_USER with password '$(sql_quote "$SYBASE_PASSWORD")'
go
SQL
}

ensure_db_user() {
  if db_user_exists; then
    return 0
  fi
  log "Agregando el usuario $SYBASE_USER a $SYBASE_DB con permisos de desarrollo..."
  run_sql <<SQL || die "no se pudo agregar el usuario $SYBASE_USER"
use $SYBASE_DB
go
sp_adduser '$SYBASE_USER', '$SYBASE_USER', null
go
grant create default to $SYBASE_USER
go
grant create table to $SYBASE_USER
go
grant create view to $SYBASE_USER
go
grant create rule to $SYBASE_USER
go
grant create function to $SYBASE_USER
go
grant create procedure to $SYBASE_USER
go
SQL
}

apply_lock_pool() {
  [[ "$SYBASE_NUMBER_OF_LOCKS" =~ ^[1-9][0-9]*$ ]] ||
    die "SYBASE_NUMBER_OF_LOCKS debe ser un entero positivo"
  log "Aplicando number of locks = $SYBASE_NUMBER_OF_LOCKS..."
  run_sql <<SQL || die "no se pudo configurar el pool de locks"
use master
go
exec sp_configure 'number of locks', $SYBASE_NUMBER_OF_LOCKS
go
SQL
}

stop_server() {
  [ "$SHUTTING_DOWN" = "1" ] && return 0
  SHUTTING_DOWN=1
  [ -n "$SERVER_PID" ] || return 0

  log "Deteniendo ASE..."
  printf 'shutdown\ngo\n' | run_sql >/dev/null 2>&1 || true

  local i
  for i in $(seq 1 "$SYBASE_SHUTDOWN_TIMEOUT"); do
    kill -0 "$SERVER_PID" 2>/dev/null || { log "ASE detenido"; return 0; }
    sleep 1
  done

  log "Forzando cierre de ASE..."
  pkill -TERM -x dataserver 2>/dev/null || true
  sleep 5
  pkill -KILL -x dataserver 2>/dev/null || true
}

trap 'stop_server; exit 0' INT TERM

main() {
  validate_config
  start_server

  if database_exists; then
    log "La base $SYBASE_DB ya existe; se conservan los datos"
  else
    init_database
  fi

  ensure_login
  ensure_db_user
  apply_lock_pool

  log "Listo: $SYBASE_DB en $SYBASE_HOSTNAME:5000 (usuario $SYBASE_USER)"
  wait "$SERVER_PID"
  local rc=$?
  [ "$SHUTTING_DOWN" = "1" ] && exit 0
  log "dataserver terminó con código $rc"
  exit "$rc"
}

main "$@"
```

## Crear un Usuario admin 
```
-- =============================================
-- CONFIGURACIÓN: Define tus credenciales aquí
-- =============================================
DECLARE @Usuario  VARCHAR(128) SELECT @Usuario = 'UserAdmin'
DECLARE @Password VARCHAR(128) SELECT @Password = 'TuClaveSecreta!'
-- =============================================

-- 1. Crear el Login en el servidor
EXEC sp_addlogin @Usuario, @Password


-- 2. Asignar el rol de Administrador de Sistema (sa_role)
EXEC sp_role 'grant', 'sa_role', @Usuario


PRINT 'Proceso completado. El usuario tiene permisos de sa_role.'

```

## Crear un usuario de servicio
```
-- =============================================
-- CONFIGURACIÓN: Cambia estos valores
-- =============================================
DECLARE @Usuario   VARCHAR(128) SELECT @Usuario = 'UsrServiceArreconsa'
DECLARE @Password  VARCHAR(128) SELECT @Password = 'TuPasswordFuerte123!'
DECLARE @BaseDatos VARCHAR(128) SELECT @BaseDatos = 'master'
-- =============================================

-- 1. Crear el Login a nivel de Servidor
EXEC sp_addlogin @Usuario, @Password, @BaseDatos
GO

-- 2. Entrar a la BD y crear el usuario
USE Arreconsa -- Nota: Sybase no permite variables en el comando USE directamente
GO
DECLARE @Usuario VARCHAR(128) SELECT @Usuario = 'UsrServiceArreconsa'
EXEC sp_adduser @Usuario, @Usuario
GO

-- 3. Asignar permisos DML (Sybase no usa roles db_datareader por defecto como SQL Server)
-- Se recomienda dar permisos directos o crear un grupo.
GRANT SELECT, INSERT, UPDATE, DELETE TO UsrServiceArreconsa
GO

PRINT 'Usuario de servicio creado y con permisos DML asignados.'
GO
```

## Eliminar un usuario
```
-- =============================================
-- CONFIGURACIÓN: Usuario a borrar
-- =============================================
DECLARE @Usuario VARCHAR(128) SELECT @Usuario = 'UsrServiceArreconsa'
-- =============================================

-- 1. Eliminar de la base de datos específica
-- Debes estar en la BD para ejecutar sp_dropuser
USE Arreconsa
EXEC sp_dropuser @Usuario

-- 2. Eliminar el Login del servidor

USE master
EXEC sp_droplogin @Usuario


PRINT 'Usuario eliminado correctamente del servidor y la base de datos.'
GO
```
