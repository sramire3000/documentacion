# Docker Composer APACHE KAFKA

### crear folder server-apache-kafka
```
mkdir -p server-apache-kafka
```
### Crear filder
```
mkdir -p zookeeper_data
mkdir -p zookeeper_logs
mkdir -p zookeeper_secrets

mkdir -p kafka_data
mkdir -p kafka_logs

sudo chmod 777 zookeeper_data
sudo chmod 777 zookeeper_logs
sudo chmod 777 zookeeper_secrets

sudo chmod 777 kafka_data
sudo chmod 777 kafka_logs

```


### File docker-compose.yaml
```
version: '3'

services:

  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.3
    container_name: load-balancer
    restart: always
    networks:
      - network_dev      
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"
    volumes:
      - ./zookeeper_data:/var/lib/zookeeper/data
      - ./zookeeper_logs:/var/lib/zookeeper/log
      - ./zookeeper_secrets:/etc/zookeeper/secrets

  kafka:
    image: confluentinc/cp-kafka:7.4.3
    container_name: msg-broker
    restart: always
    networks:
      - network_dev    
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://host.docker.internal:9092,PLAINTEXT_INTERNAL://broker:29092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
    ports:
      - "9092:9092"
    volumes:
      - ./kafka_data:/var/lib/kafka/data
         
networks:
  network_dev:
    external: true 
```






