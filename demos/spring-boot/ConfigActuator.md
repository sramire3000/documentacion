# Actuator health, info, metricas y estado del batch

## Archivo "pom.xml"
```
<!-- Actuator: health, info, metricas y estado del batch -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## Achivo "application.properties"
```
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
```
