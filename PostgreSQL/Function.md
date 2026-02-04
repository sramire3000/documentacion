# Funciones Utilitarios

### Crear Schema Public
```bash
CREATE SCHEMA public;
```

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

### de Numero a Letras

1
```bash
CREATE OR REPLACE FUNCTION public.fn_1_999_es(x INT)
RETURNS TEXT
LANGUAGE plpgsql
IMMUTABLE
AS $$
DECLARE
  u TEXT[] := ARRAY['cero','uno','dos','tres','cuatro','cinco','seis','siete','ocho','nueve'];
  d10_19 TEXT[] := ARRAY[
    'diez','once','doce','trece','catorce','quince',
    'dieciséis','diecisiete','dieciocho','diecinueve'
  ];
  v21_29 TEXT[] := ARRAY[
    'veintiuno','veintidós','veintitrés','veinticuatro','veinticinco',
    'veintiséis','veintisiete','veintiocho','veintinueve'
  ];
  decenas TEXT[] := ARRAY['','', 'veinte','treinta','cuarenta','cincuenta','sesenta','setenta','ochenta','noventa'];
  centenas TEXT[] := ARRAY['','ciento','doscientos','trescientos','cuatrocientos','quinientos','seiscientos','setecientos','ochocientos','novecientos'];

  c INT;
  r INT;
  dd INT;
  uu INT;
  outt TEXT := '';
BEGIN
  IF x IS NULL OR x < 0 OR x > 999 THEN
    RAISE EXCEPTION 'fn_1_999_es(x) requiere 0..999. Recibido: %', x;
  END IF;

  IF x = 0 THEN
    RETURN 'cero';
  END IF;

  IF x = 100 THEN
    RETURN 'cien';
  END IF;

  c := x / 100;
  r := x % 100;

  IF c > 0 THEN
    outt := centenas[c+1]; -- arrays empiezan en 1
  END IF;

  IF r = 0 THEN
    RETURN outt;
  END IF;

  IF outt <> '' THEN
    outt := outt || ' ';
  END IF;

  IF r < 10 THEN
    outt := outt || u[r+1];
    RETURN outt;

  ELSIF r BETWEEN 10 AND 19 THEN
    outt := outt || d10_19[(r-10)+1];
    RETURN outt;

  ELSIF r = 20 THEN
    outt := outt || 'veinte';
    RETURN outt;

  ELSIF r BETWEEN 21 AND 29 THEN
    outt := outt || v21_29[(r-21)+1];
    RETURN outt;

  ELSE
    dd := r / 10;
    uu := r % 10;

    outt := outt || decenas[dd+1];

    IF uu > 0 THEN
      outt := outt || ' y ' || u[uu+1];
    END IF;

    RETURN outt;
  END IF;
END;
$$;
```
