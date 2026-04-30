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
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI `
  input=dba_3 `
  password=mi_clave_super_secreta `
  algorithm=PBEWITHHMACSHA512ANDAES_256 `
  ivGeneratorClassName=org.jasypt.iv.RandomIvGenerator
```

### Linux
```bash
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
  input="valor_a_encriptar" \
  password="mi_clave_secreta" \
  algorithm=PBEWITHHMACSHA512ANDAES_256 \
  ivGeneratorClassName=org.jasypt.iv.RandomIvGenerator
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
		log.info("SystemDefault                   : {} :", zonaSistema);
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
-[Download FakeSMTP es un servidor SMTP falso gratuito](http://nilhcem.github.io/FakeSMTP/downloads/fakeSMTP-latest.zip)

<img width="658" height="457" alt="image" src="https://github.com/user-attachments/assets/b4f5a71f-cb8f-4a4d-887c-f745dd62f037" />

