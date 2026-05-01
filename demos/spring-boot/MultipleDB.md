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

		<!-- JASYPT Encriptacion para Usuario/Clave en propertie de DB -->
		<dependency>
			<groupId>com.github.ulisesbocchio</groupId>
			<artifactId>jasypt-spring-boot-starter</artifactId>
			<version>3.0.5</version>
		</dependency>

		<!-- DATA JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<!-- PostgreSQL Driver -->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- SQL Server Driver (mssql-jdbc) -->
		<dependency>
			<groupId>com.microsoft.sqlserver</groupId>
			<artifactId>mssql-jdbc</artifactId>
			<scope>runtime</scope>
		</dependency>
```

## Archuvo "application.properties"
```
# =============================================
# JPA (Si se usa)
# =============================================
spring.jpa.defer-datasource-initialization=true
spring.jpa.open-in-view=false

# =============================================
# DATASOURCE - SQL Server 2017
# =============================================
app.jpa.sqlserver.jdbc-url=jdbc:sqlserver://192.168.0.8:1433;databaseName=Arreconsa;encrypt=false;trustServerCertificate=true
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
app.jpa.postgres.jdbc-url=jdbc:postgresql://192.168.0.8:5432/Arreconsa
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
```

## Archivo "SqlServerJpaConfig.java"
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

## Archivo "PostgresJpaConfig.java"
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


