# SERVIDOR JENKINGS

## Archivo ".env"
```
JENKINS_PORT=8080
JENKINS_CONTAINER_NAME=jenkins
JENKINS_CONTAINER_MEM_LIMIT=4g
JENKINS_CONTAINER_MEM_RESERV=2g
```

## Archivo "docker-compose.yml"
```
services:
  jenkins:
    image: jenkins/jenkins:lts
    container_name: ${JENKINS_CONTAINER_NAME}
    privileged: true
    user: root
    ports:
      - "${JENKINS_PORT}:8080"
      - "50000:50000"
    volumes:
      - ./jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    restart: unless-stopped
    deploy:
      resources:
        limits:
          cpus: '2.0'
          memory: ${JENKINS_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: '0.5'
          memory: ${JENKINS_CONTAINER_MEM_RESERV}
```
