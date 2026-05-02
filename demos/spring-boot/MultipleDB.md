# Multiple Base de datos

## Paquetes
```
com.example.demo_multi_db
|- domain
|- application
|- bootstrap
|  |- DemoMultiDbApplication.java
|- infrastructure
|  |- persistence
|  |  |- sqlserver
|  |  |  |- entity
|  |  |  |- repository
|  |  |  `- adapter
|  |  `- postgres
|  |     |- entity
|  |     |- repository
|  |     `- adapter
|  `- web
|- resources
`- shared
```

## Archivo "pom.xml"
```
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

		<!-- MySQL Driver -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- Sybase Driver (jTDS) -->
		<dependency>
			<groupId>net.sourceforge.jtds</groupId>
			<artifactId>jtds</artifactId>
			<version>1.3.1</version>
			<scope>runtime</scope>
		</dependency>

		<!-- Community dialects for Sybase -->
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-community-dialects</artifactId>
		</dependency>

		<!-- DATA JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
```

## Archuvo "application.properties"
```
# =============================================
# JASYPT
# =============================================
# Algoritmo de encriptación
jasypt.encryptor.algorithm=PBEWITHHMACSHA512ANDAES_256
# Generador de vector de inicialización (IV) aleatorio
jasypt.encryptor.iv-generator-classname=org.jasypt.iv.RandomIvGenerator

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
spring.jpa.open-in-view=false

# =============================================
# DATASOURCE - SQL Server 2017
# =============================================
app.jpa.sqlserver.jdbc-url=jdbc:sqlserver://host.docker.internal:1433;databaseName=Arreconsa;encrypt=false;trustServerCertificate=true
app.jpa.sqlserver.username=ENC(SynWXXW3P48VAglqBzbH2fwo0EJxn31rHVGYqlhV+pA/oE6//IR6FRZjnuytc5RV)
app.jpa.sqlserver.password=ENC(zqdtOH9UuSjQvFrU2LQmE/qjRPglvRSNgMAV2xWMGPB5ik9FTyRVtKMeCbDnSgKR)
app.jpa.sqlserver.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
app.jpa.sqlserver.minimum-idle=2
app.jpa.sqlserver.maximum-pool-size=10
app.jpa.sqlserver.connection-timeout=30000
app.jpa.sqlserver.validation-timeout=5000
app.jpa.sqlserver.idle-timeout=600000
app.jpa.sqlserver.max-lifetime=1800000
app.jpa.sqlserver.keepalive-time=300000
app.jpa.sqlserver.pool-name=SqlServerPool
app.jpa.sqlserver.hibernate.ddl-auto=none
app.jpa.sqlserver.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
app.jpa.sqlserver.show-sql=true
app.jpa.sqlserver.format-sql=true

# =============================================
# DATASOURCE - PostgreSQL
# =============================================
app.jpa.postgres.jdbc-url=jdbc:postgresql://host.docker.internal:5432/Arreconsa
app.jpa.postgres.username=ENC(SynWXXW3P48VAglqBzbH2fwo0EJxn31rHVGYqlhV+pA/oE6//IR6FRZjnuytc5RV)
app.jpa.postgres.password=ENC(zqdtOH9UuSjQvFrU2LQmE/qjRPglvRSNgMAV2xWMGPB5ik9FTyRVtKMeCbDnSgKR)
app.jpa.postgres.driver-class-name=org.postgresql.Driver
app.jpa.postgres.minimum-idle=2
app.jpa.postgres.maximum-pool-size=10
app.jpa.postgres.connection-timeout=30000
app.jpa.postgres.validation-timeout=5000
app.jpa.postgres.idle-timeout=600000
app.jpa.postgres.max-lifetime=1800000
app.jpa.postgres.keepalive-time=300000
app.jpa.postgres.pool-name=PostgresPool
app.jpa.postgres.hibernate.ddl-auto=none
app.jpa.postgres.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
app.jpa.postgres.show-sql=true
app.jpa.postgres.format-sql=true

# =============================================
# DATASOURCE - Mysql
# =============================================
app.jpa.mysql.jdbc-url=jdbc:mysql://host.docker.internal:3307/Arreconsa?allowPublicKeyRetrieval=true&useSSL=false
app.jpa.mysql.username=ENC(SynWXXW3P48VAglqBzbH2fwo0EJxn31rHVGYqlhV+pA/oE6//IR6FRZjnuytc5RV)
app.jpa.mysql.password=ENC(zqdtOH9UuSjQvFrU2LQmE/qjRPglvRSNgMAV2xWMGPB5ik9FTyRVtKMeCbDnSgKR)
app.jpa.mysql.driver-class-name=com.mysql.cj.jdbc.Driver
app.jpa.mysql.minimum-idle=2
app.jpa.mysql.maximum-pool-size=10
app.jpa.mysql.connection-timeout=30000
app.jpa.mysql.validation-timeout=5000
app.jpa.mysql.idle-timeout=600000
app.jpa.mysql.max-lifetime=1800000
app.jpa.mysql.keepalive-time=300000
app.jpa.mysql.pool-name=MySqlPool
app.jpa.mysql.hibernate.ddl-auto=none
app.jpa.mysql.hibernate.dialect=org.hibernate.dialect.MySQLDialect
app.jpa.mysql.show-sql=true
app.jpa.mysql.format-sql=true

# =============================================
# DATASOURCE - Sybase
# =============================================
app.jpa.sybase.jdbc-url=jdbc:jtds:sybase://host.docker.internal:5000/master
app.jpa.sybase.username=ENC(Pd8UFqx/6VwQZzU4spIcxrvbXKt3IwZ74el9hyVV1nQjKMGbsuDbRkfCvHgVhNgC)
app.jpa.sybase.password=ENC(XLE/HFJ8GkJ7CkVpms9Ghatj3PlxbmxK2etWV+LvE1jU3dY2GtZhy0G31ooWSg/Y)
app.jpa.sybase.driver-class-name=net.sourceforge.jtds.jdbc.Driver
app.jpa.sybase.connection-test-query=SELECT 1
app.jpa.sybase.minimum-idle=2
app.jpa.sybase.maximum-pool-size=10
app.jpa.sybase.connection-timeout=30000
app.jpa.sybase.validation-timeout=5000
app.jpa.sybase.idle-timeout=600000
app.jpa.sybase.max-lifetime=1800000
app.jpa.sybase.keepalive-time=300000
app.jpa.sybase.pool-name=SybasePool
app.jpa.sybase.hibernate.ddl-auto=none
app.jpa.sybase.hibernate.dialect=org.hibernate.community.dialect.SybaseASELegacyDialect
app.jpa.sybase.show-sql=true
app.jpa.sybase.format-sql=true
```

## Congiguraciones del DataSources JAVA

### Archivo "MySqlJpaConfig.java"
```
package com.example.demo_multi_db.infrastructure.persistence.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo_multi_db.infrastructure.persistence.mysql.repository", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager")
public class MySqlJpaConfig {

  private final Environment environment;

  public MySqlJpaConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean(name = "mysqlDataSource")
  @ConfigurationProperties(prefix = "app.jpa.mysql")
  public DataSource mysqlDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = "mysqlEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource) {

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(mysqlDataSource);
    factoryBean.setPackagesToScan("com.example.demo_multi_db.infrastructure.persistence.mysql.entity");
    factoryBean.setPersistenceUnitName("mysqlPersistenceUnit");
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaPropertyMap(buildJpaProperties("app.jpa.mysql"));
    return factoryBean;
  }

  @Bean(name = "mysqlTransactionManager")
  public PlatformTransactionManager mysqlTransactionManager(
      @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEmf) {
    return new JpaTransactionManager(mysqlEmf.getObject());
  }

  private Map<String, Object> buildJpaProperties(String prefix) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty(prefix + ".hibernate.ddl-auto", "none"));
    properties.put("hibernate.dialect", environment.getProperty(prefix + ".hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getProperty(prefix + ".show-sql", "true"));
    properties.put("hibernate.format_sql", environment.getProperty(prefix + ".format-sql", "true"));
    return properties;
  }
}
```

### Archivo "PostgresJpaConfig.java"
```
package com.example.demo_multi_db.infrastructure.persistence.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo_multi_db.infrastructure.persistence.postgres.repository", entityManagerFactoryRef = "postgresEntityManagerFactory", transactionManagerRef = "postgresTransactionManager")
public class PostgresJpaConfig {

  private final Environment environment;

  public PostgresJpaConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean(name = "postgresDataSource")
  @ConfigurationProperties(prefix = "app.jpa.postgres")
  public DataSource postgresDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = "postgresEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
      @Qualifier("postgresDataSource") DataSource postgresDataSource) {

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(postgresDataSource);
    factoryBean.setPackagesToScan("com.example.demo_multi_db.infrastructure.persistence.postgres.entity");
    factoryBean.setPersistenceUnitName("postgresPersistenceUnit");
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaPropertyMap(buildJpaProperties("app.jpa.postgres"));
    return factoryBean;
  }

  @Bean(name = "postgresTransactionManager")
  public PlatformTransactionManager postgresTransactionManager(
      @Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgresEmf) {
    return new JpaTransactionManager(postgresEmf.getObject());
  }

  private Map<String, Object> buildJpaProperties(String prefix) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty(prefix + ".hibernate.ddl-auto", "none"));
    properties.put("hibernate.dialect", environment.getProperty(prefix + ".hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getProperty(prefix + ".show-sql", "true"));
    properties.put("hibernate.format_sql", environment.getProperty(prefix + ".format-sql", "true"));
    return properties;
  }
}
```

### Archivo "SqlServerJpaConfig.java"
```
package com.example.demo_multi_db.infrastructure.persistence.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.demo_multi_db.infrastructure.persistence.sqlserver.repository", entityManagerFactoryRef = "sqlServerEntityManagerFactory", transactionManagerRef = "sqlServerTransactionManager")
public class SqlServerJpaConfig {

  private final Environment environment;

  public SqlServerJpaConfig(Environment environment) {
    this.environment = environment;
  }

  @Primary
  @Bean(name = "sqlServerDataSource")
  @ConfigurationProperties(prefix = "app.jpa.sqlserver")
  public DataSource sqlServerDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Primary
  @Bean(name = "sqlServerEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean sqlServerEntityManagerFactory(
      @Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) {

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(sqlServerDataSource);
    factoryBean.setPackagesToScan("com.example.demo_multi_db.infrastructure.persistence.sqlserver.entity");
    factoryBean.setPersistenceUnitName("sqlServerPersistenceUnit");
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaPropertyMap(buildJpaProperties("app.jpa.sqlserver"));
    return factoryBean;
  }

  @Primary
  @Bean(name = "sqlServerTransactionManager")
  public PlatformTransactionManager sqlServerTransactionManager(
      @Qualifier("sqlServerEntityManagerFactory") LocalContainerEntityManagerFactoryBean sqlServerEmf) {
    return new JpaTransactionManager(sqlServerEmf.getObject());
  }

  private Map<String, Object> buildJpaProperties(String prefix) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty(prefix + ".hibernate.ddl-auto", "none"));
    properties.put("hibernate.dialect", environment.getProperty(prefix + ".hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getProperty(prefix + ".show-sql", "true"));
    properties.put("hibernate.format_sql", environment.getProperty(prefix + ".format-sql", "true"));
    return properties;
  }
}
```

### Archivo "SybaseJpaConfig.java"
```
package com.example.demo_multi_db.infrastructure.persistence.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo_multi_db.infrastructure.persistence.sybase.repository", entityManagerFactoryRef = "sybaseEntityManagerFactory", transactionManagerRef = "sybaseTransactionManager")
public class SybaseJpaConfig {

  private final Environment environment;

  public SybaseJpaConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean(name = "sybaseDataSource")
  @ConfigurationProperties(prefix = "app.jpa.sybase")
  public HikariDataSource sybaseDataSource() {
    HikariDataSource ds = DataSourceBuilder.create().type(HikariDataSource.class).build();
    // jTDS 1.3.1 implementa JDBC 3.0 (no JDBC 4.0), por lo que HikariCP no puede
    // usar Connection.isValid(). Se fuerza una query SQL para validar conexiones.
    if (ds.getConnectionTestQuery() == null) {
      ds.setConnectionTestQuery("SELECT 1");
    }
    return ds;
  }

  @Bean(name = "sybaseEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean sybaseEntityManagerFactory(
      @Qualifier("sybaseDataSource") DataSource sybaseDataSource) {

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(sybaseDataSource);
    factoryBean.setPackagesToScan("com.example.demo_multi_db.infrastructure.persistence.sybase.entity");
    factoryBean.setPersistenceUnitName("sybasePersistenceUnit");
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaPropertyMap(buildJpaProperties("app.jpa.sybase"));
    return factoryBean;
  }

  @Bean(name = "sybaseTransactionManager")
  public PlatformTransactionManager sybaseTransactionManager(
      @Qualifier("sybaseEntityManagerFactory") LocalContainerEntityManagerFactoryBean sybaseEmf) {
    return new JpaTransactionManager(sybaseEmf.getObject());
  }

  private Map<String, Object> buildJpaProperties(String prefix) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty(prefix + ".hibernate.ddl-auto", "none"));
    properties.put("hibernate.dialect", environment.getProperty(prefix + ".hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getProperty(prefix + ".show-sql", "true"));
    properties.put("hibernate.format_sql", environment.getProperty(prefix + ".format-sql", "true"));
    return properties;
  }
}
```

## NOTA: Si solo hay una configuracion de base datos colocar @Primary a cada metodo y si son mas solo una configuracion puede llevar @Primary

