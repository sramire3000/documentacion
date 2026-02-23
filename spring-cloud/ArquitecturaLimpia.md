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
### 🟠 CASOS DE USO
### 🔵 INFRASTRUCTURE
### 🌐 WEB


