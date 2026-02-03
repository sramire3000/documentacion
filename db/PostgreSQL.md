# PostgreSQL

## Roles

### Create ROLE DBA
```bash
CREATE ROLE dba WITH SUPERUSER;
GRANT USAGE ON SCHEMA CLINICA TO dba;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA CLINICA TO dba WITH GRANT OPTION;
```

### Create ROLE JEFE ESPECIALISTA
```bash
CREATE ROLE jefe_especialista;
GRANT USAGE ON SCHEMA CLINICA TO jefe_especialista;
GRANT SELECT, INSERT, UPDATE ON CLINICA.EXPEDIENTE, CLINICA.EXPEDIENTE_DIAGNOSTICO, CLINICA.ESPECIALISTA TO jefe_especialista WITH GRANT OPTION;
GRANT DELETE ON CLINICA.ESPECIALISTA TO jefe_especialista WITH GRANT OPTION;
```

### Eliminar un ROL
```bash
DROP OWNED BY user_service CASCADE;
DROP ROLE user_service;
```


## User

### Create Users
```bash
CREATE USER administrador WITH PASSWORD '12345' IN ROLE dba;
CREATE USER jefe_especialista_1 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE jefe_especialista;
```




### Example the user service (svc_application_tipo_ambiente)
```bash
svc_crm_writer_prod
svc_erp_reader_qa
svc_etl_ingestion_stage
svc_bi_api_dev
```
