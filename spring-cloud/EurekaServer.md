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

### pom.xml
```
		<!-- Eureka Server -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>
```

### Clase Principal Adicionar
````
@EnableEurekaServer
````

### application.properties
````
# Configuración base del servidor
spring.application.name=jh-eureka-server

# Puerto en el que se ejecutará el servidor Eureka
server.port=8761

# Eureka Server standalone (no se registra a sí mismo)
eureka.client.register-with-eureka=false

# No necesita obtener el registro de otros servicios, ya que es un servidor Eureka independiente
eureka.client.fetch-registry=false

# PRODUCCIÓN: Auto-preservación HABILITADA para evitar pérdida de instancias por problemas de red
eureka.server.enable-self-preservation=true
eureka.server.renewal-percent-threshold=0.85
eureka.server.expected-client-renewal-interval-seconds=30

# Hostname del servidor (configurable por variable de entorno para producción)
eureka.instance.hostname=${EUREKA_HOST:eureka-server.example.com}

# PRODUCCIÓN: HTTPS para comunicación segura
eureka.client.service-url.defaultZone=https://${eureka.instance.hostname}:${server.port}/eureka/

# Preferir hostname sobre IP en producción
eureka.instance.prefer-ip-address=false

# Intervalo de actualización del registro (en segundos)
eureka.client.registry-fetch-interval-seconds=30

# Configuración de timeouts para producción
eureka.server.response-cache-update-interval-ms=60000
eureka.instance.lease-expiration-duration-in-seconds=90
eureka.instance.lease-renewal-interval-in-seconds=30

# SEGURIDAD: Dashboard deshabilitado en producción
eureka.dashboard.enabled=true

# Información del servidor
info.app.name=${spring.application.name}
info.app.description=Service Registry Server

#Logs
logging.level.root=INFO
logging.level.com.netflix.eureka=WARN
logging.level.com.netflix.discovery=WARN
logging.level.org.springframework.web=INFO
logging.level.guru.springframework.blogs.controllers=INFO
logging.level.org.hibernate=ERROR
logging.file.name=E:/Runtime/JHTechnologiesSV/logs/jh-eureka-server.log
logging.logback.rollingpolicy.max-history=15
logging.logback.rollingpolicy.max-file-size=5MB

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
