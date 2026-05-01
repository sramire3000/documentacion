# SERVIDOR POSTGRESQL

## Crear un usuario admin
```
-- =============================================
-- CONFIGURACIÓN: Define tus credenciales aquí
-- =============================================
DO $$
DECLARE 
    _usuario  TEXT := 'UserAdmin'; -- En Postgres se prefiere minúsculas
    _password TEXT := 'Clave123';
BEGIN
    -- 1. Crear el rol con privilegios de Superusuario
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = _usuario) THEN
        EXECUTE format('CREATE ROLE %I WITH LOGIN SUPERUSER PASSWORD %L', _usuario, _password);
        RAISE NOTICE 'Usuario administrador % creado con éxito.', _usuario;
    ELSE
        RAISE NOTICE 'El usuario % ya existe.', _usuario;
    END IF;
END $$;
```

## Crear un usuario de servicio
```
-- =============================================
-- CONFIGURACIÓN: Cambia estos valores
-- =============================================
DO $$
DECLARE 
    _usuario   TEXT := 'usr_service_arreconsa';
    _password  TEXT := 'TuPasswordFuerte123!';
    _database  TEXT := 'arreconsa';
    _schema    TEXT := 'public'; -- Esquema por defecto
BEGIN
    -- 1. Crear el usuario (Rol con LOGIN)
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = _usuario) THEN
        EXECUTE format('CREATE ROLE %I WITH LOGIN PASSWORD %L', _usuario, _password);
    END IF;

    -- 2. Asignar permisos básicos
    -- Nota: Debes estar conectado a la base de datos específica para que esto funcione
    EXECUTE format('GRANT CONNECT ON DATABASE %I TO %I', _database, _usuario);
    EXECUTE format('GRANT USAGE ON SCHEMA %I TO %I', _schema, _usuario);
    
    -- Otorgar SELECT, INSERT, UPDATE, DELETE en todas las tablas actuales
    EXECUTE format('GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA %I TO %I', _schema, _usuario);
    
    -- Opcional: Otorgar permisos en tablas futuras (solo versiones 9.0+)
    -- EXECUTE format('ALTER DEFAULT PRIVILEGES IN SCHEMA %I GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO %I', _schema, _usuario);

    RAISE NOTICE 'Usuario de servicio % configurado correctamente.', _usuario;
END $$;
```

## Eliminar un usuario
```
-- =============================================
-- CONFIGURACIÓN: Escribe aquí el usuario a borrar
-- =============================================
DO $$
DECLARE 
    _usuario TEXT := 'usr_service_arreconsa';
    _database TEXT := 'arreconsa';
BEGIN
    IF EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = _usuario) THEN
        -- 1. Desvincular privilegios
        EXECUTE format('REVOKE ALL PRIVILEGES ON DATABASE %I FROM %I', _database, _usuario);
        
        -- 2. Reasignar objetos (Si el usuario es dueño de algo, se pasa a postgres)
        -- EXECUTE format('REASSIGN OWNED BY %I TO postgres', _usuario);
        
        -- 3. Eliminar dependencias de privilegios
        EXECUTE format('DROP OWNED BY %I', _usuario);
        
        -- 4. Eliminar el rol
        EXECUTE format('DROP ROLE %I', _usuario);
        
        RAISE NOTICE 'Usuario % eliminado por completo.', _usuario;
    ELSE
        RAISE NOTICE 'El usuario % no existe.', _usuario;
    END IF;
END $$;
```
