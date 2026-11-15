# Usuario de servicio

### Paso 1: Crear el Inicio de Sesión y el Usuario en la Base de Datos
```
-- 1. Crear el inicio de sesión (Login) a nivel de servidor
CREATE LOGIN [UsuarioServicio] WITH PASSWORD = 'xxxxxxxxxx';
GO

-- 2. Cambiar a la base de datos donde tendrá acceso
USE [BaseDeDatos];
GO

-- 3. Crear el usuario de la base de datos asociado al login creado
CREATE USER [UsuarioServicio] FOR LOGIN [UsuarioServicio];
GO
```

### Paso 2: Otorgar los Permisos Específicos
```
USE [BaseDeDatos];
GO

-- 1. Otorgar permisos básicos de lectura, inserción, actualización y borrado en todas las tablas
GRANT SELECT, INSERT, UPDATE, DELETE TO [UsuarioServicio];
GO

-- 2. Otorgar permiso para ejecutar todos los Stored Procedures existentes y futuros
GRANT EXECUTE TO [UsuarioServicio];
GO

-- 3. Otorgar permiso para ejecutar todas las Funciones (Escalares, de Tabla y CLR)
GRANT VIEW DEFINITION TO [UsuarioServicio]; -- Opcional: permite ver la definición si es necesario
GRANT EXECUTE TO [UsuarioServicio]; 
-- Nota: En SQL Server, las funciones definidas por el usuario (incluyendo CLR) se ejecutan con el permiso EXECUTE.
GO
```

### Eliminar
```
-- 1. Primero, elimina el usuario dentro de la base de datos específica
USE [BaseDeDatos];
GO

DROP USER [UsuarioServicio];
GO

-- 2. Segundo, elimina el inicio de sesión a nivel de servidor
DROP LOGIN [UsuarioServicio];
GO
```
