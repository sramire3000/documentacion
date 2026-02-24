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
RolEntity.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "roles", schema = "seguridad")
public class RolEntity {

  // El identificador único del rol, generado automáticamente como UUID.
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "pk_idrol", nullable = false, unique = true)
  private UUID pkIdrol;

  // El nombre del rol, por ejemplo: "ROLE_ADMIN", "ROLE_USER", etc.
  @Column(name = "name", nullable = false, unique = true, length = 50, columnDefinition = "varchar(50)")
  private String name;

  // Indica si el rol está activo o no. Un valor de true significa que el
  // rol está activo, mientras que false indica que está inactivo.
  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  // El nombre de usuario que creó el rol. Este campo es útil para auditoría y
  @Column(name = "ult_username", length = 50, columnDefinition = "varchar(50)")
  private String ultUsername;

}
```
RolRepository.java
```bash
package sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import sv.jh.springcloud.msvc.seguridad.app.infrastructure.persistence.entity.RolEntity;

public interface RolRepository extends CrudRepository<RolEntity, UUID> {

}
```

### 🔵 WEB
RolController.java
```
package sv.jh.springcloud.msvc.seguridad.app.web.controller;

import java.util.List;
import java.util.UUID;

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

import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolRequest;
import sv.jh.springcloud.msvc.seguridad.app.application.dto.RolResponse;
import sv.jh.springcloud.msvc.seguridad.app.application.usecase.create.CreatedRolUseCase;
import sv.jh.springcloud.msvc.seguridad.app.application.usecase.deleted.DeletedRolUseCase;
import sv.jh.springcloud.msvc.seguridad.app.application.usecase.list.ListRolesUseCase;
import sv.jh.springcloud.msvc.seguridad.app.application.usecase.searches.SearchesRolUseCase;
import sv.jh.springcloud.msvc.seguridad.app.application.usecase.update.UpdateRolUseCase;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

  private final CreatedRolUseCase create;
  private final UpdateRolUseCase update;
  private final ListRolesUseCase list;
  private final DeletedRolUseCase deleted;
  private final SearchesRolUseCase searchs;

  @GetMapping
  public ResponseEntity<List<RolResponse>> list() {
    return ResponseEntity.ok(list.ejecutar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RolResponse> findById(@PathVariable UUID id) {
    RolResponse response = searchs.findById(id);
    if (response != null) {
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<RolResponse> create(@RequestBody RolRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(create.ejecutar(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RolResponse> update(@PathVariable UUID id, @RequestBody RolRequest request) {

    if (!id.equals(request.getPkIdrol())) {
      return ResponseEntity.badRequest().build();
    }

    RolResponse response = searchs.findById(id);

    if (response != null) {
      return ResponseEntity.ok(update.ejecutar(request));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {

    // Buscar el rol por su id para validar si existe antes de eliminarlo
    RolResponse response = searchs.findById(id);
    // Si el rol existe, se elimina y se devuelve una respuesta con el código de
    // estado 204 (No Content)
    if (response != null) {
      deleted.ejecutar(id);
      return ResponseEntity.noContent().build();
    }
    // Si el rol no existe, se devuelve una respuesta con el código de estado 404
    return ResponseEntity.notFound().build();
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

