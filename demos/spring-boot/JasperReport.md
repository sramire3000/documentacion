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
