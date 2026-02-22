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
