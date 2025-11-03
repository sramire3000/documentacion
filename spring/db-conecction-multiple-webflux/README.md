# Multiple DataBase Conection JDK 21 Spring WebFlux

- Mongo Db
- PostgresSQL
- Mysql
- Sybase JTDS
- Sybase JConect
- Mongo DB

## File pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.5.7</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo-config-db-webflux</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo-config-db-webflux</name>
	<description>Demo project for Spring Boot</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.projectreactor</groupId>
			<artifactId>reactor-test</artifactId>
			<scope>test</scope>
		</dependency>
	
	
		<!-- VALIDACION -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		
		<!-- Driver de Mongo -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
		</dependency>
		
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## Postgress

### pom.xml
```
	<!-- Driver PostgreSQL -->
	<dependency>
	    <groupId>org.postgresql</groupId>
	    <artifactId>r2dbc-postgresql</artifactId>
	    <scope>runtime</scope>
	</dependency>
	
	<!-- VALIDACION -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-validation</artifactId>
	</dependency>	
```

### Properties 
```Postgres

# ============================================================================
# CONFIGURACION R2DBC POSTGRESQL OPTIMIZADA - SPRING WEBFLUX
# ============================================================================

# URL de conexion principal
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/migration?reWriteBatchedInserts=true&preferQueryMode=extended&tcpKeepAlive=true

# Credenciales
spring.r2dbc.username=postgres
spring.r2dbc.password=password

# ============================================================================
# CONFIGURACION OPTIMIZADA DEL POOL DE CONEXIONES
# ============================================================================

# Tamano del pool
spring.r2dbc.pool.initial-size=5
spring.r2dbc.pool.max-size=20
spring.r2dbc.pool.max-acquire-time=30s
spring.r2dbc.pool.max-life-time=30m
spring.r2dbc.pool.max-idle-time=10m
spring.r2dbc.pool.max-create-connection-time=5s

# ============================================================================
# PROPIEDADES AVANZADAS POSTGRESQL OPTIMIZADAS
# ============================================================================

# Optimizaciones de rendimiento
spring.r2dbc.properties.preferQueryMode=extended
spring.r2dbc.properties.reWriteBatchedInserts=true
spring.r2dbc.properties.tcpKeepAlive=true
spring.r2dbc.properties.socketTimeout=30
spring.r2dbc.properties.connectTimeout=10
spring.r2dbc.properties.cancelSignalTimeout=10
spring.r2dbc.properties.preparedStatementCacheQueries=1024
spring.r2dbc.properties.preparedStatementCacheSizeMiB=32
spring.r2dbc.properties.assumeMinServerVersion=12
spring.r2dbc.properties.applicationName=Spring-WebFlux-App

# ============================================================================
# CONFIGURACION SPRING DATA R2DBC
# ============================================================================

spring.data.r2dbc.repositories.type=auto
spring.data.r2dbc.schema=public

# ============================================================================
# LOGGING PARA DEBUG (OPCIONAL)
# ============================================================================

#logging.level.org.springframework.data.r2dbc=DEBUG
#logging.level.io.r2dbc.postgresql=DEBUG
#logging.level.org.springframework.r2dbc=INFO
```

## Sql Server

### pom.xml
```
	<!-- DRIVER R2DBC PARA SQL SERVER -->
	<dependency>
		<groupId>io.r2dbc</groupId>
		<artifactId>r2dbc-mssql</artifactId>
		<scope>runtime</scope>
	</dependency>

	<!-- VALIDACION -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-validation</artifactId>
	</dependency>
```

### Properties
```

# ============================================================================
# CONFIGURACION R2DBC SQL SERVER OPTIMIZADA - SPRING WEBFLUX
# ============================================================================

# URL de conexion principal SQL Server
spring.r2dbc.url=r2dbc:sqlserver://localhost:1433;databaseName=migration;trustServerCertificate=true;encrypt=true

# Credenciales
spring.r2dbc.username=sa
spring.r2dbc.password=Password123

# ============================================================================
# CONFIGURACION OPTIMIZADA DEL POOL DE CONEXIONES
# ============================================================================

# Tamano del pool
spring.r2dbc.pool.initial-size=5
spring.r2dbc.pool.max-size=20
spring.r2dbc.pool.max-acquire-time=30s
spring.r2dbc.pool.max-life-time=30m
spring.r2dbc.pool.max-idle-time=10m
spring.r2dbc.pool.max-create-connection-time=5s

# ============================================================================
# PROPIEDADES AVANZADAS SQL SERVER OPTIMIZADAS
# ============================================================================

# Optimizaciones de rendimiento para SQL Server
spring.r2dbc.properties.trustServerCertificate=true
spring.r2dbc.properties.encrypt=true
spring.r2dbc.properties.sendStringParametersAsUnicode=true
spring.r2dbc.properties.loginTimeout=15
spring.r2dbc.properties.socketTimeout=30
#spring.r2dbc.properties.connectTimeout=15
spring.r2dbc.properties.packetSize=8000
spring.r2dbc.properties.applicationName=Spring-WebFlux-App

# Configuracion de sentencias preparadas
spring.r2dbc.properties.statementPoolingCacheSize=1024
spring.r2dbc.properties.prepareMethod=direct

# ============================================================================
# CONFIGURACION SPRING DATA R2DBC
# ============================================================================

spring.data.r2dbc.repositories.type=auto
# SQL Server usa esquemas por defecto (dbo), no es necesario especificar


# ============================================================================
# CONFIGURACION ADICIONAL PARA SQL SERVER
# ============================================================================

# Deshabilitar inicializacion de schema (opcional)
spring.sql.init.mode=never

# ============================================================================
# LOGGING PARA DEBUG (OPCIONAL)
# ============================================================================

#logging.level.org.springframework.data.r2dbc=DEBUG
#logging.level.io.r2dbc.mssql=DEBUG
#logging.level.org.springframework.r2dbc=INFO
#logging.level.com.microsoft.sqlserver.jdbc=DEBUG

```

### MongoDb

## pom.xml
```
(+)<!-- Driver para Mongo -->
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
    </dependency>
   
    
(-)	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-r2dbc</artifactId>
	</dependency>
    
```

### Properties
```
# ============================================================================
# CONFIGURACION MONGODB - SPRING WEBFLUX
# ============================================================================

# URI de conexiOn MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=migration
spring.data.mongodb.username=admin
spring.data.mongodb.password=password
spring.data.mongodb.authentication-database=admin

# ============================================================================
# CONFIGURACION OPTIMIZADA DEL CLIENTE REACTIVO MONGODB
# ============================================================================

# Configuracion del pool de conexiones
spring.data.mongodb.client.min-pool-size=5
spring.data.mongodb.client.max-pool-size=20
spring.data.mongodb.client.max-connection-idle-time=30000
spring.data.mongodb.client.max-connection-life-time=1800000
spring.data.mongodb.client.max-acquire-time=30000
spring.data.mongodb.client.max-size=20

# Timeouts y configuracion de socket
spring.data.mongodb.client.connect-timeout=5000
spring.data.mongodb.client.socket-timeout=30000
spring.data.mongodb.client.heartbeat-socket-timeout=20000
spring.data.mongodb.client.heartbeat-frequency=10000

# Configuracion de escritura
spring.data.mongodb.client.retry-writes=true
spring.data.mongodb.client.retry-reads=true

# ============================================================================
# CONFIGURACION SPRING DATA MONGODB REACTIVE
# ============================================================================

# Habilitar repositorios reactivos
spring.data.mongodb.repositories.type=reactive

# Configuracion de auto-indexacion
spring.data.mongodb.auto-index-creation=true

# ConfiguraciOn de tipo de escritura
spring.data.mongodb.write-concern=ACKNOWLEDGED

# ============================================================================
# CONFIGURACION ADICIONAL PARA MONGODB
# ============================================================================

# Lectura de preferencias
spring.data.mongodb.read-preference=primary

# Configuracion de transacciones (si usas replicas)
spring.data.mongodb.transaction-enabled=false

# ============================================================================
# CONFIGURACION SPRING WEBFLUX
# ============================================================================

# ConfiguraciOn del servidor Netty
server.netty.connection-timeout=30s
server.netty.idle-timeout=60s

# ============================================================================
# LOGGING PARA DEBUG (OPCIONAL)
# ============================================================================

#logging.level.org.springframework.data.mongodb=DEBUG
#logging.level.org.mongodb.driver=DEBUG
#logging.level.reactor.netty=DEBUG
#logging.level.org.springframework.web.reactive=DEBUG

# ============================================================================
# CONFIGURACION REACTOR (OPCIONAL)
# ============================================================================

# ConfiguraciOn de Reactor para mejor debugging

```
