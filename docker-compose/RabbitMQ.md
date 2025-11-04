# Docker Composer RabbitMQ

### File .env
```
RABBITMQ_USER=guest
RABBITMQ_PASS=guest
RABBITMQ_SERVER=service-rabbitmq-server
RABBITMQ_PORT1=5672
RABBITMQ_PORT2=15672
```


### File docker-compose.yaml
```
version: '3'

services:

  service-rabbitmq-server:
    container_name: ${RABBITMQ_SERVER}
    image: rabbitmq:3.10.25-management-alpine
    deploy:
       resources:
           limits:
             cpus: '1.5'
             memory: 500M
           reservations:
             cpus: '1.0'
             memory: 200M    
    ports:
      - ${RABBITMQ_PORT1}:5672
      - ${RABBITMQ_PORT2}:15672
    restart: none
    environment:
      RABBITMQ_ERLANG_COOKIE: "rabbitcookie"
      RABBITMQ_DEFAULT_USER: ${RABBITMQ_USER}
      RABBITMQ_DEFAULT_PASS: ${RABBITMQ_PASS}
    volumes:
       - db_data:/var/lib/rabbitmq
       - db_data:/var/log/rabbitmq    
       - db_data:/var/lib/rabbitmq/mnesia
    networks:
      - network_dev

networks:
  network_dev:
    external: true
  
volumes:
  db_data:
    driver: local  
```










