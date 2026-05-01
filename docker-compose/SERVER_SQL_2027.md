# SERVIDOR SQL 2017

## Archivo .env
```
# Configuración de SQL Server
SQL_CONTAINER_NAME=mssql_2017
SQL_SA_PASSWORD=TuPasswordFuerte123!
SQL_MEM_LIMIT_MB=1536
SQL_CONTAINER_MEM_LIMIT=2g
SQL_CONTAINER_MEM_RESERV=1g
```

## Archivo "docker-compose.yml"
```
services:
  sqlserver:
    image: mcr.microsoft.com/mssql/server:2017-latest
    container_name: ${SQL_CONTAINER_NAME}
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: ${SQL_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: "0.1"
          memory: ${SQL_CONTAINER_MEM_RESERV}
    ports:
      - "1433:1433"
    environment:
      - ACCEPT_EULA=Y
      - SA_PASSWORD=${SQL_SA_PASSWORD}
      - MSSQL_MEMORY_LIMIT_MB=${SQL_MEM_LIMIT_MB}
    volumes:
      - mssql_data:/var/opt/mssql
    restart: unless-stopped

volumes:
  mssql_data:
```

## Crear un Usuario admin
```
-- Creamos el login desactivando la validación de políticas
CREATE LOGIN [NombreDeTuUsuario] 
WITH PASSWORD = 'PasswordSimple123', -- Aquí pones la que quieras
CHECK_POLICY = OFF,                  -- Desactiva la complejidad
CHECK_EXPIRATION = OFF;              -- Evita que caduque
GO

-- Asignamos los permisos de Admin
ALTER SERVER ROLE [sysadmin] 
ADD MEMBER [NombreDeTuUsuario];
GO
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

## Elimnar un usuario
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



