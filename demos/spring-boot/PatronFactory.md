# Patron de diseno Factory

## 1. Estructura del Proyecto

### Arquitectura MVC Implementada

```
src/main/java/com/example/app/
├── controller/
│   └── PagoController.java           ← VIEW/API REST
├── services/
│   ├── IPagoService.java             ← Interface de negocio
│   └── PagoService.java              ← Lógica de negocio (SERVICE)
├── models/
│   ├── PagoRequest.java              ← MODEL (Request)
│   └── PagoResponse.java             ← MODEL (Response)
├── factory/
│   └── implement/
│       └── PagoFactory.java          ← FACTORY PATTERN
├── implement/
│   ├── PagoPaypal.java
│   ├── PagoTarjetaCredito.java
│   ├── PagoTarjetaDebito.java
│   ├── PagoTransferenciaBancaria.java
│   ├── PagoBitcoint.java
│   ├── PagoEfectivo.java
│   ├── PagoApplePay.java
│   └── PagoGooglePay.java            ← IMPLEMENTACIONES CONCRETAS
├── enums/
│   └── TipoDePago.java               ← ENUMERACIÓN
└── DemoPatronFactoryApplication.java ← BOOTSTRAP
```

### Patrones Identificados ✓

| Patrón | Ubicación | Estado |
|--------|-----------|--------|
| **Factory Pattern** | `PagoFactory.java` | ✓ Implementado |
| **Strategy Pattern** | Interfaces de pago (`IPagoService`) | ✓ Implementado |
| **Spring MVC** | `PagoController.java` | ✓ Implementado |
| **Dependency Injection** | `PagoService` con `@Service` | ✓ Implementado |
| **REST API** | `@RestController` | ✓ Implementado |

---
