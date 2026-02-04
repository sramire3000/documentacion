# Funciones Utilitarios

### Crear Schema Public
```bash
CREATE SCHEMA public;
```

### Obtener la hora del Sistema
```bash
CREATE OR REPLACE FUNCTION public.fn_obtener_hora_actual()
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
CREATE OR REPLACE FUNCTION public.fn_obtener_fecha_actual()
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
CREATE OR REPLACE FUNCTION public.fn_rellenar_izquierda(
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
CREATE or REPLACE FUNCTION public.fn_meses_letras(numeroMes INT) RETURNS VARCHAR(30) AS $$
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

### Pasar de Money a Letras con dos decimales

1. Paso
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

2. Paso
```bash
CREATE OR REPLACE FUNCTION public.fn_numero_a_letras_es(p_n BIGINT)
RETURNS TEXT
LANGUAGE plpgsql
IMMUTABLE
AS $$
DECLARE
  n BIGINT := p_n;
  millones INT;
  miles INT;
  resto INT;
  texto TEXT := '';
BEGIN
  IF n IS NULL THEN
    RETURN NULL;
  END IF;

  IF n < 0 THEN
    RAISE EXCEPTION 'Solo se admiten enteros no negativos. Recibido: %', n;
  END IF;

  IF n > 10000000 THEN
    RAISE EXCEPTION 'El entero % excede el máximo permitido de 10,000,000', n;
  END IF;

  IF n = 0 THEN
    RETURN 'cero';
  END IF;

  millones := (n / 1000000)::INT;
  resto    := (n % 1000000)::INT;

  IF millones > 0 THEN
    IF millones = 1 THEN
      texto := 'un millón';
    ELSE
      texto := public.fn_1_999_es(millones) || ' millones';
    END IF;
  END IF;

  miles := (resto / 1000)::INT;
  resto := (resto % 1000)::INT;

  IF miles > 0 THEN
    IF texto <> '' THEN texto := texto || ' '; END IF;

    IF miles = 1 THEN
      texto := texto || 'mil';
    ELSE
      texto := texto || public.fn_1_999_es(miles) || ' mil';
    END IF;
  END IF;

  IF resto > 0 THEN
    IF texto <> '' THEN texto := texto || ' '; END IF;
    texto := texto || public.fn_1_999_es(resto);
  END IF;

  RETURN texto;
END;
$$;
```

3. Paso
```bash
CREATE OR REPLACE FUNCTION public.fn_dolares_a_letras(p_monto NUMERIC)
RETURNS TEXT
LANGUAGE plpgsql
STABLE
AS $$
DECLARE
  v_entero   BIGINT;
  v_centavos INT;
  v_letras   TEXT;
  v_moneda   TEXT;
BEGIN
  IF p_monto IS NULL THEN
    RETURN NULL;
  END IF;

  IF p_monto < 0 THEN
    RAISE EXCEPTION 'Solo se admiten montos no negativos (recibido: %)', p_monto;
  END IF;

  IF p_monto > 10000000.00 THEN
    RAISE EXCEPTION 'El monto % excede el máximo permitido de 10,000,000.00', p_monto;
  END IF;

  -- Validación: no permitir más de 2 decimales
  -- (si trae 3+ decimales, monto*100 no será entero)
  IF (p_monto * 100) <> trunc(p_monto * 100) THEN
    RAISE EXCEPTION 'El monto % debe tener como máximo 2 decimales', p_monto;
  END IF;

  v_entero   := floor(p_monto)::BIGINT;
  v_centavos := round((p_monto - v_entero) * 100)::INT;

  v_letras := public.fn_numero_a_letras_es(v_entero);

  -- Ajuste masculino antes de "dólar(es)": uno -> un, veintiuno -> veintiún, y uno -> y un
  IF (v_entero % 10 = 1) AND (v_entero % 100 <> 11) THEN
    v_letras := regexp_replace(v_letras, '\mveintiuno\M$', 'veintiún');
    v_letras := regexp_replace(v_letras, '\my uno\M$', 'y un');
    v_letras := regexp_replace(v_letras, '\muno\M$', 'un');
  END IF;

  IF v_entero = 1 THEN
    v_moneda := 'DÓLAR';
  ELSE
    v_moneda := 'DÓLARES';
  END IF;

  RETURN upper(v_letras) || ' ' || v_moneda
         || ' CON ' || lpad(v_centavos::TEXT, 2, '0') || '/100';
END;
$$;
```

4. Paso
```bash
CREATE OR REPLACE FUNCTION public.fn_dolares_a_letras(p_monto MONEY)
RETURNS TEXT
LANGUAGE plpgsql
STABLE
AS $$
BEGIN
  RETURN public.fn_dolares_a_letras(p_monto::NUMERIC);
END;
$$;
```

5. Ejemplos
```bash
SELECT public.fn_dolares_a_letras(0.00);
-- CERO DÓLARES CON 00/100

SELECT public.fn_dolares_a_letras(1.00);
-- UN DÓLAR CON 00/100

SELECT public.fn_dolares_a_letras(21.35);
-- VEINTIÚN DÓLARES CON 35/100

SELECT public.fn_dolares_a_letras(10000000.00);
-- DIEZ MILLONES DÓLARES CON 00/100

SELECT public.fn_dolares_a_letras(12.345);
-- ERROR: El monto ... debe tener como máximo 2 decimales
```

### Valida una Fecha

```bash
CREATE OR REPLACE FUNCTION public.fn_is_valid_date_yyyy_mm_dd(p_text text)
RETURNS boolean
LANGUAGE plpgsql
IMMUTABLE
AS $$
DECLARE
  d date;
BEGIN
  -- Si algo falla por cualquier motivo, devolvemos false
  BEGIN
    -- 1) NULL o formato incorrecto
    IF p_text IS NULL OR p_text !~ '^\d{4}-\d{2}-\d{2}$' THEN
      RETURN false;
    END IF;

    -- 2) Convertir
    d := to_date(p_text, 'YYYY-MM-DD');

    -- 3) Validar que no fue "ajustada" (round-trip exacto)
    RETURN to_char(d, 'YYYY-MM-DD') = p_text;

  EXCEPTION WHEN OTHERS THEN
    RETURN false;
  END;
END;
$$;
```

ejemplo
```bash
SELECT public.fn_is_valid_date_yyyy_mm_dd('2026-02-04'); -- true
SELECT public.fn_is_valid_date_yyyy_mm_dd('2026-02-30'); -- false
SELECT public.fn_is_valid_date_yyyy_mm_dd('2026-2-04');  -- false (formato)
SELECT public.fn_is_valid_date_yyyy_mm_dd(NULL);         -- false
```

###  IVA “sobre” monto neto (monto SIN IVA)
```bash
CREATE OR REPLACE FUNCTION public.fn_calcular_iva_sv(
    p_monto      NUMERIC,
    p_porcentaje NUMERIC DEFAULT 13,
    p_decimales  INT     DEFAULT 2
)
RETURNS NUMERIC
LANGUAGE plpgsql
AS $$
DECLARE
    v_iva NUMERIC;
BEGIN
    -- Validaciones básicas
    IF p_monto IS NULL THEN
        RETURN NULL;
    END IF;

    IF p_decimales IS NULL OR p_decimales < 0 THEN
        RAISE EXCEPTION 'La cantidad de decimales debe ser >= 0. Valor recibido: %', p_decimales;
    END IF;

    IF p_porcentaje IS NULL OR p_porcentaje < 0 THEN
        RAISE EXCEPTION 'El porcentaje debe ser >= 0. Valor recibido: %', p_porcentaje;
    END IF;

    -- Cálculo IVA
    v_iva := p_monto * (p_porcentaje / 100);

    -- Redondeo con decimales solicitados
    RETURN ROUND(v_iva, p_decimales);
END;
$$;
```

Ejemplo
```bash
SELECT public.fn_calcular_iva_sv(100, 13, 2); -- 13.00
SELECT public.fn_calcular_iva_sv(250.75, 13, 2); -- 32.598
```

###  IVA “incluido” (extraer IVA desde un total CON IVA)
```bash

CREATE OR REPLACE FUNCTION public.fn_extraer_iva_incluido_sv(
    p_total      NUMERIC,
    p_porcentaje NUMERIC DEFAULT 13,
    p_decimales  INT     DEFAULT 2
)
RETURNS NUMERIC
LANGUAGE plpgsql
AS $$
DECLARE
    v_base NUMERIC;
    v_iva  NUMERIC;
BEGIN
    IF p_total IS NULL THEN
        RETURN NULL;
    END IF;

    IF p_decimales IS NULL OR p_decimales < 0 THEN
        RAISE EXCEPTION 'La cantidad de decimales debe ser >= 0. Valor recibido: %', p_decimales;
    END IF;

    IF p_porcentaje IS NULL OR p_porcentaje < 0 THEN
        RAISE EXCEPTION 'El porcentaje debe ser >= 0. Valor recibido: %', p_porcentaje;
    END IF;

    IF p_porcentaje = 0 THEN
        RETURN ROUND(0, p_decimales);
    END IF;

    v_base := p_total / (1 + (p_porcentaje / 100));
    v_iva  := p_total - v_base;

    RETURN ROUND(v_iva, p_decimales);
END;
$$;
```

ejemplo
```bash
SELECT public.fn_extraer_iva_incluido_sv(113, 13, 2); -- 13.00
SELECT public.fn_extraer_iva_incluido_sv(1000, 13, 2); -- 115.04 (aprox)
```

### Redondeo retroactivo
```bash
CREATE OR REPLACE FUNCTION public.fn_redondeo_retroactivo_money(
    p_monto     numeric,
    p_decimales integer
)
RETURNS numeric
LANGUAGE plpgsql
IMMUTABLE
AS $$
DECLARE
    v numeric;
    d integer;
    d_inicio integer := 9;
BEGIN
    -- Convertimos a numeric para poder manejar escala variable
    v := p_monto::numeric;

    -- Si p_decimales es NULL, lo tratamos como 0
    p_decimales := COALESCE(p_decimales, 0);

    -- Si p_decimales es mayor que 9, no tiene sentido “bajar desde 9”
    -- en ese caso empezamos desde p_decimales (o podrías lanzar error)
    d_inicio := GREATEST(d_inicio, p_decimales);

    -- Redondeo escalonado: 9,8,7,... hasta p_decimales
    FOR d IN REVERSE d_inicio..p_decimales LOOP
        v := round(v, d);
    END LOOP;

    RETURN v;
END;
$$;
```

Ejemplo
```bash
SELECT public.fn_redondeo_retroactivo_money(123.4567891234, 2);
SELECT public.fn_redondeo_retroactivo_money(123.4567891234, 6);
SELECT public.fn_redondeo_retroactivo_money(123.4567891234, 0);
SELECT public.fn_redondeo_retroactivo_money(123.4567891234, -1); -- decenas,
```
