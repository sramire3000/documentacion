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

### 🟢 DOMAIN

### 🟡 APPLICATION

### 🟠 CASOS DE USO

### 🔵 INFRASTRUCTURE

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

