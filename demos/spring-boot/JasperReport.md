# Jasper Report

### pom.xml
```
<!-- JasperReports dependency -->
<dependency>
  <groupId>net.sf.jasperreports</groupId>
  <artifactId>jasperreports</artifactId>
  <version>6.21.3</version>
  <exclusions>
    <exclusion>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

### cliente_report.jrxml
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd"
  name="cliente_report"
  pageWidth="595" pageHeight="842"
  columnWidth="555" leftMargin="20" rightMargin="20"
  topMargin="20" bottomMargin="20">

  <parameter name="titulo" class="java.lang.String" />
  <parameter name="empresa" class="java.lang.String" />
  <parameter name="LOGO" class="java.io.InputStream" />

  <field name="id" class="java.lang.Long" />
  <field name="name" class="java.lang.String" />

  <title>
    <band height="110">
      <image scaleImage="RetainShape">
        <reportElement x="0" y="5" width="150" height="50" />
        <imageExpression><![CDATA[$P{LOGO}]]></imageExpression>
      </image>
      <textField>
        <reportElement x="150" y="0" width="405" height="45" />
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font size="20" isBold="true" />
        </textElement>
        <textFieldExpression><![CDATA[$P{empresa}]]></textFieldExpression>
      </textField>
      <line>
        <reportElement x="0" y="60" width="555" height="1" forecolor="#4A90D9" />
      </line>
      <textField>
        <reportElement x="0" y="65" width="555" height="30" />
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font size="14" />
        </textElement>
        <textFieldExpression><![CDATA[$P{titulo}]]></textFieldExpression>
      </textField>
    </band>
  </title>

  <columnHeader>
    <band height="30">
      <staticText>
        <reportElement x="0" y="0" width="80" height="30" backcolor="#4A90D9" forecolor="#FFFFFF"
          mode="Opaque" />
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font isBold="true" />
        </textElement>
        <text><![CDATA[ID]]></text>
      </staticText>
      <staticText>
        <reportElement x="80" y="0" width="475" height="30" backcolor="#4A90D9" forecolor="#FFFFFF"
          mode="Opaque" />
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font isBold="true" />
        </textElement>
        <text><![CDATA[Nombre]]></text>
      </staticText>
    </band>
  </columnHeader>

  <detail>
    <band height="25">
      <rectangle>
        <reportElement x="0" y="0" width="555" height="25" backcolor="#EEEEEE" mode="Opaque">
          <printWhenExpression><![CDATA[$V{REPORT_COUNT} % 2 == 0]]></printWhenExpression>
        </reportElement>
        <graphicElement>
          <pen lineWidth="0" />
        </graphicElement>
      </rectangle>
      <rectangle>
        <reportElement x="0" y="0" width="555" height="25" backcolor="#FFFFFF" mode="Opaque">
          <printWhenExpression><![CDATA[$V{REPORT_COUNT} % 2 != 0]]></printWhenExpression>
        </reportElement>
        <graphicElement>
          <pen lineWidth="0" />
        </graphicElement>
      </rectangle>
      <textField>
        <reportElement x="0" y="0" width="80" height="25" />
        <textElement textAlignment="Center" verticalAlignment="Middle" />
        <textFieldExpression><![CDATA[$F{id}]]></textFieldExpression>
      </textField>
      <textField>
        <reportElement x="80" y="0" width="475" height="25" />
        <textElement verticalAlignment="Middle">
          <font />
        </textElement>
        <textFieldExpression><![CDATA[$F{name}]]></textFieldExpression>
      </textField>
    </band>
  </detail>

  <pageFooter>
    <band height="25">
      <textField>
        <reportElement x="0" y="0" width="555" height="25" />
        <textElement textAlignment="Right" verticalAlignment="Middle">
          <font size="8" />
        </textElement>
        <textFieldExpression><![CDATA["Página " + $V{PAGE_NUMBER}]]></textFieldExpression>
      </textField>
    </band>
  </pageFooter>

</jasperReport>

```

## Objetos Base

### Entity "Cliente.java"
```
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

}
```

### Repository "IClienteRepository.java"
```
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

}
```

### Service "IClienteService.java"
```
import java.util.List;

public interface IClienteService {

  // Salvar um cliente
  public Cliente save(Cliente cliente);

  // Listar todos os clientes
  public List<Cliente> findAll();

  // Buscar um cliente por ID
  public Cliente findById(Long id);

  // Deletar um cliente por ID
  public void deleteById(Long id);
}
```

### Implementacion "ClienteServiceImpl.java"
```
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

  private final IClienteRepository clienteRepository;

  @Override
  public Cliente save(Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @Override
  public List<Cliente> findAll() {
    return clienteRepository.findAll();
  }

  @Override
  public Cliente findById(Long id) {
    return clienteRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    clienteRepository.deleteById(id);
  }

}
```

### Controllador "ClienteController.java"
```
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

  private final IClienteService clienteService;
  private final IReportService reportService;

  @GetMapping
  public ResponseEntity<List<Cliente>> findAll() {
    return ResponseEntity.ok(clienteService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> findById(@PathVariable Long id) {
    Cliente cliente = clienteService.findById(id);
    if (cliente == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(cliente);
  }

  @PostMapping
  public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
    if (clienteService.findById(id) == null) {
      return ResponseEntity.notFound().build();
    }
    cliente.setId(id);
    return ResponseEntity.ok(clienteService.save(cliente));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    if (clienteService.findById(id) == null) {
      return ResponseEntity.notFound().build();
    }
    clienteService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
```

### la clase "Load.java" en el paquete configuration
```
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Load {

  private final IClienteService clienteService;

  @Bean
  CommandLineRunner initData() {
    return args -> {
      clienteService.save(new Cliente(null, "Hector"));
      clienteService.save(new Cliente(null, "Walter"));
    };
  }
}

```

## Objetos Jaspert Report

### DTO "ReportRequestDto.java"
```
import lombok.Data;

@Data
public class ReportRequestDto {

 private String titulo; // Título del reporte, se puede usar como parámetro en el template JRXML
  private String empresa; // Nombre de la empresa u organización
  private String formato; // PDF, EXCEL, WORD
  private Boolean paginated; // true para Word, configurable para Excel

}

}
```

### DTO "ReportResponseDto.java"
```
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportResponseDto {

  private String base64;

}
```

### Interfase "IReportService.java"
```
import java.util.List;
import java.util.Map;

public interface IReportService {

  /**
   * Genera un PDF a partir de un template JRXML y retorna el resultado en Base64.
   *
   * @param templatePath ruta del template dentro de resources (ej:
   *                     /reports/cliente_report.jrxml)
   * @param parameters   parámetros enviados al reporte (titulo, empresa, etc.)
   * @param data         lista de objetos con los datos del reporte
   * @return PDF codificado en Base64
   */
  String generatePdfBase64(String templatePath, Map<String, Object> parameters, List<?> data) throws Exception;

  /**
   * Genera un reporte en el formato especificado y retorna el resultado en
   * Base64.
   *
   * @param templatePath ruta del template dentro de resources
   * @param parameters   parámetros enviados al reporte
   * @param data         lista de objetos con los datos del reporte
   * @param formato      formato del reporte (PDF, EXCEL, WORD)
   * @param paginated    true para paginar, false para no paginar (aplica
   *                     principalmente a EXCEL)
   * @return Archivo codificado en Base64
   */
  String generateReportBase64(String templatePath, Map<String, Object> parameters, List<?> data,
      String formato, boolean paginated) throws Exception;


}
```

### Implementacion "ReportServiceImpl.java"
```
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

@Service
public class ReportServiceImpl implements IReportService {

  @Override
  public String generatePdfBase64(String templatePath, Map<String, Object> parameters, List<?> data) throws Exception {
    InputStream template = getClass().getResourceAsStream(templatePath);
    JasperReport jasperReport = JasperCompileManager.compileReport(template);
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
    return Base64.getEncoder().encodeToString(pdfBytes);
  }

  @Override
  public String generateReportBase64(String templatePath, Map<String, Object> parameters, List<?> data,
      String formato, boolean paginated) throws Exception {

    InputStream template = getClass().getResourceAsStream(templatePath);
    JasperReport jasperReport = JasperCompileManager.compileReport(template);
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

    byte[] reportBytes;

    switch (formato.toUpperCase()) {
      case "PDF":
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        break;
      case "EXCEL":
        reportBytes = exportToExcel(jasperPrint, paginated);
        break;
      case "WORD":
        reportBytes = exportToWord(jasperPrint, paginated);
        break;
      default:
        throw new IllegalArgumentException("Formato no soportado: " + formato);
    }

    return Base64.getEncoder().encodeToString(reportBytes);
  }

  private byte[] exportToExcel(JasperPrint jasperPrint, boolean paginated) throws Exception {
    JRXlsxExporter exporter = new JRXlsxExporter();
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    SimpleXlsxExporterConfiguration config = new SimpleXlsxExporterConfiguration();

    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
    exporter.setConfiguration(config);
    exporter.exportReport();

    return output.toByteArray();
  }

  private byte[] exportToWord(JasperPrint jasperPrint, boolean paginated) throws Exception {
    JRDocxExporter exporter = new JRDocxExporter();
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    SimpleDocxExporterConfiguration config = new SimpleDocxExporterConfiguration();

    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
    exporter.setConfiguration(config);
    exporter.exportReport();

    return output.toByteArray();
  }

}
```

### Adicionar al Controllador "ClienteController"
```
  @PostMapping("/report")
  public ResponseEntity<ReportResponseDto> generateReport(@RequestBody ReportRequestDto request) {
    try {
      List<Cliente> clientes = clienteService.findAll();
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("titulo", request.getTitulo());
      parameters.put("empresa", request.getEmpresa());
      parameters.put("LOGO", getClass().getResourceAsStream("/reports/logo.png"));

      // Obtener el formato solicitado (por defecto PDF)
      String formato = request.getFormato() != null ? request.getFormato() : "PDF";

      // Determinar si debe paginar
      boolean paginated = true; // Por defecto pagina (para Word y PDF)
      if ("EXCEL".equalsIgnoreCase(formato)) {
        // Para Excel, verificar si debe paginar (si no está especificado, no pagina)
        paginated = request.getPaginated() != null && request.getPaginated();
      }

      String base64 = reportService.generateReportBase64("/reports/cliente_report.jrxml", parameters, clientes, formato,
          paginated);
      return ResponseEntity.ok(new ReportResponseDto(base64));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
```

# demo-jasper

API REST construida con **Spring Boot 4** que gestiona clientes y genera reportes PDF usando **JasperReports**, retornando el resultado en **Base64**.

---

## Tecnologías

| Tecnología        | Versión  |
|-------------------|----------|
| Java              | 17       |
| Spring Boot       | 4.0.6    |
| Spring Data JPA   | -        |
| H2 Database       | en memoria |
| Lombok            | -        |
| JasperReports     | 6.21.3   |

---

## Estructura del proyecto

```
src/main/java/com/example/demo_jasper/
├── configuration/
│   └── Load.java               # Carga datos iniciales al arrancar (CommandLineRunner)
├── controller/
│   └── ClienteController.java  # Endpoints REST para clientes y reportes
├── dto/
│   ├── ReportRequestDto.java   # Parámetros de entrada para el reporte (titulo, empresa)
│   └── ReportResponseDto.java  # Respuesta del reporte en Base64
├── entity/
│   └── Cliente.java            # Entidad JPA mapeada a la tabla "clientes"
├── implement/
│   └── ClienteServiceImpl.java # Implementación de IClienteService
├── report/
│   ├── IReportService.java     # Interfaz genérica de reportes (reutilizable)
│   └── ReportServiceImpl.java  # Implementación con JasperReports
├── repository/
│   └── IClienteRepository.java # Repositorio JPA (extiende JpaRepository)
└── services/
    └── IClienteService.java    # Interfaz de negocio para clientes

src/main/resources/
├── application.properties      # Configuración de la aplicación
└── reports/
    ├── cliente_report.jrxml    # Template JasperReports para el listado de clientes
    └── logo.png                # Logo que se muestra en el encabezado del reporte PDF
```

---

## Configuración

```properties
server.port=8080

# Base de datos H2 en memoria
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Consola H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

> La consola H2 está disponible en: `http://localhost:8080/h2-console`
> JDBC URL: `jdbc:h2:mem:testdb` | Usuario: `sa` | Contraseña: *(vacía)*

---

## Datos iniciales

Al iniciar la aplicación, la clase `Load` inserta automáticamente los siguientes clientes:

| ID | Nombre |
|----|--------|
| 1  | Hector |
| 2  | Walter |

---

## Endpoints

Base URL: `http://localhost:8080/api/clientes`

### Clientes

| Método   | Ruta              | Descripción                    | Cuerpo (JSON)                  |
|----------|-------------------|--------------------------------|--------------------------------|
| `GET`    | `/`               | Lista todos los clientes       | -                              |
| `GET`    | `/{id}`           | Obtiene un cliente por ID      | -                              |
| `POST`   | `/`               | Crea un nuevo cliente          | `{ "name": "Juan" }`           |
| `PUT`    | `/{id}`           | Actualiza un cliente existente | `{ "name": "Juan Actualizado"}`|
| `DELETE` | `/{id}`           | Elimina un cliente por ID      | -                              |

### Reportes

| Método | Ruta      | Descripción                                           | Formatos soportados |
|--------|-----------|-------------------------------------------------------|---------------------|
| `POST` | `/report` | Genera reportes del listado de clientes en Base64     | PDF, Excel, Word    |

**Request body:**
```json
{
  "titulo": "Listado de Clientes",
  "empresa": "Mi Empresa S.A.",
  "formato": "PDF",
  "paginated": true
}
```

**Parámetros:**
- `titulo` (string): Título del reporte
- `empresa` (string): Nombre de la empresa
- `formato` (string, opcional): Formato del reporte. Opciones: `PDF`, `EXCEL`, `WORD`. **Defecto:** `PDF`
- `paginated` (boolean, opcional):
  - Para **Excel**: `false` para una sola hoja (defecto), `true` para paginar
  - Para **Word**: Se respeta el valor especificado (defecto `true`)
  - Para **PDF**: Siempre pagina

**Response:**
```json
{
  "base64": "JVBERi0xLjQu..."
}
```

> Para visualizar el PDF/Excel/Word puedes pegar el valor de `base64` en [base64.guru](https://base64.guru/converter/decode)

---

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

---

## Generación de reportes en múltiples formatos

Desde la versión actualizada, el endpoint `/api/clientes/report` soporta tres formatos: **PDF**, **Excel** y **Word**.

### Ejemplos de uso

#### 1. Generar reporte en PDF (defecto)
```json
POST /api/clientes/report

{
  "titulo": "Reporte de Clientes",
  "empresa": "Mi Empresa",
  "formato": "PDF"
}
```

#### 2. Generar reporte en Excel (sin paginar - una sola hoja)
```json
POST /api/clientes/report

{
  "titulo": "Reporte de Clientes",
  "empresa": "Mi Empresa",
  "formato": "EXCEL",
  "paginated": false
}
```

#### 3. Generar reporte en Excel (paginado - múltiples hojas)
```json
POST /api/clientes/report

{
  "titulo": "Reporte de Clientes",
  "empresa": "Mi Empresa",
  "formato": "EXCEL",
  "paginated": true
}
```

#### 4. Generar reporte en Word
```json
POST /api/clientes/report

{
  "titulo": "Reporte de Clientes",
  "empresa": "Mi Empresa",
  "formato": "WORD",
  "paginated": true
}
```

### Comportamiento por formato

| Formato | Sin `paginated` | Con `paginated: false` | Con `paginated: true` |
|---------|-----------------|------------------------|----------------------|
| **PDF** | Pagina (defecto) | Pagina | Pagina |
| **EXCEL** | Una sola hoja | Una sola hoja | Múltiples hojas (una por página) |
| **WORD** | Pagina | Respeta config | Respeta config |

---

## Generar un nuevo reporte (uso genérico)

El servicio `IReportService` está diseñado para ser reutilizado con cualquier entidad y template.

**Pasos:**

1. Crea un nuevo template `.jrxml` en `src/main/resources/reports/`
2. Inyecta `IReportService` en tu controlador o servicio:

```java
private final IReportService reportService;
```

3. **Opción A:** Llama al método clásico para generar PDF:

```java
Map<String, Object> parameters = new HashMap<>();
parameters.put("titulo", "Mi Reporte");
parameters.put("empresa", "Mi Empresa");
parameters.put("LOGO", getClass().getResourceAsStream("/reports/logo.png"));

String base64 = reportService.generatePdfBase64(
    "/reports/mi_reporte.jrxml",
    parameters,
    miListaDeDatos
);
```

4. **Opción B:** Llama al método nuevo para generar en múltiples formatos:

```java
Map<String, Object> parameters = new HashMap<>();
parameters.put("titulo", "Mi Reporte");
parameters.put("empresa", "Mi Empresa");

// Generar en Excel sin paginar
String base64 = reportService.generateReportBase64(
    "/reports/mi_reporte.jrxml",
    parameters,
    miListaDeDatos,
    "EXCEL",
    false  // sin paginar
);

// Generar en Word paginado
String base64 = reportService.generateReportBase64(
    "/reports/mi_reporte.jrxml",
    parameters,
    miListaDeDatos,
    "WORD",
    true  // con paginación
);
```

### Parámetros disponibles en los templates JRXML

| Parámetro  | Tipo              | Descripción                                      |
|------------|-------------------|--------------------------------------------------|
| `titulo`   | `String`          | Título del reporte                               |
| `empresa`  | `String`          | Nombre de la empresa                             |
| `LOGO`     | `InputStream`     | Logo mostrado en el encabezado (imagen PNG/JPG)  |

Puedes agregar más parámetros en el `Map` y declararlos en el `.jrxml` con:
```xml
<parameter name="miParametro" class="java.lang.String"/>
```

---

## Entidad Cliente

```java
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
```
