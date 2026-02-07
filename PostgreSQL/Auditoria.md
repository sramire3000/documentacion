# Auditoria

# Tablas
````
CREATE TABLE IF NOT EXISTS [SCHEMA].auditoria
(
    auditoria_id    SERIAL NOT NULL ,
    tipo            character(1) COLLATE pg_catalog."default",
    tabla           character varying(50) COLLATE pg_catalog."default",
    registro        character varying(100) COLLATE pg_catalog."default",
    campo           character varying(50) COLLATE pg_catalog."default",
    valor_antes     character varying(50) COLLATE pg_catalog."default",
    valor_despues   character varying(50) COLLATE pg_catalog."default",
    fecha timestamp without time zone,
    usuario         character varying(50) COLLATE pg_catalog."default",
    pc              character varying(50) COLLATE pg_catalog."default",
    user_app        character(32)         COLLATE pg_catalog."default",
    CONSTRAINT pk_auditoria PRIMARY KEY (auditoria_id)
)

````
