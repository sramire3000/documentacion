# Estructura Recomendada

```
lib/
├── core/                          # Capa de infraestructura compartida
│   ├── constants/
│   ├── errors/
│   ├── network/
│   ├── theme/
│   ├── utils/
│   └── widgets/
├── features/                      # Módulos de negocio (Bounded Contexts)
│   ├── auth/                      # Contexto: Autenticación
│   │   ├── application/
│   │   │   ├── blocs/            # O providers/cubits/controllers
│   │   │   ├── events/
│   │   │   ├── states/
│   │   │   └── services/         # Application Services
│   │   ├── domain/
│   │   │   ├── entities/
│   │   │   ├── value_objects/
│   │   │   ├── repositories/     # Interfaces de repositorios
│   │   │   ├── failures/
│   │   │   └── use_cases/        # Casos de uso
│   │   ├── infrastructure/
│   │   │   ├── data_sources/     # APIs, Local Storage
│   │   │   ├── dtos/             # Data Transfer Objects
│   │   │   ├── mappers/          # Mappers Entity <-> DTO
│   │   │   └── repositories/     # Implementaciones concretas
│   │   └── presentation/
│   │       ├── pages/
│   │       ├── widgets/
│   │       ├── cubits/           # O blocs/controllers
│   │       └── state/
│   ├── user/                      # Contexto: Usuario
│   │   ├── application/
│   │   ├── domain/
│   │   ├── infrastructure/
│   │   └── presentation/
│   └── products/                  # Contexto: Productos
│       ├── application/
│       ├── domain/
│       ├── infrastructure/
│       └── presentation/
├── shared/                        # Elementos compartidos entre contextos
│   ├── domain/
│   │   ├── entities/
│   │   └── value_objects/
│   └── infrastructure/
└── main.dart
```
