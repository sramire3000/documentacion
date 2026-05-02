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

## Para obtener un monitoreo con un acabado profesional y técnico, lo ideal es importar tableros que ya han sido probados y optimizados por la comunidad. Para microservicios desarrollados con Spring Boot y Micrometer, existen opciones que ofrecen visualizaciones avanzadas de la JVM, el recolector de basura (GC), hilos y rendimiento de HTTP.

Aquí tienes las mejores opciones y cómo configurarlas:

1. Spring Boot Statistics (ID: 11378)
Este es probablemente el estándar de oro para monitoreo técnico de Spring Boot. Se ve muy profesional porque utiliza paneles detallados de:

Estado de la JVM: Uso de memoria Heap y Non-Heap, recuento de hilos y pausas del Garbage Collector.

Rendimiento de Requests: Gráficos de latencia (percentiles), tasas de error (4xx, 5xx) y throughput (peticiones por segundo).

Conexiones de Base de Datos: Si usas HikariCP, muestra el pool de conexiones activas y en espera.

2. JVM (Micrometer) (ID: 4701)
Si buscas algo puramente técnico centrado en la salud del sistema operativo y la máquina virtual:

Detalle de CPU: Uso del sistema vs. uso del proceso.

File Descriptors: Monitoreo de archivos abiertos para prevenir fugas de recursos.

Uptime y Logs: Conteos de eventos de log por nivel (Error, Warn, Info).

3. Node Exporter Full (ID: 1860)
Si además de los microservicios quieres monitorear el servidor o contenedor donde corren (CPU total, RAM del sistema, I/O de disco, red):

Este requiere que añadas un servicio llamado node-exporter a tu docker-compose.yml. Es lo que usan los ingenieros de confiabilidad (SRE) para ver la salud del "fierro" o hardware.

Cómo instalar estos Dashboards
No es necesario diseñarlos desde cero; puedes importarlos directamente en segundos:

En el menú lateral de Grafana, haz clic en Dashboards > New > Import.

En el campo "Import via grafana.com", escribe el ID (ejemplo: 11378).

Haz clic en Load.

Selecciona tu Data Source de Prometheus en el menú desplegable que aparecerá abajo.

Haz clic en Import.

Recomendación para una estética "Pro"
Para que el dashboard se vea realmente técnico y útil:

Variables: Asegúrate de que el dashboard use variables (filtros arriba) para poder alternar entre diferentes instancias o microservicios sin cambiar de pantalla.

Alertas: Configura umbrales de color (rojo para latencias superiores a 500ms o uso de CPU > 80%) para que el dashboard sea una herramienta de diagnóstico visual inmediata.

Dark Mode: Grafana luce mucho más profesional en su tema oscuro predeterminado para centros de monitoreo (NOC).

Si quieres añadir el monitoreo de infraestructura (Node Exporter) al Docker Compose que hicimos antes para que se vea más completo, solo avísame.





