# SERVIDOR MONGO VER. 7

## Archivo ".env"
```
MONGO_CONTAINER_NAME=mongodb_server
MONGO_INITDB_ROOT_USERNAME=admin
MONGO_INITDB_ROOT_PASSWORD=password123
MONGO_PORT=27017
MONGO_CONTAINER_MEM_LIMIT=2g
MONGO_CONTAINER_MEM_RESERV=1g

MONGO_EXPRESS_PORT=8084

```

## Archivo "docker-compose.yml"
```
services:
  mongodb:
    image: mongo:7.0
    container_name: ${MONGO_CONTAINER_NAME}
    restart: unless-stopped
    ports:
      - "${MONGO_PORT}:27017"
    environment:
      - MONGO_INITDB_ROOT_USERNAME=${MONGO_INITDB_ROOT_USERNAME}
      - MONGO_INITDB_ROOT_PASSWORD=${MONGO_INITDB_ROOT_PASSWORD}
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: ${MONGO_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: "0.1"
          memory: ${MONGO_CONTAINER_MEM_RESERV}
    volumes:
      - mongo_data:/data/db
      
  service-mongodb-express:
    container_name: service-mongodb-express
    deploy:
       resources:
           limits:
             cpus: '0.7'
             memory: 512M
           reservations:
             cpus: '0.5'
             memory: 256M        
    image: mongo-express:1.0.0-alpha.4
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: ${MONGO_INITDB_ROOT_USERNAME}
      ME_CONFIG_MONGODB_ADMINPASSWORD: ${MONGO_INITDB_ROOT_PASSWORD}
      ME_CONFIG_MONGODB_SERVER: ${MONGO_CONTAINER_NAME}
    ports:
      - ${MONGO_EXPRESS_PORT}:8081
    restart: none
    depends_on:
      - mongodb      

volumes:
  mongo_data:
    driver: local
```
