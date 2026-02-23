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

## Ejemplo:

### 🔵 1️⃣ DOMAIN (Reglas de negocio puras)
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

### 🟡 2️⃣ APPLICATION (Casos de uso)

Interface del repositorio (Puerto)
```
public interface ProductoRepository {
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorId(UUID id);
    List<Producto> listar();
}
```

Caso de uso: Listar productos
```
@Service
public class ListarProductosUseCase {

    private final ProductoRepository repository;

    public ListarProductosUseCase(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> ejecutar() {
        return repository.listar();
    }
}
```

### 🔴 3️⃣ INFRASTRUCTURE (Base de datos)

Entidad JPA
```
@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private BigDecimal precio;
    private Integer stock;
}
```

JpaRepository
```
public interface ProductoJpaRepository 
    extends JpaRepository<ProductoEntity, UUID> {
}
```
Adaptador (Implementación del puerto)
```
@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private final ProductoJpaRepository jpaRepository;

    @Override
    public List<Producto> listar() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Producto toDomain(ProductoEntity entity) {
        return new Producto(
            entity.getId(),
            entity.getNombre(),
            entity.getPrecio(),
            entity.getStock()
        );
    }
}
```

### 🟢 4️⃣ INTERFACES (Controller REST)
```
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ListarProductosUseCase listarUseCase;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        var productos = listarUseCase.ejecutar();

        var response = productos.stream()
                .map(p -> new ProductoResponseDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getStock()))
                .toList();

        return ResponseEntity.ok(response);
    }
}
```

## 🗄 Base de Datos

Tabla productos
```
CREATE TABLE productos (
    id UUID PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio NUMERIC(15,2) NOT NULL,
    stock INTEGER NOT NULL
);
```

Recomendado:
- PostgreSQL
- Migraciones con Flyway

## 🔁 Flujo completo
```
Cliente API
   ↓
Controller
   ↓
UseCase
   ↓
Repository (interface)
   ↓
RepositoryImpl
   ↓
JPA
   ↓
Base de datos
```
## 🚀 Ventajas de esta arquitectura

✅ Separación clara
✅ Fácil de escalar
✅ Fácil de testear
✅ Lista para microservicios
✅ Puedes agregar multi-tenant después
✅ No depende totalmente de Spring
