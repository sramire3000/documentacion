# Multiple DataBase Conection JDK 21 Spring WebFlux

- SqlServer 2019
- PostgresSQL
- Mysql
- Sybase JTDS
- Sybase JConect
- Mongo DB

## File pom.xml
```
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
