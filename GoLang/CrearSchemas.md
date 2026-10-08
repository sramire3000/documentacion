# Crear Schemas de diferentes bases de datos

### Crear carpeta "xtrac-sql"
### Crear archivo "main.go"
### Contenido de "main.go"
```bash
package main

import (
	"database/sql"
	"encoding/json"
	"flag"
	"fmt"
	"log"
	"os"
	"strings"

	_ "github.com/denisenkom/go-mssqldb"
	_ "github.com/go-sql-driver/mysql"
	_ "github.com/lib/pq"
	_ "github.com/thda/tds"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// Configuración de la conexión a la base de datos
type Config struct {
	DBType   string
	Server   string
	Port     int
	User     string
	Password string
	Database string
	Schema   string
	Output   string
	SSLMode  string // Para PostgreSQL
}

// Estructura para almacenar la información de una columna
type Column struct {
	ColumnName   string `json:"columnName"`
	DataType     string `json:"dataType"`
	IsNullable   string `json:"isNullable"`
	MaxLength    int    `json:"maxLength,omitempty"`
	Precision    int    `json:"precision,omitempty"`
	Scale        int    `json:"scale,omitempty"`
	IsPrimaryKey bool   `json:"isPrimaryKey"`
	IsIdentity   bool   `json:"isIdentity"`
	DefaultValue string `json:"defaultValue,omitempty"`
}

// Estructura para almacenar información de relaciones (Foreign Keys)
type ForeignKey struct {
	ConstraintName      string `json:"constraintName"`
	ColumnName          string `json:"columnName"`
	ReferencedTableName string `json:"referencedTableName"`
	ReferencedColumnName string `json:"referencedColumnName"`
}

// Estructura para almacenar la información de una tabla
type Table struct {
	TableName    string       `json:"tableName"`
	Schema       string       `json:"schema"`
	Columns      []Column     `json:"columns"`
	ForeignKeys  []ForeignKey `json:"foreignKeys,omitempty"`
}

// Estructura principal que contiene todas las tablas
type DatabaseSchema struct {
	DatabaseName string  `json:"databaseName"`
	DBType       string  `json:"dbType"`
	Schema       string  `json:"defaultSchema"`
	Tables       []Table `json:"tables"`
}

// Estructura para MongoDB
type MongoCollection struct {
	CollectionName string                 `json:"collectionName"`
	DatabaseName   string                 `json:"databaseName"`
	Indexes        []MongoIndex           `json:"indexes,omitempty"`
	SampleDocument map[string]interface{} `json:"sampleDocument,omitempty"`
}

type MongoIndex struct {
	Name   string          `json:"name"`
	Keys   []MongoIndexKey `json:"keys"`
	Unique bool            `json:"unique"`
}

type MongoIndexKey struct {
	Field     string `json:"field"`
	Direction int    `json:"direction"`
}

type MongoSchema struct {
	DatabaseName string            `json:"databaseName"`
	DBType       string            `json:"dbType"`
	Collections  []MongoCollection `json:"collections"`
}

func main() {
	// Definir flags
	dbType := flag.String("dbtype", "", "Tipo de base de datos (sqlserver, sybase, mysql, postgres, mongodb)")
	server := flag.String("server", "localhost", "Servidor de la base de datos")
	port := flag.Int("port", 0, "Puerto de la base de datos (se usará el puerto por defecto según el tipo)")
	user := flag.String("user", "", "Usuario de la base de datos")
	password := flag.String("password", "", "Contraseña de la base de datos")
	database := flag.String("database", "", "Nombre de la base de datos")
	schema := flag.String("schema", "dbo", "Schema por defecto (para bases de datos que lo soportan)")
	output := flag.String("output", "database_schema.json", "Archivo de salida JSON")
	sslMode := flag.String("sslmode", "disable", "Modo SSL (para PostgreSQL)")
	help := flag.Bool("help", false, "Mostrar ayuda")

	flag.Parse()

	// Mostrar ayuda si se solicita
	if *help {
		printHelp()
		return
	}

	// Validar parámetros requeridos
	if *dbType == "" || *user == "" || *password == "" || *database == "" {
		fmt.Println("Error: Los parámetros dbtype, user, password y database son requeridos")
		fmt.Println("\nUso:")
		flag.PrintDefaults()
		os.Exit(1)
	}

	// Configurar puerto por defecto según el tipo de BD
	if *port == 0 {
		*port = getDefaultPort(*dbType)
	}

	// Nota: Para Sybase, se usa go-mssqldb en lugar de thda/tds
	// ya que tiene mejor compatibilidad y mantenimiento
	// Configuración de la conexión
	config := Config{
		DBType:   strings.ToLower(*dbType),
		Server:   *server,
		Port:     *port,
		User:     *user,
		Password: *password,
		Database: *database,
		Schema:   *schema,
		Output:   *output,
		SSLMode:  *sslMode,
	}

	// Validar tipo de base de datos
	if !isValidDBType(config.DBType) {
		fmt.Printf("Error: Tipo de base de datos no válido: %s\n", config.DBType)
		fmt.Println("Tipos válidos: sqlserver, sybase, mysql, postgres, mongodb")
		os.Exit(1)
	}

	fmt.Printf("Configuración:\n")
	fmt.Printf("  Tipo de BD: %s\n", config.DBType)
	fmt.Printf("  Servidor: %s:%d\n", config.Server, config.Port)
	fmt.Printf("  Base de datos: %s\n", config.Database)
	fmt.Printf("  Schema: %s\n", config.Schema)
	fmt.Printf("  Archivo de salida: %s\n", config.Output)
	fmt.Println()

	// Procesar según el tipo de base de datos
	if config.DBType == "mongodb" {
		processMongoDB(config)
	} else {
		processSQLDatabase(config)
	}
}

func isValidDBType(dbType string) bool {
	validTypes := []string{"sqlserver", "sybase", "mysql", "postgres", "mongodb"}
	for _, t := range validTypes {
		if dbType == t {
			return true
		}
	}
	return false
}

func getDefaultPort(dbType string) int {
	switch dbType {
	case "sqlserver":
		return 1433
	case "sybase":
		return 5000
	case "mysql":
		return 3306
	case "postgres":
		return 5432
	case "mongodb":
		return 27017
	default:
		return 0
	}
}

func processSQLDatabase(config Config) {
	// Crear cadena de conexión según el tipo de BD
	connectionString := getConnectionString(config)

	// Determinar el driver según el tipo de BD
	driverName := getDriverName(config.DBType)

	// Conectar a la base de datos
	db, err := sql.Open(driverName, connectionString)
	if err != nil {
		log.Fatal("Error al conectar a la base de datos:", err)
	}
	defer db.Close()

	// Verificar la conexión
	err = db.Ping()
	if err != nil {
		log.Fatal("Error al verificar la conexión:", err)
	}

	fmt.Printf("✅ Conexión exitosa a %s\n", strings.ToUpper(config.DBType))

	// Extraer el esquema de la base de datos
	schema, err := extractDatabaseSchema(db, config)
	if err != nil {
		log.Fatal("Error al extraer el esquema:", err)
	}

	// Guardar en archivo JSON
	err = saveToJSONFile(schema, config.Output)
	if err != nil {
		log.Fatal("Error al guardar el archivo JSON:", err)
	}

	fmt.Printf("✅ Esquema guardado en: %s\n", config.Output)

	// Generar archivo Markdown
	markdownOutput := generateMarkdownFilename(config.Database, config.DBType, config.Schema)
	err = saveToMarkdownFile(schema, markdownOutput)
	if err != nil {
		log.Fatal("Error al guardar el archivo Markdown:", err)
	}

	fmt.Printf("✅ Documentación guardada en: %s\n", markdownOutput)
	fmt.Printf("📊 Total de tablas procesadas: %d\n", len(schema.Tables))
}

func processMongoDB(config Config) {
	// Crear cadena de conexión para MongoDB
	connectionString := fmt.Sprintf("mongodb://%s:%s@%s:%d/%s",
		config.User, config.Password, config.Server, config.Port, config.Database)

	client, err := mongo.Connect(nil, options.Client().ApplyURI(connectionString))
	if err != nil {
		log.Fatal("Error al conectar a MongoDB:", err)
	}
	defer client.Disconnect(nil)

	// Verificar la conexión
	err = client.Ping(nil, nil)
	if err != nil {
		log.Fatal("Error al verificar la conexión a MongoDB:", err)
	}

	fmt.Printf("✅ Conexión exitosa a MongoDB\n")

	// Extraer el esquema de MongoDB
	schema, err := extractMongoDBSchema(client, config.Database)
	if err != nil {
		log.Fatal("Error al extraer el esquema de MongoDB:", err)
	}

	// Guardar en archivo JSON
	err = saveToJSONFile(schema, config.Output)
	if err != nil {
		log.Fatal("Error al guardar el archivo JSON:", err)
	}

	fmt.Printf("✅ Esquema de MongoDB guardado en: %s\n", config.Output)

	// Generar archivo Markdown
	markdownOutput := generateMarkdownFilename(config.Database, config.DBType, "")
	err = saveMongoDBToMarkdownFile(schema, markdownOutput)
	if err != nil {
		log.Fatal("Error al guardar el archivo Markdown de MongoDB:", err)
	}

	fmt.Printf("✅ Documentación de MongoDB guardada en: %s\n", markdownOutput)
	fmt.Printf("📊 Total de colecciones procesadas: %d\n", len(schema.Collections))
}

func getDriverName(dbType string) string {
	switch dbType {
	case "sqlserver":
		return "sqlserver"
	case "sybase":
		// Usar TDS driver (github.com/neweric2021/tds) - versión actualizada de 2021
		return "tds"
	case "mysql":
		return "mysql"
	case "postgres":
		return "postgres"
	default:
		return ""
	}
}

func getConnectionString(config Config) string {
	switch config.DBType {
	case "sqlserver":
		return fmt.Sprintf("server=%s;port=%d;user id=%s;password=%s;database=%s",
			config.Server, config.Port, config.User, config.Password, config.Database)
	case "sybase":
		// Usar formato TDS para Sybase (driver: github.com/thda/tds)
		// Parámetros: encryptPassword=no para evitar problemas con autenticación
		// readTimeout y writeTimeout para evitar timeouts
		return fmt.Sprintf("tds://%s:%s@%s:%d/%s?encryptPassword=no&readTimeout=30&writeTimeout=30",
			config.User, config.Password, config.Server, config.Port, config.Database)
	case "mysql":
		return fmt.Sprintf("%s:%s@tcp(%s:%d)/%s",
			config.User, config.Password, config.Server, config.Port, config.Database)
	case "postgres":
		return fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=%s",
			config.Server, config.Port, config.User, config.Password, config.Database, config.SSLMode)
	default:
		return ""
	}
}

func extractDatabaseSchema(db *sql.DB, config Config) (*DatabaseSchema, error) {
	schema := &DatabaseSchema{
		DatabaseName: config.Database,
		DBType:       config.DBType,
		Schema:       config.Schema,
		Tables:       []Table{},
	}

	// Consulta para obtener tablas según el tipo de BD
	queryTables := getTablesQuery(config.DBType, config.Schema)

	rowsTables, err := db.Query(queryTables)
	if err != nil {
		return nil, fmt.Errorf("error al consultar tablas: %v", err)
	}
	defer rowsTables.Close()

	fmt.Printf("🔍 Extrayendo información de tablas...\n")

	for rowsTables.Next() {
		var tableSchema, tableName string

		// Manejar diferentes estructuras de resultados según la BD
		switch config.DBType {
		case "sqlserver", "sybase":
			err = rowsTables.Scan(&tableSchema, &tableName)
		case "mysql":
			err = rowsTables.Scan(&tableSchema, &tableName)
		case "postgres":
			err = rowsTables.Scan(&tableSchema, &tableName)
		}

		if err != nil {
			return nil, fmt.Errorf("error al escanear tabla: %v", err)
		}

		// Obtener columnas para esta tabla
		columns, err := extractTableColumns(db, config.DBType, tableSchema, tableName)
		if err != nil {
			return nil, fmt.Errorf("error al extraer columnas para tabla %s: %v", tableName, err)
		}

		// Obtener Foreign Keys para esta tabla
		foreignKeys, err := extractForeignKeys(db, config.DBType, tableSchema, tableName)
		if err != nil {
			// Registrar warning pero continuar
			fmt.Printf("  ⚠️  No se pudieron extraer Foreign Keys para %s: %v\n", tableName, err)
			foreignKeys = []ForeignKey{}
		}

		table := Table{
			TableName:   tableName,
			Schema:      tableSchema,
			Columns:     columns,
			ForeignKeys: foreignKeys,
		}

		schema.Tables = append(schema.Tables, table)
		fmt.Printf("  📋 Tabla procesada: %s.%s (%d columnas, %d relaciones)\n", tableSchema, tableName, len(columns), len(foreignKeys))
	}

	if err = rowsTables.Err(); err != nil {
		return nil, fmt.Errorf("error iterando sobre tablas: %v", err)
	}

	return schema, nil
}

func getTablesQuery(dbType string, defaultSchema string) string {
	switch dbType {
	case "sqlserver":
		return fmt.Sprintf(`
			SELECT 
				TABLE_SCHEMA,
				TABLE_NAME
			FROM INFORMATION_SCHEMA.TABLES
			WHERE TABLE_TYPE = 'BASE TABLE'
			AND TABLE_SCHEMA = '%s'
			ORDER BY TABLE_SCHEMA, TABLE_NAME
		`, defaultSchema)
	case "sybase":
		// Consulta simplificada para Sybase - obtener todas las tablas del usuario/schema
		return fmt.Sprintf(`
			SELECT 
				user_name(uid) as schema_name,
				name as table_name
			FROM sysobjects 
			WHERE type = 'U'  -- Tablas de usuario
			AND user_name(uid) = '%s'
			ORDER BY schema_name, table_name
		`, defaultSchema)
	case "mysql":
		return `
			SELECT 
				TABLE_SCHEMA,
				TABLE_NAME
			FROM INFORMATION_SCHEMA.TABLES
			WHERE TABLE_TYPE = 'BASE TABLE'
			AND TABLE_SCHEMA = DATABASE()
			ORDER BY TABLE_SCHEMA, TABLE_NAME
		`
	case "postgres":
		return fmt.Sprintf(`
			SELECT 
				table_schema,
				table_name
			FROM information_schema.tables
			WHERE table_type = 'BASE TABLE'
			AND table_schema = '%s'
			ORDER BY table_schema, table_name
		`, defaultSchema)
	default:
		return ""
	}
}

func extractTableColumns(db *sql.DB, dbType, schemaName, tableName string) ([]Column, error) {
	// Para Sybase, construimos la consulta dinámicamente sin parámetros
	if dbType == "sybase" {
		return extractSybaseTableColumns(db, tableName)
	}

	queryColumns := getColumnsQuery(dbType)
	var rowsColumns *sql.Rows
	var err error

	// Usar parámetros preparados correctamente para cada base de datos
	switch dbType {
	case "sqlserver":
		rowsColumns, err = db.Query(queryColumns, sql.Named("schema", schemaName), sql.Named("table", tableName))
	case "mysql":
		rowsColumns, err = db.Query(queryColumns, schemaName, tableName)
	case "postgres":
		// PostgreSQL usa $1, $2 para parámetros
		rowsColumns, err = db.Query(queryColumns, schemaName, tableName)
	default:
		return nil, fmt.Errorf("tipo de base de datos no soportado: %s", dbType)
	}

	if err != nil {
		return nil, fmt.Errorf("error al consultar columnas: %v", err)
	}
	defer rowsColumns.Close()

	var columns []Column

	for rowsColumns.Next() {
		col, err := scanColumn(rowsColumns, dbType)
		if err != nil {
			return nil, err
		}
		columns = append(columns, col)
	}

	if err = rowsColumns.Err(); err != nil {
		return nil, fmt.Errorf("error iterando sobre columnas: %v", err)
	}

	return columns, nil
}

// Función específica para extraer columnas de Sybase (sin parámetros)
func extractSybaseTableColumns(db *sql.DB, tableName string) ([]Column, error) {
	// Consulta simplificada para Sybase - sin la parte compleja de claves primarias que causa errores
	query := fmt.Sprintf(`
		SELECT 
			c.name as column_name,
			t.name as data_type,
			c.length,
			c.prec as numeric_precision,
			c.scale as numeric_scale,
			CASE 
				WHEN c.status & 8 = 8 THEN 'YES' 
				ELSE 'NO' 
			END as is_nullable,
			CASE 
				WHEN c.status & 128 = 128 THEN 1 
				ELSE 0 
			END as is_identity,
			ISNULL(OBJECT_NAME(c.cdefault), '') as default_value,
			0 as is_primary_key  -- Por ahora, no detectamos claves primarias para evitar errores
		FROM syscolumns c
		JOIN systypes t ON c.usertype = t.usertype
		WHERE c.id = object_id('%s')
		ORDER BY c.colid
	`, tableName)

	rowsColumns, err := db.Query(query)
	if err != nil {
		return nil, fmt.Errorf("error al consultar columnas: %v", err)
	}
	defer rowsColumns.Close()

	var columns []Column

	for rowsColumns.Next() {
		var col Column
		var isNullable string
		var length, prec, scale sql.NullInt32
		var isPrimaryKey, isIdentity int

		err := rowsColumns.Scan(
			&col.ColumnName,
			&col.DataType,
			&length,
			&prec,
			&scale,
			&isNullable,
			&isIdentity,
			&col.DefaultValue,
			&isPrimaryKey,
		)
		if err != nil {
			return nil, fmt.Errorf("error al escanear columna: %v", err)
		}

		// Convertir valores
		col.IsNullable = isNullable
		col.IsPrimaryKey = (isPrimaryKey == 1)
		col.IsIdentity = (isIdentity == 1)

		if length.Valid {
			col.MaxLength = int(length.Int32)
		}
		if prec.Valid {
			col.Precision = int(prec.Int32)
		}
		if scale.Valid {
			col.Scale = int(scale.Int32)
		}

		columns = append(columns, col)
	}

	if err = rowsColumns.Err(); err != nil {
		return nil, fmt.Errorf("error iterando sobre columnas: %v", err)
	}

	// Intentar obtener información de claves primarias por separado
	primaryKeys, err := getSybasePrimaryKeys(db, tableName)
	if err != nil {
		// Si hay error, simplemente continuamos sin información de PKs
		fmt.Printf("  ⚠️  No se pudieron obtener claves primarias para %s: %v\n", tableName, err)
	} else {
		// Actualizar las columnas que son claves primarias
		for i, col := range columns {
			if _, isPK := primaryKeys[col.ColumnName]; isPK {
				columns[i].IsPrimaryKey = true
			}
		}
	}

	return columns, nil
}

// Función separada para obtener claves primarias en Sybase
func getSybasePrimaryKeys(db *sql.DB, tableName string) (map[string]bool, error) {
	primaryKeys := make(map[string]bool)

	// Método 1: Usar sysindexkeys (más confiable)
	// El índice 1 (indid=1) típicamente es la clave primaria en Sybase
	query := fmt.Sprintf(`
		SELECT DISTINCT
			c.name
		FROM sysindexes i
		JOIN sysindexkeys ik ON i.id = ik.id AND i.indid = ik.indid
		JOIN syscolumns c ON ik.id = c.id AND ik.colid = c.colid
		WHERE i.id = object_id('%s')
		AND i.indid = 1
	`, tableName)

	rows, err := db.Query(query)
	if err == nil {
		defer rows.Close()
		for rows.Next() {
			var columnName string
			err := rows.Scan(&columnName)
			if err == nil && columnName != "" {
				primaryKeys[columnName] = true
			}
		}
	}

	if len(primaryKeys) > 0 {
		return primaryKeys, nil
	}

	// Método 2: Alternativa si sysindexkeys falla
	query2 := fmt.Sprintf(`
		SELECT DISTINCT
			c.name
		FROM sysindexes i
		JOIN syscolumns c ON i.id = c.id
		WHERE i.id = object_id('%s')
		AND i.indid = 1
		AND (c.colid = i.key1 OR c.colid = i.key2 OR c.colid = i.key3)
	`, tableName)

	rows2, err := db.Query(query2)
	if err == nil {
		defer rows2.Close()
		for rows2.Next() {
			var columnName string
			err := rows2.Scan(&columnName)
			if err == nil && columnName != "" {
				primaryKeys[columnName] = true
			}
		}
	}

	return primaryKeys, nil
}

// Método alternativo para obtener PKs (mantenerlo para compatibilidad)
func getSybasePrimaryKeysAlternative(db *sql.DB, tableName string) (map[string]bool, error) {
	primaryKeys := make(map[string]bool)
	// Esta función es mantenida pero ya no se utiliza
	return primaryKeys, nil
}

// Consulta alternativa más simple para claves primarias (DEPRECATED)
func getSybasePrimaryKeysSimple(db *sql.DB, tableName string) (map[string]bool, error) {
	primaryKeys := make(map[string]bool)
	// Esta función es mantenida pero ya no se utiliza
	return primaryKeys, nil
}

func getColumnsQuery(dbType string) string {
	switch dbType {
	case "sqlserver":
		return `
			SELECT 
				c.COLUMN_NAME,
				c.DATA_TYPE,
				c.IS_NULLABLE,
				c.CHARACTER_MAXIMUM_LENGTH,
				c.NUMERIC_PRECISION,
				c.NUMERIC_SCALE,
				CASE WHEN pk.COLUMN_NAME IS NOT NULL THEN 1 ELSE 0 END AS IS_PRIMARY_KEY,
				COLUMNPROPERTY(OBJECT_ID(c.TABLE_SCHEMA + '.' + c.TABLE_NAME), c.COLUMN_NAME, 'IsIdentity') AS IS_IDENTITY,
				COALESCE(c.COLUMN_DEFAULT, '') AS COLUMN_DEFAULT
			FROM INFORMATION_SCHEMA.COLUMNS c
			LEFT JOIN (
				SELECT 
					ku.TABLE_SCHEMA,
					ku.TABLE_NAME,
					ku.COLUMN_NAME
				FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
				INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE ku
					ON tc.CONSTRAINT_TYPE = 'PRIMARY KEY'
					AND tc.CONSTRAINT_NAME = ku.CONSTRAINT_NAME
			) pk ON c.TABLE_SCHEMA = pk.TABLE_SCHEMA 
				AND c.TABLE_NAME = pk.TABLE_NAME 
				AND c.COLUMN_NAME = pk.COLUMN_NAME
			WHERE c.TABLE_SCHEMA = @schema 
				AND c.TABLE_NAME = @table
			ORDER BY c.ORDINAL_POSITION
		`
	case "mysql":
		return `
			SELECT 
				COLUMN_NAME,
				DATA_TYPE,
				IS_NULLABLE,
				CHARACTER_MAXIMUM_LENGTH,
				NUMERIC_PRECISION,
				NUMERIC_SCALE,
				CASE WHEN COLUMN_KEY = 'PRI' THEN 1 ELSE 0 END AS IS_PRIMARY_KEY,
				CASE WHEN EXTRA LIKE '%auto_increment%' THEN 1 ELSE 0 END AS IS_IDENTITY,
				COALESCE(COLUMN_DEFAULT, '') AS COLUMN_DEFAULT
			FROM INFORMATION_SCHEMA.COLUMNS
			WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?
			ORDER BY ORDINAL_POSITION
		`
	case "postgres":
		return `
			SELECT 
				column_name,
				data_type,
				is_nullable,
				character_maximum_length,
				numeric_precision,
				numeric_scale,
				CASE 
					WHEN (SELECT COUNT(*) 
						  FROM information_schema.key_column_usage k
						  JOIN information_schema.table_constraints tc 
						  ON k.constraint_name = tc.constraint_name 
						  AND k.table_schema = tc.table_schema
						  WHERE k.table_schema = $1 
							AND k.table_name = $2 
							AND k.column_name = c.column_name
							AND tc.constraint_type = 'PRIMARY KEY') > 0 
					THEN 1 
					ELSE 0 
				END AS is_primary_key,
				CASE 
					WHEN column_default LIKE 'nextval%' THEN 1 
					ELSE 0 
				END AS is_identity,
				COALESCE(column_default, '') AS column_default
			FROM information_schema.columns c
			WHERE table_schema = $1 
			  AND table_name = $2
			ORDER BY ordinal_position
		`
	default:
		return ""
	}
}

// extractForeignKeys extrae las claves foráneas de una tabla
func extractForeignKeys(db *sql.DB, dbType, schemaName, tableName string) ([]ForeignKey, error) {
	var fks []ForeignKey
	
	switch dbType {
	case "sqlserver":
		return extractForeignKeysSQLServer(db, schemaName, tableName)
	case "mysql":
		return extractForeignKeysMySQL(db, schemaName, tableName)
	case "postgres":
		return extractForeignKeysPostgreSQL(db, schemaName, tableName)
	case "sybase":
		return extractForeignKeysSybase(db, tableName)
	default:
		return fks, nil
	}
}

// extractForeignKeysSQLServer extrae Foreign Keys para SQL Server
func extractForeignKeysSQLServer(db *sql.DB, schemaName, tableName string) ([]ForeignKey, error) {
	query := `
		SELECT 
			rc.CONSTRAINT_NAME,
			kcu.COLUMN_NAME,
			ccu.TABLE_NAME as REFERENCED_TABLE_NAME,
			ccu.COLUMN_NAME as REFERENCED_COLUMN_NAME
		FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS rc
		JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu 
			ON rc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME
		JOIN INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu 
			ON rc.UNIQUE_CONSTRAINT_NAME = ccu.CONSTRAINT_NAME
		WHERE kcu.TABLE_SCHEMA = @schema
			AND kcu.TABLE_NAME = @table
	`
	
	rows, err := db.Query(query, sql.Named("schema", schemaName), sql.Named("table", tableName))
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	
	var fks []ForeignKey
	for rows.Next() {
		var fk ForeignKey
		err := rows.Scan(&fk.ConstraintName, &fk.ColumnName, &fk.ReferencedTableName, &fk.ReferencedColumnName)
		if err != nil {
			return nil, err
		}
		fks = append(fks, fk)
	}
	
	return fks, rows.Err()
}

// extractForeignKeysMySQL extrae Foreign Keys para MySQL
func extractForeignKeysMySQL(db *sql.DB, schemaName, tableName string) ([]ForeignKey, error) {
	query := `
		SELECT 
			CONSTRAINT_NAME,
			COLUMN_NAME,
			REFERENCED_TABLE_NAME,
			REFERENCED_COLUMN_NAME
		FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
		WHERE TABLE_SCHEMA = ? 
			AND TABLE_NAME = ?
			AND REFERENCED_TABLE_NAME IS NOT NULL
	`
	
	rows, err := db.Query(query, schemaName, tableName)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	
	var fks []ForeignKey
	for rows.Next() {
		var fk ForeignKey
		err := rows.Scan(&fk.ConstraintName, &fk.ColumnName, &fk.ReferencedTableName, &fk.ReferencedColumnName)
		if err != nil {
			return nil, err
		}
		fks = append(fks, fk)
	}
	
	return fks, rows.Err()
}

// extractForeignKeysPostgreSQL extrae Foreign Keys para PostgreSQL
func extractForeignKeysPostgreSQL(db *sql.DB, schemaName, tableName string) ([]ForeignKey, error) {
	query := `
		SELECT 
			tc.constraint_name,
			kcu.column_name,
			ccu.table_name as referenced_table_name,
			ccu.column_name as referenced_column_name
		FROM information_schema.table_constraints tc
		JOIN information_schema.key_column_usage kcu 
			ON tc.constraint_name = kcu.constraint_name
		JOIN information_schema.constraint_column_usage ccu 
			ON ccu.constraint_name = tc.constraint_name
		WHERE tc.constraint_type = 'FOREIGN KEY'
			AND tc.table_schema = $1
			AND tc.table_name = $2
	`
	
	rows, err := db.Query(query, schemaName, tableName)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	
	var fks []ForeignKey
	for rows.Next() {
		var fk ForeignKey
		err := rows.Scan(&fk.ConstraintName, &fk.ColumnName, &fk.ReferencedTableName, &fk.ReferencedColumnName)
		if err != nil {
			return nil, err
		}
		fks = append(fks, fk)
	}
	
	return fks, rows.Err()
}

// extractForeignKeysSybase extrae Foreign Keys para Sybase
func extractForeignKeysSybase(db *sql.DB, tableName string) ([]ForeignKey, error) {
	var fks []ForeignKey

	// Método 1: Usar sysreferences con sintaxis correcta (evitar 'key' como palabra reservada)
	query := fmt.Sprintf(`
		SELECT DISTINCT
			o.name as constraint_name,
			c.name as column_name,
			object_name(r.reftabid) as referenced_table_name,
			col_name(r.reftabid, r.refcol) as referenced_column_name
		FROM sysreferences r
		JOIN sysobjects o ON r.constrid = o.id
		JOIN syscolumns c ON r.tableid = c.id
		WHERE object_name(r.tableid) = '%s'
		AND (c.colid = r.keyno1 OR c.colid = r.keyno2 OR c.colid = r.keyno3)
	`, tableName)

	rows, err := db.Query(query)
	if err == nil {
		defer rows.Close()
		for rows.Next() {
			var fk ForeignKey
			err := rows.Scan(&fk.ConstraintName, &fk.ColumnName, &fk.ReferencedTableName, &fk.ReferencedColumnName)
			if err == nil {
				fks = append(fks, fk)
			}
		}
	}

	if len(fks) > 0 {
		return fks, nil
	}

	// Método 2: Alternativa usando sysconstraints
	query2 := fmt.Sprintf(`
		SELECT DISTINCT
			ct.constraint_name,
			c.name as column_name,
			object_name(r.reftabid) as referenced_table_name,
			col_name(r.reftabid, r.refcol) as referenced_column_name
		FROM sysconstraints ct
		JOIN sysreferences r ON ct.constrid = r.constrid
		JOIN syscolumns c ON r.tableid = c.id
		WHERE object_name(r.tableid) = '%s'
	`, tableName)

	rows2, err := db.Query(query2)
	if err == nil {
		defer rows2.Close()
		for rows2.Next() {
			var fk ForeignKey
			err := rows2.Scan(&fk.ConstraintName, &fk.ColumnName, &fk.ReferencedTableName, &fk.ReferencedColumnName)
			if err == nil {
				fks = append(fks, fk)
			}
		}
	}

	return fks, nil
}

func scanColumn(rows *sql.Rows, dbType string) (Column, error) {
	var col Column
	var isNullable string
	var charMaxLength, numericPrecision, numericScale sql.NullInt32
	var isPrimaryKey, isIdentity int

	switch dbType {
	case "sqlserver":
		err := rows.Scan(
			&col.ColumnName,
			&col.DataType,
			&isNullable,
			&charMaxLength,
			&numericPrecision,
			&numericScale,
			&isPrimaryKey,
			&isIdentity,
			&col.DefaultValue,
		)
		if err != nil {
			return col, err
		}
	case "mysql", "postgres":
		err := rows.Scan(
			&col.ColumnName,
			&col.DataType,
			&isNullable,
			&charMaxLength,
			&numericPrecision,
			&numericScale,
			&isPrimaryKey,
			&isIdentity,
			&col.DefaultValue,
		)
		if err != nil {
			return col, err
		}
	}

	// Convertir valores comunes
	col.IsNullable = isNullable
	col.IsPrimaryKey = (isPrimaryKey == 1)
	col.IsIdentity = (isIdentity == 1)

	if charMaxLength.Valid {
		col.MaxLength = int(charMaxLength.Int32)
	}
	if numericPrecision.Valid {
		col.Precision = int(numericPrecision.Int32)
	}
	if numericScale.Valid {
		col.Scale = int(numericScale.Int32)
	}

	return col, nil
}

func extractMongoDBSchema(client *mongo.Client, databaseName string) (*MongoSchema, error) {
	schema := &MongoSchema{
		DatabaseName: databaseName,
		DBType:       "mongodb",
		Collections:  []MongoCollection{},
	}

	// Obtener lista de colecciones
	collections, err := client.Database(databaseName).ListCollectionNames(nil, nil)
	if err != nil {
		return nil, err
	}

	fmt.Printf("🔍 Extrayendo información de colecciones...\n")

	for _, collName := range collections {
		fmt.Printf("  📁 Procesando colección: %s\n", collName)

		collection := MongoCollection{
			CollectionName: collName,
			DatabaseName:   databaseName,
			Indexes:        []MongoIndex{},
		}

		// Aquí podrías agregar lógica para extraer índices y documentos de muestra
		// Por simplicidad, solo agregamos la colección básica

		schema.Collections = append(schema.Collections, collection)
	}

	return schema, nil
}

func saveToJSONFile(data interface{}, filename string) error {
	file, err := os.Create(filename)
	if err != nil {
		return fmt.Errorf("error al crear archivo: %v", err)
	}
	defer file.Close()

	encoder := json.NewEncoder(file)
	encoder.SetIndent("", "  ")

	err = encoder.Encode(data)
	if err != nil {
		return fmt.Errorf("error al codificar JSON: %v", err)
	}

	return nil
}

// generateMarkdownFilename genera el nombre del archivo markdown basado en el tipo de BD y schema
func generateMarkdownFilename(databaseName, dbType, schema string) string {
	// Capitalizar el tipo de BD para el nombre del archivo
	dbTypeFormatted := capitalizeDBType(dbType)
	
	// Incluir schema en el nombre si está disponible
	if schema != "" {
		return fmt.Sprintf("Db%s_%s.md", dbTypeFormatted, schema)
	}
	return fmt.Sprintf("Db%s.md", dbTypeFormatted)
}

// capitalizeDBType convierte el tipo de BD a formato de nombre de archivo
func capitalizeDBType(dbType string) string {
	switch strings.ToLower(dbType) {
	case "sqlserver":
		return "SQLServer"
	case "sybase":
		return "Sybase"
	case "mysql":
		return "MySQL"
	case "postgres":
		return "PostgreSQL"
	case "mongodb":
		return "MongoDB"
	default:
		return dbType
	}
}

// saveToMarkdownFile guarda el esquema SQL en un archivo markdown
func saveToMarkdownFile(schema *DatabaseSchema, filename string) error {
	file, err := os.Create(filename)
	if err != nil {
		return fmt.Errorf("error al crear archivo markdown: %v", err)
	}
	defer file.Close()

	// Encabezado del documento
	fmt.Fprintf(file, "# Documentación de Base de Datos: %s\n\n", schema.DatabaseName)
	fmt.Fprintf(file, "**Tipo de Base de Datos:** %s  \n", capitalizeDBType(schema.DBType))
	fmt.Fprintf(file, "**Schema:** %s  \n", schema.Schema)
	fmt.Fprintf(file, "**Total de Tablas:** %d  \n\n", len(schema.Tables))

	// Índice de tablas
	fmt.Fprintf(file, "## Índice de Tablas\n\n")
	for _, table := range schema.Tables {
		fmt.Fprintf(file, "- [%s.%s](#%s)\n", table.Schema, table.TableName, generateTableAnchor(table.TableName))
	}
	fmt.Fprintf(file, "\n---\n\n")

	// Documentación de cada tabla
	for _, table := range schema.Tables {
		writeTableDocumentation(file, &table)
	}

	// Mapa de Relaciones
	fmt.Fprintf(file, "\n---\n\n")
	writeRelationshipsMap(file, schema)

	return nil
}

// writeTableDocumentation escribe la documentación de una tabla en formato markdown
func writeTableDocumentation(file *os.File, table *Table) {
	// Encabezado de la tabla
	fmt.Fprintf(file, "## %s.%s\n\n", table.Schema, table.TableName)
	fmt.Fprintf(file, "**Columnas:** %d\n\n", len(table.Columns))

	// Tabla de columnas
	fmt.Fprintf(file, "| Nombre | Tipo Datos | Nuleable | PK | Identity | FK | Default |\n")
	fmt.Fprintf(file, "|--------|------------|----------|----|-----------|----|----------|\n")

	for _, col := range table.Columns {
		pk := "❌"
		if col.IsPrimaryKey {
			pk = "✅"
		}

		identity := "❌"
		if col.IsIdentity {
			identity = "✅"
		}

		defaultValue := col.DefaultValue
		if defaultValue == "" {
			defaultValue = "-"
		}

		maxLength := ""
		if col.MaxLength > 0 {
			maxLength = fmt.Sprintf("(%d)", col.MaxLength)
		}

		precision := ""
		if col.Precision > 0 {
			precision = fmt.Sprintf("(%d", col.Precision)
			if col.Scale > 0 {
				precision += fmt.Sprintf(",%d", col.Scale)
			}
			precision += ")"
		}

		dataType := col.DataType + maxLength + precision

		// Verificar si esta columna es parte de un Foreign Key
		fkInfo := "-"
		for _, fk := range table.ForeignKeys {
			if fk.ColumnName == col.ColumnName {
				fkInfo = fmt.Sprintf("%s(%s)", fk.ReferencedTableName, fk.ReferencedColumnName)
				break
			}
		}

		fmt.Fprintf(file, "| %s | %s | %s | %s | %s | %s | %s |\n",
			col.ColumnName,
			dataType,
			col.IsNullable,
			pk,
			identity,
			fkInfo,
			defaultValue,
		)
	}

	fmt.Fprintf(file, "\n\n")
}

// generateTableAnchor genera un ancla para la tabla (para los links en el índice)
func generateTableAnchor(tableName string) string {
	return strings.ToLower(strings.ReplaceAll(tableName, "_", ""))
}

// writeRelationshipsMap genera la sección de Mapa de Relaciones
func writeRelationshipsMap(file *os.File, schema *DatabaseSchema) {
	fmt.Fprintf(file, "## Mapa de Relaciones\n\n")

	// Recopilar todas las relaciones
	relations := make(map[string][]string)
	hasRelations := false

	for _, table := range schema.Tables {
		if len(table.ForeignKeys) > 0 {
			hasRelations = true
			tableName := table.TableName

			for _, fk := range table.ForeignKeys {
				// Crear relación: tabla_origen (1) --< tabla_destino (muchos)
				// Considerando que la FK apunta a la tabla destino
				relation := fmt.Sprintf("%s --< %s", fk.ReferencedTableName, tableName)
				relations[tableName] = append(relations[tableName], relation)
			}
		}
	}

	if !hasRelations {
		fmt.Fprintf(file, "*No hay relaciones de Foreign Keys detectadas en esta base de datos.*\n\n")
		return
	}

	// Escribir relaciones en formato ERD simplificado
	for tableName, rels := range relations {
		if len(rels) > 0 {
			fmt.Fprintf(file, "### Relaciones de %s\n\n", tableName)
			fmt.Fprintf(file, "```\n")
			for _, rel := range rels {
				fmt.Fprintf(file, "%s\n", rel)
			}
			fmt.Fprintf(file, "```\n\n")
		}
	}

	// Resumen de todas las relaciones
	fmt.Fprintf(file, "### Resumen de Relaciones\n\n")
	fmt.Fprintf(file, "| Tabla Origen | Tabla Destino | Columna FK | Columna Ref |\n")
	fmt.Fprintf(file, "|---|---|---|---|\n")

	for _, table := range schema.Tables {
		for _, fk := range table.ForeignKeys {
			fmt.Fprintf(file, "| %s | %s | %s | %s |\n",
				table.TableName,
				fk.ReferencedTableName,
				fk.ColumnName,
				fk.ReferencedColumnName,
			)
		}
	}

	fmt.Fprintf(file, "\n")
}

// saveMongoDBToMarkdownFile guarda el esquema de MongoDB en un archivo markdown
func saveMongoDBToMarkdownFile(schema *MongoSchema, filename string) error {
	file, err := os.Create(filename)
	if err != nil {
		return fmt.Errorf("error al crear archivo markdown de MongoDB: %v", err)
	}
	defer file.Close()

	// Encabezado del documento
	fmt.Fprintf(file, "# Documentación de Base de Datos MongoDB: %s\n\n", schema.DatabaseName)
	fmt.Fprintf(file, "**Tipo de Base de Datos:** MongoDB  \n")
	fmt.Fprintf(file, "**Total de Colecciones:** %d  \n\n", len(schema.Collections))

	// Índice de colecciones
	fmt.Fprintf(file, "## Índice de Colecciones\n\n")
	for _, collection := range schema.Collections {
		fmt.Fprintf(file, "- [%s](#%s)\n", collection.CollectionName, generateTableAnchor(collection.CollectionName))
	}
	fmt.Fprintf(file, "\n---\n\n")

	// Documentación de cada colección
	for _, collection := range schema.Collections {
		writeMongoCollectionDocumentation(file, &collection)
	}

	return nil
}

// writeMongoCollectionDocumentation escribe la documentación de una colección MongoDB
func writeMongoCollectionDocumentation(file *os.File, collection *MongoCollection) {
	fmt.Fprintf(file, "## %s\n\n", collection.CollectionName)
	fmt.Fprintf(file, "**Nombre de Colección:** %s  \n", collection.CollectionName)
	fmt.Fprintf(file, "**Base de Datos:** %s  \n", collection.DatabaseName)
	fmt.Fprintf(file, "**Total de Índices:** %d  \n\n", len(collection.Indexes))

	if len(collection.Indexes) > 0 {
		fmt.Fprintf(file, "### Índices\n\n")
		fmt.Fprintf(file, "| Nombre | Campos | Único |\n")
		fmt.Fprintf(file, "|--------|--------|-------|\n")

		for _, idx := range collection.Indexes {
			fields := ""
			for _, key := range idx.Keys {
				if fields != "" {
					fields += ", "
				}
				fields += key.Field
			}

			unique := "❌"
			if idx.Unique {
				unique = "✅"
			}

			fmt.Fprintf(file, "| %s | %s | %s |\n", idx.Name, fields, unique)
		}
		fmt.Fprintf(file, "\n")
	}

	if len(collection.SampleDocument) > 0 {
		fmt.Fprintf(file, "### Documento de Muestra\n\n")
		fmt.Fprintf(file, "```json\n")
		sampleJSON, _ := json.MarshalIndent(collection.SampleDocument, "", "  ")
		fmt.Fprintf(file, "%s\n", string(sampleJSON))
		fmt.Fprintf(file, "```\n\n")
	}

	fmt.Fprintf(file, "\n")
}

func printHelp() {
	fmt.Println("🚀 Extractor de Esquema de Base de Datos Multiplataforma")
	fmt.Println("========================================================")
	fmt.Println("Este programa extrae la estructura de bases de datos SQL y NoSQL")
	fmt.Println("y las guarda en un archivo JSON.")
	fmt.Println()
	fmt.Println("📋 Parámetros:")
	fmt.Println("  -dbtype    Tipo de base de datos (sqlserver, sybase, mysql, postgres, mongodb) *REQUERIDO*")
	fmt.Println("  -server    Servidor de la base de datos (default: localhost)")
	fmt.Println("  -port      Puerto de la base de datos (default: según el tipo de BD)")
	fmt.Println("  -user      Usuario de la base de datos *REQUERIDO*")
	fmt.Println("  -password  Contraseña de la base de datos *REQUERIDO*")
	fmt.Println("  -database  Nombre de la base de datos *REQUERIDO*")
	fmt.Println("  -schema    Schema por defecto (default: dbo)")
	fmt.Println("  -output    Archivo de salida JSON (default: database_schema.json)")
	fmt.Println("  -sslmode   Modo SSL para PostgreSQL (default: disable)")
	fmt.Println("  -help      Mostrar esta ayuda")
	fmt.Println()
	fmt.Println("💡 Ejemplos de uso:")
	fmt.Println("  SQL Server: ./extractor -dbtype sqlserver -user sa -password secret -database MiDB -schema dbo -output esquema.json")
	fmt.Println("  PostgreSQL: ./extractor -dbtype postgres -user postgres -password pass -database MiDB -schema public -output esquema.json")
	fmt.Println("  MySQL:      ./extractor -dbtype mysql -user root -password pass -database MiDB -output esquema.json")
	fmt.Println("  Sybase:     ./extractor -dbtype sybase -user sa -password secret -database MiDB -schema dbo -output esquema.json")
	fmt.Println("  MongoDB:    ./extractor -dbtype mongodb -user admin -password pass -database MiDB -output esquema.json")
	fmt.Println("  Ayuda:      ./extractor -help")
	fmt.Println()
	fmt.Println("🔧 Valores por defecto:")
	fmt.Println("  SQL Server: puerto 1433, schema dbo")
	fmt.Println("  Sybase:     puerto 5000, schema dbo")
	fmt.Println("  MySQL:      puerto 3306, schema nombre_de_la_base")
	fmt.Println("  PostgreSQL: puerto 5432, schema public")
	fmt.Println("  MongoDB:    puerto 27017")
}

```
### crear archivo "go.mod"
### Contenido de "go.mod"
```bash
module schema-extractor

go 1.19

require (
	github.com/denisenkom/go-mssqldb v0.12.3
	github.com/go-sql-driver/mysql v1.7.1
	github.com/lib/pq v1.10.9
	github.com/thda/tds v0.1.7
	go.mongodb.org/mongo-driver v1.12.1
)

require (
	github.com/golang-sql/civil v0.0.0-20220223132316-b832511892a9 // indirect
	github.com/golang-sql/sqlexp v0.1.0 // indirect
	github.com/golang/snappy v0.0.4 // indirect
	github.com/klauspost/compress v1.16.7 // indirect
	github.com/montanaflynn/stats v0.7.1 // indirect
	github.com/xdg-go/pbkdf2 v1.0.0 // indirect
	github.com/xdg-go/scram v1.1.2 // indirect
	github.com/xdg-go/stringprep v1.0.4 // indirect
	github.com/youmark/pkcs8 v0.0.0-20201027041543-1326539a0a0a // indirect
	golang.org/x/crypto v0.12.0 // indirect
	golang.org/x/net v0.10.0 // indirect
	golang.org/x/sync v0.3.0 // indirect
	golang.org/x/text v0.12.0 // indirect
)

```
### Instalar librerias
```bash
go get
```

### Compilar Linux
```bash
go build -o extractor
```
### Compilar Windows
```bash
go build -o extractor.exe
```

### Ayuda completa
```bash
./extractor -help
```

## Uso en Windows

### Extract SQLSERVER
```bash
.\extractor ^
-dbtype sqlserver ^
-user sa ^
-password "Password123" ^
-database PruebaDB ^
-schema dbo ^
-output pruebaDB_esquema.json
```
### Extract MYSQL
```
.\extractor ^
-dbtype mysql ^
-user root ^
-password "Password123" ^
-database PruebaDB ^
-output pruebaDB_esquema.json
```

### Extract PostgreSQL con schema public
```
.\extractor ^
-dbtype postgres ^
-user postgres ^
-password "Password123" ^
-database PruebaDB ^
-schema public ^
-output pruebaDB_esquema.json
```

### Extract Sybase
```
.\extractor ^
-dbtype sybase ^
-user sa ^
-password "Password123" ^
-database PruebaDB ^
-schema dbo ^
-output pruebaDB_esquema.json
```

## Uso en Linux

