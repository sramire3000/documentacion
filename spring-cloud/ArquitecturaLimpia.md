# ARQUITECTURA LIMPIA

```
sv.jh.springcloud.msvc.seguridad.app
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
```bash
package sv.jh.springcloud.msvc.seguridad.app.utils;

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
```bash
package sv.jh.springcloud.msvc.seguridad.app.domain.exception;

public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }
}
```

Rol.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.domain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

import sv.jh.springcloud.msvc.seguridad.app.domain.exception.BusinessException;

@Getter
@Builder
public class Rol {

  private UUID pkIdrol;
  private String name;
  private Boolean isActive;
  private String ultUsername;

  public Rol(UUID pkIdrol, String name, Boolean isActive, String ultUsername) {
    this.pkIdrol = pkIdrol;
    this.name = name;
    this.isActive = isActive;
    this.ultUsername = ultUsername;
    validarName();
  }

  public static Rol of(UUID pkIdrol, String name, Boolean isActive, String ultUsername) {
    Rol p = new Rol(pkIdrol, name, isActive, ultUsername);
    p.validarName();
    return p;
  }

  public void validarName() {
    if (name == null || name.isEmpty()) {
      throw new BusinessException("El nombre no puede estar vacio");
    }
  }
}
```

### 🟡 APPLICATION
RolRequest.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class RolRequest {
  private UUID pkIdrol;
  private String name;
  private Boolean isActive;
  private String ultUsername;
}
```
RolResponse.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RolResponse {
  private UUID pkIdrol;
  private String name;
  private Boolean isActive;
  private String ultUsername;
}
```
RolMapper.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.mapper;

import org.springframework.stereotype.Component;

import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolRequest;
import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolResponse;
import sv.jh.springcloud.msvc.seguridad.app.domain.model.Rol;
import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.entity.RolEntity;

@Component
public class RolMapper {

  public Rol toDomain(RolRequest request) {
    return new Rol(
        request.getPkIdrol(),
        request.getName(),
        request.getIsActive(),
        request.getUltUsername());
  }

  public Rol toDomain(RolEntity entity) {
    return new Rol(
        entity.getPkIdrol(),
        entity.getName(),
        entity.getIsActive(),
        entity.getUltUsername());
  }

  public RolEntity toEntity(Rol rol) {
    RolEntity entity = new RolEntity();
    entity.setPkIdrol(rol.getPkIdrol());
    entity.setName(rol.getName());
    entity.setIsActive(rol.getIsActive());
    entity.setUltUsername(rol.getUltUsername());
    return entity;
  }

  public RolResponse toResponse(Rol rol) {
    return new RolResponse(
        rol.getPkIdrol(),
        rol.getName(),
        rol.getIsActive(),
        rol.getUltUsername());
  }
}
```
### 🟠 CASOS DE USO
CreatedRolUseCase.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.usecase.create;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolRequest;
import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolResponse;
import sv.jh.springcloud.msvc.seguridad.app.application.mapper.RolMapper;
import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.repository.RolRepository;
import sv.jh.springcloud.msvc.seguridad.app.utils.UseCase;

@UseCase
@RequiredArgsConstructor
public class CreatedRolUseCase {

  private final RolRepository repository;
  private final RolMapper mapper;

  @Transactional
  public RolResponse ejecutar(RolRequest request) {

    var rol = mapper.toDomain(request);
    rol.validarName();
    var entity = mapper.toEntity(rol);
    entity.setPkIdrol(null);
    var saved = repository.save(entity);
    return mapper.toResponse(mapper.toDomain(saved));
  }
}
```
DeletedRolUseCase.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.usecase.deleted;

import java.util.UUID;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.repository.RolRepository;
import sv.jh.springcloud.msvc.seguridad.app.utils.UseCase;

@UseCase
@RequiredArgsConstructor
public class DeletedRolUseCase {
  private final RolRepository repository;

  @Transactional
  public void ejecutar(UUID pkIdrol) {
    repository.deleteById(pkIdrol);
  }
}
```
ListRolesUseCase.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.usecase.list;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolResponse;
import sv.jh.springcloud.msvc.seguridad.app.application.mapper.RolMapper;
import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.repository.RolRepository;
import sv.jh.springcloud.msvc.seguridad.app.utils.UseCase;

@UseCase
@RequiredArgsConstructor
public class ListRolesUseCase {

  private final RolRepository repository;
  private final RolMapper mapper;

  @Transactional(readOnly = true)
  public List<RolResponse> ejecutar() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(mapper::toDomain)
        .map(mapper::toResponse)
        .toList();
  }
}
```
SearchesRolUseCase.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.usecase.searches;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolResponse;
import sv.jh.springcloud.msvc.seguridad.app.application.mapper.RolMapper;
import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.repository.RolRepository;
import sv.jh.springcloud.msvc.seguridad.app.utils.UseCase;

@UseCase
@RequiredArgsConstructor
public class SearchesRolUseCase {

  private final RolRepository repository;
  private final RolMapper mapper;

  @Transactional(readOnly = true)
  public RolResponse findById(UUID id) {
    return repository.findById(id)
        .map(mapper::toDomain)
        .map(mapper::toResponse)
        .orElse(null);
  }
}
```
UpdateRolUseCase.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.application.usecase.update;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolRequest;
import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolResponse;
import sv.jh.springcloud.msvc.seguridad.app.application.mapper.RolMapper;
import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.repository.RolRepository;
import sv.jh.springcloud.msvc.seguridad.app.utils.UseCase;

@UseCase
@RequiredArgsConstructor
public class UpdateRolUseCase {
  private final RolRepository repository;
  private final RolMapper mapper;

  @Transactional
  public RolResponse ejecutar(RolRequest request) {
    var rol = mapper.toDomain(request);
    rol.validarName();
    var entity = mapper.toEntity(rol);
    var saved = repository.save(entity);
    return mapper.toResponse(mapper.toDomain(saved));
  }
}
```

### 🔵 INFRASTRUCTURE
```bash

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

