# TRIGGER


### Plantilla Function
````
CREATE OR REPLACE FUNCTION CLINICA.BorradoPaciente() RETURNS TRIGGER
AS $$
DECLARE

BEGIN
   RETURN NEW;
END
$$
LANGUAGE plpgsql;
````

## Tabla Ejemplo
````
CREATE TABLE IF NOT EXISTS clinica.paciente
(
    pk_idpaciente       clinica.id_paciente NOT NULL,
    nombre character    varying(20)         NOT NULL,
    apellido character  varying(20)         NOT NULL,
    sexo                character(1)        NOT NULL,
    fechanacimiento     date                NOT NULL,
    ciudad character    varying(20)         NOT NULL,
    estado character    varying(20)         NOT NULL,
    telefono            character(10) ,
    CONSTRAINT paciente_pkey PRIMARY KEY (pk_idpaciente),
    CONSTRAINT paciente_telefono_key UNIQUE (telefono)
);

CREATE TABLE CLINICA.DATOS_PACIENTES_PERSONAL(
	folio SERIAL       PRIMARY KEY,
	tipoMovimiento     VARCHAR(20),
	idPaciente         CLINICA.ID_PACIENTE,
	nombrePaciente     VARCHAR(20),
	apellidoPaciente   VARCHAR(20),
	usuario            VARCHAR(20),
	fecha              TIMESTAMP
);
````

## Ejemplo de trigger de borrado
````
CREATE OR REPLACE FUNCTION CLINICA.trg_paciente_set_deleted_at() RETURNS TRIGGER
AS $$
DECLARE
   usuario     VARCHAR(20) := (Select CURRENT_USER);
   fechaActual TIMESTAMP   := (Select LEFT(CAST(CURRENT_TIMESTAMP AS CHAR(30)),19));
BEGIN
    INSERT INTO clinica.DATOS_PACIENTES_PERSONAL(
	  tipomovimiento
	, idpaciente
	, nombrepaciente
	, apellidopaciente
	, usuario
	, fecha
	)
	VALUES (
	'BORRADO'
	, OLD.pk_idPaciente
	, OLD.nombre
	, OLD.apellido
	, usuario
	, fechaActual);
	
   RETURN NEW;
END
$$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER paciente_set_deleted_at AFTER DELETE ON CLINICA.PACIENTE
FOR EACH ROW
EXECUTE PROCEDURE CLINICA.trg_paciente_set_deleted_at();
````

## Ejemplo de Actualización
````
CREATE OR REPLACE FUNCTION CLINICA.trg_paciente_set_deleted_at() RETURNS TRIGGER
AS $$
DECLARE
   usuario     VARCHAR(20) := (Select CURRENT_USER);
   fechaActual TIMESTAMP   := (Select LEFT(CAST(CURRENT_TIMESTAMP AS CHAR(30)),19));
BEGIN
    INSERT INTO clinica.DATOS_PACIENTES_PERSONAL(
	  tipomovimiento
	, idpaciente
	, nombrepaciente
	, apellidopaciente
	, usuario
	, fecha
	)
	VALUES (
	'ACTUALIZADO'
	, OLD.pk_idPaciente
	, OLD.nombre
	, OLD.apellido
	, usuario
	, fechaActual);
	
   RETURN NEW;
END
$$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER paciente_set_update_at AFTER UPDATE ON CLINICA.PACIENTE
FOR EACH ROW
EXECUTE PROCEDURE CLINICA.trg_paciente_set_update_at();
````

## Eliminación de triggers
````
DROP TRIGGER paciente_set_deleted_at ON CLINICA.PACIENTE;
DROP TRIGGER paciente_set_update_at ON CLINICA.PACIENTE;
````
