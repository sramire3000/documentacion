# SERVIDOR SQL 2017

## Archivo .env
```
# =============================================================================
# Configuración centralizada de SQL Server para desarrollo.
# Todo lo ajustable vive aquí; docker-compose.yml solo consume estas variables.
# =============================================================================

# ---- Proyecto / contenedor ----
COMPOSE_PROJECT_NAME=sqlserver-2022-dev
MSSQL_CONTAINER_NAME=sqlserver-2022-dev
# 2022-latest, 2019-latest o 2017-latest. Para 2025 usar 2025-latest.
MSSQL_IMAGE=mcr.microsoft.com/mssql/server:2022-latest
MSSQL_RESTART_POLICY=unless-stopped

# ---- Edición ----
# Developer = gratis para desarrollo, sin límite de características.
# NUNCA usar Developer/Express en producción.
MSSQL_PID=Developer

# ---- Credenciales ----
# Mínimo 8 caracteres y debe combinar mayúsculas, minúsculas, dígitos y símbolo.
# Ojo: se aplica solo en la primera inicialización del volumen. Si la cambias
# después, la contraseña real del sa sigue siendo la anterior.
MSSQL_SA_PASSWORD='SqlDev#2026!'

# ---- Red ----
# Interfaz del host donde se publica el puerto. 127.0.0.1 = solo local.
MSSQL_BIND_IP=127.0.0.1
# Puerto publicado en el host.
MSSQL_PORT=1433
# Puerto interno de SQL Server (MSSQL_TCP_PORT). Debe coincidir con el mapeo.
MSSQL_TCP_PORT=1433

# ---- Idioma / zona horaria ----
TZ=America/El_Salvador
# SQL Server Agent dentro del contenedor (útil para jobs en dev).
MSSQL_AGENT_ENABLED=false
# La imagen 2022 no aplica MSSQL_COLLATION: se usa la colación por defecto
# (SQL_Latin1_General_CP1_CI_AS). Para otra, crear la base con COLLATE.

# ---- Persistencia ----
# Volumen con nombre (recomendado). Borrarlo elimina todas las bases.
MSSQL_VOLUME_NAME=sqlserver_dev_data

# ---- Límites del contenedor ----
# SQL Server en Linux requiere 2 GB de RAM como mínimo para arrancar.
# No bajar MSSQL_MEM_LIMIT de 2g.
# El motor detecta este límite cgroup al arrancar, por eso no hace falta
# configurar memoria dentro de SQL Server (MSSQL_MEMORY_LIMIT_MB se ignora
# en esta imagen). Verificar con: docker compose logs | grep "Detected".
MSSQL_CPUS_LIMIT=1.0
MSSQL_CPUS_RESERVATION=0.25
MSSQL_MEM_LIMIT=2g
MSSQL_MEM_RESERVATION=512m

# ---- Healthcheck / apagado ----
MSSQL_HEALTHCHECK_INTERVAL=10s
MSSQL_HEALTHCHECK_TIMEOUT=5s
MSSQL_HEALTHCHECK_RETRIES=10
MSSQL_HEALTHCHECK_START_PERIOD=60s
# SQL Server necesita tiempo para un checkpoint limpio al detenerse.
MSSQL_STOP_GRACE_PERIOD=45s

# ---- Logs ----
LOG_MAX_SIZE=10m
LOG_MAX_FILES=3
```

## Archivo "docker-compose.yml"
```
name: ${COMPOSE_PROJECT_NAME:-sqlserver-dev}

services:
  sqlserver:
    image: ${MSSQL_IMAGE:-mcr.microsoft.com/mssql/server:2022-latest}
    container_name: ${MSSQL_CONTAINER_NAME:-sqlserver-dev}
    restart: ${MSSQL_RESTART_POLICY:-unless-stopped}
    environment:
      ACCEPT_EULA: "Y"
      MSSQL_PID: ${MSSQL_PID:-Developer}
      MSSQL_SA_PASSWORD: ${MSSQL_SA_PASSWORD:?Debes definir MSSQL_SA_PASSWORD en .env}
      MSSQL_TCP_PORT: ${MSSQL_TCP_PORT:-1433}
      MSSQL_AGENT_ENABLED: ${MSSQL_AGENT_ENABLED:-false}
      TZ: ${TZ:-America/El_Salvador}
    ports:
      # Host:contenedor. Solo accesible desde 127.0.0.1 por defecto.
      - "${MSSQL_BIND_IP:-127.0.0.1}:${MSSQL_PORT:-1433}:${MSSQL_TCP_PORT:-1433}"
    volumes:
      # Volumen con nombre: Docker hereda el propietario (uid 10001) de la imagen,
      # por eso funciona sin tocar permisos. Los bind mounts suelen fallar aquí.
      - type: volume
        source: mssql_data
        target: /var/opt/mssql
    # Límites para desarrollo (Compose v2 los aplica al contenedor).
    # SQL Server detecta el límite cgroup al arrancar (ver errorlog:
    # "Detected N MB of RAM"), así que mem_limit es el tope efectivo del motor.
    cpus: "${MSSQL_CPUS_LIMIT:-1.0}"
    mem_limit: "${MSSQL_MEM_LIMIT:-2g}"
    mem_reservation: "${MSSQL_MEM_RESERVATION:-512m}"
    # Equivalentes para Swarm / compatibilidad con deploy.
    deploy:
      resources:
        limits:
          cpus: "${MSSQL_CPUS_LIMIT:-1.0}"
          memory: "${MSSQL_MEM_LIMIT:-2g}"
        reservations:
          cpus: "${MSSQL_CPUS_RESERVATION:-0.25}"
          memory: "${MSSQL_MEM_RESERVATION:-512m}"
    healthcheck:
      # sqlcmd -l 3 corta el login rápido para no agotar el timeout del check.
      # Las imágenes 2022+ usan mssql-tools18; el fallback cubre imágenes viejas.
      test:
        - CMD-SHELL
        - >-
          cmd=/opt/mssql-tools18/bin/sqlcmd;
          [ -x "$$cmd" ] || cmd=/opt/mssql-tools/bin/sqlcmd;
          "$$cmd" -S "localhost,$$MSSQL_TCP_PORT" -U sa
          -P "$$MSSQL_SA_PASSWORD" -C -b -l 3 -Q "SELECT 1" -o /dev/null
      interval: ${MSSQL_HEALTHCHECK_INTERVAL:-10s}
      timeout: ${MSSQL_HEALTHCHECK_TIMEOUT:-5s}
      retries: ${MSSQL_HEALTHCHECK_RETRIES:-10}
      start_period: ${MSSQL_HEALTHCHECK_START_PERIOD:-60s}
    stop_grace_period: ${MSSQL_STOP_GRACE_PERIOD:-45s}
    logging:
      driver: json-file
      options:
        max-size: ${LOG_MAX_SIZE:-10m}
        max-file: "${LOG_MAX_FILES:-3}"

volumes:
  mssql_data:
    name: ${MSSQL_VOLUME_NAME:-sqlserver_dev_data}
```

## Crear un Usuario admin
```
-- =============================================
-- CONFIGURACIÓN: Define tus credenciales aquí
-- =============================================
DECLARE @Usuario   NVARCHAR(128) = 'UsrAdmin'
DECLARE @Password  NVARCHAR(128) = 'TuPasswordFuerte123' -- Al ser CHECK_POLICY = OFF, puede ser simple
-- =============================================

DECLARE @SQL NVARCHAR(MAX)

-- 1. Construir y ejecutar la creación del Login
SET @SQL = 'CREATE LOGIN [' + @Usuario + '] 
            WITH PASSWORD = ''' + @Password + ''', 
            CHECK_POLICY = OFF, 
            CHECK_EXPIRATION = OFF;'

PRINT 'Creando Login: ' + @Usuario
EXEC sp_executesql @SQL

-- 2. Construir y ejecutar la asignación de Sysadmin
SET @SQL = 'ALTER SERVER ROLE [sysadmin] ADD MEMBER [' + @Usuario + '];'

PRINT 'Asignando permisos de sysadmin...'
EXEC sp_executesql @SQL

PRINT 'Proceso completado con éxito.'

```

## Crear un usuario de servicio
```
-- =============================================
-- CONFIGURACIÓN: Cambia estos valores
-- =============================================
DECLARE @Usuario   NVARCHAR(128) = 'UsrServiceArreconsa'
DECLARE @Password  NVARCHAR(128) = 'TuPasswordFuerte123!'
DECLARE @BaseDatos NVARCHAR(128) = 'Arreconsa'
-- =============================================

-- 1. Crear el Login a nivel de Servidor
DECLARE @sqlLogin NVARCHAR(MAX) = 
'CREATE LOGIN [' + @Usuario + '] 
 WITH PASSWORD = ''' + @Password + ''', 
 CHECK_POLICY = OFF, 
 CHECK_EXPIRATION = OFF;
 ALTER LOGIN [' + @Usuario + '] ENABLE;'

EXEC sp_executesql @sqlLogin

-- 2. Crear el Usuario en la Base de Datos y asignar Roles
DECLARE @sqlUser NVARCHAR(MAX) = 
'USE [' + @BaseDatos + ']; 
 CREATE USER [' + @Usuario + '] FOR LOGIN [' + @Usuario + '];
 ALTER ROLE [db_datareader] ADD MEMBER [' + @Usuario + '];
 ALTER ROLE [db_datawriter] ADD MEMBER [' + @Usuario + '];'

EXEC sp_executesql @sqlUser

-- 3. Prueba de Verificación
PRINT 'Usuario creado exitosamente. Verificando...'
EXEC ('USE [' + @BaseDatos + ']; EXECUTE AS LOGIN = ''' + @Usuario + '''; 
       SELECT USER_NAME() AS [Usuario_Actual], DB_NAME() AS [Base_Actual]; 
       REVERT;')
```

## Eliminar un usuario
```
-- =============================================
-- CONFIGURACIÓN: Escribe aquí el usuario a borrar
-- =============================================
DECLARE @UsuarioABorrar NVARCHAR(128) = 'UsrServiceArreconsa'
-- =============================================

DECLARE @DynamicSQL NVARCHAR(MAX)

-- 1. Eliminar el usuario de todas las Bases de Datos
-- Usamos sp_MSforeachdb pero construyendo el comando con la variable
SET @DynamicSQL = '
EXEC sp_MSforeachdb ''
    IF EXISTS (SELECT 1 FROM [?].sys.database_principals WHERE name = ''''' + @UsuarioABorrar + ''''')
    BEGIN
        PRINT ''''Eliminando usuario [' + @UsuarioABorrar + '] de la base de datos: [?]'''';
        DECLARE @dropUser NVARCHAR(MAX) = ''''USE [?]; DROP USER [' + @UsuarioABorrar + '];'''';
        EXEC sp_executesql @dropUser;
    END
'';'

EXEC sp_executesql @DynamicSQL

-- 2. Eliminar el Login del Servidor
SET @DynamicSQL = '
IF EXISTS (SELECT 1 FROM sys.server_principals WHERE name = ''' + @UsuarioABorrar + ''')
BEGIN
    PRINT ''Eliminando Login [' + @UsuarioABorrar + '] del servidor...'';
    DROP LOGIN [' + @UsuarioABorrar + '];
END
ELSE
BEGIN
    PRINT ''El Login [' + @UsuarioABorrar + '] no existe o ya fue eliminado.'';
END'

EXEC sp_executesql @DynamicSQL

```

## Usuario logeados
```
SELECT 
    s.session_id,
    s.login_name,
    s.host_name,
    s.program_name,
    d.name AS database_name,
    s.status,
    s.login_time
FROM sys.dm_exec_sessions s
INNER JOIN sys.databases d ON s.database_id = d.database_id
WHERE s.is_user_process = 1; -- Filtra para no ver procesos internos del sistema
```



