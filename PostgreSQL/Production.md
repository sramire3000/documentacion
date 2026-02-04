# Produccion

### Create Role DBA
```bash
CREATE ROLE dba WITH SUPERUSER;
GRANT USAGE ON SCHEMA CLINICA TO dba;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA CLINICA TO dba WITH GRANT OPTION;
```

### Create User DBA
```bash
CREATE USER administrador WITH PASSWORD '12345' IN ROLE dba;
```

# Crear usuarios de servicio

### Crear Role
```bash
CREATE ROLE user_service;
GRANT USAGE ON SCHEMA clinica TO user_service;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA clinica TO user_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA clinica TO user_service;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA clinica TO user_service;
```

### Create User
```bash
CREATE USER svc_microservicios_write_dev WITH PASSWORD '12345' IN ROLE user_service;
CREATE USER svc_microservicios_write_qa WITH PASSWORD '12345' IN ROLE user_service;
CREATE USER svc_microservicios_write_pro WITH PASSWORD '12345' IN ROLE user_service;
````
