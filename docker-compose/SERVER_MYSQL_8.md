# SERVIDOR MYSQL VER 8

## Archivo ".env"
```
# Password del root
MYSQL_ROOT_PASSWORD=TuPasswordRaiz123!
MYSQL_DATABASE=mi_proyecto_db
MYSQL_USER=development
MYSQL_PASSWORD=TuPassword
MYSQL_PORT=3306

MYSQL_CONTAINER_NAME=mysql8
MYSQL_CONTAINER_MEM_LIMIT=2g
MYSQL_CONTAINER_MEM_RESERV=1g
```

## Archivo "docker-compose.yml"
```
services:
  mysql_db:
    image: mysql:8.0
    container_name: ${MYSQL_CONTAINER_NAME}
    restart: unless-stopped
    ports:
      - "${MYSQL_PORT}:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
      - MYSQL_DATABASE=${MYSQL_DATABASE}
      - MYSQL_USER=${MYSQL_USER}
      - MYSQL_PASSWORD=${MYSQL_PASSWORD}
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: ${MYSQL_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: "0.1"
          memory: ${MYSQL_CONTAINER_MEM_RESERV}
    volumes:
      - mysql_data:/var/lib/mysql
    # Importante para compatibilidad con algunos clientes antiguos
    command: --default-authentication-plugin=mysql_native_password

volumes:
  mysql_data:
    driver: local
```

## Crear un usuario Admin
```
-- =============================================
-- CONFIGURACIÓN: Define tus credenciales aquí
-- =============================================
SET @Usuario  = 'HRAMIREZ';
SET @Password = 'Clave123';
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
