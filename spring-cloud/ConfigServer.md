# Servidor de configuracion

### Spring Iniz
```
groupId      = sv.jh.springcloud.config.server
artifactId   = jh-config-server
packageName  = sv.jh.springcloud.config.server.app
type         = jar
Version Java = 21
```

### Dependencias
```
Spring boot Dev Tools
Config Server Spring Clound config
Actuator
Eureka Client
````

### pom.xml
````
		<!-- Spring Cloud Config Server -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>

		<!-- Spring Boot Actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
````

### Activar el Config server en el main Application
````
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
````

### application.properties
````
# Spring Cloud Config Server name
spring.application.name=jh-config-server
# Config Server port
server.port=8888
# Git repository location for configuration files
spring.cloud.config.server.git.uri=file:///C:/temp/spring-cloud-jh-repository

````
