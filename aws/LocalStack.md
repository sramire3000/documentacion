# LocalStackLocalStack 

es un simulador de servicios de nube que se ejecuta localmente en un contenedor de Docker. Te permite levantar réplicas locales de servicios de AWS con un rendimiento y comportamiento muy similar al real.  ¿Qué servicios puedes simular gratis?En su versión comunitaria (open-source), incluye la mayoría de los servicios fundamentales que necesitas para estudiar y certificar:

* Computación y Contenedores: Lambda, ECS.
* Almacenamiento: S3.Bases de Datos: DynamoDB, RDS (limitado).
* Mensajería e Integración: SQS, SNS, Kinesis.
* Redes y Seguridad: IAM (simulado para compatibilidad), API Gateway, Route53.

## Cómo montar tu laboratorio en 3 pasos

### 1. Install Docker

Crear un archivo docker-compose.yml
```
version: "3.8"

services:
  localstack:
    container_name: localstack_main
    image: localstack/localstack
    ports:
      - "127.0.0.1:4566:4566"            # Puerto único para todos los servicios de LocalStack
    environment:
      - DATA_DIR=/tmp/localstack/data
    volumes:
      - "./volume:/var/lib/localstack"
```

