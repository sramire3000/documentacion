# Docker Composer APACHE KAFKA

### crear folder server-apache-kafka
```
mkdir -p server-apache-kafka
```

### Crear filder
```
# Zookeper
mkdir -p zookeeper_data
mkdir -p zookeeper_logs
mkdir -p zookeeper_secrets

sudo chmod 777 zookeeper_data
sudo chmod 777 zookeeper_logs
sudo chmod 777 zookeeper_secrets

# Kafka
mkdir -p kafka_data
mkdir -p kafka_secrets
mkdir -p kafka_logs
mkdir -p kafka_certs

sudo chmod 777 kafka_data
sudo chmod 777 kafka_secrets
sudo chmod 777 kafka_logs
sudo chmod 777 kafka_certs
```

### File ".env"
```
# Versiones de las imágenes
ZOOKEEPER_VERSION=7.4.3
KAFKA_VERSION=7.4.3

# Nombres de los contenedores
ZOOKEEPER_CONTAINER_NAME=server-load-balancer
KAFKA_CONTAINER_NAME=server-msg-broker

# Configuración de Zookeeper
ZOOKEEPER_CLIENT_PORT=2181
ZOOKEEPER_TICK_TIME=2000

# Configuración de Kafka
KAFKA_BROKER_ID=1
KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1
KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1
KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1
KAFKA_LOG_DIRS=/var/lib/kafka/data

# Puertos
ZOOKEEPER_HOST_PORT=2181
KAFKA_HOST_PORT=9092

# Red
NETWORK_NAME=network_dev

# Listeners de Kafka
KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://host.docker.internal:9092,PLAINTEXT_INTERNAL://broker:29092
```

### File docker-compose.yaml
```
version: '3'

services:

  zookeeper:
    image: confluentinc/cp-zookeeper:${ZOOKEEPER_VERSION}
    container_name: ${ZOOKEEPER_CONTAINER_NAME}
    restart: always
    networks:
      - ${NETWORK_NAME}      
    environment:
      ZOOKEEPER_CLIENT_PORT: ${ZOOKEEPER_CLIENT_PORT}
      ZOOKEEPER_TICK_TIME: ${ZOOKEEPER_TICK_TIME}
    ports:
      - "${ZOOKEEPER_HOST_PORT}:${ZOOKEEPER_CLIENT_PORT}"
    volumes:
      - ./zookeeper_data:/var/lib/zookeeper/data
      - ./zookeeper_logs:/var/lib/zookeeper/log
      - ./zookeeper_secrets:/etc/zookeeper/secrets

  kafka:
    image: confluentinc/cp-kafka:${KAFKA_VERSION}
    container_name: ${KAFKA_CONTAINER_NAME}
    restart: always
    networks:
      - ${NETWORK_NAME}    
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: ${KAFKA_BROKER_ID}
      KAFKA_ZOOKEEPER_CONNECT: ${KAFKA_ZOOKEEPER_CONNECT}
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: ${KAFKA_ADVERTISED_LISTENERS}
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: ${KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR}
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: ${KAFKA_TRANSACTION_STATE_LOG_MIN_ISR}
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: ${KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR}
      KAFKA_LOG_DIRS: ${KAFKA_LOG_DIRS}
    ports:
      - "${KAFKA_HOST_PORT}:9092"
    volumes:
      - ./kafka_data:/var/lib/kafka/data
      - ./kafka_secrets:/etc/kafka/secrets
      - ./kafka_logs:/opt/kafka/logs
      - ./kafka_certs:/etc/kafka/certs      
      - ./kafka_logs:/var/lib/kafka/logs
         
networks:
  network_dev:
    external: true
```











