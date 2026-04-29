# Configuracion logger

## Archivo "application.properties"
```
# =============================================
# LOGGING
# =============================================
# =============================================
# LOGGING
# =============================================
logging.level.com.example.demo_spring_batch=INFO
logging.level.org.springframework.web=INFO
logging.level.guru.springframework.blogs.controllers=INFO
logging.level.org.springframework.batch=INFO
logging.pattern.dateformat="dd-MM-yyyy HH:mm:ss.SSSZ"
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/graphql.log
logging.logback.rollingpolicy.max-file-size=10MB
logging.logback.rollingpolicy.max-history=30

# Suppress Hibernate logging
logging.level.org.hibernate=INFO
logging.level.org.hibernate.SQL=DEBUG

```
