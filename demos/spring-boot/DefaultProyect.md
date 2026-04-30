# Default Pom.xml y Properties

## Spring Initializr
-[URL](https://start.spring.io/)

<img width="1359" height="863" alt="image" src="https://github.com/user-attachments/assets/573911fa-5259-4eff-a685-85c6d081ed17" />


## Archivo "pom.xml" add standard
```
<dependencies>

  <!-- Begin Actuator: health, info, metricas -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator-test</artifactId>
    <scope>test</scope>
  </dependency>
  <!-- End Actuator: health, info, metricas y estado del batch -->
  
  <!-- Begin validation -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation-test</artifactId>
    <scope>test</scope>
  </dependency>
  <!-- End validation -->

  <!-- CONFIGURATION PROCESSOR -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
  </dependency>

  <!-- JASYPT Encriptacion para Usuario/Clave en propertie de DB -->
  <dependency>
      <groupId>com.github.ulisesbocchio</groupId>
      <artifactId>jasypt-spring-boot-starter</artifactId>
      <version>3.0.5</version>
  </dependency>


</dependencies>

<build>
  <finalName>app</finalName>
</build>


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
logging.file.name=logs/${spring.application.name}.log

# Tamano de archivo
logging.logback.rollingpolicy.max-file-size=10MB

# Historico
logging.logback.rollingpolicy.max-history=30

# =============================================
# Spring batch (Si se usa)
# =============================================
logging.level.org.springframework.batch=INFO

# =============================================
# Hibernate (Si se usa)
# =============================================
logging.level.org.hibernate.sql=debug
logging.level.org.hibernate=INFO
logging.level.org.hibernate.SQL=DEBUG

# =============================================
# JPA (Si se usa)
# =============================================
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### Forma de Usar logger
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger log = LoggerFactory.getLogger(GenColorProcessor.class);

log.debug("Procesando color_id={} descripcion={}", item.getColorId(), item.getColorDescripcion());
```

## Archivo "launch.json"
```
{
  "configurations": [
    {
      "vmArgs": "-Xms128m -Xmx384m -XX:MaxMetaspaceSize=96m -XX:MaxDirectMemorySize=32m -XX:+UseG1GC -XX:+UseStringDeduplication",
      "envFile": "${workspaceFolder}/.env",
      "env": {
        "JASYPT_ENCRYPTOR_PASSWORD" :"mi_clave_super_secreta",
     },
    }
    }
  ]
}
```

## Add Encripter application.properties
```
# =============================================
# JASYPT
# =============================================
# Algoritmo de encriptación
jasypt.encryptor.algorithm=PBEWITHHMACSHA512ANDAES_256
# Generador de vector de inicialización (IV) aleatorio
jasypt.encryptor.iv-generator-classname=org.jasypt.iv.RandomIvGenerator



spring.datasource.username=ENC(U0VSVkVSTkFN)
spring.datasource.password=ENC(QVBJMTAyMw==)
```

### Metodo de encriptar
-[Download direct](https://repo1.maven.org/maven2/org/jasypt/jasypt/1.9.3/jasypt-1.9.3.jar)

### test Main
```
	public static void main(String[] args) {
		SpringApplication.run(DemoMultiDbApplication.class, args);

		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("mi_clave_super_secreta");
		encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
		encryptor.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());

		String usuario = "db_user";
		String encriptado = encryptor.encrypt(usuario);

		System.out.println("ENC(" + encriptado + ")");

	}
```
