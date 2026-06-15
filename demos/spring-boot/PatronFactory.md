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

## 2. Ejemplo:

### La clase "TipoDePago.java" de tipo enums
```
package com.example.app.enums;

public enum TipoDePago {
  PAYPAL,
  TARJETA_CREDITO,
  TARJETA_DEBITO,
  TRANSFERENCIA_BANCARIA,
  BITCOIN,
  EFECTIVO,
  APPLE_PAY,
  GOOGLE_PAY
}

```

### Las clases "PagoRequest.java" de tipo models
```
package com.example.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.example.app.enums.TipoDePago;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {

  private TipoDePago tipoDePago;
  private Double monto;
  private String descripcion;

}
```

### La clase "PagoResponse.java" de tipo models
```
package com.example.app.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.example.app.enums.TipoDePago;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PagoResponse {

  private String id;
  private TipoDePago tipoDePago;
  private Double monto;
  private String descripcion;
  private String estado;
  private LocalDateTime fechaCreacion;
  private String mensaje;

}
```

### La clase "IPagoService.java" de tipo interface
```
package com.example.app.services;

public interface IPagoService {

  /**
   * Crea/procesa un pago con el método específico de pago
   */
  void crearPago();

  /**
   * Obtiene el nombre del tipo de pago
   */
  default String obtenerNombrePago() {
    return this.getClass().getSimpleName();
  }

  /**
   * Valida si el tipo de pago está disponible
   */
  default boolean estaDisponible() {
    return true;
  }

}
```

