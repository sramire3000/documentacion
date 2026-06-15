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

### La clase "PagoPaypal.java"
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoPaypal implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoPaypal.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago con PAYPAL - Transacción iniciada");
  }

}
```

### La clase "PagoTarjetaCredito.java"
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoTarjetaCredito implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoTarjetaCredito.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago con TARJETA DE CRÉDITO - Autenticación en proceso");
  }

}
```

### La clase "PagoTarjetaDebito.java"
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoTarjetaDebito implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoTarjetaDebito.class);

  @Override
  public void crearPago() {
    log.info("Procesando pago con TARJETA DE DÉBITO - Descuento de fondos autorizado");
  }

}
```

### La clase "PagoTransferenciaBancaria.java"
```
package com.example.app.implement;

import org.springframework.stereotype.Service;

import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagoTransferenciaBancaria implements IPagoService {

  private static final Logger log = LogManager.getLogger(PagoTransferenciaBancaria.class);

  @Override
  public void crearPago() {
    log.info("Procesando TRANSFERENCIA BANCARIA - En cola de procesamiento");
  }

}
```

## FACTORY

### La clase "PagoFactory.java"
```
package com.example.app.factory;

import java.util.HashMap;
import java.util.Map;

import com.example.app.enums.TipoDePago;
import com.example.app.implement.PagoApplePay;
import com.example.app.implement.PagoBitcoint;
import com.example.app.implement.PagoEfectivo;
import com.example.app.implement.PagoGooglePay;
import com.example.app.implement.PagoPaypal;
import com.example.app.implement.PagoTarjetaCredito;
import com.example.app.implement.PagoTarjetaDebito;
import com.example.app.implement.PagoTransferenciaBancaria;
import com.example.app.services.IPagoService;

public class PagoFactory {

  private final static Map<TipoDePago, IPagoService> pagos = new HashMap<>() {

    private static final long serialVersionUID = 1L;

    {
      put(TipoDePago.PAYPAL, new PagoPaypal());
      put(TipoDePago.TARJETA_CREDITO, new PagoTarjetaCredito());
      put(TipoDePago.TARJETA_DEBITO, new PagoTarjetaDebito());
      put(TipoDePago.TRANSFERENCIA_BANCARIA, new PagoTransferenciaBancaria());
      put(TipoDePago.BITCOIN, new PagoBitcoint());
      put(TipoDePago.EFECTIVO, new PagoEfectivo());
      put(TipoDePago.APPLE_PAY, new PagoApplePay());
      put(TipoDePago.GOOGLE_PAY, new PagoGooglePay());
    }
  };

  public IPagoService obteterPago(TipoDePago tipoDePago) {
    return pagos.get(tipoDePago);
  }
}
```

## LA MAIN

### La clase "main.java"
```
package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.app.enums.TipoDePago;
import com.example.app.factory.PagoFactory;
import com.example.app.services.IPagoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class DemoPatronFactoryApplication {

	private static final Logger log = LogManager.getLogger(DemoPatronFactoryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoPatronFactoryApplication.class, args);

		// Demostración del patrón Factory
		mostrarDemostracionFactory();
	}

	/**
	 * Demuestra el funcionamiento del patrón Factory
	 */
	private static void mostrarDemostracionFactory() {
		log.info("");
		log.info("╔═══════════════════════════════════════════════════════════╗");
		log.info("║  DEMOSTRACIÓN DEL PATRÓN FACTORY CON SPRING BOOT          ║");
		log.info("║  La API REST está disponible en http://localhost:8080    ║");
		log.info("║  Endpoints:                                               ║");
		log.info("║  - POST   /api/pagos                 (Procesar pago)      ║");
		log.info("║  - GET    /api/pagos/tipos           (Ver tipos)          ║");
		log.info("║  - GET    /api/pagos/health          (Health check)       ║");
		log.info("╚═══════════════════════════════════════════════════════════╝");

		// Ejemplo de uso directo del factory
		log.info("");
		log.info("Ejemplo de procesamiento directo usando Factory:");
		PagoFactory pagoFactory = new PagoFactory();

		for (TipoDePago tipo : TipoDePago.values()) {
			IPagoService pago = pagoFactory.obteterPago(tipo);
			if (pago != null) {
				log.info("  → Tipo: {} - {}", tipo, pago.obtenerNombrePago());
				pago.crearPago();
			}
		}

		log.info("");
		log.info("✓ Aplicación iniciada correctamente con patrón MVC + Factory");
		log.info("");
	}

}
```
