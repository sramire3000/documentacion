# Programacion orientada a aspectos

## Archivo "pom.xml" Version spring 4.0.6
```
<!-- AOP -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aop</artifactId>
</dependency>
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
</dependency>
```

## Archivo "application.properties"
```
# =============================================
# LOGGING
# =============================================
logging.level.sv.com.acme.app.infrastructure.aop=INFO
logging.level.sv.com.acme.app=INFO
logging.level.sv.com.acme.app.application=INFO
logging.level.sv.com.acme.app.infrastructure.web=WARN
logging.level.org.springframework.web=INFO
logging.pattern.dateformat="dd-MM-yyyy HH:mm:ss.SSSZ"
logging.level.guru.springframework.blogs.controllers=INFO
```

## La clase "ExecutionTimeAspect.java"
```

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ExecutionTimeAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeAspect.class);

  @Around("""
      	execution(* sv.com.acme.app.application..*(..))
      	|| execution(* sv.com.acme.app.infrastructure.web..*(..))
      	|| execution(* sv.com.acme.app.infrastructure..*(..))
      """)
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.nanoTime();

    try {
      return joinPoint.proceed();
    } finally {
      long elapsedNanos = System.nanoTime() - start;
      long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(elapsedNanos);
      LOGGER.info("{} executed in {} ms", joinPoint.getSignature().toShortString(), elapsedMillis);
    }
  }
}
```
