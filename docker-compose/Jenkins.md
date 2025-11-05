# Docker Composer JENKINS


### File docker-compose.yaml
```
version: "3"

services:
  service-jenkins-server:
    container_name: service-jenkins-server
    #image: sramire3000/jenkins_server:1.0.0
    image: jenkins/jenkins
    deploy:
       resources:
          limits:
             cpus: '0.7'
             memory: 1024M
          reservations:
             cpus: '0.5'
             memory: 512M  
    expose:
      - 9080
    ports:
      - "9080:9080"
    restart: no
    networks:
      - network_dev
    environment:
      - JENKINS_OPTS="--httpPort=9080"
    volumes:
      - ./jenkins_data:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
networks:
  network_dev:
    external: true
```
