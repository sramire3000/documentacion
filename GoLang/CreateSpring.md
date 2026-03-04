# Create Spring boot

## Create carpeta "create-spring"
### Create archivo "spring-generator.go" en la carpeta "create-sprng"
### Contenido del archivo "spring-generator.go"
```bash
package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"
	"text/template"
	"time"
)

// Estructuras para parsear el JSON
type DatabaseSchema struct {
	DatabaseName  string  `json:"databaseName"`
	DBType        string  `json:"dbType"`
	DefaultSchema string  `json:"defaultSchema"`
	Tables        []Table `json:"tables"`
}

type Table struct {
	TableName string   `json:"tableName"`
	Schema    string   `json:"schema"`
	Columns   []Column `json:"columns"`
}

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

// Configuración de rutas
type OutputPaths struct {
	EntitiesPath        string
	RepositoriesPath    string
	ModelsPath          string
	ServicesPath        string
	ImplementationsPath string
	DTOsPath            string
}

// Templates con Lombok
var entityTemplate = `package entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Entidad JPA para la tabla {{.Schema}}.{{.TableName}}
 * 
 * <p>Esta clase representa la tabla {{.TableName}} en la base de datos</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
@Entity
@Table(name = "{{.TableName}}", schema = "{{.Schema}}")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class {{.ClassName}} {
    {{range .Columns}}
    /**
     * {{.ColumnName}} - {{.DataType}}{{if .MaxLength}} (max: {{.MaxLength}}){{end}}{{if .IsPrimaryKey}} - PRIMARY KEY{{end}}{{if .IsIdentity}} - AUTO INCREMENT{{end}}
     */
    {{if .IsPrimaryKey}}@Id{{end}}
    {{if .IsIdentity}}@GeneratedValue(strategy = GenerationType.IDENTITY){{end}}
    @Column(name = "{{.ColumnName}}"{{if not (eq .IsNullable "YES")}}, nullable = false{{end}}{{if .MaxLength}}, length = {{.MaxLength}}{{end}})
    private {{.JavaType}} {{.FieldName}};
    {{end}}
}
`

var repositoryTemplate = `package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import entities.{{.ClassName}};
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad {{.ClassName}}
 * 
 * <p>Proporciona métodos para acceder a los datos de la tabla {{.TableName}}</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
@Repository
public interface {{.ClassName}}Repository extends JpaRepository<{{.ClassName}}, Long> {
    {{range .Columns}}
    {{if eq .ColumnName "name"}}
    /**
     * Busca por nombre
     */
    Optional<{{$.ClassName}}> findByName(String name);
    {{end}}
    {{if eq .ColumnName "email"}}
    /**
     * Busca por email
     */
    Optional<{{$.ClassName}}> findByEmail(String email);
    {{end}}
    {{if eq .ColumnName "is_active"}}
    /**
     * Busca registros activos
     */
    List<{{$.ClassName}}> findByIsActiveTrue();
    
    /**
     * Busca registros inactivos
     */
    List<{{$.ClassName}}> findByIsActiveFalse();
    {{end}}
    {{if and (eq .ColumnName "username") (eq $.TableName "users")}}
    /**
     * Busca por username
     */
    Optional<{{$.ClassName}}> findByUsername(String username);
    {{end}}{{end}}
    
    /**
     * Cuenta registros activos
     */
    {{range .Columns}}{{if eq .ColumnName "is_active"}}long countByIsActiveTrue();{{end}}{{end}}
}
`

var dtoTemplate = `package dtos;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * DTO para {{.ClassName}}
 * 
 * <p>Esta clase representa el objeto de transferencia de datos para {{.TableName}}</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class {{.ClassName}}DTO {
    {{range .Columns}}
    {{if not .IsNullable}}@NotNull(message = "{{.FieldName}} no puede ser nulo"){{end}}
    {{if eq .DataType "varchar"}}@Size(max = {{.MaxLength}}, message = "{{.FieldName}} no puede exceder {{.MaxLength}} caracteres"){{end}}
    {{if or (eq .ColumnName "email") (eq .ColumnName "email_address")}}@Email(message = "Formato de email inválido"){{end}}
    private {{.JavaType}} {{.FieldName}};
    {{end}}
}
`

var serviceTemplate = `package services;

import dtos.{{.ClassName}}DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Servicio para la entidad {{.ClassName}}
 * 
 * <p>Contiene la lógica de negocio para operaciones relacionadas con {{.TableName}}</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
public interface {{.ClassName}}Service {
    
    /**
     * Obtiene todos los registros
     */
    List<{{.ClassName}}DTO> findAll();
    
    /**
     * Obtiene todos los registros con paginación
     */
    Page<{{.ClassName}}DTO> findAll(Pageable pageable);
    
    /**
     * Busca por ID
     */
    {{.ClassName}}DTO findById(Long id);
    
    /**
     * Guarda un nuevo registro
     */
    {{.ClassName}}DTO create({{.ClassName}}DTO {{.LowerClassName}}DTO);
    
    /**
     * Actualiza un registro existente
     */
    {{.ClassName}}DTO update(Long id, {{.ClassName}}DTO {{.LowerClassName}}DTO);
    
    /**
     * Elimina un registro por ID
     */
    void delete(Long id);
    
    /**
     * Verifica si existe un registro con el ID especificado
     */
    boolean exists(Long id);
    {{range .Columns}}
    {{if eq .ColumnName "name"}}
    /**
     * Busca por nombre
     */
    {{$.ClassName}}DTO findByName(String name);
    {{end}}
    {{if eq .ColumnName "email"}}
    /**
     * Busca por email
     */
    {{$.ClassName}}DTO findByEmail(String email);
    {{end}}
    {{if eq .ColumnName "is_active"}}
    /**
     * Busca todos los registros activos
     */
    List<{{$.ClassName}}DTO> findAllActive();
    {{end}}{{end}}
}
`

var serviceImplTemplate = `package services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.{{.ClassName}}Service;
import dtos.{{.ClassName}}DTO;
import entities.{{.ClassName}};
import repositories.{{.ClassName}}Repository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para {{.ClassName}}
 * 
 * <p>Implementa las operaciones de negocio para {{.TableName}}</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class {{.ClassName}}ServiceImpl implements {{.ClassName}}Service {
    
    private final {{.ClassName}}Repository {{.LowerClassName}}Repository;
    
    /**
     * Convierte Entity a DTO
     */
    private {{.ClassName}}DTO toDTO({{.ClassName}} entity) {
        if (entity == null) {
            return null;
        }
        
        return {{.ClassName}}DTO.builder(){{range .Columns}}
            .{{.FieldName}}(entity.get{{.FieldNameTitle}}()){{end}}
            .build();
    }
    
    /**
     * Convierte DTO a Entity
     */
    private {{.ClassName}} toEntity({{.ClassName}}DTO dto) {
        if (dto == null) {
            return null;
        }
        
        return {{.ClassName}}.builder(){{range .Columns}}
            .{{.FieldName}}(dto.get{{.FieldNameTitle}}()){{end}}
            .build();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<{{.ClassName}}DTO> findAll() {
        log.info("Obteniendo todos los registros de {{.ClassName}}");
        return {{.LowerClassName}}Repository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<{{.ClassName}}DTO> findAll(Pageable pageable) {
        log.info("Obtenendo registros de {{.ClassName}} paginados: {}", pageable);
        return {{.LowerClassName}}Repository.findAll(pageable)
            .map(this::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public {{.ClassName}}DTO findById(Long id) {
        log.info("Buscando {{.ClassName}} con ID: {}", id);
        return {{.LowerClassName}}Repository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("{{.ClassName}} no encontrado con ID: " + id));
    }
    
    @Override
    public {{.ClassName}}DTO create({{.ClassName}}DTO {{.LowerClassName}}DTO) {
        log.info("Creando nuevo {{.ClassName}}");
        {{.ClassName}} entity = toEntity({{.LowerClassName}}DTO);
        {{.ClassName}} saved = {{.LowerClassName}}Repository.save(entity);
        return toDTO(saved);
    }
    
    @Override
    public {{.ClassName}}DTO update(Long id, {{.ClassName}}DTO {{.LowerClassName}}DTO) {
        log.info("Actualizando {{.ClassName}} con ID: {}", id);
        if (!{{.LowerClassName}}Repository.existsById(id)) {
            throw new RuntimeException("{{.ClassName}} no encontrado con ID: " + id);
        }
        
        {{.ClassName}} entity = toEntity({{.LowerClassName}}DTO);
        // Asegurar que el ID se mantenga
        {{range .Columns}}{{if .IsPrimaryKey}}entity.set{{.FieldNameTitle}}(id);{{end}}{{end}}
        
        {{.ClassName}} updated = {{.LowerClassName}}Repository.save(entity);
        return toDTO(updated);
    }
    
    @Override
    public void delete(Long id) {
        log.info("Eliminando {{.ClassName}} con ID: {}", id);
        if (!{{.LowerClassName}}Repository.existsById(id)) {
            throw new RuntimeException("{{.ClassName}} no encontrado con ID: " + id);
        }
        {{.LowerClassName}}Repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return {{.LowerClassName}}Repository.existsById(id);
    }
    {{range .Columns}}
    {{if eq .ColumnName "name"}}
    @Override
    @Transactional(readOnly = true)
    public {{$.ClassName}}DTO findByName(String name) {
        log.info("Buscando {{$.ClassName}} por nombre: {}", name);
        return {{$.LowerClassName}}Repository.findByName(name)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("{{$.ClassName}} no encontrado con nombre: " + name));
    }
    {{end}}
    {{if eq .ColumnName "email"}}
    @Override
    @Transactional(readOnly = true)
    public {{$.ClassName}}DTO findByEmail(String email) {
        log.info("Buscando {{$.ClassName}} por email: {}", email);
        return {{$.LowerClassName}}Repository.findByEmail(email)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("{{$.ClassName}} no encontrado con email: " + email));
    }
    {{end}}
    {{if eq .ColumnName "is_active"}}
    @Override
    @Transactional(readOnly = true)
    public List<{{$.ClassName}}DTO> findAllActive() {
        log.info("Obteniendo todos los registros activos de {{$.ClassName}}");
        return {{$.LowerClassName}}Repository.findByIsActiveTrue()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    {{end}}{{end}}
}
`

var mapperTemplate = `package mappers;

import org.mapstruct.*;
import entities.{{.ClassName}};
import dtos.{{.ClassName}}DTO;

/**
 * Mapper para convertir entre {{.ClassName}} y {{.ClassName}}DTO
 * 
 * <p>Utiliza MapStruct para mapeo automático entre entidad y DTO</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
@Mapper(componentModel = "spring")
public interface {{.ClassName}}Mapper {
    
    /**
     * Convierte Entity a DTO
     */
    {{.ClassName}}DTO toDTO({{.ClassName}} entity);
    
    /**
     * Convierte DTO a Entity
     */
    {{.ClassName}} toEntity({{.ClassName}}DTO dto);
    
    /**
     * Actualiza Entity desde DTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO({{.ClassName}}DTO dto, @MappingTarget {{.ClassName}} entity);
}
`

var controllerTemplate = `package controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.{{.ClassName}}Service;
import dtos.{{.ClassName}}DTO;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST para {{.ClassName}}
 * 
 * <p>Expose endpoints REST para operaciones CRUD en {{.TableName}}</p>
 * 
 * @author Generated on {{.Timestamp}}
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{{.KebabCaseName}}")
public class {{.ClassName}}Controller {
    
    private final {{.ClassName}}Service {{.LowerClassName}}Service;
    
    @GetMapping
    public ResponseEntity<List<{{.ClassName}}DTO>> getAll() {
        log.info("GET /api/{{.KebabCaseName}}");
        List<{{.ClassName}}DTO> result = {{.LowerClassName}}Service.findAll();
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/page")
    public ResponseEntity<Page<{{.ClassName}}DTO>> getAll(Pageable pageable) {
        log.info("GET /api/{{.KebabCaseName}}/page");
        Page<{{.ClassName}}DTO> result = {{.LowerClassName}}Service.findAll(pageable);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<{{.ClassName}}DTO> getById(@PathVariable Long id) {
        log.info("GET /api/{{.KebabCaseName}}/{}", id);
        {{.ClassName}}DTO result = {{.LowerClassName}}Service.findById(id);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    public ResponseEntity<{{.ClassName}}DTO> create(@Valid @RequestBody {{.ClassName}}DTO {{.LowerClassName}}DTO) {
        log.info("POST /api/{{.KebabCaseName}}");
        {{.ClassName}}DTO result = {{.LowerClassName}}Service.create({{.LowerClassName}}DTO);
        return ResponseEntity.created(URI.create("/api/{{.KebabCaseName}}/" + result.getId()))
                .body(result);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<{{.ClassName}}DTO> update(@PathVariable Long id, @Valid @RequestBody {{.ClassName}}DTO {{.LowerClassName}}DTO) {
        log.info("PUT /api/{{.KebabCaseName}}/{}", id);
        {{.ClassName}}DTO result = {{.LowerClassName}}Service.update(id, {{.LowerClassName}}DTO);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/{{.KebabCaseName}}/{}", id);
        {{.LowerClassName}}Service.delete(id);
        return ResponseEntity.noContent().build();
    }
    {{range .Columns}}
    {{if eq .ColumnName "name"}}
    @GetMapping("/name/{name}")
    public ResponseEntity<{{$.ClassName}}DTO> getByName(@PathVariable String name) {
        log.info("GET /api/{{$.KebabCaseName}}/name/{}", name);
        {{$.ClassName}}DTO result = {{$.LowerClassName}}Service.findByName(name);
        return ResponseEntity.ok(result);
    }
    {{end}}
    {{if eq .ColumnName "email"}}
    @GetMapping("/email/{email}")
    public ResponseEntity<{{$.ClassName}}DTO> getByEmail(@PathVariable String email) {
        log.info("GET /api/{{$.KebabCaseName}}/email/{}", email);
        {{$.ClassName}}DTO result = {{$.LowerClassName}}Service.findByEmail(email);
        return ResponseEntity.ok(result);
    }
    {{end}}
    {{if eq .ColumnName "is_active"}}
    @GetMapping("/active")
    public ResponseEntity<List<{{$.ClassName}}DTO>> getActive() {
        log.info("GET /api/{{$.KebabCaseName}}/active");
        List<{{$.ClassName}}DTO> result = {{$.LowerClassName}}Service.findAllActive();
        return ResponseEntity.ok(result);
    }
    {{end}}{{end}}
}
`

func main() {
	// Configuración de flags
	jsonFile := flag.String("json", "", "Ruta al archivo JSON con el esquema de la base de datos")
	entitiesPath := flag.String("entities", "./src/main/java/com/example/entities", "Ruta de salida para las entidades")
	repositoriesPath := flag.String("repositories", "./src/main/java/com/example/repositories", "Ruta de salida para los repositorios")
	dtosPath := flag.String("dtos", "./src/main/java/com/example/dtos", "Ruta de salida para los DTOs")
	servicesPath := flag.String("services", "./src/main/java/com/example/services", "Ruta de salida para las interfaces de servicio")
	implementationsPath := flag.String("implementations", "./src/main/java/com/example/services/impl", "Ruta de salida para las implementaciones de servicio")
	mappersPath := flag.String("mappers", "./src/main/java/com/example/mappers", "Ruta de salida para los mappers")
	controllersPath := flag.String("controllers", "./src/main/java/com/example/controllers", "Ruta de salida para los controladores")

	flag.Parse()

	// Validar que se proporcionó el archivo JSON
	if *jsonFile == "" {
		log.Fatal("Debe especificar la ruta al archivo JSON usando -json")
	}

	// Crear estructura de rutas
	paths := OutputPaths{
		EntitiesPath:        *entitiesPath,
		RepositoriesPath:    *repositoriesPath,
		ModelsPath:          *dtosPath,
		ServicesPath:        *servicesPath,
		ImplementationsPath: *implementationsPath,
		DTOsPath:            *dtosPath,
	}

	// Crear directorios si no existen
	createDirectories(paths, *mappersPath, *controllersPath)

	// Leer y parsear el archivo JSON
	schema, err := readSchema(*jsonFile)
	if err != nil {
		log.Fatalf("Error leyendo el archivo JSON: %v", err)
	}

	// Generar las clases para cada tabla
	for _, table := range schema.Tables {
		err := generateClasses(table, paths, *mappersPath, *controllersPath)
		if err != nil {
			log.Printf("Error generando clases para %s: %v", table.TableName, err)
		}
	}

	fmt.Println("Generación completada exitosamente!")
	fmt.Printf("Entidades: %s\n", paths.EntitiesPath)
	fmt.Printf("Repositorios: %s\n", paths.RepositoriesPath)
	fmt.Printf("DTOs: %s\n", paths.DTOsPath)
	fmt.Printf("Servicios: %s\n", paths.ServicesPath)
	fmt.Printf("Implementaciones: %s\n", paths.ImplementationsPath)
	fmt.Printf("Mappers: %s\n", *mappersPath)
	fmt.Printf("Controladores: %s\n", *controllersPath)
}

func readSchema(filename string) (*DatabaseSchema, error) {
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	var schema DatabaseSchema
	err = json.Unmarshal(data, &schema)
	if err != nil {
		return nil, err
	}

	return &schema, nil
}

func createDirectories(paths OutputPaths, mappersPath, controllersPath string) {
	dirs := []string{
		paths.EntitiesPath,
		paths.RepositoriesPath,
		paths.DTOsPath,
		paths.ServicesPath,
		paths.ImplementationsPath,
		mappersPath,
		controllersPath,
	}

	for _, dir := range dirs {
		if err := os.MkdirAll(dir, 0755); err != nil {
			log.Fatalf("Error creando directorio %s: %v", dir, err)
		}
	}
}

func generateClasses(table Table, paths OutputPaths, mappersPath, controllersPath string) error {
	className := toCamelCase(table.TableName, true)
	lowerClassName := toCamelCase(table.TableName, false)
	kebabCaseName := toKebabCase(table.TableName)

	// Preparar datos para los templates
	templateData := struct {
		TableName      string
		Schema         string
		ClassName      string
		LowerClassName string
		KebabCaseName  string
		Columns        []ColumnTemplate
		Timestamp      string
	}{
		TableName:      table.TableName,
		Schema:         table.Schema,
		ClassName:      className,
		LowerClassName: lowerClassName,
		KebabCaseName:  kebabCaseName,
		Columns:        prepareColumns(table.Columns),
		Timestamp:      time.Now().Format("2006-01-02 15:04:05"),
	}

	// Generar Entity
	if err := generateFile(filepath.Join(paths.EntitiesPath, className+".java"), entityTemplate, templateData); err != nil {
		return err
	}

	// Generar Repository
	if err := generateFile(filepath.Join(paths.RepositoriesPath, className+"Repository.java"), repositoryTemplate, templateData); err != nil {
		return err
	}

	// Generar DTO
	if err := generateFile(filepath.Join(paths.DTOsPath, className+"DTO.java"), dtoTemplate, templateData); err != nil {
		return err
	}

	// Generar Service Interface
	if err := generateFile(filepath.Join(paths.ServicesPath, className+"Service.java"), serviceTemplate, templateData); err != nil {
		return err
	}

	// Generar Service Implementation
	if err := generateFile(filepath.Join(paths.ImplementationsPath, className+"ServiceImpl.java"), serviceImplTemplate, templateData); err != nil {
		return err
	}

	// Generar Mapper
	if err := generateFile(filepath.Join(mappersPath, className+"Mapper.java"), mapperTemplate, templateData); err != nil {
		return err
	}

	// Generar Controller
	if err := generateFile(filepath.Join(controllersPath, className+"Controller.java"), controllerTemplate, templateData); err != nil {
		return err
	}

	return nil
}

func generateFile(filename string, templateStr string, data interface{}) error {
	tmpl, err := template.New("file").Parse(templateStr)
	if err != nil {
		return err
	}

	file, err := os.Create(filename)
	if err != nil {
		return err
	}
	defer file.Close()

	return tmpl.Execute(file, data)
}

type ColumnTemplate struct {
	ColumnName     string
	DataType       string
	IsNullable     string
	MaxLength      int
	Precision      int
	Scale          int
	IsPrimaryKey   bool
	IsIdentity     bool
	DefaultValue   string
	JavaType       string
	FieldName      string
	FieldNameTitle string
}

func prepareColumns(columns []Column) []ColumnTemplate {
	var result []ColumnTemplate

	for _, col := range columns {
		javaType := sqlToJavaType(col.DataType, col.Precision, col.Scale)
		fieldName := toCamelCase(col.ColumnName, false)
		fieldNameTitle := toCamelCase(col.ColumnName, true)

		result = append(result, ColumnTemplate{
			ColumnName:     col.ColumnName,
			DataType:       col.DataType,
			IsNullable:     col.IsNullable,
			MaxLength:      col.MaxLength,
			Precision:      col.Precision,
			Scale:          col.Scale,
			IsPrimaryKey:   col.IsPrimaryKey,
			IsIdentity:     col.IsIdentity,
			DefaultValue:   col.DefaultValue,
			JavaType:       javaType,
			FieldName:      fieldName,
			FieldNameTitle: fieldNameTitle,
		})
	}

	return result
}

func sqlToJavaType(sqlType string, precision, scale int) string {
	switch strings.ToLower(sqlType) {
	case "bigint":
		return "Long"
	case "int", "integer":
		return "Integer"
	case "smallint":
		return "Short"
	case "tinyint":
		return "Byte"
	case "bit":
		return "Boolean"
	case "decimal", "numeric":
		if scale > 0 {
			return "BigDecimal"
		}
		if precision > 10 {
			return "Long"
		}
		return "Integer"
	case "money", "smallmoney":
		return "BigDecimal"
	case "float", "real":
		return "Double"
	case "datetime", "datetime2", "smalldatetime", "timestamp":
		return "LocalDateTime"
	case "date":
		return "java.time.LocalDate"
	case "time":
		return "java.time.LocalTime"
	case "char", "varchar", "text", "nchar", "nvarchar", "ntext":
		return "String"
	case "binary", "varbinary", "image":
		return "byte[]"
	default:
		return "String"
	}
}

func toCamelCase(s string, capitalizeFirst bool) string {
	parts := strings.Split(s, "_")
	for i, part := range parts {
		if i == 0 && !capitalizeFirst {
			parts[i] = strings.ToLower(part)
		} else {
			if len(part) > 0 {
				parts[i] = strings.ToUpper(part[:1]) + strings.ToLower(part[1:])
			}
		}
	}
	return strings.Join(parts, "")
}

func toKebabCase(s string) string {
	return strings.ReplaceAll(strings.ToLower(s), "_", "-")
}
```

### Compilar
```
go build -o spring-generator spring-generator.go
```
## Uso

### Ejecutar en linux
```bash
./spring-generator \
-json JHTechnologiesSV_esquema.json \
-entities ./src/main/java/com/example/entities \ 
-repositories ./src/main/java/com/example/repositories \ 
-dtos ./src/main/java/com/example/dtos  \
-services ./src/main/java/com/example/services \ 
-implementations ./src/main/java/com/example/services/impl \ 
-mappers ./src/main/java/com/example/mappers \
-controllers ./src/main/java/com/example/controllers
```
