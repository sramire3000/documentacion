# Configuracion del Circuit Braker en los microservicio Clientes

### Dependencia
````
Resilience4J
````

### Dependencia a nivel de pom.xml
````
<!-- Circuit Breaker -->
<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>

<!-- AOP -->
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
        <version>3.5.6</version>
</dependency>
````

### Configuracion a nivel de codigo
````
package sv.jh.springcloud.msvc.items.app;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppCiruitBreakerConfig {

        @Bean
        Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
                return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                                .circuitBreakerConfig(CircuitBreakerConfig
                                                .custom()
                                                // Número de llamadas para calcular el porcentaje de fallos
                                                .slidingWindowSize(10)
                                                // Porcentaje de fallos para abrir el circuito
                                                .failureRateThreshold(50)
                                                // Tiempo que el circuito permanecerá abierto antes de permitir nuevas
                                                // llamadas
                                                .waitDurationInOpenState(Duration.ofSeconds(10L))
                                                // Número de llamadas permitidas en estado half-open antes de decidir si
                                                // se abre o se cierra el circuito
                                                .permittedNumberOfCallsInHalfOpenState(5)
                                                // Duración máxima permitida para una llamada antes de considerarla
                                                // lenta y contribuir al porcentaje de fallos
                                                .slowCallDurationThreshold(Duration.ofSeconds(2L))
                                                // Porcentaje de llamadas lentas para abrir el circuito
                                                .slowCallRateThreshold(50)
                                                .build())
                                .timeLimiterConfig(TimeLimiterConfig
                                                .custom()
                                                // Tiempo máximo permitido para la ejecución de una llamada antes de que
                                                // se considere un fallo por timeout
                                                .timeoutDuration(Duration.ofSeconds(4L))
                                                .build())
                                .build());
        }
}

````

### Configuracion por YML
````
resilience4j:
  circuitbreaker:
    configs:
      defecto:
        sliding-window-size: 6
        failure-rate-threshold: 50
        wait-duration-in-open-state: 20s
        permitted-number-of-calls-in-half-open-state: 4
        slow-call-duration-threshold: 3s
        slow-call-rate-threshold: 50
    instances:
      items:
        base-config: defecto
  timelimiter:
    configs:
      defecto:
        timeout-duration: 4s
    instances:
      items:
        base-config: defecto

````
