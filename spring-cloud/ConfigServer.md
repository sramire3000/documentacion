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
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- Spring Boot DevTools (optional, for development purposes) -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>

		<!-- Spring Boot Test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- Spring Boot Actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<!-- Spring Cloud Config Server -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>

		<!-- Spring Cloud Netflix Eureka Client -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
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

# Git repository base directory for temporary clones
spring.cloud.config.server.git.basedir=E:/Runtime/JHTechnologiesSV/tmp-base-git
# Git repository location for configuration files
spring.cloud.config.server.git.uri=https://github.com/sramire3000/spring-cloud-jh-repository.git
spring.cloud.config.server.git.force-pull=true
spring.cloud.config.server.git.skip-ssl-validation=true
spring.cloud.config.server.git.username=${GIT_USERNAME}
spring.cloud.config.server.git.password=${GIT_PASSWORD}
spring.cloud.config.server.git.default-label=master
spring.cloud.config.server.git.refresh-rate=10

#Habilitar Actuator End Point
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always


#Cliente Eureka
eureka.client.serviceUrl.defaultZone=http://192.168.0.7:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.client.registry-fetch-interval-seconds=10
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true


#logs
logging.level.root=INFO
logging.level.org.springframework.web=INFO
logging.level.guru.springframework.blogs.controllers=INFO
logging.level.org.hibernate=ERROR
logging.file.name=E:/Runtime/JHTechnologiesSV/logs/jh-config-server.log
logging.logback.rollingpolicy.max-history=15
logging.logback.rollingpolicy.max-file-size=5MB

````
