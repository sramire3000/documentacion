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
Este archivo se encuentra en la carpeta:
- [Download entrypoint.zip](https://github.com/sramire3000/documentacion/blob/master/docker-compose/extras/entrypoint.zip)

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
