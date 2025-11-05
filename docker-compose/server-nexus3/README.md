# MNEXUS

## Configuración
Crear el archivo ".env" y configurar adicionar las siguientes variables:

```
NEXUS_SERVER=service-nexus3-server
NEXUS_PORT1=8110
NEXUS_PORT2=8120
NEXUS_PORT3=8130
URL_WEB=http://localhost:8110/
```

user=admin
pass=archivo en nexus<admin.password>


## Docker

| Descrpción             | Comando |
| ----------------- | ------------------------------------------------------------------ |
| Subir Servicio de Docker  | docker-compose up -d |
| Bajar docker-compose down | docker-compose down |
| Bajar docker-compose down Destruir el Volumen | docker compose down --volumes|

## Crear un Nuevo Repositorio
```
+New Repository
	+Server Administration And configuration
		+Manage repositories
			+Create Repository
				+Maven2 hosted
					-Name = arreconsa-snapshot
					-version Policy=Mixed
					-Strict Conten type Validation = Uncheked
```                    

https://www.youtube.com/watch?v=lIld357NUEs		