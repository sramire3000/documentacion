# Comandos utiles desde consola

### Muestra Maximo de conexiones en el server
````
SHOW max_connections;
````

### Muestra donde se gurdan los datos
````
Show data_directory;
````

### Muestra status del server
````
pg_ctl -D "C:/Program Files/PostgreSQL/18/data" status
````

### Inicia el servicio
````
pg_ctl -D "C:/Program Files/PostgreSQL/18/data" start
````

### Inicia el servicio
````
pg_ctl -D "C:/Program Files/PostgreSQL/18/data" stop
````

## Modiciación del archivo "postgresql.conf"

### Maximas conexiones
````
ALTER SYSTEM SET max_connections='250';
````

