# Usuario Admin


### Crear un usuario Admin
```
-- 1. Crear el inicio de sesión con autenticación de SQL Server
CREATE LOGIN [TuUsuario] WITH PASSWORD = 'TuContraseñaSegura';
GO

-- 2. Asignarle el rol de sysadmin (privilegios equivalentes a 'sa')
ALTER SERVER ROLE sysadmin ADD MEMBER [TuUsuario];
GO
```

### Eliminar un usuario Admin
```
-- 1. (Opcional pero recomendado) Desconectar las sesiones activas de ese usuario si las hubiera
-- Nota: SQL Server cerrará las conexiones abiertas por este inicio de sesión.

-- 2. Eliminar el inicio de sesión del servidor
DROP LOGIN [TuUsuario];
GO
```
