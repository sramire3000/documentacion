# Docker Composer MYSQL

### File .env
```
MYSQL57_ROOT_PASSWORD=[password]
MYSQL57_SERVER=service-mysql57-server
MYSQL57_PORT=3306
```

### File docker-compose.yaml
```
version: '3'

services:

  service-mysql57-server:   
    container_name: ${MYSQL57_SERVER}
    image: mysql:5.7
    command: --default-authentication-plugin=mysql_native_password
    deploy:
       resources:
           limits:
             cpus: '1.5'
             memory: 500M
           reservations:
             cpus: '1.0'
             memory: 200M
    ports:
      - ${MYSQL57_PORT}:3306
    restart: always
    networks:
      - network_dev
    volumes:
      - db_data:/var/lib/mysql
      - ./schemazipkin.sql:/docker-entrypoint-initdb.d/schemazipkin.sql
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL57_ROOT_PASSWORD}
         
networks:
  network_dev:
    external: true
  

  
volumes:
  db_data:
    driver: local  

```


