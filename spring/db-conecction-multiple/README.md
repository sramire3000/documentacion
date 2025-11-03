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
spring.datasource.sqlserver.url=jdbc:sqlserver://[ip_server]:[port_server];databaseName=migration;encrypt=false
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

## Postgress Server

## Mysql Server

## Mongo Server




