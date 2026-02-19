# Spring Cloud API Gateway

### Spring Iniz
```
groupId      = sv.jh.springcloud.gateway.server
artifactId   = jh-gateway-server
packageName  = sv.jh.springcloud.gateway.server.app
type         = jar
Version Java = 21
```

### Dependencias
```
Spring Boot Devtools
Eureka Discovery Client
Reactive Gateway Spring Cloud Routing
Spring boot Actuator
Lombok
```

### Add pom.xml
````
  <!-- Circuit Breaker reactor -->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
  </dependency>
````

### application.properties
````
# Configuración de la aplicación
spring.application.name=jh-gateway-server
# Puerto del servidor
server.port=8090

# Configuracion Eureka Client
#Instancia en Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.client.registry-fetch-interval-seconds=10
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

#Habilitar Actuator End Point
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

````

### application.yml
````
spring:
  cloud:
    gateway:
      # Configuración del servidor reactivo (WebFlux) de Spring Cloud Gateway.
      server:
        webflux:
          routes:
            # -------- Ruta 1: productos --------
            - id: jh-msvc-products          # Identificador único de la ruta
              uri: lb://jh-msvc-products    # URI del destino con discovery + balanceo (Eureka/Consul). "lb://" activa el LoadBalancer
              predicates:
                - Path=/api/products/**     # Predicate: coincide con cualquier URL que empiece por /api/products/
                #- Header=token, \d+         # Opcional: coincide solo si el header "token" tiene un valor con digitos. Se ejecuta antes de los filtros.
                #- Header=token, 12345       # Opcional: coincide solo si el header "token" tiene el valor "12345". Se ejecuta antes de los filtros.
                #- Method=GET, POST          # Opcional: coincide solo con solicitudes HTTP GET. Se ejecuta antes de los filtros.
                #- Query=color, verde        # Opcional: coincide solo si el parámetro de consulta "color" tiene el valor "verde". Se ejecuta antes de los filtros.
                #- Cookie=color, zul         # Opcional: coincide solo si la cookie "color" tiene el valor "zul". Se ejecuta antes de los filtros.
                #- Header=Content-Type, application/json # Opcional: coincide solo si el header "Content-Type" es "application/json". Se ejecuta antes de los filtros.
              filters:
                - StripPrefix=2             # Filtro: elimina los 2 primeros segmentos del path (/api/products)
                - EjemploCookie=Hola mi mensaje personalizado para productos!, user, Andres # Filtro personalizado que agrega una cookie "user" con valor "Andres" y un mensaje personalizado. Se ejecuta después de StripPrefix.
            # -------- Ruta 2: items --------
            - id: jh-msvc-items             # Identificador único de la ruta
              uri: lb://jh-msvc-items       # Servicio "items" resuelto por el registro (Eureka/Consul) con balanceo
              predicates:
                - Path=/api/items/**        # Aplica a cualquier path bajo /api/items/
              filters:
                - StripPrefix=2             # Elimina "/api/items" y reenvía el resto al servicio
                - AddRequestHeader=token-request, sdnfjernferhfr  # Agrega un header personalizado "token-reques" con un valor fijo. Se ejecuta después de StripPrefix.
                - AddResponseHeader=token-response, sdnfjernferhfr  # Agrega un header personalizado "token-response" a la respuesta. Se ejecuta después de que el servicio responda.
                - AddRequestParameter=param-request, sdnfjernferhfr  # Agrega un parámetro de consulta "param-request" con un valor fijo. Se ejecuta después de StripPrefix.
                - SetResponseHeader=Content-Type, application/json  # Establece el header "Content-Type" de la respuesta a "application/json". Se ejecuta después de que el servicio responda.

````

## Example de Filtro global
````
package sv.jh.springcloud.gateway.server.app.filters;

import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/** Sample global filter for logging or modifying requests/responses */
@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {

  private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    // Log the incoming request URI
    logger.info("Global filter applied to request: {}", exchange.getRequest().getURI());
    // Modify the request before it is forwarded to the downstream service
    logger.info("ejecutandos el filtro antes de request PRE");

    // You can add headers, modify the request body, etc. here
    ServerWebExchange modifiedExchange = exchange.mutate()
        .request(builder -> builder.header("token", "abcdefg"))
        .build();

    return chain.filter(modifiedExchange).then(Mono.fromRunnable(() -> {
      // Forma Normal de obtener el header token
      String token = modifiedExchange.getRequest().getHeaders().getFirst("token");
      if (token != null) {
        logger.info("Forma Normal token " + token);
        // Add the token to the response headers
        exchange.getResponse().getHeaders().add("token", token);
      }
      // Forma Reactiva de obtener el header token
      Optional.ofNullable(modifiedExchange.getRequest().getHeaders().getFirst("token")).ifPresent(t -> {
        logger.info("Forma Reactiva token " + t);
        // Add the token to the response headers
        exchange.getResponse().getHeaders().add("token", t);
      });

      // Modify the response after the request has been processed
      logger.info("ejecutandos el filtro despues de request POST");
      exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
      //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
    }));
  }

  @Override
  public int getOrder() {
    return 100; // Set the order of the filter (lower values have higher precedence)
  }

}
````

## Example Filter Factory
````
package sv.jh.springcloud.gateway.server.app.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Component
public class SampleCookieGatewayFilterFactory
    extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

  private final Logger logger = LoggerFactory.getLogger(AbstractGatewayFilterFactory.class);

  // Constructor para inicializar la configuración del filtro
  public SampleCookieGatewayFilterFactory() {
    super(ConfigurationCookie.class);
  }

  @Override
  public GatewayFilter apply(ConfigurationCookie config) {
    return new OrderedGatewayFilter((exchange, chain) -> {
      logger.info("Applying PRE SampleCookieGatewayFilter with config: " + config.getMessage());
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        Optional.ofNullable(config.cookieValue).ifPresent(cookie -> {
          exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, config.cookieValue).build());
        });
        logger.info("Applying POST SampleCookieGatewayFilter with config: " + config.getMessage());
      }));
    }, 100);

  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList("message", "cookieName", "cookieValue");
  }

  @Override
  public String name() {
    return "EjemploCookie";
  }

  // Clase de configuración para el filtro (si es necesario)
  public static class ConfigurationCookie {

    private String cookieName;
    private String cookieValue;
    private String message;

    public String getCookieName() {
      return cookieName;
    }

    public void setCookieName(String cookieName) {
      this.cookieName = cookieName;
    }

    public String getCookieValue() {
      return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
      this.cookieValue = cookieValue;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }

}
````



