# Drivers de Bases de Datos - Documentación Completa

## Tabla de Contenidos

1. [Visión General](#visión-general)
2. [SQL Server](#sql-server)
3. [Sybase ASE](#sybase-ase)
4. [MySQL](#mysql)
5. [PostgreSQL](#postgresql)
6. [MongoDB](#mongodb)
7. [Instalación Multiplataforma](#instalación-multiplataforma)
8. [Ejemplos Completos](#ejemplos-completos)
9. [Troubleshooting](#troubleshooting)

---

## Visión General

Este proyecto soporta **5 bases de datos diferentes** con drivers puros Go que funcionan en Windows, Linux y macOS:

| Base de Datos | Driver | Versión | Puerto Default | Puro Go |
|---|---|---|---|---|
| SQL Server | `go-mssqldb` | Latest | 1433 | ✅ |
| Sybase ASE | `thda/tds` | v0.1.7 | 5000 | ✅ |
| MySQL | `go-sql-driver/mysql` | Latest | 3306 | ✅ |
| PostgreSQL | `lib/pq` | Latest | 5432 | ✅ |
| MongoDB | `mongo-driver` | Latest | 27017 | ✅ |

---

# SQL Server

## Información General

- **Nombre**: Microsoft SQL Server Driver
- **Driver Go**: `github.com/denisenkom/go-mssqldb`
- **Repositorio**: https://github.com/denisenkom/go-mssqldb
- **Licencia**: BSD-3-Clause
- **Compatibilidad**: SQL Server 2005+
- **Plataformas**: Windows, Linux, macOS
- **Go mínimo**: 1.8+

## Instalación

```bash
go get github.com/denisenkom/go-mssqldb
```

## Cadena de Conexión

### Formato General
```
server=hostname;port=1433;user id=username;password=password;database=dbname
```

### Ejemplo
```go
cnxStr := fmt.Sprintf("server=%s;port=%d;user id=%s;password=%s;database=%s",
    "localhost", 1433, "sa", "Password123", "master")
db, _ := sql.Open("sqlserver", cnxStr)
```

## Parámetros Comunes

| Parámetro | Valor | Descripción |
|---|---|---|
| `server` | hostname | Nombre del servidor |
| `port` | 1433 | Puerto SQL Server |
| `user id` | username | Usuario de acceso |
| `password` | password | Contraseña |
| `database` | dbname | Base de datos inicial |
| `encrypt` | disable, true, false | Control SSL/TLS |
| `trustServerCertificate` | true, false | Confiar certificado servidor |
| `connection timeout` | segundos | Timeout de conexión |

## Ejemplo de Uso

```go
package main

import (
    "database/sql"
    "log"
    _ "github.com/denisenkom/go-mssqldb"
)

func main() {
    cnxStr := "server=192.168.1.100;port=1433;user id=sa;password=MyPassword;database=AdventureWorks"
    
    db, err := sql.Open("sqlserver", cnxStr)
    if err != nil {
        log.Fatal(err)
    }
    defer db.Close()

    // Verificar conexión
    if err := db.Ping(); err != nil {
        log.Fatal(err)
    }
    
    // Ejecutar consulta
    rows, _ := db.Query("SELECT TOP 5 * FROM Person.Person")
    defer rows.Close()
    
    for rows.Next() {
        var id int
        var name string
        rows.Scan(&id, &name)
        log.Printf("ID: %d, Name: %s\n", id, name)
    }
}
```

---

# Sybase ASE

## Información General

- **Nombre**: SAP Sybase Adaptive Server Enterprise
- **Driver Go**: `github.com/thda/tds`
- **Versión Recomendada**: v0.1.7
- **Repositorio**: https://github.com/thda/tds
- **Licencia**: BSD-3-Clause
- **Compatibilidad**: Sybase ASE 12.5+, IQ, RS
- **Plataformas**: Windows, Linux, macOS ✅ **MULTIPLATAFORMA**
- **Go mínimo**: 1.8+

## Instalación

```bash
go get github.com/thda/tds@v0.1.7
```

## Cadena de Conexión

### Formato General
```
tds://username:password@host:port/database?parameters
```

### Ejemplo - RECOMENDADO (Sybase Antiguo)
```go
cnxStr := "tds://hr19234:Cusca2024@172.31.4.34:4100/bc_regional?encryptPassword=no&readTimeout=30&writeTimeout=30"
db, _ := sql.Open("tds", cnxStr)
```

## Parámetros Críticos para Sybase Antiguo

⚠️ **IMPORTANTE**: Estas opciones son necesarias para Sybase ASE < 15.5

| Parámetro | Valor | Descripción |
|---|---|---|
| `encryptPassword` | `no` | **CRÍTICO**: Desabilita encriptación RSA (no soportada en ASE < 15.5) |
| `readTimeout` | 30 | Timeout lectura en segundos |
| `writeTimeout` | 30 | Timeout escritura en segundos |

## Parámetros Opcionales

| Parámetro | Valor | Descripción |
|---|---|---|
| `charset` | utf8, latin1 | Codificación de caracteres (default: utf8) |
| `textSize` | 4096-2147483647 | Máximo tamaño de campos TEXT/IMAGE (bytes) |
| `applicationName` | string | Nombre de la aplicación (auditoría) |
| `tls-enable` | true, false | Forzar TLS |
| `packetSize` | bytes | Tamaño de paquete TDS |

## Ejemplo de Uso

```go
package main

import (
    "database/sql"
    "fmt"
    "log"
    _ "github.com/thda/tds"
)

func main() {
    // NOTA: encryptPassword=no es CRÍTICO para Sybase ASE < 15.5
    cnxStr := fmt.Sprintf(
        "tds://%s:%s@%s:%d/%s?encryptPassword=no&readTimeout=30&writeTimeout=30",
        "hr19234",
        "Cusca2024",
        "172.31.4.34",
        4100,
        "bc_regional",
    )
    
    db, err := sql.Open("tds", cnxStr)
    if err != nil {
        log.Fatal(err)
    }
    defer db.Close()

    // Verificar conexión
    if err := db.Ping(); err != nil {
        log.Fatal(err)
    }

    // Consultar tablas
    rows, _ := db.Query(`
        SELECT name FROM sysobjects 
        WHERE type = 'U' 
        ORDER BY name
    `)
    defer rows.Close()

    for rows.Next() {
        var tableName string
        rows.Scan(&tableName)
        log.Println("Tabla:", tableName)
    }
}
```

---

# MySQL

## Información General

- **Nombre**: MySQL Database
- **Driver Go**: `github.com/go-sql-driver/mysql`
- **Repositorio**: https://github.com/go-sql-driver/mysql
- **Licencia**: Mozilla Public License 2.0
- **Compatibilidad**: MySQL 5.7+, MariaDB 10.2+
- **Plataformas**: Windows, Linux, macOS
- **Go mínimo**: 1.10+

## Instalación

```bash
go get github.com/go-sql-driver/mysql
```

## Cadena de Conexión

### Formato General
```
username:password@tcp(host:port)/database?parameters
```

### Ejemplo Básico
```go
cnxStr := "root:password@tcp(localhost:3306)/mydb"
db, _ := sql.Open("mysql", cnxStr)
```

### Ejemplo con Parámetros
```go
cnxStr := "root:password@tcp(localhost:3306)/mydb?charset=utf8mb4&parseTime=true&loc=Local"
db, _ := sql.Open("mysql", cnxStr)
```

## Parámetros Comunes

| Parámetro | Valor | Descripción |
|---|---|---|
| `charset` | utf8mb4, utf8 | Codificación (recomendado: utf8mb4) |
| `parseTime` | true, false | Parsear TIME/DATETIME como time.Time |
| `loc` | Local, UTC | Zona horaria (Local recomendado) |
| `timeout` | Ej: 10s | Timeout de conexión |
| `maxAllowedPacket` | bytes | Tamaño máximo de paquete |

## Ejemplo de Uso

```go
package main

import (
    "database/sql"
    "log"
    _ "github.com/go-sql-driver/mysql"
)

func main() {
    cnxStr := "root:password@tcp(192.168.1.100:3306)/employees?charset=utf8mb4&parseTime=true"
    
    db, err := sql.Open("mysql", cnxStr)
    if err != nil {
        log.Fatal(err)
    }
    defer db.Close()

    // Verificar conexión
    if err := db.Ping(); err != nil {
        log.Fatal(err)
    }

    // Consultar
    rows, _ := db.Query("SELECT id, name FROM employees LIMIT 5")
    defer rows.Close()

    for rows.Next() {
        var id int
        var name string
        rows.Scan(&id, &name)
        log.Printf("ID: %d, Name: %s\n", id, name)
    }
}
```

---

# PostgreSQL

## Información General

- **Nombre**: PostgreSQL Database
- **Driver Go**: `github.com/lib/pq`
- **Repositorio**: https://github.com/lib/pq
- **Licencia**: BSD-2-Clause
- **Compatibilidad**: PostgreSQL 8.2+
- **Plataformas**: Windows, Linux, macOS
- **Go mínimo**: 1.1+

## Instalación

```bash
go get github.com/lib/pq
```

## Cadena de Conexión

### Formato General
```
host=hostname port=5432 user=username password=password dbname=database sslmode=disable
```

### Ejemplo Básico
```go
cnxStr := "host=localhost port=5432 user=postgres password=mypass dbname=mydb sslmode=disable"
db, _ := sql.Open("postgres", cnxStr)
```

### Ejemplo URL
```go
cnxStr := "postgres://username:password@localhost:5432/database?sslmode=disable"
db, _ := sql.Open("postgres", cnxStr)
```

## Parámetros Comunes

| Parámetro | Valor | Descripción |
|---|---|---|
| `host` | hostname | Servidor PostgreSQL |
| `port` | 5432 | Puerto (default: 5432) |
| `user` | username | Usuario de acceso |
| `password` | password | Contraseña |
| `dbname` | database | Base de datos inicial |
| `sslmode` | disable, require, prefer, verify-ca, verify-full | Modo SSL/TLS |
| `connect_timeout` | segundos | Timeout de conexión |
| `search_path` | schema | Schema a usar (default: public) |

## Ejemplo de Uso

```go
package main

import (
    "database/sql"
    "log"
    _ "github.com/lib/pq"
)

func main() {
    cnxStr := "host=192.168.1.100 port=5432 user=postgres password=mypass dbname=company sslmode=disable"
    
    db, err := sql.Open("postgres", cnxStr)
    if err != nil {
        log.Fatal(err)
    }
    defer db.Close()

    // Verificar conexión
    if err := db.Ping(); err != nil {
        log.Fatal(err)
    }

    // Consultar
    rows, _ := db.Query("SELECT id, name FROM users LIMIT 10")
    defer rows.Close()

    for rows.Next() {
        var id int
        var name string
        rows.Scan(&id, &name)
        log.Printf("ID: %d, Name: %s\n", id, name)
    }
}
```

---

# MongoDB

## Información General

- **Nombre**: MongoDB NoSQL Database
- **Driver Go**: `go.mongodb.org/mongo-driver`
- **Repositorio**: https://github.com/mongodb/mongo-go-driver
- **Licencia**: Apache License 2.0
- **Compatibilidad**: MongoDB 2.6+
- **Plataformas**: Windows, Linux, macOS
- **Go mínimo**: 1.10+

## Instalación

```bash
go get go.mongodb.org/mongo-driver/mongo
go get go.mongodb.org/mongo-driver/mongo/options
```

## Cadena de Conexión

### Formato General
```
mongodb://username:password@host:port/database?parameters
mongodb+srv://username:password@cluster.mongodb.net/database?parameters
```

### Ejemplo Básico
```go
cnxStr := "mongodb://localhost:27017"
client, _ := mongo.Connect(context.Background(), options.Client().ApplyURI(cnxStr))
```

### Ejemplo con Credenciales
```go
cnxStr := "mongodb://user:password@192.168.1.100:27017/mydb?authSource=admin"
client, _ := mongo.Connect(context.Background(), options.Client().ApplyURI(cnxStr))
```

## Parámetros Comunes

| Parámetro | Valor | Descripción |
|---|---|---|
| `authSource` | database | BD para autenticación (default: admin) |
| `authMechanism` | SCRAM-SHA-1, SCRAM-SHA-256 | Mecanismo de autenticación |
| `connectTimeoutMS` | ms | Timeout conexión |
| `serverSelectionTimeoutMS` | ms | Timeout selección servidor |
| `readPreference` | primary, secondary | Preferencia lectura |
| `retryWrites` | true, false | Reintentar escrituras |

## Ejemplo de Uso

```go
package main

import (
    "context"
    "log"
    "go.mongodb.org/mongo-driver/mongo"
    "go.mongodb.org/mongo-driver/mongo/options"
)

func main() {
    cnxStr := "mongodb://myuser:mypass@192.168.1.100:27017/mydb?authSource=admin"
    
    client, err := mongo.Connect(context.Background(), options.Client().ApplyURI(cnxStr))
    if err != nil {
        log.Fatal(err)
    }
    defer client.Disconnect(context.Background())

    // Verificar conexión
    if err := client.Ping(context.Background(), nil); err != nil {
        log.Fatal(err)
    }

    // Acceder a colecciones
    db := client.Database("mydb")
    collection := db.Collection("users")
    
    // Insertar documento
    result, _ := collection.InsertOne(context.Background(), map[string]interface{}{
        "name": "John",
        "age":  30,
    })
    
    log.Println("Inserted ID:", result.InsertedID)
}
```

---

# Instalación Multiplataforma

## Windows PowerShell

### Instalar todos los drivers

```powershell
cd C:\ruta\del\proyecto
go get github.com/denisenkom/go-mssqldb
go get github.com/thda/tds@v0.1.7
go get github.com/go-sql-driver/mysql
go get github.com/lib/pq
go get go.mongodb.org/mongo-driver/mongo
go mod tidy
```

### Compilar para Windows

```powershell
$env:GOOS = "windows"
$env:GOARCH = "amd64"
go build -o extractor.exe

# Usar
.\extractor.exe -dbtype sqlserver -server localhost -port 1433 -user sa -password "Password" -database master
.\extractor.exe -dbtype sybase -server 172.31.4.34 -port 4100 -user hr19234 -password Cusca2024 -database bc_regional
.\extractor.exe -dbtype mysql -server localhost -port 3306 -user root -password "password" -database mydb
.\extractor.exe -dbtype postgres -server localhost -port 5432 -user postgres -password "password" -database mydb
```

## Linux Bash

### Instalar todos los drivers

```bash
cd /home/usuario/proyecto
go get github.com/denisenkom/go-mssqldb
go get github.com/thda/tds@v0.1.7
go get github.com/go-sql-driver/mysql
go get github.com/lib/pq
go get go.mongodb.org/mongo-driver/mongo
go mod tidy
```

### Compilar para Linux

```bash
export GOOS=linux
export GOARCH=amd64
go build -o extractor

# Usar
./extractor -dbtype sqlserver -server 192.168.1.100 -port 1433 -user sa -password "Password" -database master
./extractor -dbtype sybase -server 172.31.4.34 -port 4100 -user hr19234 -password Cusca2024 -database bc_regional
./extractor -dbtype mysql -server 192.168.1.100 -port 3306 -user root -password "password" -database employees
./extractor -dbtype postgres -server 192.168.1.100 -port 5432 -user postgres -password "password" -database company
```

## macOS Bash/Zsh

### Instalar todos los drivers

```bash
cd ~/proyecto
go get github.com/denisenkom/go-mssqldb
go get github.com/thda/tds@v0.1.7
go get github.com/go-sql-driver/mysql
go get github.com/lib/pq
go get go.mongodb.org/mongo-driver/mongo
go mod tidy
```

### Compilar para macOS

```bash
export GOOS=darwin
export GOARCH=amd64
go build -o extractor

# Usar
./extractor -dbtype mysql -server localhost -port 3306 -user root -password "password" -database mydb
```

---

# Ejemplos Completos

## Ejemplo 1: Extrayendo esquema de diferentes BDs

```go
package main

import (
    "database/sql"
    "fmt"
    "log"
    
    _ "github.com/denisenkom/go-mssqldb"
    _ "github.com/thda/tds"
    _ "github.com/go-sql-driver/mysql"
    _ "github.com/lib/pq"
)

func connectToDatabase(dbType, server string, port int, user, password, database string) (*sql.DB, error) {
    var connStr string
    var driver string

    switch dbType {
    case "sqlserver":
        driver = "sqlserver"
        connStr = fmt.Sprintf("server=%s;port=%d;user id=%s;password=%s;database=%s",
            server, port, user, password, database)
    
    case "sybase":
        driver = "tds"
        connStr = fmt.Sprintf("tds://%s:%s@%s:%d/%s?encryptPassword=no&readTimeout=30&writeTimeout=30",
            user, password, server, port, database)
    
    case "mysql":
        driver = "mysql"
        connStr = fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=true",
            user, password, server, port, database)
    
    case "postgres":
        driver = "postgres"
        connStr = fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=disable",
            server, port, user, password, database)
    
    default:
        return nil, fmt.Errorf("tipo de BD desconocida: %s", dbType)
    }

    db, err := sql.Open(driver, connStr)
    if err != nil {
        return nil, err
    }

    if err := db.Ping(); err != nil {
        return nil, err
    }

    fmt.Printf("✅ Conectado a %s exitosamente\n", dbType)
    return db, nil
}

func main() {
    // Ejemplos de conexión
    conexiones := []struct {
        dbType   string
        server   string
        port     int
        user     string
        password string
        database string
    }{
        {"sqlserver", "localhost", 1433, "sa", "Password123", "master"},
        {"sybase", "172.31.4.34", 4100, "hr19234", "Cusca2024", "bc_regional"},
        {"mysql", "localhost", 3306, "root", "password", "mydb"},
        {"postgres", "localhost", 5432, "postgres", "password", "mydb"},
    }

    for _, conn := range conexiones {
        db, err := connectToDatabase(conn.dbType, conn.server, conn.port, conn.user, conn.password, conn.database)
        if err != nil {
            log.Printf("❌ Error conectando a %s: %v\n", conn.dbType, err)
            continue
        }
        defer db.Close()

        // Realizar operación
        rows, _ := db.Query("SELECT 1")
        rows.Close()
    }
}
```

## Ejemplo 2: Función genérica para extraer tablas

```go
func getTables(db *sql.DB, dbType string, schema string) ([]string, error) {
    var query string

    switch dbType {
    case "sqlserver", "sybase":
        query = fmt.Sprintf(`
            SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = '%s' AND TABLE_TYPE = 'BASE TABLE'
        `, schema)
    
    case "mysql":
        query = fmt.Sprintf(`
            SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = '%s' AND TABLE_TYPE = 'BASE TABLE'
        `, schema)
    
    case "postgres":
        query = fmt.Sprintf(`
            SELECT table_name FROM information_schema.tables
            WHERE table_schema = '%s' AND table_type = 'BASE TABLE'
        `, schema)
    
    default:
        return nil, fmt.Errorf("BD no soportada: %s", dbType)
    }

    rows, err := db.Query(query)
    if err != nil {
        return nil, err
    }
    defer rows.Close()

    var tables []string
    for rows.Next() {
        var tableName string
        if err := rows.Scan(&tableName); err != nil {
            return nil, err
        }
        tables = append(tables, tableName)
    }

    return tables, nil
}
```

---

# Troubleshooting

## SQL Server

### Error: "login failed for user"
```
Causa: Credenciales incorrectas o modo de autenticación
Solución: Verificar usuario/contraseña, SQL Server en modo mixto (SQL + Windows)
```

### Error: "named instance not found"
```
Causa: Nombre de instancia incorrecto
Solución: Usar "servidor\\INSTANCIA" o puerto TCP específico
```

## Sybase

### Error: "driver: bad connection"
```
Causa: Falta parámetro encryptPassword=no
Solución: Usar ?encryptPassword=no en cadena conexión
```

### Error: "can not parse param"
```
Causa: Driver thda/tds incompatible
Solución: Usar versión v0.1.7: go get github.com/thda/tds@v0.1.7
```

## MySQL

### Error: "access denied for user"
```
Causa: Credenciales incorrectas o usuario sin permisos
Solución: Verificar usuario, contraseña y permisos en MySQL
```

### Error: "charset unknown"
```
Causa: Charset no soportado
Solución: Usar utf8mb4 o utf8 en parámetro ?charset=
```

## PostgreSQL

### Error: "password authentication failed"
```
Causa: Contraseña incorrecta o autenticación no configurada
Solución: Verificar pg_hba.conf y credenciales
```

### Error: "SSL is required"
```
Causa: Servidor requiere SSL pero se usó sslmode=disable
Solución: Usar ?sslmode=require o instalar certificado
```

## MongoDB

### Error: "connection refused"
```
Causa: MongoDB no está corriendo o puerto incorrecto
Solución: Iniciar MongoDB, verificar puerto (default: 27017)
```

### Error: "authentication failed"
```
Causa: Credenciales incorrectas o authSource mal configurado
Solución: Usar ?authSource=admin o la BD correcta
```

---

# Resumen de Puertos y Conexión

| BD | Driver | Puerto | Usuario Default | Cadena Ejemplo |
|---|---|---|---|---|
| **SQL Server** | go-mssqldb | 1433 | sa | `server=localhost;port=1433;user id=sa;password=pass;database=master` |
| **Sybase** | thda/tds | 5000 | sa | `tds://sa:pass@localhost:5000/master?encryptPassword=no` |
| **MySQL** | go-sql-driver | 3306 | root | `root:pass@tcp(localhost:3306)/mydb` |
| **PostgreSQL** | lib/pq | 5432 | postgres | `host=localhost port=5432 user=postgres password=pass dbname=mydb` |
| **MongoDB** | mongo-driver | 27017 | - | `mongodb://localhost:27017` |

---

## Versión

- **Documento**: 2.0 (Completo)
- **Fecha**: 2026-09-07
- **Go Version**: 1.8+
- **Bases de Datos**: 5 (SQL Server, Sybase, MySQL, PostgreSQL, MongoDB)
- **Estado**: ✅ Listo para producción
