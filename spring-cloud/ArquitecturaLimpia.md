# ARQUITECTURA LIMPIA

```
com.tuempresa.productos
│
├── domain/                         # Núcleo del negocio (sin Spring)
│   ├── model/
│   │   └── Producto.java
│   ├── exception/
│   │   └── BusinessException.java
│   └── service/
│       └── ProductoDomainService.java
│
├── application/                    # Casos de uso
│   ├── usecase/
│   │   ├── crear/
│   │   │   └── CrearProductoUseCase.java
│   │   ├── listar/
│   │   │   └── ListarProductosUseCase.java
│   │   ├── actualizar/
│   │   │   └── ActualizarProductoUseCase.java
│   │   └── eliminar/
│   │       └── EliminarProductoUseCase.java
│   │
│   ├── dto/
│   │   ├── ProductoRequest.java
│   │   └── ProductoResponse.java
│   │
│   └── mapper/
│       └── ProductoMapper.java
│
├── infrastructure/                 # Implementación técnica
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── ProductoEntity.java
│   │   └── repository/
│   │       └── ProductoRepository.java   # Spring Data JPA
│   │
│   ├── security/
│   ├── config/
│   └── exception/
│       └── GlobalExceptionHandler.java
│
├── web/                            # Entrada HTTP
│   └── controller/
│       └── ProductoController.java
│
└── ProductosApplication.java
```

## 🔄 Flujo ahora
```
Controller
   ↓
UseCase
   ↓
Repository (Spring Data JPA)
   ↓
Base de datos
```

## Ejemplo:

### 🟢 DOMAIN
Producto.java (Modelo de dominio puro)
```
package com.tuempresa.productos.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor
public class Producto {

    private Long id;
    private String nombre;
    private Double precio;

    public void validar() {
        if (precio <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }
    }
}
```

### 🟡 APPLICATION
ProductoRequest.java
```
package com.tuempresa.productos.application.dto;

import lombok.Data;

@Data
public class ProductoRequest {
    private String nombre;
    private Double precio;
}
```
ProductoResponse.java
```
package com.tuempresa.productos.application.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private Double precio;
}
```
ProductoMapper.java
```
package com.tuempresa.productos.application.mapper;

import org.springframework.stereotype.Component;
import com.tuempresa.productos.infrastructure.persistence.entity.ProductoEntity;
import com.tuempresa.productos.application.dto.*;

@Component
public class ProductoMapper {

    public ProductoEntity toEntity(ProductoRequest request) {
        ProductoEntity entity = new ProductoEntity();
        entity.setNombre(request.getNombre());
        entity.setPrecio(request.getPrecio());
        return entity;
    }

    public ProductoResponse toResponse(ProductoEntity entity) {
        return new ProductoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getPrecio()
        );
    }
}
```
### 🟠 CASOS DE USO
CrearProductoUseCase.java
```
package com.tuempresa.productos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoRepository;
import com.tuempresa.productos.application.dto.*;
import com.tuempresa.productos.application.mapper.ProductoMapper;

@Service
@RequiredArgsConstructor
public class CrearProductoUseCase {

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Transactional
    public ProductoResponse ejecutar(ProductoRequest request) {
        var entity = mapper.toEntity(request);
        var saved = repository.save(entity);
        return mapper.toResponse(saved);
    }
}
```
ListarProductosUseCase.java
```
package com.tuempresa.productos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoRepository;
import com.tuempresa.productos.application.mapper.ProductoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarProductosUseCase {

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    public List<?> ejecutar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
```
EliminarProductoUseCase.java
```
package com.tuempresa.productos.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoRepository;

@Service
@RequiredArgsConstructor
public class EliminarProductoUseCase {

    private final ProductoRepository repository;

    @Transactional
    public void ejecutar(Long id) {
        repository.deleteById(id);
    }
}
```
### 🔵 INFRASTRUCTURE
ProductoEntity.java
```
package com.tuempresa.productos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
}
```
ProductoRepository.java
```
package com.tuempresa.productos.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuempresa.productos.infrastructure.persistence.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
}
```
### 🌐 WEB
ProductoController.java
```
package com.tuempresa.productos.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.tuempresa.productos.application.dto.*;
import com.tuempresa.productos.application.usecase.*;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final CrearProductoUseCase crear;
    private final ListarProductosUseCase listar;
    private final EliminarProductoUseCase eliminar;

    @PostMapping
    public ProductoResponse crear(@RequestBody ProductoRequest request) {
        return crear.ejecutar(request);
    }

    @GetMapping
    public List<?> listar() {
        return listar.ejecutar();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        eliminar.ejecutar(id);
    }
}
```

