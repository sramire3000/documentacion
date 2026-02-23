# ARQUITECTURA LIMPIA PARA API

## 🏗 Arquitectura Recomendada (Clean + Profesional)
Basada en principios de Robert C. Martin y aplicada a Spring Boot.

📁 Estructura de Carpetas
```bash
com.tuempresa.productosapi
│
├── domain
│   ├── model
│   │     └── Producto.java
│   ├── repository
│   │     └── ProductoRepository.java
│
├── application
│   ├── usecase
│   │     ├── CrearProductoUseCase.java
│   │     ├── ObtenerProductoUseCase.java
│   │     └── ListarProductosUseCase.java
│   ├── dto
│   │     ├── ProductoRequestDTO.java
│   │     └── ProductoResponseDTO.java
│
├── infrastructure
│   ├── persistence
│   │     ├── entity
│   │     │     └── ProductoEntity.java
│   │     ├── repository
│   │     │     └── ProductoJpaRepository.java
│   │     └── adapter
│   │           └── ProductoRepositoryImpl.java
│   ├── config
│   └── exception
│
├── interfaces
│   └── rest
│         └── ProductoController.java
│
└── ProductosApiApplication.java
```

## Ejemplo:

🔵 1️⃣ DOMAIN (Reglas de negocio puras)
Producto (modelo de dominio)
```bash
public class Producto {
    private UUID id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;

    public void validarStock() {
        if (stock < 0) {
            throw new IllegalStateException("Stock no puede ser negativo");
        }
    }
}
```













