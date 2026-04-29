# Spring Batch

## Configuracion

### Adicionar al archivo "pom.xml"
```
<!-- Spring Batch -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-batch</artifactId>
</dependency>

<!-- Spring JDBC (JdbcTemplate + TransactionManager) -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<!-- SQL Server Driver (mssql-jdbc) -->
<dependency>
  <groupId>com.microsoft.sqlserver</groupId>
  <artifactId>mssql-jdbc</artifactId>
  <scope>runtime</scope>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <optional>true</optional>
</dependency>

<!-- Scheduling support (included in spring-boot-starter) -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- Test -->
<dependency>
  <groupId>org.springframework.batch</groupId>
  <artifactId>spring-batch-test</artifactId>
  <scope>test</scope>
</dependency>
```

### Archivo "application.properties"
```
# =============================================
# SERVIDOR WEB
# =============================================
server.port=8080

# =============================================
# DATASOURCE ORIGEN - SQL Server 2017
# =============================================
spring.datasource.sqlserver.jdbc-url=jdbc:sqlserver://localhost:1433;databaseName=Arreconsa;encrypt=false;trustServerCertificate=true
spring.datasource.sqlserver.username=sa
spring.datasource.sqlserver.password=TuPasswordFuerte123!
spring.datasource.sqlserver.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# =============================================
# DATASOURCE DESTINO - PostgreSQL
# =============================================
spring.datasource.postgres.jdbc-url=jdbc:postgresql://localhost:5432/Arreconsa
spring.datasource.postgres.username=postgres
spring.datasource.postgres.password=password
spring.datasource.postgres.driver-class-name=org.postgresql.Driver

# =============================================
# SPRING BATCH - Metadata en PostgreSQL
# =============================================
spring.batch.jdbc.initialize-schema=always
# Deshabilita la ejecucion automatica del job al arrancar la app.
# El Scheduler controla cuando se ejecuta.
spring.batch.job.enabled=false

# =============================================
# SPRING BATCH - Parametros de migracion
# =============================================
# Cantidad de registros por chunk
migration.chunk-size=5
# Tablas a migrar (separadas por coma)
migration.tables=gen_departamentos,gen_colores

# =============================================
# SCHEDULER - Cron para ejecucion diaria
# Formato: segundo minuto hora dia mes dia-semana
# Por defecto: todos los dias a las 02:00 AM
# =============================================
migration.scheduler.cron=0 0 2 * * ?
```

## Paquetes
```
-batch.processor
-batch.reader
-batch.writer
-config
-model
-scheduler
```



