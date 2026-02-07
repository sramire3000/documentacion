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

## Ejemplo de trigger de borrado
````
CREATE OR REPLACE FUNCTION CLINICA.BorradoPaciente() RETURNS TRIGGER
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
````
