# Microservicio Cliente

## Datos del microservicio Client

### Spring Iniz
```
groupId      = sv.jh.springcloud.msvc.items
packageId    = jh-msvc-items
packageName  = sv.jh.springcloud.msvc.items.app
type         = jar
Version Java = 21
```

### Dependencias
```
Spring Web
spring boot Devtools
Cloud Bootstrap spring cloud
Spring Reactive Web
OpenFeign
Lombok
Actuator
Eureka Discovery Client
Resilience4J
Cloud Bootstrap
Config Client
```

### Pom.xml
```
		<!-- Spring Boot Starter webflux -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>

		<!-- Spring WebMVC -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<!-- Spring Cloud Starter -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter</artifactId>
		</dependency>

		<!-- Spring Cloud Starter Bootstrap -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bootstrap</artifactId>
		</dependency>

		<!-- OpenFeign -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>

		<!-- DevTools -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>

		<!-- Lombok -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<!-- WebFlux Test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- WebMVC Test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- Actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<!-- Eureka Client -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

		<!-- Circuit Breaker -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>

		<!-- AOP -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
			<version>3.5.6</version>
		</dependency>

		<!-- Spring Cloud Config Client -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>

```

### Packages
````
models
controllers
services
clients
````

### applications.properties
```
# Configuración del microservicio de items
spring.application.name=jh-msvc-items
# Puerto en el que se ejecutará el microservicio
server.port=8082

config.base-url.endpoint.msvc-products=http://jh-msvc-products

# Configuracion Eureka Client
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.client.registry-fetch-interval-seconds=10
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

#Habilitar Actuator End Point
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

```

### application.yml
```
resilience4j:
  circuitbreaker:
    configs:
      defecto:
        sliding-window-size: 6
        failure-rate-threshold: 50
        wait-duration-in-open-state: 20s
        permitted-number-of-calls-in-half-open-state: 4
        slow-call-duration-threshold: 3s
        slow-call-rate-threshold: 50
    instances:
      items:
        base-config: defecto
  timelimiter:
    configs:
      defecto:
        timeout-duration: 4s
    instances:
      items:
        base-config: defecto
```

### bootstrap.properties
```
# Configuración del microservicio de items
spring.application.name=jh-msvc-items

# Configuración del servidor de configuración
spring.cloud.config.uri=http://localhost:8888
# Configuración del perfil activo
spring.profiles.active=dev
```
