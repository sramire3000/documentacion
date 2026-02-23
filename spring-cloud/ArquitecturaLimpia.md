# ARQUITECTURA LIMPIA

```
com.tuempresa.productos
│
├── domain/                         # Modelo de negocio puro
│   ├── model/
│   │   └── Producto.java
│   ├── exception/
│   │   └── BusinessException.java
│   └── service/
│       └── ProductoDomainService.java
│
├── application/                    # Lógica de aplicación
│   ├── service/
│   │   └── ProductoService.java
│   ├── dto/
│   │   ├── ProductoRequest.java
│   │   └── ProductoResponse.java
│   └── mapper/
│       └── ProductoMapper.java
│
├── infrastructure/                 # Implementación técnica
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── ProductoEntity.java
│   │   └── repository/
│   │       └── ProductoRepository.java
│   │
│   ├── security/
│   ├── config/
│   └── exception/
│       └── GlobalExceptionHandler.java
│
├── web/                            # Capa de entrada (Controllers)
│   ├── controller/
│   │   └── ProductoController.java
│   └── dto/
│
└── ProductosApplication.java
```
