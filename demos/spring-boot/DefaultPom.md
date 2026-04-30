# Default pom.xml

## Archivo "pom.xml" add standard
```
<!-- Actuator: health, info, metricas y estado del batch -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- DEVTOOLS -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <scope>runtime</scope>
  <optional>true</optional>
</dependency>

<!-- LOMBOK -->
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <optional>true</optional>
</dependency>

<!-- Begin javax.validation -->
<dependency>
  <groupId>javax.validation</groupId>
  <artifactId>validation-api</artifactId>
  <version>2.0.1.Final</version>
</dependency>
<!-- End javax.validation -->
```

### archivo de configuracion de "application.properties"
```

# Puerto del servidor
server.port=8080

# =============================================
# ACTUATOR
# =============================================
# Expone: health, info, metrics, batch jobs y env
management.endpoints.web.exposure.include=health,info,metrics,env,batches
management.endpoint.health.show-details=always
management.endpoint.health.show-components=always
management.info.env.enabled=true
info.app.name=demo-spring-batch
info.app.description=Migracion SQL Server 2017 -> PostgreSQL
info.app.version=1.0.0

# =============================================
# LOGGING
# =============================================
logging.level.[paquete_principal]=INFO
logging.level.org.springframework.web=INFO
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.dateformat="dd-MM-yyyy HH:mm:ss.SSSZ"
logging.level.guru.springframework.blogs.controllers=INFO

# Ubicacion
logging.file.name=logs/[nombre_archivo_log].log

# Tamano de archivo
logging.logback.rollingpolicy.max-file-size=10MB

# Historico
logging.logback.rollingpolicy.max-history=30

# Suppress Hibernate logging (Si se usa)
logging.level.org.hibernate=INFO
logging.level.org.hibernate.SQL=DEBUG

# Spring batch (Si se usa)
logging.level.org.springframework.batch=INFO

#JPA (Si se usa)
spring.jpa.show-sql=true
#Habilita las consultas nativas
logging.level.org.hibernate.sql=debug


```

### Forma de Usar logger
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger log = LoggerFactory.getLogger(GenColorProcessor.class);

log.debug("Procesando color_id={} descripcion={}", item.getColorId(), item.getColorDescripcion());
```
