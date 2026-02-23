# Configuración de conección de PostgreSQL

````
# Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/[DB]
spring.datasource.username=[user_db]
spring.datasource.password=[password_db]
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

# JPA / Hibernate
# Configuración de Hibernate para la generación automática del esquema de la base de datos
spring.jpa.hibernate.ddl-auto=update
# Mostrar las consultas SQL en la consola (false para PRODUCCIÓN)
spring.jpa.show-sql=true
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

## COMPATIBILIDAD DE TIPOS DE DATOS

### 📌 1️⃣ Tipos Numéricos
```
------------------------------------------------------------------------------------------
| PostgreSQL         | Java (estándar) | Java (JPA recomendado) | Notas                  |
| ------------------ | --------------- | ---------------------- | ---------------------- |
| `smallint`         | `short`         | `Short`                | 2 bytes                |
| `integer`          | `int`           | `Integer`              | Más común              |
| `bigint`           | `long`          | `Long`                 | IDs grandes            |
| `decimal`          | `BigDecimal`    | `BigDecimal`           | Precisión exacta       |
| `numeric`          | `BigDecimal`    | `BigDecimal`           | Igual que decimal      |
| `real`             | `float`         | `Float`                | 4 bytes                |
| `double precision` | `double`        | `Double`               | 8 bytes                |
| `serial`           | `int`           | `Integer`              | Auto incremento        |
| `bigserial`        | `long`          | `Long`                 | Auto incremento grande |
------------------------------------------------------------------------------------------
```

### 📌 2️⃣ Tipos de Texto
```
-------------------------------------------------
| PostgreSQL   | Java     | Notas               |
| ------------ | -------- | ------------------- |
| `char(n)`    | `String` | Longitud fija       |
| `varchar(n)` | `String` | Más común           |
| `text`       | `String` | Sin límite práctico |
| `citext`     | `String` | Case insensitive    |
-------------------------------------------------
```

### 📌 3️⃣ Tipos Booleanos
```
--------------------------------------
| PostgreSQL | Java                  |
| ---------- | --------------------- |
| `boolean`  | `boolean` / `Boolean` |
--------------------------------------
```

### 📌 4️⃣ Fecha y Hora (Muy Importante 🚨)
```
----------------------------------------------------------------------------
| PostgreSQL    | Java moderno (Recomendado)         | Alternativa antigua |
| ------------- | ---------------------------------- | ------------------- |
| `date`        | `LocalDate`                        | `java.sql.Date`     |
| `time`        | `LocalTime`                        | `java.sql.Time`     |
| `timestamp`   | `LocalDateTime`                    | `Timestamp`         |
| `timestamptz` | `OffsetDateTime` / `ZonedDateTime` | `Timestamp`         |
| `interval`    | `Duration` / `Period`              | —                   |
----------------------------------------------------------------------------
```
👉 Recomendación para microservicios modernos:
Usa siempre java.time.* (Java 8+) y evita java.util.Date.

### 📌 5️⃣ Tipos Binarios
```
-------------------------
| PostgreSQL | Java     |
| ---------- | -------- |
| `bytea`    | `byte[]` |
-------------------------
```

### 📌 7️⃣ UUID
```
-----------------------
| PostgreSQL | Java   |
| ---------- | ------ |
| `uuid`     | `UUID` |
-----------------------
```
Muy recomendado para sistemas multi-cliente como el que estás desarrollando 👌

### 📌 8️⃣ Arreglos
```
-----------------------------
| PostgreSQL  | Java        |
| ----------- | ----------- |
| `integer[]` | `Integer[]` |
| `text[]`    | `String[]`  |
| `uuid[]`    | `UUID[]`    |
-----------------------------
```

### 📌 9️⃣ Enumeraciones
```
-----------------------
| PostgreSQL | Java   |
| ---------- | ------ |
| `enum`     | `enum` |
-----------------------
```
Ejemplo:
SQL
```
CREATE TYPE estado AS ENUM ('ACTIVO', 'INACTIVO');
```
JAVA
```
public enum Estado {
    ACTIVO,
    INACTIVO
}
```

### 📌 🔟 Tipos Especiales
```
-----------------------------
| PostgreSQL | Java         |
| ---------- | ------------ |
| `money`    | `BigDecimal` |
| `inet`     | `String`     |
| `cidr`     | `String`     |
| `macaddr`  | `String`     |
| `xml`      | `String`     |
| `tsvector` | `String`     |
-----------------------------
```

## 🔥 Recomendación para tu arquitectura limpia en microservicios
En tus Entities (infraestructura) usa:

- Integer, Long (no primitivos)
- BigDecimal para dinero
- UUID para IDs en sistemas multi-tenant
- LocalDateTime para auditoría
- jsonb si tienes módulos dinámicos
- Si quieres, puedo darte ahora:

✅ Tabla optimizada para JPA
✅ Ejemplo completo de entidad Producto
✅ Buenas prácticas para PostgreSQL en sistemas SaaS multi-cliente
✅ Conversión específica usando Spring Data + Hibernate
