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

2. Configurar un perfil falso en AWS CLI
Como todo es local, no necesitas credenciales reales. Configura un perfil de simulación en tu terminal:
```
aws configure --profile localstack
```
* AWS Access Key ID: test
* AWS Secret Access Key: test
* Default region name: us-east-1
* Default output format: json

3. Interactuar con tu AWS Local
Para apuntar tus comandos a tu laboratorio local en lugar de a internet, solo debes usar el parámetro --endpoint-url:
```
# Crear un balde (bucket) en S3 local
aws --endpoint-url=http://localhost:4566 s3 mb s3://mi-bucket-de-prueba

# Listar tus buckets locales
aws --endpoint-url=http://localhost:4566 s3 ls
```
