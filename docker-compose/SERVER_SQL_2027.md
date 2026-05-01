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
-- 1. Crear el Login a nivel de Servidor
-- Usamos CHECK_POLICY = OFF solo si necesitas una clave simple, 
-- pero es mejor usar una compleja y dejarlo en ON.
CREATE LOGIN [User_App_Servicio] 
WITH PASSWORD = 'ClaveSegura_2026!', 
CHECK_POLICY = ON, 
CHECK_EXPIRATION = OFF;
GO

-- 2. Cambiar al contexto de tu Base de Datos
USE [TuBaseDeDatos]; 
GO

-- 3. Crear el Usuario en la Base de Datos vinculado al Login
CREATE USER [User_App_Servicio] FOR LOGIN [User_App_Servicio];
GO

-- 4. Asignar roles de lectura y escritura
-- db_datareader: Permite SELECT en todas las tablas
ALTER ROLE [db_datareader] ADD MEMBER [User_App_Servicio];

-- db_datawriter: Permite INSERT, UPDATE, DELETE en todas las tablas
ALTER ROLE [db_datawriter] ADD MEMBER [User_App_Servicio];
GO
```




