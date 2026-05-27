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
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN"
  "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports
    http://jasperreports.sourceforge.net/xsd/jasperreport.xsd"
  name="cliente_report"
  pageWidth="595" pageHeight="842"
  columnWidth="555" leftMargin="20" rightMargin="20"
  topMargin="20" bottomMargin="20">

  <parameter name="titulo" class="java.lang.String"/>
  <parameter name="empresa" class="java.lang.String"/>

  <field name="id" class="java.lang.Long"/>
  <field name="name" class="java.lang.String"/>

  <title>
    <band height="90">
      <textField>
        <reportElement x="0" y="0" width="555" height="40"/>
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font size="20" isBold="true"/>
        </textElement>
        <textFieldExpression><![CDATA[$P{empresa}]]></textFieldExpression>
      </textField>
      <textField>
        <reportElement x="0" y="45" width="555" height="30"/>
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font size="14"/>
        </textElement>
        <textFieldExpression><![CDATA[$P{titulo}]]></textFieldExpression>
      </textField>
    </band>
  </title>

  <columnHeader>
    <band height="30">
      <staticText>
        <reportElement x="0" y="0" width="80" height="30" backcolor="#4A90D9" forecolor="#FFFFFF" mode="Opaque"/>
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font isBold="true"/>
        </textElement>
        <text><![CDATA[ID]]></text>
      </staticText>
      <staticText>
        <reportElement x="80" y="0" width="475" height="30" backcolor="#4A90D9" forecolor="#FFFFFF" mode="Opaque"/>
        <textElement textAlignment="Center" verticalAlignment="Middle">
          <font isBold="true"/>
        </textElement>
        <text><![CDATA[Nombre]]></text>
      </staticText>
    </band>
  </columnHeader>

  <detail>
    <band height="25">
      <textField>
        <reportElement x="0" y="0" width="80" height="25"/>
        <textElement textAlignment="Center" verticalAlignment="Middle"/>
        <textFieldExpression><![CDATA[$F{id}]]></textFieldExpression>
      </textField>
      <textField>
        <reportElement x="80" y="0" width="475" height="25"/>
        <textElement verticalAlignment="Middle">
          <font/>
        </textElement>
        <textFieldExpression><![CDATA[$F{name}]]></textFieldExpression>
      </textField>
    </band>
  </detail>

  <pageFooter>
    <band height="25">
      <textField>
        <reportElement x="0" y="0" width="555" height="25"/>
        <textElement textAlignment="Right" verticalAlignment="Middle">
          <font size="8"/>
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
