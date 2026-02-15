# Microservicio EUREKA Server

### Spring Iniz
```
groupId      = sv.jh.springcloud.eureka.server
artifactId   = jh-eureka-server
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

# Puerto en el que se ejecutará el servidor Eureka
server.port=8761

# Eureka Server standalone (no se registra a sí mismo)
eureka.client.register-with-eureka=false

# No necesita obtener el registro de otros servicios, ya que es un servidor Eureka independiente
eureka.client.fetch-registry=false

# Deshabilitar la auto-preservación para evitar problemas de registro en entornos locales
eureka.server.enable-self-preservation=false

# URL/host explícitos para evitar problemas de resolución en local
eureka.instance.hostname=localhost

# Configuración de la URL del servidor Eureka para que los clientes puedan registrarse correctamente
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

# Preferir IP en lugar de hostname para evitar problemas de resolución en entornos locales
eureka.instance.prefer-ip-address=true

# Intervalo de actualización del registro (en segundos)
eureka.client.registry-fetch-interval-seconds=30

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
eureka.instance.instance-id=${spring.cloud.client.hostname}:${spring.application.name}:${random.value}
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.client.registry-fetch-interval-seconds=10
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

#Habilitar Actuator End Point
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
````
