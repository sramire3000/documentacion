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

### Stop el servicio
````
pg_ctl -D "C:/Program Files/PostgreSQL/18/data" stop
````

### Restart el servicio
````
pg_ctl -D "C:/Program Files/PostgreSQL/18/data" restart
````


## Modiciación del archivo "postgresql.conf"

### Maximas conexiones
````
ALTER SYSTEM SET max_connections='250';
````

### Cambiar el lenguaje de los mensajes
````
ALTER SYSTEM SET lc_messages = 'en_US.UTF-8';
ALTER SYSTEM SET lc_messages = 'Spanish_Spain.1252';
ALTER SYSTEM SET lc_messages = 'Spanish_Mexico.1252';
````

### Reset Max Conexion
````
ALTER SYSTEM RESET max_connections;
ALTER SYSTEM RESET lc_messages;
````

### Vistas de BD
````
select * from pg_views;
select * from pg_roles;
select * from pg_user;
Select * from pg_tables;
Select * from pg_timezone_names;
````

### Create un Table Space
````
CREATE TABLESPACE ts_ejemplo LOCATION 'E:\ts_jh_technologies';
DROP TABLESPACE ts_ejemplo;
````

