# PostgreSQL

## Roles


### Create ROLE JEFE ESPECIALISTA
```bash
CREATE ROLE jefe_especialista;
GRANT USAGE ON SCHEMA CLINICA TO jefe_especialista;
GRANT SELECT, INSERT, UPDATE ON CLINICA.EXPEDIENTE, CLINICA.EXPEDIENTE_DIAGNOSTICO, CLINICA.ESPECIALISTA TO jefe_especialista WITH GRANT OPTION;
GRANT DELETE ON CLINICA.ESPECIALISTA TO jefe_especialista WITH GRANT OPTION;
```

### Create ROLE ESPECIALISTA
```bash
CREATE ROLE especialista;
GRANT USAGE ON SCHEMA CLINICA TO especialista;
GRANT SELECT, INSERT, UPDATE ON CLINICA.EXPEDIENTE, CLINICA.EXPEDIENTE_DIAGNOSTICO TO especialista WITH GRANT OPTION;
GRANT SELECT ON CLINICA.PACIENTE TO especialista WITH GRANT OPTION;
```

### Create ROLE RECEPCIONISTA
```bash
CREATE ROLE recepcionista;
GRANT USAGE ON SCHEMA CLINICA TO recepcionista;
GRANT SELECT, INSERT, UPDATE ON CLINICA.PACIENTE, CLINICA.CITA, CLINICA.AGENDAR_CITA TO recepcionista WITH GRANT OPTION;
GRANT DELETE ON CLINICA.PACIENTE TO recepcionista WITH GRANT OPTION;
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
CREATE USER especialista_1 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE especialista;
CREATE USER especialista_2 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE especialista;
CREATE USER especialista_3 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE especialista;
CREATE USER recepcionista_1 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE recepcionista;
CREATE USER recepcionista_2 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE recepcionista;
CREATE USER recepcionista_3 WITH PASSWORD '12345' CONNECTION LIMIT 1 IN ROLE recepcionista;
```

### REVOCAR PERMISOS, BORRAR USUARIOS Y ROLES
```bash
DROP USER recepcionista_3;
REVOKE SELECT, INSERT, UPDATE ON CLINICA.PACIENTE, CLINICA.CITA, CLINICA.AGENDAR_CITA FROM recepcionista;
REVOKE DELETE ON CLINICA.PACIENTE FROM recepcionista;
REVOKE USAGE ON SCHEMA CLINICA FROM recepcionista;
DROP ROLE recepcionista;
```

### Example the user service (svc_application_tipo_ambiente)
```bash
svc_crm_writer_prod
svc_erp_reader_qa
svc_etl_ingestion_stage
svc_bi_api_dev
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

# FUNCTION

### Plantilla function
```bash
CREATE or REPLACE FUNCTION nameFunction() RETURNS datatype AS $$
DECLARE ....
BEGIN

 Comandos SQL....

END;
$$ LANGUAGE plpgsql;
```

### Ejecutar funcion
```bash
Select nameFunction()
```

### Ejemplo de funcion sin parametros
```bash
CREATE or REPLACE FUNCTION CLINICA.HolaMundo() RETURNS VARCHAR(20) AS $$
DECLARE 
   mensaje VARCHAR(20) := 'HOLA MUNDO';
BEGIN   
	RETURN mensaje;
END;
$$ LANGUAGE plpgsql;
```

### Ejemplo con parametro
```
CREATE or REPLACE FUNCTION CLINICA.HolaMundo(mensaje VARCHAR(20)) RETURNS VARCHAR(20) AS $$
BEGIN   
	RETURN mensaje;
END;
$$ LANGUAGE plpgsql;
```

### Ejemplo de funcion con IF
```bash
CREATE or REPLACE FUNCTION CLINICA.NumeroMayorMenor(numero1 INT, numero2 INT) RETURNS VARCHAR(30) AS $$
BEGIN
 IF numero1 > numero2 THEN
    return 'El número: ' || numero1 || ' es mayor que el número: ' || numero2;
 ELSEIF numero1 < numero2 THEN 
    return 'El número: ' || numero1 || ' es menor que el número: ' || numero2;
 ELSE
    return 'El número: ' || numero1 || ' es igual que el número: ' || numero2;
 END IF;
END;
$$ LANGUAGE plpgsql;
```

### Ejemplo de funcion con CASE
```bash
CREATE or REPLACE FUNCTION CLINICA.MesesAño(numeroMes INT) RETURNS VARCHAR(30) AS $$
DECLARE mensaje VARCHAR(30) := 'El número del mes es: ';
BEGIN
	CASE
	  WHEN numeroMes = 1 THEN 
	     RETURN mensaje || 'Enero';
	  WHEN numeroMes = 2 THEN 
	     RETURN mensaje || 'Febrero';
	  WHEN numeroMes = 3 THEN 
	     RETURN mensaje || 'Marzo';
	  WHEN numeroMes = 4 THEN 
	     RETURN mensaje || 'Abril';
	  WHEN numeroMes = 5 THEN 
	     RETURN mensaje || 'Mayo';
	  WHEN numeroMes = 6 THEN 
	     RETURN mensaje || 'Junio';
	  WHEN numeroMes = 7 THEN 
	     RETURN mensaje || 'Julio';
	  WHEN numeroMes = 8 THEN 
	     RETURN mensaje || 'Agosto';
	  WHEN numeroMes = 9 THEN 
	     RETURN mensaje || 'Setptiembre';
	  WHEN numeroMes = 10 THEN 
	     RETURN mensaje || 'Octube';
	  WHEN numeroMes = 11 THEN 
	     RETURN mensaje || 'Noviembre';
	  WHEN numeroMes = 12 THEN 
	     RETURN mensaje || 'Dicembre';	
	  ELSE
	     RETURN 'El número no corresponde al mes o no está declarado';
	END CASE;
END;
$$ LANGUAGE plpgsql;
```

### Ejemplo de funcion con LOOP
```bash
CREATE or REPLACE FUNCTION CLINICA.Loop(numero INT) RETURNS INT AS $$
DECLARE i INT := 0;
BEGIN
	FOR i IN [OPTIONAL REVERSE] 1..numero [OPTIONAL BYTE 2] LOOP
	   RAISE NOTICE 'CONTADOR %', i;
	END LOOP;
	
END;
$$ LANGUAGE plpgsql;
```

### Ejemplo de funcion con WHILE
```bash
CREATE or REPLACE FUNCTION CLINICA.While(numero INT) RETURNS INT AS $$
DECLARE i INT := 0;
BEGIN
	WHILE i < numero LOOP
		RAISE NOTICE 'CONTADOR %', i;
		i = i + 1;
    END LOOP;
END;
$$ LANGUAGE plpgsql;
```


# PROCEDURE

### Plantilla Procedure
```bash
CREATE PROCEDURE nameProcedure()
LANGUAGE plpgsql
AS $$

   Comandos SQL....

END; $$
```

### Ejecutar Procedure
```bash
call nameProcedure();
```

# TRIGGER

### Plantilla trigger

```bash
CREATE FUNCTION nameFunction() RETURN TRIGGER
AS $$ BEGIN

  Comandos Sql...

END;
$$ LANGUAGE plpgsql;
```

```bash
CREATE TRIGGER name_Trigger{BEFORE | AFTER | INSTEAD OF} {event [OR...] }
ON name_Table
FOR EACH ROW
EXECUTE PROCEDURE nameFunction();
```
Where event can be one of: INSERT, UPDATE, DELETE, TRUNCATE


# LIBRERIA DE FUNCIONES

### Obtener la hora del Sistema
```bash
CREATE OR REPLACE FUNCTION CLINICA.obtener_hora_actual()
RETURNS text
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN pg_catalog.to_char(CURRENT_TIMESTAMP, 'HH24:MI:SS');
END;
$$;
```

### Obtener la fecha en formato yyyy-mm-dd
```bash
CREATE OR REPLACE FUNCTION CLINICA.obtener_fecha_actual()
RETURNS text
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN CURRENT_DATE::text;   -- ya viene como YYYY-MM-DD
END;
$$;
```


