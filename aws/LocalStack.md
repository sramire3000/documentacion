# LocalStackLocalStack 

es un simulador de servicios de nube que se ejecuta localmente en un contenedor de Docker. Te permite levantar réplicas locales de servicios de AWS con un rendimiento y comportamiento muy similar al real.  ¿Qué servicios puedes simular gratis?En su versión comunitaria (open-source), incluye la mayoría de los servicios fundamentales que necesitas para estudiar y certificar:

* Computación y Contenedores: Lambda, ECS.
* Almacenamiento: S3.Bases de Datos: DynamoDB, RDS (limitado).
* Mensajería e Integración: SQS, SNS, Kinesis.
* Redes y Seguridad: IAM (simulado para compatibilidad), API Gateway, Route53.


## Install AWS CLI

Para instalar la AWS CLI (Interfaz de Línea de Comandos de AWS), el proceso varía un poco según el sistema operativo que utilices. Como tu objetivo es usarlo con tu laboratorio local, no necesitas una cuenta real de AWS para instalarlo ni para dar los primeros pasos.

Aquí tienes la guía paso a paso para los principales sistemas operativos:

1. Instalación por Sistema Operativo
En Windows
La forma más sencilla es usar el instalador oficial de Windows (MSI).

Abre tu terminal (PowerShell o CMD) y ejecuta el siguiente comando para descargar e instalar la última versión:
```
msiexec.exe /i https://awscli.amazonaws.com/AWSCLIV2.msi
```

En macOS
Puedes instalarlo usando el instalador gráfico oficial o mediante la terminal.

Opción recomendada (Terminal):
```
curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"
sudo installer -pkg AWSCLIV2.pkg -target /
```

Opción alternativa (Si usas Homebrew):
```
brew install awscli
```

En Linux (Ubuntu, Debian, RedHat, etc.)
En Linux se recomienda descargar el binario oficial directamente de AWS:
```
# 1. Descargar el archivo zip
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"

# 2. Descomprimirlo (asegúrate de tener instalado 'unzip')
unzip awscliv2.zip

# 3. Ejecutar el instalador
sudo ./aws/install
```

2. Verificar la Instalación
Una vez que termine la instalación, cierra tu terminal actual y abre una nueva. Ejecuta el siguiente comando para comprobar que todo está en orden:
```
aws --version
```


## Cómo montar tu laboratorio en 3 pasos

### 1. Install Docker

Crear un archivo docker-compose.yml
```
version: "3.8"

services:
  localstack:
    container_name: localstack_main
    # Cambiamos "latest" por la versión 4.4.0
    image: localstack/localstack:4.4.0
    ports:
      - "127.0.0.1:4566:4566"
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
