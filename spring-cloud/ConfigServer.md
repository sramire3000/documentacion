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
