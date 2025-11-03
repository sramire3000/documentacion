# Multiple DataBase Conection JDK 21

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
	<artifactId>demo-multiple-db</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo-multiple-db</name>
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
		<jconn4-systemPath>/home/hector-ramirez/demo-spring-boot/demo-multiple-db/src/main/resources/lib/jconn4.jar</jconn4-systemPath>
		<java.version>21</java.version>
	</properties>
	<dependencies>
	
		<!-- Spring Web -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	
		<!-- Spring Actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		<!-- Spring JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<!-- Spring Validation -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		
		<!-- Spring DevTools -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		

		<!-- Spring Processor -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
		
		<!-- Lombok -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		
		<!-- Driver SQLServer -->
		<dependency>
			<groupId>com.microsoft.sqlserver</groupId>
			<artifactId>mssql-jdbc</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<!-- Driver Mysql -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<!-- Driver Postgres -->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
				
		
		<!-- Driver JTDS -->
		<dependency>
			<groupId>net.sourceforge.jtds</groupId>
			<artifactId>jtds</artifactId>
		</dependency>
		
		<!-- Driver Mongodb -->	
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>		

		<!-- Driver SybaseJ -->		
		<dependency>
		    <groupId>com.sybase</groupId>
		    <artifactId>jconn4</artifactId>
		    <version>7.0</version>
		    <scope>system</scope>
		    <systemPath>${jconn4-systemPath}</systemPath>
		</dependency>

		<!-- Spring Test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<annotationProcessorPaths>
						<path>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-configuration-processor</artifactId>
						</path>
						<path>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</path>
					</annotationProcessorPaths>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>

```

## SQL Server 2019

### File applcation.properties
```
# Configuracion SQL Server
spring.datasource.sqlserver.url=jdbc:sqlserver://[ip_server]:[port_server];databaseName=[database_name];encrypt=false
spring.datasource.sqlserver.username=[user_db]
spring.datasource.sqlserver.password=[password_db]
spring.datasource.sqlserver.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.sqlserver.dialect.dialect=org.hibernate.dialect.SQLServer2012Dialect
spring.datasource.sqlserver.ddl=none
```
### File DbSqlServer.java
```
package com.example.demo.app.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuración principal de base de datos para Microsoft SQL Server.
 * 
 * <p>Esta clase proporciona la configuración completa para la conexión a bases de datos
 * Microsoft SQL Server, incluyendo configuración del pool de conexiones HikariCP,
 * EntityManagerFactory y TransactionManager específicamente optimizados para SQL Server.</p>
 * 
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *   <li>Configuración como DataSource primario ({@code @Primary})</li>
 *   <li>Pool de conexiones optimizado para alta concurrencia</li>
 *   <li>Configuraciones específicas del driver JDBC de SQL Server</li>
 *   <li>Optimizaciones de rendimiento para operaciones batch</li>
 *   <li>Soporte para características avanzadas de SQL Server</li>
 * </ul>
 * 
 * <p><strong>Propiedades requeridas en application.properties:</strong></p>
 * <pre>
 * spring.datasource.sqlserver.username=usuario
 * spring.datasource.sqlserver.password=contraseña
 * spring.datasource.sqlserver.url=jdbc:sqlserver://host:puerto;databaseName=nombre_bd
 * spring.datasource.sqlserver.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
 * spring.datasource.sqlserver.ddl=none
 * spring.datasource.sqlserver.dialect.dialect=org.hibernate.dialect.SQLServerDialect
 * </pre>
 * 
 * <p><strong>Propiedades opcionales:</strong></p>
 * <pre>
 * spring.datasource.sqlserver.schema=esquema
 * spring.datasource.sqlserver.catalog=catalogo
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages            = "com.example.demo.app.repository.sqlserver", 
    entityManagerFactoryRef = "sqlServerEntityManager", 
    transactionManagerRef   = "sqlServerTransactionManager"
)
public class DbSqlServer {

    /** 
     * Tamaño máximo del pool de conexiones - SQL Server maneja mejor conexiones concurrentes 
     */
    private static final int MAX_POOL_SIZE = 30;
    
    /** 
     * Mínimo de conexiones inactivas en el pool 
     */
    private static final int MIN_IDLE = 5;
    
    /** 
     * Tiempo máximo de vida de una conexión en milisegundos 
     */
    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    
    /** 
     * Timeout para establecer conexión en milisegundos 
     */
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30);
    
    /** 
     * Timeout para conexiones inactivas en milisegundos 
     */
    private static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(10);
    
    @Autowired
    private Environment env;
    
    /**
     * Configura y crea el DataSource primario para SQL Server.
     * 
     * <p>Este bean está marcado como {@code @Primary} y proporciona una instancia configurada 
     * de {@link HikariDataSource} optimizada para SQL Server con las siguientes características:</p>
     * 
     * <ul>
     *   <li>Configuración de pool con tamaño máximo de {@value #MAX_POOL_SIZE} conexiones</li>
     *   <li>Mínimo de {@value #MIN_IDLE} conexiones inactivas</li>
     *   <li>Detección de leaks configurada a 60 segundos</li>
     *   <li>SQL de inicialización de conexión para validación temprana</li>
     *   <li>Configuraciones específicas del driver Microsoft JDBC</li>
     * </ul>
     * 
     * @return DataSource primario configurado para SQL Server
     * @throws IllegalStateException si alguna propiedad requerida no está configurada
     * 
     * @see #configureSqlServerConnectionPool(HikariDataSource)
     * @see #getRequiredProperty(String)
     */
    @Bean
    @Primary
    DataSource DataSourceSqlServer() {
        String user = getRequiredProperty("spring.datasource.sqlserver.username");
        String pass = getRequiredProperty("spring.datasource.sqlserver.password");
        String url = getRequiredProperty("spring.datasource.sqlserver.url");
        String driver = getRequiredProperty("spring.datasource.sqlserver.driver-class-name");
        
        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(user)
                .password(pass)
                .url(url)
                .driverClassName(driver)
                .build();
        
        dataSource.setConnectionInitSql("SELECT 1");
        dataSource.setLeakDetectionThreshold(60000);
        
        // Configuración optimizada del pool de conexiones para SQL Server
        configureSqlServerConnectionPool(dataSource);
        
        return dataSource;
    }
    
    /**
     * Configura las propiedades específicas del pool de conexiones HikariCP para SQL Server.
     * 
     * <p>Esta configuración incluye optimizaciones específicas para SQL Server:</p>
     * <ul>
     *   <li>Configuraciones de seguridad y encriptación</li>
     *   <li>Timeouts de conexión y socket</li>
     *   <li>Optimizaciones de rendimiento para batch operations</li>
     *   <li>Configuración de packet size y statement pooling</li>
     *   <li>Parámetros específicos del driver Microsoft JDBC</li>
     * </ul>
     * 
     * @param dataSource DataSource Hikari a configurar
     */
    private void configureSqlServerConnectionPool(HikariDataSource dataSource) {
        // Configuración del tamaño del pool
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        
        // Configuración de timeouts
        dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);
        dataSource.setMaxLifetime(MAX_LIFETIME);
        
        // Configuración de rendimiento y monitoreo
        dataSource.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        dataSource.setValidationTimeout(TimeUnit.SECONDS.toMillis(5));
        
        // Configuraciones específicas de SQL Server
        dataSource.addDataSourceProperty("trustServerCertificate", "true");
        dataSource.addDataSourceProperty("encrypt", "false");
        dataSource.addDataSourceProperty("loginTimeout", "15");
        dataSource.addDataSourceProperty("socketTimeout", "30");
        dataSource.addDataSourceProperty("cancelQueryTimeout", "10");
        dataSource.addDataSourceProperty("applicationName", "Spring-Boot-App");
        dataSource.addDataSourceProperty("applicationIntent", "ReadWrite");
        dataSource.addDataSourceProperty("multiSubnetFailover", "false");
        dataSource.addDataSourceProperty("sendTimeAsDatetime", "true");
        dataSource.addDataSourceProperty("useFmtOnly", "false");
        
        // Optimizaciones de rendimiento específicas
        dataSource.addDataSourceProperty("packetSize", "8000");
        dataSource.addDataSourceProperty("useBulkCopyForBatchInsert", "true");
        dataSource.addDataSourceProperty("disableStatementPooling", "false");
        dataSource.addDataSourceProperty("statementPoolingCacheSize", "0"); // 0 = tamaño ilimitado
        
        // Configuración del nombre del pool para monitoreo
        dataSource.setPoolName("SQLServer-HikariPool");
        
        // Query de validación específica para SQL Server
        dataSource.setConnectionTestQuery("SELECT 1");
    }
    
    /**
     * Configura el EntityManagerFactory primario para SQL Server.
     * 
     * <p>Este bean está marcado como {@code @Primary} y proporciona un 
     * {@link LocalContainerEntityManagerFactoryBean} configurado para:</p>
     * 
     * <ul>
     *   <li>Escanear entidades en el paquete {@code com.example.demo.app.entity.sqlserver}</li>
     *   <li>Utilizar el adaptador Hibernate JPA</li>
     *   <li>Aplicar propiedades específicas de Hibernate optimizadas para SQL Server</li>
     *   <li>Soporte para operaciones batch y claves generadas</li>
     * </ul>
     * 
     * @return EntityManagerFactory primario configurado para SQL Server
     * 
     * @see #sqlServerHibernateProperties()
     */
    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean sqlServerEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(DataSourceSqlServer());
        em.setPackagesToScan("com.example.demo.app.entity.sqlserver");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(sqlServerHibernateProperties());
        return em;
    }
    
    /**
     * Define las propiedades de Hibernate optimizadas para SQL Server.
     * 
     * <p>Incluye configuraciones específicas para las características de SQL Server:</p>
     * <ul>
     *   <li>Batch processing con tamaño optimizado</li>
     *   <li>Soporte para claves generadas automáticamente</li>
     *   <li>Configuración de isolation level READ_COMMITTED</li>
     *   <li>Manejo optimizado de transacciones y LOBs</li>
     *   <li>Soporte para esquema y catálogo por defecto</li>
     * </ul>
     * 
     * @return Mapa de propiedades de Hibernate optimizadas para SQL Server
     * 
     * @see #configureHibernateLogging(Map)
     */
    private Map<String, Object> sqlServerHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // Configuración básica
        properties.put("hibernate.hbm2ddl.auto", getRequiredProperty("spring.datasource.sqlserver.ddl"));
        properties.put("hibernate.dialect", getRequiredProperty("spring.datasource.sqlserver.dialect.dialect"));
        
        // Optimizaciones específicas de SQL Server
        properties.put("hibernate.jdbc.batch_size", "25"); // SQL Server tiene límites de batch
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.batch_versioned_data", "true");
        properties.put("hibernate.jdbc.batch_versioned_data", "true");
        
        // Configuraciones específicas para SQL Server
        properties.put("hibernate.use_sql_comments", "false");
        properties.put("hibernate.jdbc.use_get_generated_keys", "true");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        
        // Cache y consultas
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.generate_statistics", "false");
        
        // Manejo de conexiones y transacciones
        properties.put("hibernate.connection.provider_disables_autocommit", "true");
        properties.put("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_HOLD");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        
        // Configuración de isolation level (opcional)
        properties.put("hibernate.connection.isolation", "2"); // READ_COMMITTED
        
        // Optimizaciones de esquema y catalog
        properties.put("hibernate.default_schema", getSchemaProperty());
        properties.put("hibernate.default_catalog", getCatalogProperty());
        properties.put("hibernate.jdbc.time_zone", "UTC");
        
        // Configuración para manejo de identidad
        properties.put("hibernate.id.new_generator_mappings", "true");
        properties.put("hibernate.jdbc.fetch_size", "100");
        
        // Logging (configuración por ambiente)
        configureHibernateLogging(properties);
        
        return properties;
    }
    
    /**
     * Configura el TransactionManager primario para SQL Server.
     * 
     * <p>Este bean está marcado como {@code @Primary} y proporciona un 
     * {@link JpaTransactionManager} configurado con:</p>
     * 
     * <ul>
     *   <li>Transacciones anidadas habilitadas</li>
     *   <li>Timeout de 30 segundos por defecto</li>
     *   <li>Validación de transacciones existentes</li>
     *   <li>Configuración optimizada para alta concurrencia</li>
     *   <li>Detección temprana de rollbacks globales</li>
     * </ul>
     * 
     * @return PlatformTransactionManager primario configurado para SQL Server
     */
    @Bean
    @Primary
    PlatformTransactionManager sqlServerTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sqlServerEntityManager().getObject());
        transactionManager.setNestedTransactionAllowed(true);
        transactionManager.setDefaultTimeout(30); // 30 segundos timeout por defecto
        
        // Configuración específica para SQL Server
        transactionManager.setValidateExistingTransaction(true);
        transactionManager.setRollbackOnCommitFailure(false);
        transactionManager.setFailEarlyOnGlobalRollbackOnly(true);
        
        return transactionManager;
    }
    
    /**
     * Obtiene una propiedad requerida del entorno.
     * 
     * @param key Clave de la propiedad a obtener
     * @return Valor de la propiedad
     * @throws IllegalStateException si la propiedad no está configurada
     */
    private String getRequiredProperty(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required property '" + key + "' is not set");
        }
        return value.trim();
    }
    
    /**
     * Obtiene la propiedad del esquema o retorna "dbo" por defecto.
     * 
     * <p>SQL Server utiliza típicamente "dbo" (Database Owner) como esquema por defecto.</p>
     * 
     * @return Nombre del esquema a utilizar, "dbo" si no está especificado
     */
    private String getSchemaProperty() {
        // Permite schema opcional para SQL Server
        String schema = env.getProperty("spring.datasource.sqlserver.schema");
        return schema != null ? schema.trim() : "dbo";
    }
    
    /**
     * Obtiene la propiedad del catálogo (base de datos) para SQL Server.
     * 
     * <p>En SQL Server, el catálogo típicamente se refiere al nombre de la base de datos.</p>
     * 
     * @return Nombre del catálogo a utilizar, o null si no está especificado
     */
    private String getCatalogProperty() {
        // Permite catalog opcional para SQL Server
        return env.getProperty("spring.datasource.sqlserver.catalog");
    }
    
    /**
     * Configura el logging de Hibernate basado en el perfil de ejecución.
     * 
     * <p>En desarrollo habilita logging detallado para debugging, mientras que en producción
     * deshabilita completamente el logging de SQL para maximizar el rendimiento.</p>
     * 
     * @param properties Mapa de propiedades donde se configurará el logging
     * 
     * @see #isDevelopmentProfile()
     */
    private void configureHibernateLogging(Map<String, Object> properties) {
        if (isDevelopmentProfile()) {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "true");
            properties.put("hibernate.use_sql_comments", "true");
            properties.put("hibernate.type", "trace");
        } else {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "false");
            // En producción, deshabilitar completamente el logging de SQL
            properties.put("hibernate.generate_statistics", "false");
        }
    }
    
    /**
     * Verifica si el perfil activo es de desarrollo.
     * 
     * @return true si el perfil activo es "dev" o "development", false en caso contrario
     */
    private boolean isDevelopmentProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile) || "development".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
```

## PostgreSQL Server

### File application.properties
```
# Configuracion PostgreSQL
spring.datasource.postgres.url=jdbc:postgresql://[ip_server]:[port_server]/[database_name]
spring.datasource.postgres.username=[user_db]
spring.datasource.postgres.password=[password_db]
spring.datasource.postgres.driver-class-name=org.postgresql.Driver
spring.datasource.postgres.dialect.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.postgres.ddl=none
```
### File DbPostgres.java
```
package com.example.demo.app.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuración de base de datos para PostgreSQL.
 * 
 * <p>Esta clase proporciona la configuración completa para la conexión a bases de datos
 * PostgreSQL, incluyendo configuración del pool de conexiones HikariCP,
 * EntityManagerFactory y TransactionManager específicamente optimizados para PostgreSQL.</p>
 * 
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *   <li>Configuración optimizada para el alto rendimiento de PostgreSQL</li>
 *   <li>Soporte para batch rewriting de inserciones (mejora significativa de rendimiento)</li>
 *   <li>Configuración de prepared statements cache</li>
 *   <li>Optimizaciones específicas para PostgreSQL 12+</li>
 *   <li>Manejo eficiente de conexiones concurrentes</li>
 * </ul>
 * 
 * <p><strong>Propiedades requeridas en application.properties:</strong></p>
 * <pre>
 * spring.datasource.postgres.username=usuario
 * spring.datasource.postgres.password=contraseña
 * spring.datasource.postgres.url=jdbc:postgresql://host:puerto/base_datos
 * spring.datasource.postgres.driver-class-name=org.postgresql.Driver
 * spring.datasource.postgres.ddl=none
 * spring.datasource.postgres.dialect.dialect=org.hibernate.dialect.PostgreSQLDialect
 * </pre>
 * 
 * <p><strong>Propiedades opcionales:</strong></p>
 * <pre>
 * spring.datasource.postgres.schema=esquema
 * </pre>
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages            = "com.example.demo.app.repository.postgres", 
    entityManagerFactoryRef = "postgresEntityManager", 
    transactionManagerRef   = "postgresTransactionManager"
)
public class DbPostgres {

    /** 
     * Tamaño máximo del pool de conexiones - PostgreSQL maneja mejor conexiones concurrentes 
     */
    private static final int MAX_POOL_SIZE = 20;
    
    /** 
     * Mínimo de conexiones inactivas en el pool 
     */
    private static final int MIN_IDLE = 5;
    
    /** 
     * Tiempo máximo de vida de una conexión en milisegundos 
     */
    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    
    /** 
     * Timeout para establecer conexión en milisegundos 
     */
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30);
    
    /** 
     * Timeout para conexiones inactivas en milisegundos 
     */
    private static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(10);
    
    @Autowired
    private Environment env;
    
    /**
     * Configura y crea el DataSource para PostgreSQL.
     * 
     * <p>Este bean proporciona una instancia configurada de {@link HikariDataSource} 
     * optimizada para PostgreSQL con las siguientes características:</p>
     * 
     * <ul>
     *   <li>Configuración de pool con tamaño máximo de {@value #MAX_POOL_SIZE} conexiones</li>
     *   <li>Mínimo de {@value #MIN_IDLE} conexiones inactivas</li>
     *   <li>SQL de inicialización para validación temprana de conexiones</li>
     *   <li>Detección de leaks configurada a 60 segundos</li>
     *   <li>Configuraciones específicas del driver PostgreSQL JDBC</li>
     * </ul>
     * 
     * @return DataSource configurado para PostgreSQL
     * @throws IllegalStateException si alguna propiedad requerida no está configurada
     * 
     * @see #configurePostgresConnectionPool(HikariDataSource)
     * @see #getRequiredProperty(String)
     */
    @Bean
    //@Primary
    DataSource DataSourcePostgres() {
        String user = getRequiredProperty("spring.datasource.postgres.username");
        String pass = getRequiredProperty("spring.datasource.postgres.password");
        String url = getRequiredProperty("spring.datasource.postgres.url");
        String driver = getRequiredProperty("spring.datasource.postgres.driver-class-name");
        
        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(user)
                .password(pass)
                .url(url)
                .driverClassName(driver)
                .build();
        
        dataSource.setConnectionInitSql("SELECT 1");
        dataSource.setLeakDetectionThreshold(60000);
        
        // Configuración optimizada del pool de conexiones para PostgreSQL
        configurePostgresConnectionPool(dataSource);
        
        return dataSource;
    }
    
    /**
     * Configura las propiedades específicas del pool de conexiones HikariCP para PostgreSQL.
     * 
     * <p>Esta configuración incluye optimizaciones específicas para PostgreSQL:</p>
     * <ul>
     *   <li>Cache de prepared statements para mejor rendimiento</li>
     *   <li>Batch rewriting de inserciones (mejora significativa)</li>
     *   <li>Configuración de timeouts de socket y cancelación</li>
     *   <li>Soporte para TCP KeepAlive</li>
     *   <li>Configuración para PostgreSQL 12+</li>
     * </ul>
     * 
     * @param dataSource DataSource Hikari a configurar
     */
    private void configurePostgresConnectionPool(HikariDataSource dataSource) {
        // Configuración del tamaño del pool
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        
        // Configuración de timeouts
        dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);
        dataSource.setMaxLifetime(MAX_LIFETIME);
        
        // Configuración de rendimiento y monitoreo
        dataSource.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        dataSource.setValidationTimeout(TimeUnit.SECONDS.toMillis(5));
        
        // Configuraciones específicas de PostgreSQL
        dataSource.addDataSourceProperty("preparedStatementCacheQueries", "1024");
        dataSource.addDataSourceProperty("preparedStatementCacheSizeMiB", "32");
        dataSource.addDataSourceProperty("cachePreparedStatements", "true");
        dataSource.addDataSourceProperty("preferQueryMode", "extended");
        dataSource.addDataSourceProperty("reWriteBatchedInserts", "true");
        dataSource.addDataSourceProperty("applicationName", "Spring-Boot-App");
        dataSource.addDataSourceProperty("assumeMinServerVersion", "12");
        dataSource.addDataSourceProperty("tcpKeepAlive", "true");
        dataSource.addDataSourceProperty("socketTimeout", "30");
        dataSource.addDataSourceProperty("cancelSignalTimeout", "10");
        
        // Configuración del nombre del pool para monitoreo
        dataSource.setPoolName("PostgreSQL-HikariPool");
        
        // Query de validación específica para PostgreSQL
        dataSource.setConnectionTestQuery("SELECT 1");
    }
    
    /**
     * Configura el EntityManagerFactory para PostgreSQL.
     * 
     * <p>Este bean proporciona un {@link LocalContainerEntityManagerFactoryBean} configurado para:</p>
     * <ul>
     *   <li>Escanear entidades en el paquete {@code com.example.demo.app.entity.postgres}</li>
     *   <li>Utilizar el adaptador Hibernate JPA</li>
     *   <li>Aplicar propiedades específicas de Hibernate optimizadas para PostgreSQL</li>
     *   <li>Configuración de batch processing con tamaño aumentado</li>
     * </ul>
     * 
     * @return EntityManagerFactory configurado para PostgreSQL
     * 
     * @see #postgresHibernateProperties()
     */
    @Bean
    //@Primary
    LocalContainerEntityManagerFactoryBean postgresEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(DataSourcePostgres());
        em.setPackagesToScan("com.example.demo.app.entity.postgres");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(postgresHibernateProperties());
        return em;
    }
    
    /**
     * Define las propiedades de Hibernate optimizadas para PostgreSQL.
     * 
     * <p>Incluye configuraciones específicas para las características de PostgreSQL:</p>
     * <ul>
     *   <li>Batch size aumentado a 50 (PostgreSQL maneja batches grandes eficientemente)</li>
     *   <li>Soporte completo para datos versionados en batches</li>
     *   <li>Configuración de manejo de LOBs no contextual</li>
     *   <li>Manejo optimizado de transacciones y conexiones</li>
     *   <li>Soporte para búsqueda de texto completo con Hibernate Search</li>
     * </ul>
     * 
     * @return Mapa de propiedades de Hibernate optimizadas para PostgreSQL
     * 
     * @see #configureHibernateLogging(Map)
     */
    private Map<String, Object> postgresHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // Configuración básica
        properties.put("hibernate.hbm2ddl.auto", getRequiredProperty("spring.datasource.postgres.ddl"));
        properties.put("hibernate.dialect", getRequiredProperty("spring.datasource.postgres.dialect.dialect"));
        
        // Optimizaciones específicas de PostgreSQL
        properties.put("hibernate.jdbc.batch_size", "50");
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.batch_versioned_data", "true");
        properties.put("hibernate.jdbc.batch_versioned_data", "true");
        
        // Configuración de batch rewriting para PostgreSQL (mejora significativa en INSERTS)
        properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        
        // Cache y consultas
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.generate_statistics", "false");
        
        // Manejo de conexiones y transacciones
        properties.put("hibernate.connection.provider_disables_autocommit", "true");
        properties.put("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_HOLD");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        
        // Optimizaciones de esquema
        properties.put("hibernate.default_schema", getSchemaProperty());
        properties.put("hibernate.jdbc.time_zone", "UTC");
        
        // Configuración de búsqueda de texto completo (si se usa)
        properties.put("hibernate.search.default.directory_provider", "filesystem");
        
        // Logging (configuración por ambiente)
        configureHibernateLogging(properties);
        
        return properties;
    }
    
    /**
     * Configura el TransactionManager para PostgreSQL.
     * 
     * <p>Este bean proporciona un {@link JpaTransactionManager} configurado con:</p>
     * <ul>
     *   <li>Transacciones anidadas habilitadas</li>
     *   <li>Timeout de 30 segundos por defecto</li>
     *   <li>Validación de transacciones existentes</li>
     *   <li>Configuración optimizada para el modelo de transacciones de PostgreSQL</li>
     * </ul>
     * 
     * @return PlatformTransactionManager configurado para PostgreSQL
     */
    @Bean
    //@Primary
    PlatformTransactionManager postgresTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(postgresEntityManager().getObject());
        transactionManager.setNestedTransactionAllowed(true);
        transactionManager.setDefaultTimeout(30); // 30 segundos timeout por defecto
        
        // Configuración específica para PostgreSQL
        transactionManager.setValidateExistingTransaction(true);
        transactionManager.setRollbackOnCommitFailure(false);
        
        return transactionManager;
    }
    
    /**
     * Obtiene una propiedad requerida del entorno.
     * 
     * @param key Clave de la propiedad a obtener
     * @return Valor de la propiedad
     * @throws IllegalStateException si la propiedad no está configurada
     */
    private String getRequiredProperty(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required property '" + key + "' is not set");
        }
        return value.trim();
    }
    
    /**
     * Obtiene la propiedad del esquema o retorna "public" por defecto.
     * 
     * <p>PostgreSQL utiliza "public" como esquema por defecto.</p>
     * 
     * @return Nombre del esquema a utilizar, "public" si no está especificado
     */
    private String getSchemaProperty() {
        // Permite schema opcional para PostgreSQL
        String schema = env.getProperty("spring.datasource.postgres.schema");
        return schema != null ? schema.trim() : "public";
    }
    
    /**
     * Configura el logging de Hibernate basado en el perfil de ejecución.
     * 
     * <p>En desarrollo habilita logging detallado para debugging, mientras que en producción
     * mantiene configuraciones conservadoras para mejor rendimiento.</p>
     * 
     * @param properties Mapa de propiedades donde se configurará el logging
     * 
     * @see #isDevelopmentProfile()
     */
    private void configureHibernateLogging(Map<String, Object> properties) {
        if (isDevelopmentProfile()) {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "true");
            properties.put("hibernate.use_sql_comments", "true");
            properties.put("hibernate.type", "trace");
        } else {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "false");
        }
    }
    
    /**
     * Verifica si el perfil activo es de desarrollo.
     * 
     * @return true si el perfil activo es "dev" o "development", false en caso contrario
     */
    private boolean isDevelopmentProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile) || "development".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
```


## Mysql Server

### File application.properties
```
# Configuracion Mysql
spring.datasource.mysql.url=jdbc:mysql://[ip_server]:[port_server]/[database_name]?serverTimezone=America/El_Salvador&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.mysql.username=[user_db]
spring.datasource.mysql.password=[password_db]
spring.datasource.mysql.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.mysql.dialect.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.mysql.ddl=none
```

### File DbMysql.java
```
package com.example.demo.app.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuración de base de datos para MySQL.
 * 
 * <p>Esta clase proporciona la configuración completa para la conexión a bases de datos
 * MySQL, incluyendo configuración del pool de conexiones HikariCP,
 * EntityManagerFactory y TransactionManager específicamente optimizados para MySQL.</p>
 * 
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *   <li>Configuración optimizada para el alto rendimiento de MySQL</li>
 *   <li>Soporte para batch rewriting de statements (mejora significativa de rendimiento)</li>
 *   <li>Cache extensivo de prepared statements</li>
 *   <li>Configuraciones específicas del connector JDBC de MySQL</li>
 *   <li>Manejo eficiente de conexiones concurrentes</li>
 * </ul>
 * 
 * <p><strong>Propiedades requeridas en application.properties:</strong></p>
 * <pre>
 * spring.datasource.mysql.username=usuario
 * spring.datasource.mysql.password=contraseña
 * spring.datasource.mysql.url=jdbc:mysql://host:puerto/base_datos
 * spring.datasource.mysql.driver-class-name=com.mysql.cj.jdbc.Driver
 * spring.datasource.mysql.ddl=none
 * spring.datasource.mysql.dialect.dialect=org.hibernate.dialect.MySQLDialect
 * </pre>
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages            = "com.example.demo.app.repository.mysql", 
    entityManagerFactoryRef = "mysqlEntityManager", 
    transactionManagerRef   = "mysqlTransactionManager"
)
public class DbMysql {
    
    /** 
     * Tamaño máximo del pool de conexiones - MySQL Server maneja mejor conexiones concurrentes 
     */
    private static final int MAX_POOL_SIZE = 20;
    
    /** 
     * Mínimo de conexiones inactivas en el pool 
     */
    private static final int MIN_IDLE = 5;
    
    /** 
     * Tiempo máximo de vida de una conexión en milisegundos 
     */
    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    
    /** 
     * Timeout para establecer conexión en milisegundos 
     */
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30);
    
    /** 
     * Timeout para conexiones inactivas en milisegundos 
     */
    private static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(10);
    
    @Autowired
    private Environment env;
    
    /**
     * Configura y crea el DataSource para MySQL.
     * 
     * <p>Este bean proporciona una instancia configurada de {@link HikariDataSource} 
     * optimizada para MySQL con las siguientes características:</p>
     * 
     * <ul>
     *   <li>Configuración de pool con tamaño máximo de {@value #MAX_POOL_SIZE} conexiones</li>
     *   <li>Mínimo de {@value #MIN_IDLE} conexiones inactivas</li>
     *   <li>SQL de inicialización para validación temprana de conexiones</li>
     *   <li>Query de prueba de conexión configurada</li>
     *   <li>Configuraciones específicas del driver MySQL Connector/J</li>
     * </ul>
     * 
     * @return DataSource configurado para MySQL
     * @throws IllegalStateException si alguna propiedad requerida no está configurada
     * 
     * @see #configureConnectionPool(HikariDataSource)
     * @see #getRequiredProperty(String)
     */
    @Bean
    //@Primary
    DataSource DataSourceMySql() {
        String user = getRequiredProperty("spring.datasource.mysql.username");
        String pass = getRequiredProperty("spring.datasource.mysql.password");
        String url = getRequiredProperty("spring.datasource.mysql.url");
        String driver = getRequiredProperty("spring.datasource.mysql.driver-class-name");
        
        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(user)
                .password(pass)
                .url(url)
                .driverClassName(driver)
                .build();
        
        dataSource.setConnectionInitSql("SELECT 1");
        dataSource.setConnectionTestQuery("SELECT 1");

        // Configuración optimizada del pool de conexiones
        configureConnectionPool(dataSource);
        
        return dataSource;
    }
    
    /**
     * Configura las propiedades específicas del pool de conexiones HikariCP para MySQL.
     * 
     * <p>Esta configuración incluye optimizaciones específicas para MySQL Connector/J:</p>
     * <ul>
     *   <li>Cache de prepared statements para mejor rendimiento</li>
     *   <li>Batch rewriting de statements (mejora significativa en rendimiento)</li>
     *   <li>Uso de prepared statements del servidor</li>
     *   <li>Cache de metadatos y configuración del servidor</li>
     *   <li>Optimizaciones de sesión local</li>
     * </ul>
     * 
     * @param dataSource DataSource Hikari a configurar
     */
    private void configureConnectionPool(HikariDataSource dataSource) {
        // Configuración del tamaño del pool
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        
        // Configuración de timeouts
        dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);
        dataSource.setMaxLifetime(MAX_LIFETIME);
        
        // Configuración de rendimiento
        dataSource.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setValidationTimeout(TimeUnit.SECONDS.toMillis(5));
        
        // Configuraciones específicas de MySQL
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");
        dataSource.addDataSourceProperty("useLocalSessionState", "true");
        dataSource.addDataSourceProperty("rewriteBatchedStatements", "true");
        dataSource.addDataSourceProperty("cacheResultSetMetadata", "true");
        dataSource.addDataSourceProperty("cacheServerConfiguration", "true");
        dataSource.addDataSourceProperty("elideSetAutoCommits", "true");
        dataSource.addDataSourceProperty("maintainTimeStats", "false");
        
        // Configuración del nombre del pool para monitoreo
        dataSource.setPoolName("MySQL-HikariPool");
    }
    
    /**
     * Configura el EntityManagerFactory para MySQL.
     * 
     * <p>Este bean proporciona un {@link LocalContainerEntityManagerFactoryBean} configurado para:</p>
     * <ul>
     *   <li>Escanear entidades en el paquete {@code com.example.demo.app.entity.mysql}</li>
     *   <li>Utilizar el adaptador Hibernate JPA</li>
     *   <li>Aplicar propiedades específicas de Hibernate optimizadas para MySQL</li>
     *   <li>Configuración de batch processing con tamaño optimizado</li>
     * </ul>
     * 
     * @return EntityManagerFactory configurado para MySQL
     * 
     * @see #hibernateProperties()
     */
    @Bean
    //@Primary
    LocalContainerEntityManagerFactoryBean mysqlEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(DataSourceMySql());
        em.setPackagesToScan("com.example.demo.app.entity.mysql");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(hibernateProperties());
        return em;
    }
    
    /**
     * Define las propiedades de Hibernate optimizadas para MySQL.
     * 
     * <p>Incluye configuraciones específicas para las características de MySQL:</p>
     * <ul>
     *   <li>Batch size de 50 para operaciones eficientes</li>
     *   <li>Soporte completo para datos versionados en batches</li>
     *   <li>Ordenamiento de inserts y updates para mejor rendimiento</li>
     *   <li>Manejo optimizado de conexiones y transacciones</li>
     *   <li>Configuraciones de paginación y cláusulas IN</li>
     * </ul>
     * 
     * @return Mapa de propiedades de Hibernate optimizadas para MySQL
     */
    private Map<String, Object> hibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // Configuración básica
        properties.put("hibernate.hbm2ddl.auto", getRequiredProperty("spring.datasource.mysql.ddl"));
        properties.put("hibernate.dialect", getRequiredProperty("spring.datasource.mysql.dialect.dialect"));
        
        // Optimizaciones de rendimiento
        properties.put("hibernate.jdbc.batch_size", "50");
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.batch_versioned_data", "true");
        properties.put("hibernate.jdbc.batch_versioned_data", "true");
        
        // Cache de segundo nivel y consultas
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.generate_statistics", "false");
        
        // Manejo de conexiones
        properties.put("hibernate.connection.provider_disables_autocommit", "true");
        properties.put("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_HOLD");
        
        // Timeouts y configuraciones de sesión
        properties.put("hibernate.jdbc.time_zone", "UTC");
        properties.put("hibernate.query.fail_on_pagination_over_collection_fetch", "true");
        properties.put("hibernate.query.in_clause_parameter_padding", "true");
        
        // Logging (solo en desarrollo)
        if (isDevelopmentProfile()) {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "true");
            properties.put("hibernate.use_sql_comments", "true");
        } else {
            properties.put("hibernate.show_sql", "false");
        }
        
        return properties;
    }
    
    /**
     * Configura el TransactionManager para MySQL.
     * 
     * <p>Este bean proporciona un {@link JpaTransactionManager} configurado con:</p>
     * <ul>
     *   <li>Transacciones anidadas habilitadas</li>
     *   <li>Timeout de 30 segundos por defecto</li>
     *   <li>Configuración estándar para el modelo de transacciones de MySQL</li>
     * </ul>
     * 
     * @return PlatformTransactionManager configurado para MySQL
     */
    @Bean
    //@Primary
    PlatformTransactionManager mysqlTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());
        transactionManager.setNestedTransactionAllowed(true);
        transactionManager.setDefaultTimeout(30); // 30 segundos timeout por defecto
        return transactionManager;
    }
    
    /**
     * Obtiene una propiedad requerida del entorno.
     * 
     * @param key Clave de la propiedad a obtener
     * @return Valor de la propiedad
     * @throws IllegalStateException si la propiedad no está configurada
     */
    private String getRequiredProperty(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required property '" + key + "' is not set");
        }
        return value.trim();
    }
    
    /**
     * Verifica si el perfil activo es de desarrollo.
     * 
     * @return true si el perfil activo es "dev" o "development", false en caso contrario
     */
    private boolean isDevelopmentProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile) || "development".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
```

## Sybase JTDS

### File application.properties
```
# Configuracion Sybase
spring.datasource.sybase.url=jdbc:jtds:sybase://[ip_server]:[port_server];DatabaseName=[database_name]
spring.datasource.sybase.username=[user_db]
spring.datasource.sybase.password=[password_db]
spring.datasource.sybase.driver-class-name=net.sourceforge.jtds.jdbc.Driver
spring.datasource.sybase.dialect.dialect=org.hibernate.dialect.SybaseDialect
spring.datasource.sybase.ddl=none
```
### File DbSybase.java
```
package com.example.demo.app.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuración de base de datos para Sybase ASE utilizando el driver JTDS.
 * 
 * <p>Esta clase proporciona la configuración completa para la conexión a bases de datos
 * Sybase Adaptive Server Enterprise (ASE), incluyendo configuración del pool de conexiones 
 * HikariCP, EntityManagerFactory y TransactionManager específicamente optimizados para Sybase.</p>
 * 
 * <p><strong>Diferencias con DbSybaseJ (jConnect):</strong></p>
 * <ul>
 *   <li>Utiliza el driver JTDS en lugar de jConnect</li>
 *   <li>Configuración más conservadora de batch processing</li>
 *   <li>Manejo diferente de transacciones anidadas</li>
 *   <li>Propiedades específicas del protocolo TDS</li>
 * </ul>
 * 
 * <p><strong>Propiedades requeridas en application.properties:</strong></p>
 * <pre>
 * spring.datasource.sybase.username=usuario
 * spring.datasource.sybase.password=contraseña
 * spring.datasource.sybase.url=jdbc:jtds:sybase://host:puerto/database
 * spring.datasource.sybase.driver-class-name=net.sourceforge.jtds.jdbc.Driver
 * spring.datasource.sybase.ddl=none
 * spring.datasource.sybase.dialect.dialect=org.hibernate.dialect.SybaseDialect
 * </pre>
 * 
 * <p><strong>Propiedades opcionales:</strong></p>
 * <pre>
 * spring.datasource.sybase.schema=esquema
 * </pre>
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages            = "com.example.demo.app.repository.sybase", 
    entityManagerFactoryRef = "sybaseEntityManager", 
    transactionManagerRef   = "sybaseTransactionManager"
)
public class DbSybase {
    
    /** 
     * Tamaño máximo del pool de conexiones - Sybase suele requerir menos conexiones concurrentes 
     */
    private static final int MAX_POOL_SIZE = 15;
    
    /** 
     * Mínimo de conexiones inactivas en el pool 
     */
    private static final int MIN_IDLE = 3;
    
    /** 
     * Tiempo máximo de vida de una conexión en milisegundos - Sybase es más estable con conexiones largas 
     */
    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(45);
    
    /** 
     * Timeout para establecer conexión en milisegundos - Sybase puede ser más lento en conexión 
     */
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(45);
    
    /** 
     * Timeout para conexiones inactivas en milisegundos 
     */
    private static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(15);
    
    @Autowired
    private Environment env;
    
    /**
     * Configura y crea el DataSource para Sybase ASE utilizando el driver JTDS.
     * 
     * <p>Este bean proporciona una instancia configurada de {@link HikariDataSource} 
     * optimizada para Sybase ASE con las siguientes características:</p>
     * 
     * <ul>
     *   <li>Configuración de pool con tamaño máximo de {@value #MAX_POOL_SIZE} conexiones</li>
     *   <li>Mínimo de {@value #MIN_IDLE} conexiones inactivas</li>
     *   <li>Timeouts extendidos para accommodar la latencia típica de Sybase</li>
     *   <li>Propiedades específicas del protocolo TDS versión 8.0</li>
     *   <li>Configuración optimizada para Sybase ASE 15+</li>
     * </ul>
     * 
     * @return DataSource configurado para Sybase ASE
     * @throws IllegalStateException si alguna propiedad requerida no está configurada
     * 
     * @see #configureSybaseConnectionPool(HikariDataSource)
     * @see #getRequiredProperty(String)
     */
    @Bean
    //@Primary
    DataSource DataSourceSybase() {
        String user = getRequiredProperty("spring.datasource.sybase.username");
        String pass = getRequiredProperty("spring.datasource.sybase.password");
        String url = getRequiredProperty("spring.datasource.sybase.url");
        String driver = getRequiredProperty("spring.datasource.sybase.driver-class-name");
        
        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(user)
                .password(pass)
                .url(url)
                .driverClassName(driver)
                .build();
        
        dataSource.setLeakDetectionThreshold(90000);
        
        // Configuración optimizada del pool de conexiones para Sybase
        configureSybaseConnectionPool(dataSource);
        
        return dataSource;
    }
    
    /**
     * Configura las propiedades específicas del pool de conexiones HikariCP para Sybase ASE.
     * 
     * <p>Esta configuración incluye optimizaciones específicas para Sybase:</p>
     * <ul>
     *   <li>Configuración TDS 8.0 para Sybase 15+</li>
     *   <li>Soporte para LOBs y cursores</li>
     *   <li>Optimizaciones de codificación y timeouts</li>
     *   <li>Configuraciones de red (TCP_NODELAY, KEEPALIVE)</li>
     *   <li>Parámetros de rendimiento específicos para ASE</li>
     * </ul>
     * 
     * @param dataSource DataSource Hikari a configurar
     */
    private void configureSybaseConnectionPool(HikariDataSource dataSource) {
        // Configuración del tamaño del pool (Sybase suele ser menos concurrente)
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        
        // Configuración de timeouts más largos para Sybase
        dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);
        dataSource.setMaxLifetime(MAX_LIFETIME);
        
        // Configuración de rendimiento y monitoreo
        dataSource.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(90)); // Más tiempo para Sybase
        dataSource.setValidationTimeout(TimeUnit.SECONDS.toMillis(10));
        
        // Configuraciones específicas de Sybase (JTDS Driver)
        dataSource.addDataSourceProperty("TDS", "8.0"); // Versión TDS para Sybase 15+
        dataSource.addDataSourceProperty("USE_LOBS", "true");
        dataSource.addDataSourceProperty("USE_CURSORS", "true");
        dataSource.addDataSourceProperty("SEND_STRING_PARAMETERS_AS_UNICODE", "false");
        dataSource.addDataSourceProperty("CHARSET", "utf8");
        dataSource.addDataSourceProperty("APPLICATIONNAME", "Spring-Boot-App");
        dataSource.addDataSourceProperty("SOCKET_TIMEOUT", "60");
        dataSource.addDataSourceProperty("LOGIN_TIMEOUT", "30");
        dataSource.addDataSourceProperty("NAMED_PIPE", "false");
        dataSource.addDataSourceProperty("BATCHSIZE", "100");
        dataSource.addDataSourceProperty("BUFFER_SIZE", "4096");
        dataSource.addDataSourceProperty("MAX_STATEMENTS", "500");
        
        // Optimizaciones específicas para Sybase ASE
        dataSource.addDataSourceProperty("PREPARE", "true");
        dataSource.addDataSourceProperty("CACHE_META_DATA", "true");
        dataSource.addDataSourceProperty("USE_METADATA", "true");
        dataSource.addDataSourceProperty("TCP_NODELAY", "true");
        dataSource.addDataSourceProperty("KEEPALIVE", "true");
        
        // Configuración del nombre del pool para monitoreo
        dataSource.setPoolName("Sybase-HikariPool");
        
        // Query de validación específica para Sybase
        dataSource.setConnectionTestQuery("SELECT 1");
    }
    
    /**
     * Configura el EntityManagerFactory para Sybase ASE.
     * 
     * <p>Este bean proporciona un {@link LocalContainerEntityManagerFactoryBean} configurado para:</p>
     * <ul>
     *   <li>Escanear entidades en el paquete {@code com.example.demo.app.entity.sybase}</li>
     *   <li>Utilizar el adaptador Hibernate JPA</li>
     *   <li>Aplicar propiedades específicas de Hibernate optimizadas para Sybase ASE</li>
     *   <li>Configuraciones conservadoras de batch processing apropiadas para Sybase</li>
     * </ul>
     * 
     * @return EntityManagerFactory configurado para Sybase ASE
     * 
     * @see #sybaseHibernateProperties()
     */
    @Bean
    //@Primary
    LocalContainerEntityManagerFactoryBean sybaseEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(DataSourceSybase());
        em.setPackagesToScan("com.example.demo.app.entity.sybase");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(sybaseHibernateProperties());
        return em;
    }
    
    /**
     * Define las propiedades de Hibernate optimizadas para Sybase ASE.
     * 
     * <p>Incluye configuraciones específicas para las particularidades de Sybase:</p>
     * <ul>
     *   <li>Batch size conservador ({@code 20}) debido a limitaciones de Sybase</li>
     *   <li>Deshabilitación de batch versioned data por compatibilidad</li>
     *   <li>Uso de generadores de identidad legacy para mejor compatibilidad</li>
     *   <li>Configuración de isolation level READ_COMMITTED</li>
     *   <li>Manejo optimizado de transacciones y conexiones</li>
     * </ul>
     * 
     * @return Mapa de propiedades de Hibernate optimizadas para Sybase ASE
     * 
     * @see #configureHibernateLogging(Map)
     */
    private Map<String, Object> sybaseHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // Configuración básica
        properties.put("hibernate.hbm2ddl.auto", getRequiredProperty("spring.datasource.sybase.ddl"));
        properties.put("hibernate.dialect", getRequiredProperty("spring.datasource.sybase.dialect.dialect"));
        
        // Optimizaciones específicas de Sybase
        properties.put("hibernate.jdbc.batch_size", "20"); // Sybase tiene límites más conservadores
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.batch_versioned_data", "false"); // Sybase puede tener problemas con esto
        properties.put("hibernate.jdbc.batch_versioned_data", "false");
        
        // Configuraciones específicas para Sybase
        properties.put("hibernate.use_sql_comments", "false");
        properties.put("hibernate.jdbc.use_scrollable_resultset", "true");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        properties.put("hibernate.jdbc.use_streams_for_binary", "true");
        
        // Cache y consultas (Sybase suele tener su propio cache)
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.generate_statistics", "false");
        
        // Manejo de conexiones y transacciones
        properties.put("hibernate.connection.provider_disables_autocommit", "false"); // Sybase necesita autocommit management
        properties.put("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        
        // Configuración de isolation level para Sybase
        properties.put("hibernate.connection.isolation", "2"); // READ_COMMITTED
        
        // Configuraciones de esquema y database
        properties.put("hibernate.default_schema", getSchemaProperty());
        properties.put("hibernate.jdbc.time_zone", "UTC");
        
        // Configuración para manejo de identidad en Sybase
        properties.put("hibernate.id.new_generator_mappings", "false"); // Sybase funciona mejor con el viejo
        properties.put("hibernate.jdbc.fetch_size", "50");
        
        // Configuración específica para tipos de datos Sybase
        properties.put("hibernate.type.descriptor.sql.TimestampType", "UTC");
        
        // Logging (configuración por ambiente)
        configureHibernateLogging(properties);
        
        return properties;
    }
    
    /**
     * Configura el TransactionManager para Sybase ASE.
     * 
     * <p>Este bean proporciona un {@link JpaTransactionManager} configurado con 
     * consideraciones específicas para Sybase:</p>
     * 
     * <ul>
     *   <li>Transacciones anidadas deshabilitadas (Sybase no las soporta bien)</li>
     *   <li>Timeout extendido a 60 segundos para accommodar operaciones más lentas</li>
     *   <li>Rollback automático en fallo de commit habilitado</li>
     *   <li>Validación de transacciones existentes activada</li>
     * </ul>
     * 
     * @return PlatformTransactionManager configurado para Sybase ASE
     */
    @Bean
    //@Primary
    PlatformTransactionManager sybaseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sybaseEntityManager().getObject());
        transactionManager.setNestedTransactionAllowed(false); // Sybase no soporta bien transacciones anidadas
        transactionManager.setDefaultTimeout(60); // 60 segundos timeout por defecto (Sybase es más lento)
        
        // Configuración específica para Sybase
        transactionManager.setValidateExistingTransaction(true);
        transactionManager.setRollbackOnCommitFailure(true); // Sybase puede necesitar rollback explícito
        
        return transactionManager;
    }
    
    /**
     * Obtiene una propiedad requerida del entorno.
     * 
     * @param key Clave de la propiedad a obtener
     * @return Valor de la propiedad
     * @throws IllegalStateException si la propiedad no está configurada
     */
    private String getRequiredProperty(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required property '" + key + "' is not set");
        }
        return value.trim();
    }
    
    /**
     * Obtiene la propiedad del esquema o retorna "dbo" por defecto.
     * 
     * <p>Sybase ASE utiliza típicamente "dbo" (Database Owner) como esquema por defecto.</p>
     * 
     * @return Nombre del esquema a utilizar, "dbo" si no está especificado
     */
    private String getSchemaProperty() {
        // Permite schema opcional para Sybase
        String schema = env.getProperty("spring.datasource.sybase.schema");
        return schema != null ? schema.trim() : "dbo"; // Sybase usa dbo por defecto
    }
    
    /**
     * Configura el logging de Hibernate basado en el perfil de ejecución.
     * 
     * <p>Para Sybase, el logging es más conservador incluso en desarrollo debido a que
     * Sybase puede generar logs muy verbosos que impactan el rendimiento.</p>
     * 
     * @param properties Mapa de propiedades donde se configurará el logging
     * 
     * @see #isDevelopmentProfile()
     */
    private void configureHibernateLogging(Map<String, Object> properties) {
        if (isDevelopmentProfile()) {
            properties.put("hibernate.show_sql", "false"); // Reducido incluso en dev para Sybase
            properties.put("hibernate.format_sql", "true");
            properties.put("hibernate.use_sql_comments", "false"); // Sybase puede ser verbose
        } else {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "false");
        }
        
        // Sybase específico - deshabilitar warnings de dialecto
        properties.put("hibernate.dialect.storage_engine", "default");
    }
    
    /**
     * Verifica si el perfil activo es de desarrollo.
     * 
     * @return true si el perfil activo es "dev" o "development", false en caso contrario
     */
    private boolean isDevelopmentProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile) || "development".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
```

## Sybase JT400

### File application.properties
```
# Configuracion SybaseJ
spring.datasource.sybasej.url=jdbc:sybase:Tds://[ip_server]:[port_server]/[database_name]
spring.datasource.sybasej.username=[user_db]
spring.datasource.sybasej.password=[password_db]
spring.datasource.sybasej.driver-class-name=com.sybase.jdbc4.jdbc.SybDriver
spring.datasource.sybasej.ddl=none
spring.datasource.sybasej.dialect.dialect=org.hibernate.dialect.SybaseDialect
spring.datasource.sybasej.schema=dbo

# Configuraciones adicionales para jConnect
spring.datasource.sybasej.connection-properties=CHARSET=utf8;LOGIN_TIMEOUT=30;SOCKET_TIMEOUT=60;CHARSET_CONVERTER=com.sybase.jdbc4.utils.TruncationConverter;BATCHSIZE=100;BUFFER_SIZE=4096
```
### File DbSybaseJ.java
```
package com.example.demo.app.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuración de base de datos para Sybase utilizando el driver jConnect.
 * 
 * <p>Esta clase proporciona la configuración completa para la conexión a bases de datos
 * Sybase mediante el driver jConnect, incluyendo configuración del pool de conexiones HikariCP,
 * EntityManagerFactory y TransactionManager específicos para Sybase.</p>
 * 
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *   <li>Configuración optimizada del pool de conexiones HikariCP para Sybase jConnect</li>
 *   <li>Propiedades específicas de Hibernate para mejor rendimiento con jConnect</li>
 *   <li>Soporte para transacciones JPA con configuraciones específicas para Sybase</li>
 *   <li>Configuración flexible mediante propiedades de aplicación</li>
 * </ul>
 * 
 * <p><strong>Propiedades requeridas en application.properties:</strong></p>
 * <pre>
 * spring.datasource.sybasej.username=usuario
 * spring.datasource.sybasej.password=contraseña
 * spring.datasource.sybasej.url=jdbc:sybase:Tds:host:puerto/database
 * spring.datasource.sybasej.driver-class-name=com.sybase.jdbc4.jdbc.SybDriver
 * spring.datasource.sybasej.ddl=none
 * spring.datasource.sybasej.dialect.dialect=org.hibernate.dialect.SybaseDialect
 * </pre>
 * 
 * <p><strong>Propiedades opcionales:</strong></p>
 * <pre>
 * spring.datasource.sybasej.schema=esquema
 * spring.datasource.sybasej.connection-properties=propiedad1=valor1;propiedad2=valor2
 * </pre>
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages            = "com.example.demo.app.repository.sybasej", 
    entityManagerFactoryRef = "sybasejEntityManager", 
    transactionManagerRef   = "sybasejTransactionManager"
)
public class DbSybaseJ {
    
    /** Tamaño máximo del pool de conexiones */
    private static final int MAX_POOL_SIZE = 15;
    
    /** Mínimo número de conexiones inactivas en el pool */
    private static final int MIN_IDLE = 3;
    
    /** Tiempo máximo de vida de una conexión en milisegundos */
    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(45);
    
    /** Timeout para establecer conexión en milisegundos */
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(45);
    
    /** Timeout para conexiones inactivas en milisegundos */
    private static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(15);
    
    @Autowired
    private Environment env;
    
    /**
     * Configura y crea el DataSource para Sybase utilizando jConnect.
     * 
     * <p>Este bean proporciona una instancia configurada de {@link HikariDataSource} 
     * optimizada para Sybase jConnect con las siguientes características:</p>
     * 
     * <ul>
     *   <li>Configuración de pool con tamaño máximo de {@value #MAX_POOL_SIZE} conexiones</li>
     *   <li>Mínimo de {@value #MIN_IDLE} conexiones inactivas</li>
     *   <li>Detección de leaks configurada a 90 segundos</li>
     *   <li>Propiedades específicas para jConnect aplicadas</li>
     * </ul>
     * 
     * @return DataSource configurado para Sybase jConnect
     * @throws IllegalStateException si alguna propiedad requerida no está configurada
     * 
     * @see #configureSybaseJConnectPool(HikariDataSource)
     * @see #getRequiredProperty(String)
     */
    @Bean
    //@Primary
    DataSource DataSourceSybaseJ() {
        String user = getRequiredProperty("spring.datasource.sybasej.username");
        String pass = getRequiredProperty("spring.datasource.sybasej.password");
        String url = getRequiredProperty("spring.datasource.sybasej.url");
        String driver = getRequiredProperty("spring.datasource.sybasej.driver-class-name");
        
        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(user)
                .password(pass)
                .url(url)
                .driverClassName(driver)
                .build();
        
        dataSource.setLeakDetectionThreshold(90000);
        
        // Configuración optimizada del pool de conexiones para Sybase jConnect
        configureSybaseJConnectPool(dataSource);
        
        return dataSource;
    }
    
    /**
     * Configura las propiedades específicas del pool de conexiones HikariCP para Sybase jConnect.
     * 
     * <p>Esta configuración incluye:</p>
     * <ul>
     *   <li>Tamaño del pool y conexiones inactivas</li>
     *   <li>Timeouts de conexión, inactividad y vida máxima</li>
     *   <li>Propiedades de rendimiento y monitoreo</li>
     *   <li>Configuraciones específicas de jConnect para transacciones</li>
     *   <li>Manejo de cursores optimizado para Sybase</li>
     * </ul>
     * 
     * @param dataSource DataSource Hikari a configurar
     * 
     * @see #configureConnectionProperties(HikariDataSource)
     */
    private void configureSybaseJConnectPool(HikariDataSource dataSource) {
        // Configuración del tamaño del pool
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        
        // Configuración de timeouts
        dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);
        dataSource.setMaxLifetime(MAX_LIFETIME);
        
        // Configuración de rendimiento y monitoreo
        dataSource.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(90));
        dataSource.setValidationTimeout(TimeUnit.SECONDS.toMillis(10));
        
        // CONFIGURACIÓN DE CONNECTION-PROPERTIES DESDE application.properties
        configureConnectionProperties(dataSource);
        
        // Configuraciones adicionales fijas
        dataSource.addDataSourceProperty("APPLICATIONNAME", "Spring-Boot-App");
        dataSource.addDataSourceProperty("PREPARE", "true");
        dataSource.addDataSourceProperty("CACHE_META_DATA", "true");
        dataSource.addDataSourceProperty("USE_METADATA", "true");
        dataSource.addDataSourceProperty("TCP_NODELAY", "true");
        dataSource.addDataSourceProperty("KEEPALIVE", "true");
        
        // Configuraciones específicas de jConnect para transacciones
        dataSource.addDataSourceProperty("IFX_USE_STRENC", "false");
        dataSource.addDataSourceProperty("ENABLE_BULK_LOAD", "true");
        dataSource.addDataSourceProperty("REPEAT_READ", "false");
        
        // Manejo de cursores (importante para jConnect)
        dataSource.addDataSourceProperty("CURSOR_NAME", "SYBASE_CURSOR");
        dataSource.addDataSourceProperty("CURSOR_TYPE", "DYNAMIC");
        dataSource.addDataSourceProperty("CURSOR_SENSITIVITY", "INSENSITIVE");
        
        // Configuración del nombre del pool para monitoreo
        dataSource.setPoolName("Sybase-jConnect-HikariPool");
        
        // Query de validación específica para Sybase con jConnect
        dataSource.setConnectionTestQuery("SELECT 1");
    }
    
    /**
     * Configura las propiedades de conexión específicas para jConnect desde las propiedades de aplicación.
     * 
     * <p>Las propiedades se esperan en el formato: {@code propiedad1=valor1;propiedad2=valor2}</p>
     * 
     * <p>Si no se especifican propiedades, se aplican configuraciones por defecto optimizadas para jConnect.</p>
     * 
     * @param dataSource DataSource al que aplicar las propiedades
     * 
     * @see #setDefaultConnectionProperties(HikariDataSource)
     */
    private void configureConnectionProperties(HikariDataSource dataSource) {
        // Obtener la propiedad spring.datasource.sybasej.connection-properties
        String connectionProperties = env.getProperty("spring.datasource.sybasej.connection-properties");
        
        if (connectionProperties != null && !connectionProperties.trim().isEmpty()) {
            System.out.println("Applying connection properties for SybaseJ: " + connectionProperties);
            
            // Separar las propiedades por punto y coma
            String[] propertiesArray = connectionProperties.split(";");
            
            for (String property : propertiesArray) {
                // Separar clave=valor
                String[] keyValue = property.split("=", 2);
                
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    
                    // Aplicar la propiedad al DataSource
                    dataSource.addDataSourceProperty(key, value);
                    System.out.println("Applied connection property: " + key + " = " + value);
                } else if (keyValue.length == 1 && !keyValue[0].trim().isEmpty()) {
                    // Propiedad sin valor (solo clave)
                    String key = keyValue[0].trim();
                    dataSource.addDataSourceProperty(key, "true");
                    System.out.println("Applied connection property: " + key + " = true");
                }
            }
        } else {
            // Valores por defecto si no se especifica la propiedad
            System.out.println("Using default connection properties for SybaseJ");
            setDefaultConnectionProperties(dataSource);
        }
    }
    
    /**
     * Establece las propiedades de conexión por defecto optimizadas para Sybase jConnect.
     * 
     * <p>Incluye configuraciones para:</p>
     * <ul>
     *   <li>Charset y conversión de caracteres</li>
     *   <li>Timeouts de socket y login</li>
     *   <li>Tamaño de batch y buffer</li>
     *   <li>Límite de statements</li>
     * </ul>
     * 
     * @param dataSource DataSource al que aplicar las propiedades por defecto
     */
    private void setDefaultConnectionProperties(HikariDataSource dataSource) {
        // Propiedades por defecto para Sybase jConnect
        dataSource.addDataSourceProperty("CHARSET", "utf8");
        dataSource.addDataSourceProperty("CHARSET_CONVERTER", "com.sybase.jdbc4.utils.TruncationConverter");
        dataSource.addDataSourceProperty("SOCKET_TIMEOUT", "60");
        dataSource.addDataSourceProperty("LOGIN_TIMEOUT", "30");
        dataSource.addDataSourceProperty("BATCHSIZE", "100");
        dataSource.addDataSourceProperty("BUFFER_SIZE", "4096");
        dataSource.addDataSourceProperty("MAX_STATEMENTS", "500");
    }
    
    /**
     * Configura el EntityManagerFactory para Sybase jConnect.
     * 
     * <p>Este bean proporciona un {@link LocalContainerEntityManagerFactoryBean} configurado para:</p>
     * <ul>
     *   <li>Escanear entidades en el paquete {@code com.example.demo.app.entity.sybasej}</li>
     *   <li>Utilizar el adaptador Hibernate JPA</li>
     *   <li>Aplicar propiedades específicas de Hibernate para jConnect</li>
     * </ul>
     * 
     * @return EntityManagerFactory configurado para Sybase jConnect
     * 
     * @see #sybaseJConnectHibernateProperties()
     */
    @Bean
    //@Primary
    LocalContainerEntityManagerFactoryBean sybasejEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(DataSourceSybaseJ());
        em.setPackagesToScan("com.example.demo.app.entity.sybasej");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(sybaseJConnectHibernateProperties());
        return em;
    }
    
    /**
     * Define las propiedades de Hibernate optimizadas para Sybase jConnect.
     * 
     * <p>Incluye configuraciones para:</p>
     * <ul>
     *   <li>Batch processing y optimización de operaciones</li>
     *   <li>Manejo de transacciones y conexiones</li>
     *   <li>Cache y estadísticas</li>
     *   <li>Configuraciones específicas de jConnect</li>
     *   <li>Logging adaptado al perfil de ejecución</li>
     * </ul>
     * 
     * @return Mapa de propiedades de Hibernate optimizadas para jConnect
     * 
     * @see #applyHibernateConnectionProperties(Map)
     * @see #configureJConnectHibernateLogging(Map)
     */
    private Map<String, Object> sybaseJConnectHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // Configuración básica
        properties.put("hibernate.hbm2ddl.auto", getRequiredProperty("spring.datasource.sybasej.ddl"));
        properties.put("hibernate.dialect", getRequiredProperty("spring.datasource.sybasej.dialect.dialect"));
        
        // Optimizaciones específicas para jConnect
        properties.put("hibernate.jdbc.batch_size", "25"); // jConnect maneja mejor batches medianos
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.batch_versioned_data", "true"); // jConnect soporta mejor esta característica
        properties.put("hibernate.jdbc.batch_versioned_data", "true");
        
        // Configuraciones específicas para jConnect
        properties.put("hibernate.use_sql_comments", "false");
        properties.put("hibernate.jdbc.use_scrollable_resultset", "true");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        properties.put("hibernate.jdbc.use_streams_for_binary", "true");
        
        // Cache y consultas
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.generate_statistics", "false");
        
        // Manejo de conexiones y transacciones para jConnect
        properties.put("hibernate.connection.provider_disables_autocommit", "true"); // jConnect maneja mejor el autocommit deshabilitado
        properties.put("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_HOLD");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        
        // Configuración de isolation level para Sybase con jConnect
        properties.put("hibernate.connection.isolation", "2"); // READ_COMMITTED
        
        // Configuraciones de esquema y database
        properties.put("hibernate.default_schema", getSchemaProperty());
        properties.put("hibernate.jdbc.time_zone", "UTC");
        
        // Configuración para manejo de identidad en Sybase con jConnect
        properties.put("hibernate.id.new_generator_mappings", "true"); // jConnect funciona mejor con nuevos generadores
        properties.put("hibernate.jdbc.fetch_size", "100"); // jConnect tiene mejor rendimiento con fetch size más alto
        
        // Configuración específica para tipos de datos Sybase con jConnect
        properties.put("hibernate.type.descriptor.sql.TimestampType", "UTC");
        
        // Propiedades específicas de jConnect para Hibernate
        properties.put("hibernate.sybase.jconnect.version", "6.0");
        properties.put("hibernate.sybase.use_jconnect", "true");
        
        // Aplicar propiedades de conexión específicas para Hibernate
        applyHibernateConnectionProperties(properties);
        
        // Logging (configuración por ambiente)
        configureJConnectHibernateLogging(properties);
        
        return properties;
    }
    
    /**
     * Aplica propiedades de conexión específicas de Hibernate desde las propiedades de aplicación.
     * 
     * <p>Extrae propiedades que comiencen con "hibernate." de la propiedad 
     * {@code spring.datasource.sybasej.connection-properties} y las aplica al mapa de propiedades.</p>
     * 
     * @param properties Mapa de propiedades donde se agregarán las propiedades de Hibernate
     */
    private void applyHibernateConnectionProperties(Map<String, Object> properties) {
        String connectionProperties = env.getProperty("spring.datasource.sybasej.connection-properties");
        if (connectionProperties != null && !connectionProperties.trim().isEmpty()) {
            String[] props = connectionProperties.split(";");
            for (String prop : props) {
                String[] keyValue = prop.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    // Agregar propiedades relevantes para Hibernate (que empiecen con hibernate.)
                    if (key.startsWith("hibernate.")) {
                        properties.put(key, value);
                    }
                }
            }
        }
    }
    
    /**
     * Configura el TransactionManager para Sybase jConnect.
     * 
     * <p>Este bean proporciona un {@link JpaTransactionManager} configurado con:</p>
     * <ul>
     *   <li>Transacciones anidadas habilitadas</li>
     *   <li>Timeout por defecto de 60 segundos</li>
     *   <li>Validación de transacciones existentes</li>
     *   <li>Configuración optimizada para jConnect</li>
     * </ul>
     * 
     * @return PlatformTransactionManager configurado para Sybase jConnect
     */
    @Bean
    //@Primary
    PlatformTransactionManager sybaseJTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sybasejEntityManager().getObject());
        transactionManager.setNestedTransactionAllowed(true); // jConnect soporta mejor transacciones anidadas
        transactionManager.setDefaultTimeout(60);
        
        // Configuración específica para jConnect
        transactionManager.setValidateExistingTransaction(true);
        transactionManager.setRollbackOnCommitFailure(false); // jConnect maneja mejor los commit failures
        
        return transactionManager;
    }
    
    /**
     * Obtiene una propiedad requerida del entorno.
     * 
     * @param key Clave de la propiedad a obtener
     * @return Valor de la propiedad
     * @throws IllegalStateException si la propiedad no está configurada
     */
    private String getRequiredProperty(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required property '" + key + "' is not set");
        }
        return value.trim();
    }
    
    /**
     * Obtiene la propiedad del esquema o retorna "dbo" por defecto.
     * 
     * @return Nombre del esquema a utilizar
     */
    private String getSchemaProperty() {
        String schema = env.getProperty("spring.datasource.sybasej.schema");
        return schema != null ? schema.trim() : "dbo";
    }
    
    /**
     * Configura el logging de Hibernate basado en el perfil de ejecución.
     * 
     * <p>En perfiles de desarrollo ("dev" o "development") habilita logging detallado,
     * en otros perfiles deshabilita el logging para mejor rendimiento.</p>
     * 
     * @param properties Mapa de propiedades donde se configurará el logging
     * 
     * @see #isDevelopmentProfile()
     */
    private void configureJConnectHibernateLogging(Map<String, Object> properties) {
        if (isDevelopmentProfile()) {
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");
            properties.put("hibernate.use_sql_comments", "true");
            properties.put("hibernate.type", "trace"); // Para ver los tipos de datos que maneja jConnect
        } else {
            properties.put("hibernate.show_sql", "false");
            properties.put("hibernate.format_sql", "false");
            properties.put("hibernate.use_sql_comments", "false");
        }
        
        // jConnect específico - configuraciones de debug
        properties.put("hibernate.dialect.storage_engine", "default");
    }
    
    /**
     * Verifica si el perfil activo es de desarrollo.
     * 
     * @return true si el perfil activo es "dev" o "development", false en caso contrario
     */
    private boolean isDevelopmentProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile) || "development".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
```

## Mongo Server

### File application.properties
```
# Configuracion MongoDB
spring.data.mongodb.uri=mongodb://[user_db]:[password_db]@[ip_server]:[port_server]/[database_name]?authSource=admin
spring.data.mongodb.database=[database_name]
spring.data.mongodb.auto-index-creation=true
spring.data.mongodb.write-concern=ACKNOWLEDGED
spring.data.mongodb.read-preference=PRIMARY

# Configuracion adicional para desarrollo
spring.data.mongodb.field-naming-strategy=org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy
```

### File DbMongo.java
```
package com.example.demo.app.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Configuración de base de datos para MongoDB.
 * 
 * <p>Esta clase proporciona la configuración completa para la conexión a bases de datos
 * MongoDB, incluyendo configuración del cliente MongoDB, MongoDatabaseFactory,
 * MappingMongoConverter y MongoTemplate específicamente optimizados para MongoDB.</p>
 * 
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *   <li>Configuración optimizada del pool de conexiones de MongoDB</li>
 *   <li>Manejo de timeouts de conexión y socket</li>
 *   <li>Configuración de heartbeats para monitoreo de cluster</li>
 *   <li>Eliminación del campo _class para documentos más limpios</li>
 *   <li>Soporte para réplicas y clusters de MongoDB</li>
 * </ul>
 * 
 * <p><strong>Propiedades requeridas en application.properties:</strong></p>
 * <pre>
 * spring.data.mongodb.uri=mongodb://usuario:contraseña@host:puerto/base_datos
 * spring.data.mongodb.database=nombre_base_datos
 * </pre>
 * 
 * <p><strong>Propiedades opcionales:</strong></p>
 * <pre>
 * spring.data.mongodb.auto-index-creation=true
 * spring.data.mongodb.write-concern=ACKNOWLEDGED
 * spring.data.mongodb.read-preference=PRIMARY
 * </pre>
 * 
 */
@SuppressWarnings("unused")
@Configuration
@EnableMongoRepositories(
    basePackages = "com.example.demo.app.repository.mongodb",
    mongoTemplateRef = "mongoDbTemplate"
)
public class DbMongo {

    /** 
     * Número máximo de conexiones en el pool de MongoDB 
     */
    private static final int MAX_CONNECTIONS = 100;
    
    /** 
     * Número mínimo de conexiones en el pool de MongoDB 
     */
    private static final int MIN_CONNECTIONS = 5;
    
    /** 
     * Tiempo máximo de vida de una conexión en milisegundos 
     */
    private static final long MAX_CONNECTION_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    
    /** 
     * Timeout para establecer conexión en milisegundos 
     */
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30);
    
    /** 
     * Timeout para operaciones de socket en milisegundos 
     */
    private static final long SOCKET_TIMEOUT = TimeUnit.SECONDS.toMillis(60);
    
    @Autowired
    private Environment env;
    
    /**
     * Configura y crea el cliente MongoDB.
     * 
     * <p>Este bean proporciona una instancia configurada de {@link MongoClient} 
     * con las siguientes optimizaciones:</p>
     * 
     * <ul>
     *   <li>Pool de conexiones con máximo de {@value #MAX_CONNECTIONS} conexiones</li>
     *   <li>Mínimo de {@value #MIN_CONNECTIONS} conexiones mantenidas</li>
     *   <li>Timeout de conexión de {@value #CONNECTION_TIMEOUT} ms</li>
     *   <li>Timeout de socket de {@value #SOCKET_TIMEOUT} ms</li>
     *   <li>Configuración de heartbeats para monitoreo del cluster</li>
     * </ul>
     * 
     * @return MongoClient configurado y optimizado
     * @throws IllegalStateException si las propiedades requeridas no están configuradas
     * 
     * @see #configureMongoClientSettings(String)
     * @see #getRequiredProperty(String)
     */
    @Bean
    //@Primary
    MongoClient mongoClient() {
        String connectionString = getRequiredProperty("spring.data.mongodb.uri");
        String database = getRequiredProperty("spring.data.mongodb.database");
        
        MongoClientSettings settings = configureMongoClientSettings(connectionString);
        
        return MongoClients.create(settings);
    }
    
    /**
     * Configura las opciones del cliente MongoDB.
     * 
     * <p>Esta configuración incluye optimizaciones específicas para MongoDB:</p>
     * <ul>
     *   <li>Configuración del pool de conexiones</li>
     *   <li>Timeouts de conexión y socket</li>
     *   <li>Frecuencia de heartbeats para monitoreo del cluster</li>
     *   <li>Timeout de selección de servidor</li>
     * </ul>
     * 
     * @param connectionString String de conexión a MongoDB
     * @return MongoClientSettings configurado
     */
    private MongoClientSettings configureMongoClientSettings(String connectionString) {
        ConnectionString connString = new ConnectionString(connectionString);
        
        return MongoClientSettings.builder()
                .applyConnectionString(connString)
                .applyToConnectionPoolSettings(builder -> 
                    builder.maxSize(MAX_CONNECTIONS)
                           .minSize(MIN_CONNECTIONS)
                           .maxConnectionLifeTime(MAX_CONNECTION_LIFETIME, TimeUnit.MILLISECONDS)
                           .maxWaitTime(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                )
                .applyToSocketSettings(builder ->
                    builder.connectTimeout((int) CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                           .readTimeout((int) SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                )
                .applyToServerSettings(builder ->
                    builder.heartbeatFrequency(10000, TimeUnit.MILLISECONDS) // 10 segundos
                           .minHeartbeatFrequency(500, TimeUnit.MILLISECONDS)
                )
                .applyToClusterSettings(builder ->
                    builder.serverSelectionTimeout(30000, TimeUnit.MILLISECONDS) // 30 segundos
                )
                .build();
    }
    
    /**
     * Configura la fábrica de bases de datos MongoDB.
     * 
     * <p>Este bean proporciona un {@link MongoDatabaseFactory} que se utiliza
     * para crear conexiones a la base de datos MongoDB especificada.</p>
     * 
     * @return MongoDatabaseFactory configurado
     */
    @Bean
    //@Primary
    MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), 
                getRequiredProperty("spring.data.mongodb.database"));
    }
    
    /**
     * Configura el conversor de mapeo para MongoDB.
     * 
     * <p>Este bean proporciona un {@link MappingMongoConverter} configurado para:</p>
     * <ul>
     *   <li>Eliminar el campo _class de los documentos (documentos más limpios)</li>
     *   <li>Utilizar un resolver de referencias de base de datos</li>
     *   <li>Mapear entidades Java a documentos MongoDB</li>
     * </ul>
     * 
     * @return MappingMongoConverter configurado
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    //@Primary
    MappingMongoConverter mongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        
        // Remover _class field para documentos más limpios
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return converter;
    }
    
    /**
     * Configura el template principal de MongoDB.
     * 
     * <p>Este bean está marcado para ser usado por los repositorios MongoDB y proporciona
     * un {@link MongoTemplate} configurado con:</p>
     * 
     * <ul>
     *   <li>La fábrica de base de datos configurada</li>
     *   <li>El conversor de mapeo optimizado</li>
     *   <li>Configuraciones adicionales específicas de la aplicación</li>
     * </ul>
     * 
     * @return MongoTemplate configurado y listo para usar
     * @throws Exception si ocurre un error durante la configuración
     * 
     * @see #configureMongoTemplate(MongoTemplate)
     */
    @Bean
    //@Primary
    MongoTemplate mongoDbTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory(), mongoConverter());
        
        // Configuraciones adicionales del template
        configureMongoTemplate(mongoTemplate);
        
        return mongoTemplate;
    }
    
    /**
     * Aplica configuraciones adicionales al MongoTemplate.
     * 
     * <p>Este método puede ser extendido para aplicar configuraciones específicas
     * del template como políticas de escritura, lecturas, timeouts, etc.</p>
     * 
     * @param mongoTemplate Template a configurar
     */
    private void configureMongoTemplate(MongoTemplate mongoTemplate) {
        Map<String, Object> properties = mongoHibernateProperties();
        
        // Aquí puedes configurar propiedades específicas del template si es necesario
        // Por ejemplo, configuraciones de escritura, lectura, etc.
        
        // Ejemplo de configuración adicional:
        // mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        // mongoTemplate.setReadPreference(ReadPreference.primaryPreferred());
    }
    
    /**
     * Define las propiedades específicas para MongoDB.
     * 
     * <p>Incluye configuraciones para:</p>
     * <ul>
     *   <li>Creación automática de índices</li>
     *   <li>Timeouts de conexión y socket</li>
     *   <li>Políticas de escritura (Write Concern)</li>
     *   <li>Preferencias de lectura (Read Preference)</li>
     * </ul>
     * 
     * @return Mapa de propiedades específicas de MongoDB
     */
    private Map<String, Object> mongoHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // Configuraciones específicas de MongoDB
        properties.put("mongodb.auto.index.creation", 
            env.getProperty("spring.data.mongodb.auto-index-creation", "true"));
        
        // Configuración de timeouts
        properties.put("mongodb.socket.timeout", SOCKET_TIMEOUT);
        properties.put("mongodb.connect.timeout", CONNECTION_TIMEOUT);
        
        // Configuración de escritura
        properties.put("mongodb.write.concern", 
            env.getProperty("spring.data.mongodb.write-concern", "ACKNOWLEDGED"));
        
        // Configuración de lectura
        properties.put("mongodb.read.preference", 
            env.getProperty("spring.data.mongodb.read-preference", "PRIMARY"));
        
        return properties;
    }
    
    /**
     * Obtiene una propiedad requerida del entorno.
     * 
     * @param key Clave de la propiedad a obtener
     * @return Valor de la propiedad
     * @throws IllegalStateException si la propiedad no está configurada
     */
    private String getRequiredProperty(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required property '" + key + "' is not set");
        }
        return value.trim();
    }
    
    /**
     * Verifica si el perfil activo es de desarrollo.
     * 
     * @return true si el perfil activo es "dev" o "development", false en caso contrario
     */
    private boolean isDevelopmentProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile) || "development".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
```














