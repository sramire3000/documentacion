# SONARQB

## Configuración
Crear el archivo ".env" y configurar adicionar las siguientes variables:


```
SONARQUBE_SERVER=service-sonarqube
SONARQUBE_PORT=9000
POSTGRESS_SERVER=host.docker.internal
POSTGRESS_PORT=5432
POSTGRESS_DB=sonar
POSTGRESS_USER=admin
POSTGRESS_PASSWORD=password
```

## Si hay Error
```
open powershell

wsl -d docker-desktop

sysctl -w vm.max_map_count=262144
```

## Login
```
http://localhost:9000/

Usuario : admin
Clave   : admin

Cambiar a Clave   : q$WChBcR;Xz?"ohwTg$2
```

## Docker

| Descrpción             | Comando |
| ----------------- | ------------------------------------------------------------------ |
| Subir Servicio de Docker  | docker-compose up -d |
| Bajar docker-compose down | docker-compose down |
| Bajar docker-compose down Destruir el Volumen | docker compose down --volumes|

## Configurar Token para Jenkins
```
+Administration
	+Security
		+User
			+Create User
				-Login    : UserJenkins
				-Password : q$WChBcR;Xz?"ohwTg$2
				+Token    : TokenJenkins
				Generate  : squ_f8e585d8a40e6aba0b875fd37cb3949ca757bce4
```

https://www.youtube.com/watch?v=dYGXrpU5Iq0		