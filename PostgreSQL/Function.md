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
