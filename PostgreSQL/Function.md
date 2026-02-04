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

