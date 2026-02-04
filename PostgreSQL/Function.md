# Funciones Utilitarios

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

### Rellenar a la Izquira de un caracter definido
```bash
CREATE OR REPLACE FUNCTION CLINICA.rellenar_izquierda(
    valor     varchar,
    repit     int,
    caracter  char(1)
)
RETURNS varchar
LANGUAGE plpgsql
AS $$
BEGIN
    -- Si valor es NULL, devolvemos NULL (comportamiento típico)
    IF valor IS NULL THEN
        RETURN NULL;
    END IF;

    -- Si repit es menor o igual al largo actual, se devuelve igual (lpad también lo hace)
    RETURN lpad(valor, repit, caracter);
END;
$$;
```

### Devuelve em Mes en Letras
```bash
CREATE or REPLACE FUNCTION CLINICA.MesesLetras(numeroMes INT) RETURNS VARCHAR(30) AS $$
BEGIN
	CASE
	  WHEN numeroMes = 1 THEN 
	     RETURN 'Enero';
	  WHEN numeroMes = 2 THEN 
	     RETURN  'Febrero';
	  WHEN numeroMes = 3 THEN 
	     RETURN  'Marzo';
	  WHEN numeroMes = 4 THEN 
	     RETURN  'Abril';
	  WHEN numeroMes = 5 THEN 
	     RETURN  'Mayo';
	  WHEN numeroMes = 6 THEN 
	     RETURN  'Junio';
	  WHEN numeroMes = 7 THEN 
	     RETURN  'Julio';
	  WHEN numeroMes = 8 THEN 
	     RETURN  'Agosto';
	  WHEN numeroMes = 9 THEN 
	     RETURN  'Setptiembre';
	  WHEN numeroMes = 10 THEN 
	     RETURN 'Octubre';
	  WHEN numeroMes = 11 THEN 
	     RETURN 'Noviembre';
	  WHEN numeroMes = 12 THEN 
	     RETURN  'Dicembre';	
	  ELSE
	     RETURN 'El número no corresponde al mes o no está declarado';
	END CASE;
END;
$$ LANGUAGE plpgsql;
```
