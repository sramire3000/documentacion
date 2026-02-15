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
# Eureka Server configuration
spring.application.name=jh-eureka-server
# Server port
server.port=8761

# Eureka Server specific configuration

# Eureka Server should not register itself as a client
eureka.client.register-with-eureka=false
# Eureka Server does not need to fetch registry information from itself
eureka.client.fetch-registry=false
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
