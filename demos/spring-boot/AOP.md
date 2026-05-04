# Programacion orientada a aspectos

## Archivo "pom.xml"
```
<!-- AOP -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aop</artifactId>
</dependency>
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
</dependency>
```

## Archivo "application.properties"
```
# =============================================
# LOGGING
# =============================================
logging.level.sv.com.acme.app.infrastructure.aop=INFO
logging.level.sv.com.acme.app=INFO
logging.level.sv.com.acme.app.application=INFO
logging.level.sv.com.acme.app.infrastructure.web=WARN
logging.level.org.springframework.web=INFO
logging.pattern.dateformat="dd-MM-yyyy HH:mm:ss.SSSZ"
logging.level.guru.springframework.blogs.controllers=INFO
```
