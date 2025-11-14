# Estructura recomendada
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── tuempresa/
│   │           └── tuaplicacion/
│   │               ├── application/          # Capa de Aplicación
│   │               │   ├── service/          # Servicios de aplicación
│   │               │   ├── dto/              # DTOs de entrada/salida
│   │               │   ├── command/          # Comandos (CQRS)
│   │               │   ├── query/            # Queries (CQRS)
│   │               │   └── event/            # Manejadores de eventos de aplicación
│   │               │
│   │               ├── domain/               # Capa de Dominio (Core)
│   │               │   ├── model/            # Entidades y objetos de valor
│   │               │   │   ├── entity/       # Entidades de dominio
│   │               │   │   ├── valueobject/  # Objetos de valor
│   │               │   │   ├── aggregate/    # Agregados
│   │               │   │   └── vo/           # Value Objects (alternativa)
│   │               │   ├── service/          # Servicios de dominio
│   │               │   ├── repository/       # Interfaces de repositorio
│   │               │   ├── event/            # Eventos de dominio
│   │               │   └── exception/        # Excepciones de dominio
│   │               │
│   │               ├── infrastructure/       # Capa de Infraestructura
│   │               │   ├── persistence/      # Implementación de persistencia
│   │               │   │   ├── entity/       # Entidades de persistencia (JPA)
│   │               │   │   ├── repository/   # Implementaciones de repositorio
│   │               │   │   └── mapper/       # Mappers entre dominio y persistencia
│   │               │   ├── web/              # Controladores REST
│   │               │   ├── messaging/        # Mensajería (Kafka, RabbitMQ)
│   │               │   ├── configuration/    # Configuraciones
│   │               │   └── external/         # Servicios externos
│   │               │
│   │               └── shared/               # Elementos compartidos
│   │                   ├── kernel/           # Utilidades generales
│   │                   └── exception/        # Excepciones globales
│   │
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── db/
│           └── migration/                    # Migraciones de base de datos
│
└── test/
    └── java/
        └── com/
            └── tuempresa/
                └── tuaplicacion/
                    ├── application/
                    ├── domain/
                    └── infrastructure/
```
