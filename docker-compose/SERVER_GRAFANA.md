# SERVIDOR DE GRAFANA Y PROMETEUS PARA MONITOREO DE MICROSERVICIOS

## 1. Archivo prometheus.yml
Antes de ejecutar Docker, crea este archivo para definir el "scrape config" de tus aplicaciones Spring Boot.
```
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080'] # Cambia 8080 por el puerto de tu app
```

## 2. Archivo docker-compose.yml
Este archivo levantará los contenedores necesarios. He incluido una red interna para que la comunicación sea fluida.
```
services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"
    networks:
      - monitoring
    extra_hosts:
      - "host.docker.internal:host-gateway"

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    networks:
      - monitoring
    depends_on:
      - prometheus

networks:
  monitoring:
    driver: bridge
```

## Configuración en Spring Boot
Para que esto funcione, asegúrate de que tus microservicios tengan las siguientes dependencias y configuraciones:

Dependencias (Maven):

spring-boot-starter-actuator

micrometer-registry-prometheus

Propiedades (application.properties):
```
management.endpoints.web.exposure.include=health,info,prometheus
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
```

## Pasos finales
Ejecuta docker compose up -d.

Accede a Grafana en http://localhost:3000 (usuario: admin, clave: admin).

Agrega un Data Source: Selecciona Prometheus.

En la URL del Data Source, usa http://prometheus:9090 (gracias a la red de Docker).

Importar Dashboard: Puedes usar el ID 11378 en la sección de "Import" de Grafana para obtener un panel preconfigurado excelente para Spring Boot.



