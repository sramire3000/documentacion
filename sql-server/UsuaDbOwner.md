# Usuario de tipo DB Owner

### Crear
```
-- 1. Crear el inicio de sesión a nivel de servidor
USE [master];
GO
CREATE LOGIN [AdminPruebaDb] WITH PASSWORD = 'TuContraseñaSegura123!';
GO

-- 2. Cambiar a la base de datos específica
USE [PruebaDb];
GO

-- 3. Crear el usuario en la base de datos asociado al login
CREATE USER [AdminPruebaDb] FOR LOGIN [AdminPruebaDb];
GO

-- 4. Asignarle el rol de db_owner (administrador de esta base de datos)
ALTER ROLE [db_owner] ADD MEMBER [AdminPruebaDb];
GO
```


### Eliminarlo
```
-- 1. Eliminar el usuario de la base de datos específica
USE [PruebaDb];
GO
DROP USER [AdminPruebaDb];
GO

-- 2. Eliminar el inicio de sesión del servidor
USE [master];
GO
DROP LOGIN [AdminPruebaDb];
GO
```