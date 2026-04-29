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
spring.datasource.sqlserver.username=
spring.datasource.sqlserver.password=
spring.datasource.sqlserver.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# =============================================
# DATASOURCE DESTINO - PostgreSQL
# =============================================
spring.datasource.postgres.jdbc-url=jdbc:postgresql://localhost:5432/Arreconsa
spring.datasource.postgres.username=
spring.datasource.postgres.password=
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

### Paquetes
```
-batch.processor
-batch.reader
-batch.writer
-config
-model
-scheduler
```

## Programas JAVA

### Archivo "DataSourceConfig.java" en el paquete "config"
```
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuracion de fuentes de datos.
 * - sqlServerDataSource: origen (SQL Server 2017), lectura sin bloqueos
 * (NOLOCK).
 * - postgresDataSource: destino (PostgreSQL), primario para Spring Batch
 * metadata.
 */
@Configuration
public class DataSourceConfig {

  /**
   * DataSource para SQL Server (origen).
   * Se configura con las propiedades spring.datasource.sqlserver.*
   */
  @Bean(name = "sqlServerDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.sqlserver")
  public DataSource sqlServerDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * DataSource para PostgreSQL (destino).
   * Se configura como primario para que Spring Batch use este para sus metadatos.
   */
  @Primary
  @Bean(name = "postgresDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.postgres")
  public DataSource postgresDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * JdbcTemplate para operaciones en SQL Server.
   */
  @Bean(name = "sqlServerJdbcTemplate")
  public JdbcTemplate sqlServerJdbcTemplate(@Qualifier("sqlServerDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  /**
   * JdbcTemplate para operaciones en PostgreSQL.
   */
  @Bean(name = "postgresJdbcTemplate")
  public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
```

### Archivo "MigrationJobConfig.java" en el paquete "config"
```
import javax.sql.DataSource;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo_spring_batch.app.batch.processor.GenColorProcessor;
import com.example.demo_spring_batch.app.batch.processor.GenDepartamentoProcessor;
import com.example.demo_spring_batch.app.batch.reader.GenColorReader;
import com.example.demo_spring_batch.app.batch.reader.GenDepartamentoReader;
import com.example.demo_spring_batch.app.batch.writer.GenColorWriter;
import com.example.demo_spring_batch.app.batch.writer.GenDepartamentoWriter;
import com.example.demo_spring_batch.app.model.GenColor;
import com.example.demo_spring_batch.app.model.GenDepartamento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuracion principal del job de migracion Spring Batch.
 * El job realiza:
 * 1. Limpieza de tablas destino en PostgreSQL.
 * 2. Migracion de gen_colores (SQL Server -> PostgreSQL).
 * 3. Migracion de gen_departamentos (SQL Server -> PostgreSQL).
 */
@Configuration
public class MigrationJobConfig {

  private static final Logger log = LoggerFactory.getLogger(MigrationJobConfig.class);

  @Value("${migration.chunk-size:5}")
  private int chunkSize;

  // ---------------------------------------------------------------
  // STEP: Truncate tablas destino
  // ---------------------------------------------------------------

  @Bean
  public Step truncateStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      @Qualifier("postgresJdbcTemplate") JdbcTemplate postgresJdbcTemplate) {
    return new StepBuilder("truncateStep", jobRepository)
        .tasklet((contribution, chunkContext) -> {
          log.info("Iniciando limpieza de tablas destino en PostgreSQL...");
          truncateIfExists(postgresJdbcTemplate, "gen_colores");
          truncateIfExists(postgresJdbcTemplate, "gen_departamentos");
          return RepeatStatus.FINISHED;
        }, transactionManager)
        .build();
  }

  @Bean
  public Step migrateGenColoresStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      GenColorReader genColorReader,
      GenColorProcessor genColorProcessor,
      GenColorWriter genColorWriter,
      @Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) {
    return new StepBuilder("migrateGenColoresStep", jobRepository)
        .<GenColor, GenColor>chunk(chunkSize)
        .transactionManager(transactionManager)
        .reader(genColorReader.reader(sqlServerDataSource))
        .processor(genColorProcessor)
        .writer(genColorWriter)
        .listener(stepExecutionListener("gen_colores"))
        .build();
  }

  // ---------------------------------------------------------------
  // STEP: Migrar gen_colores
  // ---------------------------------------------------------------

  @Bean
  public Step migrateGenDepartamentosStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      GenDepartamentoReader genDepartamentoReader,
      GenDepartamentoProcessor genDepartamentoProcessor,
      GenDepartamentoWriter genDepartamentoWriter,
      @Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) {
    return new StepBuilder("migrateGenDepartamentosStep", jobRepository)
        .<GenDepartamento, GenDepartamento>chunk(chunkSize)
        .transactionManager(transactionManager)
        .reader(genDepartamentoReader.reader(sqlServerDataSource))
        .processor(genDepartamentoProcessor)
        .writer(genDepartamentoWriter)
        .listener(stepExecutionListener("gen_departamentos"))
        .build();
  }

  // ---------------------------------------------------------------
  // STEP: Migrar gen_departamentos
  // ---------------------------------------------------------------

  @Bean
  public Job migrationJob(JobRepository jobRepository,
      Step truncateStep,
      Step migrateGenColoresStep,
      Step migrateGenDepartamentosStep) {
    return new JobBuilder("migrationJob", jobRepository)
        .listener(jobExecutionListener())
        .start(truncateStep)
        .next(migrateGenColoresStep)
        .next(migrateGenDepartamentosStep)
        .build();
  }

  // ---------------------------------------------------------------
  // JOB: migrationJob
  // ---------------------------------------------------------------

  /**
   * Trunca la tabla solo si tiene datos.
   * - Si la tabla tiene registros (re-ejecucion): trunca antes de migrar.
   * - Si la tabla esta vacia (primera ejecucion): omite el truncate.
   */
  private void truncateIfExists(JdbcTemplate jdbcTemplate, String tableName) {
    Integer count = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM " + tableName, Integer.class);
    if (count != null && count > 0) {
      jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
      log.info("Tabla {} truncada ({} registros eliminados).", tableName, count);
    } else {
      log.info("Tabla {} vacia, no requiere truncate.", tableName);
    }
  }

  // ---------------------------------------------------------------
  // Listeners de log
  // ---------------------------------------------------------------

  private JobExecutionListener jobExecutionListener() {
    return new JobExecutionListener() {
      @Override
      public void beforeJob(JobExecution jobExecution) {
        log.info("");
        log.info("=== INICIO JOB migrationJob - RunId: {} ===", jobExecution.getJobInstanceId());
      }

      @Override
      public void afterJob(JobExecution jobExecution) {
        log.info("=== FIN JOB migrationJob - Status: {} - Duracion: {}ms ===",
            jobExecution.getStatus(),
            jobExecution.getEndTime() != null && jobExecution.getStartTime() != null
                ? java.time.Duration.between(jobExecution.getStartTime(), jobExecution.getEndTime()).toMillis()
                : "N/A");
        log.info("");
      }
    };
  }

  private StepExecutionListener stepExecutionListener(String tableName) {
    return new StepExecutionListener() {
      @Override
      public void beforeStep(StepExecution stepExecution) {
        log.info("--- Inicio migracion tabla: {} ---", tableName);
      }

      @Override
      public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("--- Fin migracion tabla: {} | Leidos: {} | Escritos: {} | Status: {} ---",
            tableName,
            stepExecution.getReadCount(),
            stepExecution.getWriteCount(),
            stepExecution.getStatus());
        log.info("");
        return stepExecution.getExitStatus();
      }
    };
  }
}
```

### Archivo "MigrationStartupRunner.java" en el paquete "config"
```
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lanza el job de migracion al iniciar la aplicacion.
 * Se ejecuta con @Order(2), garantizando que PostgresDdlInitializer (@Order(1))
 * ya haya creado las tablas en PostgreSQL antes de que el job intente usarlas.
 *
 * El scheduler (@Scheduled) ademas lo vuelve a ejecutar segun el cron
 * configurado.
 */
@Component
@Order(2)
public class MigrationStartupRunner implements ApplicationRunner {

  private static final Logger log = LoggerFactory.getLogger(MigrationStartupRunner.class);

  private final JobOperator jobOperator;
  private final Job migrationJob;

  public MigrationStartupRunner(JobOperator jobOperator,
      @Qualifier("migrationJob") Job migrationJob) {
    this.jobOperator = jobOperator;
    this.migrationJob = migrationJob;
  }

  @Override
  public void run(ApplicationArguments args) {
    log.info("MigrationStartupRunner: lanzando job de migracion al iniciar la aplicacion...");
    try {
      Set<JobParameter<?>> parameters = Set.of(
          new JobParameter<>("executionTime", LocalDateTime.now().toString(), String.class));
      JobParameters jobParameters = new JobParameters(parameters);

      JobExecution execution = jobOperator.start(migrationJob, jobParameters);
      log.info("MigrationStartupRunner: job finalizado con status: {}", execution.getStatus());
    } catch (Exception e) {
      log.error("MigrationStartupRunner: error al ejecutar el job de migracion: {}", e.getMessage(), e);
    }
  }
}
```

### Archivo "PostgresDdlInitializer.java" en el paquete "config"
```
package com.example.demo_spring_batch.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Crea las tablas en PostgreSQL si no existen antes de iniciar la migracion.
 * Se ejecuta con @Order(1) para garantizar que las tablas existen
 * antes de que el job de migracion intente usarlas.
 */
@Component
@Order(1)
public class PostgresDdlInitializer implements ApplicationRunner {

  private static final Logger log = LoggerFactory.getLogger(PostgresDdlInitializer.class);

  private final JdbcTemplate postgresJdbcTemplate;

  public PostgresDdlInitializer(@Qualifier("postgresJdbcTemplate") JdbcTemplate postgresJdbcTemplate) {
    this.postgresJdbcTemplate = postgresJdbcTemplate;
  }

  @Override
  public void run(ApplicationArguments args) {
    log.info("Verificando/creando tablas en PostgreSQL...");
    createGenColores();
    createGenDepartamentos();
    log.info("Verificacion de tablas completada.");
  }

  private void createGenColores() {
    String ddl = """
        CREATE TABLE IF NOT EXISTS gen_colores (
            color_id             INTEGER NOT NULL,
            color_descripcion    VARCHAR(75) NOT NULL,
            color_estado         CHAR(3) NOT NULL DEFAULT 'ACT',
            usuario_codigo       VARCHAR(50) NOT NULL,
            user_create          VARCHAR(50),
            user_modify          VARCHAR(50),
            fch_create           TIMESTAMP,
            fch_modify           TIMESTAMP,
            CONSTRAINT pk_gen_colores PRIMARY KEY (color_id)
        )
        """;
    postgresJdbcTemplate.execute(ddl);
    log.info("Tabla gen_colores verificada/creada en PostgreSQL.");
  }

  private void createGenDepartamentos() {
    String ddl = """
        CREATE TABLE IF NOT EXISTS gen_departamentos (
            departamento_id           INTEGER NOT NULL,
            departamento_descripcion  VARCHAR(50) NOT NULL,
            departamento_estado       CHAR(3) NOT NULL DEFAULT 'ACT',
            pais_id                   INTEGER NOT NULL,
            usuario_codigo            VARCHAR(50) NOT NULL,
            user_create               VARCHAR(50),
            user_modify               VARCHAR(50),
            fch_create                TIMESTAMP,
            fch_modify                TIMESTAMP,
            CONSTRAINT pk_gen_departamentos PRIMARY KEY (departamento_id)
        )
        """;
    postgresJdbcTemplate.execute(ddl);
    log.info("Tabla gen_departamentos verificada/creada en PostgreSQL.");
  }
}
```



