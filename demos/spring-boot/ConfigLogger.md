# Configuracion logger

## Archivo "application.properties"
```
# =============================================
# LOGGING
# =============================================
logging.level.com.example.demo_spring_batch=INFO
logging.level.org.springframework.web=INFO
logging.level.guru.springframework.blogs.controllers=INFO
logging.level.org.springframework.batch=INFO
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/migration.log
logging.logback.rollingpolicy.max-file-size=10MB
logging.logback.rollingpolicy.max-history=30
logging.pattern.dateformat="dd-MM-yyyy HH:mm:ss.SSSZ"

logging.level.org.hibernate=ERROR
```
