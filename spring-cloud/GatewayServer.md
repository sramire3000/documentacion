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
      # Configuración del servidor reactivo (WebFlux) de Spring Cloud Gateway.
      server:
        webflux:
          routes:
            # -------- Ruta 1: productos --------
            - id: jh-msvc-products          # Identificador único de la ruta
              uri: lb://jh-msvc-products    # URI del destino con discovery + balanceo (Eureka/Consul). "lb://" activa el LoadBalancer
              predicates:
                - Path=/api/products/**     # Predicate: coincide con cualquier URL que empiece por /api/products/
              filters:
                - StripPrefix=2             # Filtro: elimina los 2 primeros segmentos del path (/api/products)
                                           #   Ejemplo: /api/products/list  -> destino recibe: /list

            # -------- Ruta 2: items --------
            - id: jh-msvc-items             # Identificador único de la ruta
              uri: lb://jh-msvc-items       # Servicio "items" resuelto por el registro (Eureka/Consul) con balanceo
              predicates:
                - Path=/api/items/**        # Aplica a cualquier path bajo /api/items/
              filters:
                - StripPrefix=2             # Elimina "/api/items" y reenvía el resto al servicio
                                           #   Ejemplo: /api/items/123  -> destino recibe: /123

````
