# Auditoria

## Schema
````
CREATE SCHEMA IF NOT EXISTS audit;
````

## Tablas
````
CREATE TABLE IF NOT EXISTS audit.auditoria
(
    pk_idAuditoria  SERIAL NOT NULL ,
    tipo            character(1) NOT NULL,
    tabla           varchar(50) NOT NULL,
    registro        varchar(100),
    campo           varchar(50),
    valorAntes      varchar(50),
    valorDespues    varchar(50),
    fecha           timestamp without time zone DEFAULT now(),
    usuarioSistema  varchar(50),
    pc              varchar(50),
    userApp         varchar(32) NOT NULL,
    CONSTRAINT pk_auditoria PRIMARY KEY (pk_idAuditoria)
);
````

### Indices
````
-- 1) para rangos/últimos
CREATE INDEX IF NOT EXISTS ix_auditoria_fecha
ON audit.auditoria (fecha DESC);

-- 2) auditoría por tabla + fecha
CREATE INDEX IF NOT EXISTS ix_auditoria_tabla_fecha
ON audit.auditoria (tabla, fecha DESC);

-- 3) auditoría por user_app + fecha
CREATE INDEX IF NOT EXISTS ix_auditoria_userapp_fecha
ON audit.auditoria (userApp, fecha DESC);

-- 4) si filtras mucho por tipo
CREATE INDEX IF NOT EXISTS ix_auditoria_tipo_tabla_fecha
ON audit.auditoria (tipo, tabla, fecha DESC);
````

### Store procedure que genera trigger para INSERT
````
````

### Store procedure que genera trigger para UPDATE
````
````

### Store procedure que genera trigger para DELETE
````
````

