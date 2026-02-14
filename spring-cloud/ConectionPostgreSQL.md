# Configuración de conección de PostgreSQL

````
#Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/JHTechnologiesSV
spring.datasource.username=usr_jhtechnologies
spring.datasource.password=91407AFC5B
spring.datasource.driver-class-name=org.postgresql.Driver

# Número mínimo de conexiones en el pool
spring.datasource.hikari.minimum-idle=10
# Número máximo de conexiones en el pool
spring.datasource.hikari.maximum-pool-size=10
# Tiempo máximo para obtener una conexión del pool (en milisegundos)
spring.datasource.hikari.connection-timeout=30000
# Tiempo máximo de inactividad de una conexión en el pool (en milisegundos)
spring.datasource.hikari.idle-timeout=600000
# Tiempo máximo de vida de una conexión en el pool (en milisegundos)
spring.datasource.hikari.max-lifetime=1800000

#JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

````
