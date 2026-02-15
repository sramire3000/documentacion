# Microservicio EUREKA Server

### Spring Iniz
```
groupId      = sv.jh.springcloud.eureka.server
packageId    = jh-eureka-server
packageName  = sv.jh.springcloud.eureka.server.app
type         = jar
Version Java = 21
```

### Dependencias
```
Eureka Server
```

### Clase Principal Adicionar
````
@EnableEurekaServer
````

### Properties
````
# Configuración base del servidor
spring.application.name=jh-eureka-server
server.port=8761

# Eureka Server standalone (no se registra a sí mismo)
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

# URL/host explícitos para evitar problemas de resolución en local
eureka.instance.hostname=localhost
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

# Ajustes recomendados para desarrollo local
#eureka.server.enable-self-preservation=false
#eureka.server.eviction-interval-timer-in-ms=10000

# UI e información del servidor
eureka.dashboard.enabled=true
info.app.name=${spring.application.name}
info.app.port=${server.port}
````

### URL
````
http://localhost:8761/
````

## Configuración Cliente

### Add Properties
````
# Configuracion Eureka Client
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
````
