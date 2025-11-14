# Estructura Recomendada
```
src/
├── app/
│   ├── core/                          # Capa de Core (Singleton Services, Guards, Interceptors)
│   │   ├── guards/
│   │   ├── interceptors/
│   │   ├── services/
│   │   └── core.module.ts
│   │
│   ├── shared/                        # Elementos compartidos entre múltiples dominios
│   │   ├── components/
│   │   ├── directives/
│   │   ├── pipes/
│   │   ├── models/
│   │   └── shared.module.ts
│   │
│   ├── domains/                       # Capa de Dominios (Bounded Contexts)
│   │   ├── users/                     # Dominio: Usuarios
│   │   │   ├── application/           # Lógica de aplicación
│   │   │   │   ├── services/
│   │   │   │   ├── commands/
│   │   │   │   ├── queries/
│   │   │   │   └── events/
│   │   │   ├── domain/                # Entidades y lógica de negocio
│   │   │   │   ├── entities/
│   │   │   │   ├── value-objects/
│   │   │   │   ├── aggregates/
│   │   │   │   ├── repositories/
│   │   │   │   └── services/
│   │   │   ├── infrastructure/        # Implementaciones concretas
│   │   │   │   ├── repositories/
│   │   │   │   ├── services/
│   │   │   │   └── mappers/
│   │   │   ├── presentation/          # Componentes y vistas
│   │   │   │   ├── components/
│   │   │   │   ├── pages/
│   │   │   │   ├── guards/
│   │   │   │   └── resolvers/
│   │   │   └── users.module.ts
│   │   │
│   │   ├── products/                  # Dominio: Productos
│   │   │   ├── application/
│   │   │   ├── domain/
│   │   │   ├── infrastructure/
│   │   │   ├── presentation/
│   │   │   └── products.module.ts
│   │   │
│   │   └── orders/                    # Dominio: Pedidos
│   │       ├── application/
│   │       ├── domain/
│   │       ├── infrastructure/
│   │       ├── presentation/
│   │       └── orders.module.ts
│   │
│   ├── app-routing.module.ts
│   └── app.module.ts
│
└── assets/
```
