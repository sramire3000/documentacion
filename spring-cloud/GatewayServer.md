# Spring Cloud API Gateway

### Spring Iniz
```
groupId      = sv.jh.springcloud.gateway.server
artifactId   = jh-gateway-server
packageName  = sv.jh.springcloud.gateway.server.app
type         = jar
Version Java = 21
```

### Dependencias
```
Spring Boot Devtools
Eureka Discovery Client
Reactive Gateway Spring Cloud Routing
Spring boot Actuator
Lombok
```

### application.properties
````
# Configuración de la aplicación
spring.application.name=jh-gateway-server
# Puerto del servidor
server.port=8090

# Configuracion Eureka Client
#Instancia en Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.client.registry-fetch-interval-seconds=10
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

#Habilitar Actuator End Point
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
````

### application.yml
````
spring:
  cloud:
    gateway:
      server:
        webflux:
          routes:
            - id: jh-msvc-products
              uri: lb://jh-msvc-products
              predicates:
                - Path=/api/products/**
              filters:
                - StripPrefix=2
            - id: jh-msvc-items
              uri: lb://jh-msvc-items
              predicates:
                - Path=/api/items/**
              filters:
                - StripPrefix=2
````
