# Default Pom.xml y Properties

## Spring Initializr
-[URL](https://start.spring.io/)

<img width="1359" height="863" alt="image" src="https://github.com/user-attachments/assets/573911fa-5259-4eff-a685-85c6d081ed17" />


## Archivo "pom.xml" add standard
```
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
```

### Adicionar el nombre del micro en el pom.xml
```
<build>
	<finalName>${project.name}</finalName>
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
logging.pattern.dateformat="dd-MM-yyyy HH:mm:ss.SSSZ"
logging.level.guru.springframework.blogs.controllers=INFO

# Ubicacion
logging.file.name=logs/${spring.application.name}.log

# Tamano de archivo
logging.logback.rollingpolicy.max-file-size=10MB

# Historico
logging.logback.rollingpolicy.max-history=30

# =============================================
# JASYPT
# =============================================
# Algoritmo de encriptación
jasypt.encryptor.algorithm=PBEWITHHMACSHA512ANDAES_256
# Generador de vector de inicialización (IV) aleatorio
jasypt.encryptor.iv-generator-classname=org.jasypt.iv.RandomIvGenerator

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
spring.datasource.username=ENC(U0VSVkVSTkFN)
spring.datasource.password=ENC(QVBJMTAyMw==)
```

## Método de encriptar
-[Download direct](https://repo1.maven.org/maven2/org/jasypt/jasypt/1.9.3/jasypt-1.9.3.jar)

Usar la utilidad de línea de comandos de Jasypt:

### Windows
```bash
@echo off

SET CLAVE_SECRETA=%1
SET PASSWORD=%2

if "%CLAVE_SECRETA%"=="" (
   echo debes de enviar la clave secreta
   echo Ejemplo: encriptar.bat admin
   exit /b 1
)

if "%PASSWORD%"=="" (
   echo debes de enviar el password para poder encriptarse
   echo Ejemplo: encriptar.bat admin
   exit /b 1
)

java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI ^
  input=%PASSWORD% ^
  password=%CLAVE_SECRETA% ^
  algorithm=PBEWITHHMACSHA512ANDAES_256 ^
  ivGeneratorClassName=org.jasypt.iv.RandomIvGenerator > encript.txt

```

### Linux
```bash
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
  input="valor_a_encriptar" \
  password="mi_clave_secreta" \
  algorithm=PBEWITHHMACSHA512ANDAES_256 \
  ivGeneratorClassName=org.jasypt.iv.RandomIvGenerator > encript.txt
```

## Crear Carpetas de arquitectura Limpia como ejemplo al iniciar 

### crea el archivo "crearPaquetes.sh" para linux
```
#!/bin/bash

BASE="$PWD"

# Bootstrap
mkdir -p "$BASE/bootstrap"

# Application
mkdir -p "$BASE/application/color/dto"
mkdir -p "$BASE/application/color/service"
mkdir -p "$BASE/application/color/usecase/impl"
mkdir -p "$BASE/application/departamento/dto"
mkdir -p "$BASE/application/departamento/service"
mkdir -p "$BASE/application/departamento/usecase/impl"
mkdir -p "$BASE/application/report/port"
mkdir -p "$BASE/application/report/service"
mkdir -p "$BASE/application/report/usecase/impl"
mkdir -p "$BASE/application/usecase"

# Domain
mkdir -p "$BASE/domain/color/exception"
mkdir -p "$BASE/domain/color/model"
mkdir -p "$BASE/domain/color/repository"
mkdir -p "$BASE/domain/departamento/exception"
mkdir -p "$BASE/domain/departamento/model"
mkdir -p "$BASE/domain/departamento/repository"

# Infrastructure
mkdir -p "$BASE/infrastructure/config"
mkdir -p "$BASE/infrastructure/email"
mkdir -p "$BASE/infrastructure/persistence/config"
mkdir -p "$BASE/infrastructure/persistence/mysql/adapter"
mkdir -p "$BASE/infrastructure/persistence/mysql/repository"
mkdir -p "$BASE/infrastructure/persistence/mysql/entity"
mkdir -p "$BASE/infrastructure/persistence/postgres/adapter"
mkdir -p "$BASE/infrastructure/persistence/postgres/repository"
mkdir -p "$BASE/infrastructure/persistence/postgres/entity"
mkdir -p "$BASE/infrastructure/persistence/sqlserver/adapter"
mkdir -p "$BASE/infrastructure/persistence/sqlserver/repository"
mkdir -p "$BASE/infrastructure/persistence/sqlserver/entity"
mkdir -p "$BASE/infrastructure/persistence/sybase/adapter"
mkdir -p "$BASE/infrastructure/persistence/sybase/repository"
mkdir -p "$BASE/infrastructure/persistence/sybase/entity"
mkdir -p "$BASE/infrastructure/web/controller"
mkdir -p "$BASE/infrastructure/web/exception"

# Shared
mkdir -p "$BASE/shared/exception"

echo "Paquetes creados exitosamente bajo $ROOT"
```

### crea el archivo "crearPaquetes.bat" para Windows
```
@echo off

cls

set BASE=%cd%

REM Bootstrap
mkdir "%BASE%\bootstrap"

REM Applicaton
mkdir "%BASE%\application"
mkdir "%BASE%\application\color"
mkdir "%BASE%\application\color\dto"
mkdir "%BASE%\application\color\service"
mkdir "%BASE%\application\color\usecase\impl"

mkdir "%BASE%\application\departamento"
mkdir "%BASE%\application\departamento\dto"
mkdir "%BASE%\application\departamento\service"
mkdir "%BASE%\application\departamento\usecase\impl"

mkdir "%BASE%\application\report\port"
mkdir "%BASE%\application\usecase"

REM Domain
mkdir "%BASE%\domain"
mkdir "%BASE%\domain\color"
mkdir "%BASE%\domain\color\exception"
mkdir "%BASE%\domain\color\model"
mkdir "%BASE%\domain\color\repository"

mkdir "%BASE%\domain\departamento"
mkdir "%BASE%\domain\departamento\exception"
mkdir "%BASE%\domain\departamento\model"
mkdir "%BASE%\domain\departamento\repository"

REM Infrastructure
mkdir "%BASE%\infrastructure"
mkdir "%BASE%\infrastructure\config"
mkdir "%BASE%\infrastructure\email"
mkdir "%BASE%\infrastructure\persistence\config"
mkdir "%BASE%\infrastructure\persistence\mysql"
mkdir "%BASE%\infrastructure\persistence\mysql\adapter"
mkdir "%BASE%\infrastructure\persistence\mysql\repository"
mkdir "%BASE%\infrastructure\persistence\mysql\entity"
mkdir "%BASE%\infrastructure\persistence\postgres"
mkdir "%BASE%\infrastructure\persistence\postgres\adapter"
mkdir "%BASE%\infrastructure\persistence\postgres\repository"
mkdir "%BASE%\infrastructure\persistence\postgres\entity"
mkdir "%BASE%\infrastructure\persistence\sqlserver"
mkdir "%BASE%\infrastructure\persistence\sqlserver\adapter"
mkdir "%BASE%\infrastructure\persistence\sqlserver\repository"
mkdir "%BASE%\infrastructure\persistence\sqlserver\entity"
mkdir "%BASE%\infrastructure\persistence\sybase"
mkdir "%BASE%\infrastructure\persistence\sybase\adapter"
mkdir "%BASE%\infrastructure\persistence\sybase\repository"
mkdir "%BASE%\infrastructure\persistence\sybase\entity"

REM Web
mkdir "%BASE%\infrastructure\web\controller"
mkdir "%BASE%\infrastructure\web\exception"

REM Shared
mkdir "%BASE%\shared"
mkdir "%BASE%\shared\exception"


echo Paquetes creados exitosamente bajo %cd%
pause
```

### Info al iniciar el Microservicio
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		MemoryUsage heapUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		long memoriaMaximaBytes = runtime.maxMemory();
		long memoriaMinimaBytes = heapUsage.getInit();
		long memoriaDisponibleBytes = runtime.freeMemory();

		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setWebApplicationType(WebApplicationType.SERVLET);
		ConfigurableApplicationContext context = app.run(args);
		Environment env = context.getEnvironment();

		String formatoFecha = env.getProperty("logging.pattern.dateformat", "yyyy-MM-dd HH:mm:ss.SSSZ");
		String zonaHoraria = TimeZone.getDefault().getID();
		String zonaSistema = ZoneId.systemDefault().getId();

		log.info("");
		log.info("<============================= START {} =============================>",
				env.getProperty("spring.application.name").toUpperCase());
		log.info("Memoria JVM al iniciar maxima   : {} ", formatMemory(memoriaMaximaBytes));
		log.info("Memoria JVM al iniciar minima   : {} ", formatMemory(memoriaMinimaBytes));
		log.info("Memoria JVM disponible          : {} ", formatMemory(memoriaDisponibleBytes));
		log.info("Formato de fecha configurado    : {} ", formatoFecha);
		log.info("Zona horaria activa             : {} ", zonaHoraria);
		log.info("SystemDefault                   : {} ", zonaSistema);
		log.info("<===============================================================================>");
		log.info("");
	}

	private static long bytesToMb(long bytes) {
		return bytes / (1024 * 1024);
	}

	private static String formatMemory(long bytes) {
		if (bytes < 0) {
			return "N/A";
		}
		return bytesToMb(bytes) + " MB (" + bytes + " bytes)";
	}

}


```

### Para generar claves por el Main
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

### Validar por un metodo la desencriptación
```
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class Load {

  private static final Logger log = LoggerFactory.getLogger(Load.class);

  @Value("${usuario_db}")
  private String usuarioDb;

  @PostConstruct
  public void imprimirUsuarioDb() {
    log.info("usuario_db => {}", usuarioDb);
  }
}
```

## JAR para prueba de email
-[Download FakeSMTP es un servidor SMTP gratuito](http://nilhcem.github.io/FakeSMTP/downloads/fakeSMTP-latest.zip)

<img width="658" height="457" alt="image" src="https://github.com/user-attachments/assets/b4f5a71f-cb8f-4a4d-887c-f745dd62f037" />

