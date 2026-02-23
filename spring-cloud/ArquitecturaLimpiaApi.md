# ARQUITECTURA LIMPIA PARA API

## 🏗 Arquitectura Recomendada (Clean + Profesional)
Basada en principios de Robert C. Martin y aplicada a Spring Boot.

📁 Estructura de Carpetas
```bash
com.tuempresa.productos
│
├── domain/                         # Núcleo puro
│   ├── model/
│   │   └── Producto.java
│   ├── repository/
│   │   └── ProductoRepository.java
│   ├── exception/
│   │   └── BusinessException.java
│   └── service/
│       └── ProductoDomainService.java
│
├── application/                    # Casos de uso
│   ├── usecase/
│   │   ├── crear/
│   │   │   ├── CrearProductoUseCase.java
│   │   │   ├── CrearProductoCommand.java
│   │   │   └── CrearProductoResponse.java
│   │   ├── listar/
│   │   │   └── ListarProductosUseCase.java
│   │   ├── actualizar/
│   │   └── eliminar/
│   │
│   ├── port/
│   │   ├── input/
│   │   └── output/
│   │       └── ProductoPersistencePort.java
│   │
│   └── mapper/
│
├── infrastructure/                 # Implementaciones técnicas
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── ProductoEntity.java
│   │   ├── repository/
│   │   │   └── ProductoJpaRepository.java
│   │   └── adapter/
│   │       └── ProductoPersistenceAdapter.java
│   │
│   ├── security/
│   │   ├── config/
│   │   ├── jwt/
│   │   ├── filter/
│   │   └── TenantFilter.java
│   │
│   ├── config/
│   │   ├── OpenApiConfig.java
│   │   └── JacksonConfig.java
│   │
│   └── exception/
│       └── GlobalExceptionHandler.java
│
├── interfaces/                     # Adaptadores de entrada
│   └── rest/
│       ├── ProductoController.java
│       └── dto/
│           ├── ProductoRequestDTO.java
│           └── ProductoResponseDTO.java
│
└── ProductosApplication.java
```

# Ejemplo:
## 📦 com.tuempresa.productos

### 🟢 DOMAIN
📄 domain/model/Producto.java
```
package com.tuempresa.productos.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Producto {

    private UUID id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String tenantId;

    public Producto(UUID id, String nombre, BigDecimal precio, Integer stock, String tenantId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tenantId = tenantId;
    }

    public void actualizar(String nombre, BigDecimal precio, Integer stock) {
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Precio no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public String getTenantId() { return tenantId; }
}
```


