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
    tabla           character varying(50) NOT NULL,
    registro        character varying(100),
    campo           character varying(50),
    valor_antes     character varying(50),
    valor_despues   character varying(50),
    fecha timestamp without time zone SET DEFAULT now(),
    usuarioSistema  character varying(50) ,
    pc              character varying(50),
    user_app        character(32) NOT NULL,
    CONSTRAINT pk_auditoria PRIMARY KEY (pk_idAuditoria)
);
````
