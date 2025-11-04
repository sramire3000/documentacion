# Docker Composer MONGO DB

### File .env
```
MONGO_USERNAME=admin
MONGO_PASSWORD=password
MONGO_SERVER=server-mongodb
MONGO_PORT=27017
MONGO_EXPRESS_PORT=8084
```

### File docker-compose.yaml
```
version: '3'

services:

  service-mongodb-server:   
    container_name: ${MONGO_SERVER}
    deploy:
       resources:
           limits:
             cpus: '0.7'
             memory: 1024M
           reservations:
             cpus: '0.5'
             memory: 512M     
    image: mongo
    ports:
      - ${MONGO_PORT}:27017
    restart: none
    networks:
      - network_dev
    volumes:      
       - db_data:/data/configdb
       - db_data:/data/db
    environment:
      MONGO_INITDB_ROOT_USERNAME: ${MONGO_USERNAME}
      MONGO_INITDB_ROOT_PASSWORD: ${MONGO_PASSWORD}
    command: ['--auth']
    
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
    networks:
      - network_dev    
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: ${MONGO_USERNAME}
      ME_CONFIG_MONGODB_ADMINPASSWORD: ${MONGO_PASSWORD}
      ME_CONFIG_MONGODB_SERVER: ${MONGO_SERVER}
    ports:
      - ${MONGO_EXPRESS_PORT}:8081
    restart: none
    depends_on:
      - service-mongodb-server
  
networks:
  network_dev:
    external: true

volumes:
  db_data:
    driver: local      
  
```


