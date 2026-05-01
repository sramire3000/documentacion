# SERVIDOR SYBASE 15.7

## Archivo ".env"
```
SYBASE_CONTAINER_NAME=sybase_15_7
SYBASE_HOST_NAME=dksybase
# Ruta local donde se guardarán los datos (ajusta si es necesario)
SYBASE_DATA_PATH=./datadir

SYBASE_USER=sa
SYBASE_PASSWORD=password

SYBASE_CONTAINER_MEM_LIMIT=2g
SYBASE_CONTAINER_MEM_RESERV=1g
```

## Archivo "docker-compose.yml"
```
services:
  sybase:
    image: ifnazar/sybase_15_7
    container_name: ${SYBASE_CONTAINER_NAME}
    hostname: ${SYBASE_HOST_NAME}
    # El comando 'bash /sybase/start' que pusiste al final del docker run
    command: bash /sybase/start
    stdin_open: true # Equivalente a -i
    tty: true        # Equivalente a -t
    ports:
      - "5000:5000"
    volumes:
      # Mapeo de la carpeta local al contenedor según tu comando
      - ${SYBASE_DATA_PATH}:/opt/sybase/external_data:Z
    restart: "no" # Equivalente a --restart=no
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: ${SYBASE_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: "0.2"
          memory: ${SYBASE_CONTAINER_MEM_RESERV}
volumes:
  # Definimos el volumen si decides usar uno gestionado por docker, 
  # pero tu comando usa un bind-mount (carpeta local).
  sybase_data:
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
