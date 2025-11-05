# Docker Composer SONARQBE

### File .env
```
SONARQUBE_SERVER=service-sonarqube
SONARQUBE_PORT=9000
POSTGRESS_SERVER=host.docker.internal
POSTGRESS_PORT=5432
POSTGRESS_DB=sonar
POSTGRESS_USER=[user_db]
POSTGRESS_PASSWORD=[password_db]
```

### File docker-compose.yaml
```
version: "3"

services:
  service-sonarqube:
    container_name: ${SONARQUBE_SERVER}
    image: sonarqube  
    deploy:
       resources:
          limits:
             cpus: '0.7'
             memory: 2560M
          reservations:
             cpus: '0.5'
             memory: 1536M     
    expose:
      - ${SONARQUBE_PORT}
    ports:
      - ${SONARQUBE_PORT}:9000
    networks:
      - network_dev
    restart: no      
    environment:
      SONARQUBE_JDBC_URL: jdbc:postgresql://${POSTGRESS_SERVER}:${POSTGRESS_PORT}/${POSTGRESS_DB}
      SONARQUBE_JDBC_USERNAME: ${POSTGRESS_USER}
      SONARQUBE_JDBC_PASSWORD: ${POSTGRESS_PASSWORD}
    volumes:
      - ./sonarqube_data:/opt/sonarqube/conf
      - ./sonarqube_data:/opt/sonarqube/data
      - ./sonarqube_data:/opt/sonarqube/extensions
      - ./sonarqube_data:/opt/sonarqube/lib/bundled-plugins
      - ./sonarqube_data:/opt/sonarqube/logs

networks:
  network_dev:
    external: true    
```




