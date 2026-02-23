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

## 🟢 DOMAIN
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

📄 domain/repository/ProductoRepository.java
```
package com.tuempresa.productos.domain.repository;

import com.tuempresa.productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository {

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(UUID id, String tenantId);

    List<Producto> listar(String tenantId);

    void eliminar(UUID id, String tenantId);
}
```

📄 domain/exception/BusinessException.java
```
package com.tuempresa.productos.domain.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
```

📄 domain/service/ProductoDomainService.java
```
package com.tuempresa.productos.domain.service;

import com.tuempresa.productos.domain.model.Producto;

public class ProductoDomainService {

    public void validarStock(Producto producto) {
        if (producto.getStock() < 0) {
            throw new RuntimeException("Stock inválido");
        }
    }
}
```

## 🟡 APPLICATION

📄 application/port/output/ProductoPersistencePort.java
```
package com.tuempresa.productos.application.port.output;

import com.tuempresa.productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoPersistencePort {

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(UUID id, String tenantId);

    List<Producto> listar(String tenantId);

    void eliminar(UUID id, String tenantId);
}
```

### 🔹 CREAR
📄 CrearProductoCommand.java
```
package com.tuempresa.productos.application.usecase.crear;

import java.math.BigDecimal;

public record CrearProductoCommand(
        String nombre,
        BigDecimal precio,
        Integer stock,
        String tenantId
) {}
```

📄 CrearProductoResponse.java
```
package com.tuempresa.productos.application.usecase.crear;

import java.util.UUID;

public record CrearProductoResponse(UUID id) {}
```

📄 CrearProductoUseCase.java
```
package com.tuempresa.productos.application.usecase.crear;

import com.tuempresa.productos.application.port.output.ProductoPersistencePort;
import com.tuempresa.productos.domain.model.Producto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CrearProductoUseCase {

    private final ProductoPersistencePort persistencePort;

    public CrearProductoUseCase(ProductoPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    public CrearProductoResponse ejecutar(CrearProductoCommand command) {

        Producto producto = new Producto(
                UUID.randomUUID(),
                command.nombre(),
                command.precio(),
                command.stock(),
                command.tenantId()
        );

        persistencePort.guardar(producto);

        return new CrearProductoResponse(producto.getId());
    }
}
```

### 🔹 LISTAR

📄 ListarProductosUseCase.java
```
package com.tuempresa.productos.application.usecase.listar;

import com.tuempresa.productos.application.port.output.ProductoPersistencePort;
import com.tuempresa.productos.domain.model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarProductosUseCase {

    private final ProductoPersistencePort persistencePort;

    public ListarProductosUseCase(ProductoPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    public List<Producto> ejecutar(String tenantId) {
        return persistencePort.listar(tenantId);
    }
}
```

## 🔴 INFRASTRUCTURE

📄 persistence/entity/ProductoEntity.java
```
package com.tuempresa.productos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    private UUID id;

    private String nombre;
    private BigDecimal precio;
    private Integer stock;

    @Column(name = "tenant_id")
    private String tenantId;

    // getters y setters
}
```

📄 ProductoJpaRepository.java
```
package com.tuempresa.productos.infrastructure.persistence.repository;

import com.tuempresa.productos.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductoJpaRepository
        extends JpaRepository<ProductoEntity, UUID> {

    List<ProductoEntity> findByTenantId(String tenantId);
}
```

📄 ProductoPersistenceAdapter.java
```
package com.tuempresa.productos.infrastructure.persistence.adapter;

import com.tuempresa.productos.application.port.output.ProductoPersistencePort;
import com.tuempresa.productos.domain.model.Producto;
import com.tuempresa.productos.infrastructure.persistence.entity.ProductoEntity;
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductoPersistenceAdapter implements ProductoPersistencePort {

    private final ProductoJpaRepository repository;

    public ProductoPersistenceAdapter(ProductoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Producto guardar(Producto producto) {
        ProductoEntity entity = toEntity(producto);
        repository.save(entity);
        return producto;
    }

    @Override
    public Optional<Producto> buscarPorId(UUID id, String tenantId) {
        return repository.findById(id)
                .filter(e -> e.getTenantId().equals(tenantId))
                .map(this::toDomain);
    }

    @Override
    public List<Producto> listar(String tenantId) {
        return repository.findByTenantId(tenantId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(UUID id, String tenantId) {
        repository.deleteById(id);
    }

    private ProductoEntity toEntity(Producto p) {
        ProductoEntity e = new ProductoEntity();
        e.setId(p.getId());
        e.setNombre(p.getNombre());
        e.setPrecio(p.getPrecio());
        e.setStock(p.getStock());
        e.setTenantId(p.getTenantId());
        return e;
    }

    private Producto toDomain(ProductoEntity e) {
        return new Producto(
                e.getId(),
                e.getNombre(),
                e.getPrecio(),
                e.getStock(),
                e.getTenantId()
        );
    }
}
```

## 🟣 INTERFACES (REST)

📄 ProductoRequestDTO.java
```
package com.tuempresa.productos.interfaces.rest.dto;

import java.math.BigDecimal;

public record ProductoRequestDTO(
        String nombre,
        BigDecimal precio,
        Integer stock
) {}
```

📄 ProductoResponseDTO.java
```
package com.tuempresa.productos.interfaces.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductoResponseDTO(
        UUID id,
        String nombre,
        BigDecimal precio,
        Integer stock
) {}
```

📄 ProductoController.java
```
package com.tuempresa.productos.interfaces.rest;

import com.tuempresa.productos.application.usecase.crear.*;
import com.tuempresa.productos.application.usecase.listar.ListarProductosUseCase;
import com.tuempresa.productos.interfaces.rest.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final CrearProductoUseCase crearUseCase;
    private final ListarProductosUseCase listarUseCase;

    public ProductoController(CrearProductoUseCase crearUseCase,
                               ListarProductosUseCase listarUseCase) {
        this.crearUseCase = crearUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public CrearProductoResponse crear(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestBody ProductoRequestDTO request) {

        return crearUseCase.ejecutar(
                new CrearProductoCommand(
                        request.nombre(),
                        request.precio(),
                        request.stock(),
                        tenantId
                )
        );
    }

    @GetMapping
    public List<ProductoResponseDTO> listar(
            @RequestHeader("X-Tenant-ID") String tenantId) {

        return listarUseCase.ejecutar(tenantId)
                .stream()
                .map(p -> new ProductoResponseDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getStock()))
                .toList();
    }
}
```

