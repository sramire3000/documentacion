# Docker Composer NEXUS 3

### File .env
```
NEXUS_SERVER=service-nexus3-server
NEXUS_PORT1=8110
NEXUS_PORT2=8120
NEXUS_PORT3=8130
```

### Crear Carpeta "nexus-data" y dar permisos
```
sudo chmod 777 nexus-data
```

### File docker-compose.yaml
```
version: "3"

services:
  service-nexus3-server:
    image: sonatype/nexus3  
    container_name: ${NEXUS_SERVER}
    deploy:
       resources:
          limits:
             cpus: '0.7'
             memory: 2560M
          reservations:
             cpus: '0.5'
             memory: 1536M      
    expose:
      - ${NEXUS_PORT1}
      - ${NEXUS_PORT2}
      - ${NEXUS_PORT3}
    ports:
      - ${NEXUS_PORT1}:8081
    restart: no
    volumes:
      - ./nexus-data:/nexus-data
    networks:
      - network_dev
      
networks:
  network_dev:
    external: true
    
volumes:
  nexus-data:
    driver: local     
```

### URL
- [Nexus 3](http://localhost:8110/)




