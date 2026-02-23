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
├── utils/         
│     └── UseCase.java
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

### 🟢 Utils
UseCase.java
```
package com.tuempresa.productos.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
@Validated
public @interface UseCase {

}

```


### 🟢 DOMAIN
BusinessException.java
```
package com.tuempresa.productos.domain.model;

public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }

}
```


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
            throw new BusinessException("El precio debe ser mayor a 0");
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
import org.springframework.transaction.annotation.Transactional;
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoRepository;
import com.tuempresa.productos.application.dto.*;
import com.tuempresa.productos.application.mapper.ProductoMapper;

@UseCase
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
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoRepository;
import com.tuempresa.productos.application.mapper.ProductoMapper;

import java.util.List;

@UseCase
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
import org.springframework.transaction.annotation.Transactional;
import com.tuempresa.productos.infrastructure.persistence.repository.ProductoRepository;

@UseCase
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
@RequestMapping("/api/productos")
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

# CREACION DE SCRIPTS

## 🐧 1️⃣ Script Linux / Mac

Guárdalo como:
```
crear-estructura.sh
```
Contenido:
```
#!/bin/bash

BASE="src/main/java/com/tuempresa/productos"

mkdir -p ~$BASE/utils

# DOMAIN
mkdir -p $BASE/domain/model
mkdir -p $BASE/domain/exception
mkdir -p $BASE/domain/service

# APPLICATION
mkdir -p $BASE/application/usecase/crear
mkdir -p $BASE/application/usecase/listar
mkdir -p $BASE/application/usecase/actualizar
mkdir -p $BASE/application/usecase/eliminar
mkdir -p $BASE/application/dto
mkdir -p $BASE/application/mapper

# INFRASTRUCTURE
mkdir -p $BASE/infrastructure/persistence/entity
mkdir -p $BASE/infrastructure/persistence/repository
mkdir -p $BASE/infrastructure/security
mkdir -p $BASE/infrastructure/config
mkdir -p $BASE/infrastructure/exception


# WEB
mkdir -p $BASE/web/controller
touch $BASE/web/controller/ProductoController.java

echo "Estructura creada correctamente 🚀"
```

Dar permisos y ejecutar
```
chmod +x crear-estructura.sh
./crear-estructura.sh
```

## 🪟 2️⃣ Script Windows

Guárdalo como:
```
crear-estructura.bat
```
Contenido:
```
@echo off

set BASE=src\main\java\com\tuempresa\productos

mkdir %BASE%\utils

REM DOMAIN
mkdir %BASE%\domain\model
mkdir %BASE%\domain\exception
mkdir %BASE%\domain\service

REM APPLICATION
mkdir %BASE%\application\usecase\crear
mkdir %BASE%\application\usecase\listar
mkdir %BASE%\application\usecase\actualizar
mkdir %BASE%\application\usecase\eliminar
mkdir %BASE%\application\dto
mkdir %BASE%\application\mapper

REM INFRASTRUCTURE
mkdir %BASE%\infrastructure\persistence\entity
mkdir %BASE%\infrastructure\persistence\repository
mkdir %BASE%\infrastructure\security
mkdir %BASE%\infrastructure\config
mkdir %BASE%\infrastructure\exception

REM WEB
mkdir %BASE%\web\controller


echo Estructura creada correctamente 🚀
pause
```

## 🎯 Resultado
```
src/main/java/com/tuempresa/productos
```

