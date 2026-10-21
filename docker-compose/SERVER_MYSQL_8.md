# SERVIDOR MYSQL VER 8

## Archivo ".env"
```
# ==========================================================
# MySQL 8 - Entorno de desarrollo
# ==========================================================

# --- Credenciales (cambiar antes de usar en producción) ---
MYSQL_ROOT_PASSWORD=xxxxxxxxxx
MYSQL_DATABASE=xxxx
MYSQL_USER=xxxxx
MYSQL_PASSWORD=xxxxxxxxx

# --- Contenedor / red ---
MYSQL_CONTAINER_NAME=mysql8-dev
# Si ya tienes otro MySQL escuchando en 3306, usa por ejemplo 3307
MYSQL_PORT=3306
TZ=America/El_Salvador

# --- Persistencia ---
# El volumen se crea con este nombre fijo para que los datos sobrevivan
# a cambios de carpeta o de nombre del proyecto.
MYSQL_VOLUME_NAME=mysql8_data
MYSQL_NETWORK_NAME=mysql8_net

# --- Límites de recursos (desarrollo) ---
# cpus: 1.0 = un núcleo completo
MYSQL_CPUS_LIMIT=1.0
MYSQL_CPUS_RESERV=0.25
MYSQL_MEM_LIMIT=1g
MYSQL_MEM_RESERV=256m

# Buffer pool de InnoDB: ~50-60% del límite de memoria
MYSQL_INNODB_BUFFER_POOL_SIZE=512M
```

## Archivo "docker-compose.yml"
```
name: mysql8-dev

services:
  mysql:
    image: mysql:8.0
    container_name: ${MYSQL_CONTAINER_NAME}
    restart: unless-stopped
    ports:
      - "${MYSQL_PORT}:3306"
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DATABASE}
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
      TZ: ${TZ}
    # Opciones pensadas para desarrollo: charset completo y buffer pool acotado
    # para que el proceso quepa dentro del límite de memoria del contenedor.
    command:
      - --default-authentication-plugin=mysql_native_password
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_0900_ai_ci
      - --innodb-buffer-pool-size=${MYSQL_INNODB_BUFFER_POOL_SIZE}
      - --max-connections=100
    deploy:
      resources:
        limits:
          cpus: "${MYSQL_CPUS_LIMIT}"
          memory: ${MYSQL_MEM_LIMIT}
        reservations:
          cpus: "${MYSQL_CPUS_RESERV}"
          memory: ${MYSQL_MEM_RESERV}
    ulimits:
      nofile:
        soft: 20000
        hard: 40000
    volumes:
      - mysql_data:/var/lib/mysql
      # Scripts .sql/.sh que se ejecutan SOLO la primera vez (volumen vacío)
      - ./init:/docker-entrypoint-initdb.d:ro
    healthcheck:
      test: ["CMD-SHELL", "mysqladmin ping -h 127.0.0.1 -uroot -p\"$$MYSQL_ROOT_PASSWORD\" --silent"]
      interval: 15s
      timeout: 5s
      retries: 10
      start_period: 60s
    networks:
      - mysql_net

volumes:
  mysql_data:
    name: ${MYSQL_VOLUME_NAME}
    driver: local

networks:
  mysql_net:
    name: ${MYSQL_NETWORK_NAME}
    driver: bridge
```

# init/

Los archivos `*.sql`, `*.sql.gz` y `*.sh` que pongas acá se ejecutan **solo la
primera vez** que arranca el contenedor, es decir cuando el volumen
`mysql8_data` está vacío.

Para reinicializar desde cero:

```bash
docker compose down -v
docker compose up -d
```

> `down -v` borra el volumen y, con él, todos los datos.


## Crear un usuario Admin
```
-- =============================================
-- CONFIGURACIÓN: Define tus credenciales aquí
-- =============================================
SET @Usuario  = 'UsrAdmin';
SET @Password = 'TuPasswordFuerte123';
SET @Host     = '%'; -- '%' permite conexión desde cualquier IP. Usa 'localhost' para local.
-- =============================================

-- 1. Crear el usuario
SET @sqlCreate = CONCAT('CREATE USER IF NOT EXISTS ''', @Usuario, '''@''', @Host, ''' IDENTIFIED BY ''', @Password, ''';');
PREPARE stmt FROM @sqlCreate;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. Otorgar privilegios totales (Símil Sysadmin)
SET @sqlGrant = CONCAT('GRANT ALL PRIVILEGES ON *.* TO ''', @Usuario, '''@''', @Host, ''' WITH GRANT OPTION;');
PREPARE stmt FROM @sqlGrant;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

FLUSH PRIVILEGES;
SELECT 'Proceso completado con éxito' AS Status;
```

### Crear un usuario de servicio
```
-- =============================================
-- CONFIGURACIÓN: Cambia estos valores
-- =============================================
SET @Usuario   = 'UsrServiceArreconsa';
SET @Password  = 'TuPasswordFuerte123!';
SET @BaseDatos = 'Arreconsa';
SET @Host      = '%';
-- =============================================

-- 1. Crear el usuario de servicio
SET @sqlCreate = CONCAT('CREATE USER IF NOT EXISTS ''', @Usuario, '''@''', @Host, ''' IDENTIFIED BY ''', @Password, ''';');
PREPARE stmt FROM @sqlCreate;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. Asignar permisos DML específicos en la base de datos
SET @sqlGrant = CONCAT('GRANT SELECT, INSERT, UPDATE, DELETE ON ', @BaseDatos, '.* TO ''', @Usuario, '''@''', @Host, ''';');
PREPARE stmt FROM @sqlGrant;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

FLUSH PRIVILEGES;

-- 3. Verificación
SELECT user, host FROM mysql.user WHERE user = @Usuario;
```

## Eliminar un usuario
```
-- =============================================
-- CONFIGURACIÓN: Escribe aquí el usuario a borrar
-- =============================================
SET @UsuarioABorrar = 'UsrServiceArreconsa';
SET @Host           = '%';
-- =============================================

-- Construir y ejecutar la eliminación
SET @sqlDrop = CONCAT('DROP USER IF EXISTS ''', @UsuarioABorrar, '''@''', @Host, ''';');

PREPARE stmt FROM @sqlDrop;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT CONCAT('Usuario ', @UsuarioABorrar, ' eliminado con éxito') AS Status;
```
