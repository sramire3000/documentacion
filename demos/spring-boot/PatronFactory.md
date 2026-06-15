# Patron de diseno Factory

## Estructura del Proyecto

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

# Ejemplo:

## Los ENUMS

### La clase "TipoDePago.java"
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

## Los Models

### Las clases "PagoRequest.java" 
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

### La clase "PagoResponse.java" 
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
## Las interfaces

### La clase "IPagoService.java"
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

## Los Servicios

### La clase "PagoApplePay.java"
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoApplePay implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoApplePay.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago con APPLE PAY - Autenticación biométrica completada");
  }

}
```

### La clase "PagoBitcoint.java" 
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoBitcoint implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoBitcoint.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago con BITCOIN - Transacción blockchain registrada");
  }

}
```

### La clase "PagoEfectivo.java" 
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoEfectivo implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoEfectivo.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago en EFECTIVO - Operación completada");
  }

}
```

### La clase "PagoGooglePay.java"
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoGooglePay implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoGooglePay.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago con GOOGLE PAY - Autenticación NFC iniciada");
  }

}
```
