# Configuración de conección de PostgreSQL

````
# Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/[DB]
spring.datasource.username=[user_db]
spring.datasource.password=[password_db]
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de HikariCP para la conexión a la base de datos
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

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
# Mostrar las consultas SQL en la consola (opcional, para desarrollo)
spring.jpa.show-sql=false
# Formatear las consultas SQL para una mejor legibilidad (opcional)
spring.jpa.properties.hibernate.format_sql=true
# Configurar el dialecto de Hibernate para PostgreSQL
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
# Deshabilitar Open Session in View para evitar problemas de rendimiento y memoria
spring.jpa.open-in-view=false
# Configuración de batch para mejorar el rendimiento en operaciones masivas
spring.jpa.properties.hibernate.jdbc.batch_size=30
# Configurar el orden de las operaciones de inserción y actualización para optimizar el rendimiento
spring.jpa.properties.hibernate.order_inserts=true
# Configurar el orden de las operaciones de actualización para optimizar el rendimiento
spring.jpa.properties.hibernate.order_updates=true

````
