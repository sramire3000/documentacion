# Docker Composer JENKINS

## Jenkins normal

### Crear el folder "jenkins_data"
```
mkdir -p jenkins_data
sudo chmod 777 jenkins_data
```

### File ".env"
```
# Docker Compose Configuration
COMPOSE_PROJECT_NAME=jenkins-project

# Jenkins Configuration
JENKINS_HTTP_PORT=9080
JENKINS_HOST_PORT=9080
JENKINS_CONTAINER_NAME=service-jenkins-server

# Resource Limits
JENKINS_CPU_LIMIT=0.7
JENKINS_MEMORY_LIMIT=1024M
JENKINS_CPU_RESERVATION=0.5
JENKINS_MEMORY_RESERVATION=512M

# Volume Configuration
JENKINS_DATA_DIR=./jenkins_data

# Network Configuration
DOCKER_NETWORK=network_dev
```

### File docker-compose.yaml
```
version: "3"

services:
  service-jenkins-server:
    container_name: ${JENKINS_CONTAINER_NAME}
    image: jenkins/jenkins
    deploy:
       resources:
          limits:
             cpus: '${JENKINS_CPU_LIMIT}'
             memory: ${JENKINS_MEMORY_LIMIT}
          reservations:
             cpus: '${JENKINS_CPU_RESERVATION}'
             memory: ${JENKINS_MEMORY_RESERVATION}
    expose:
      - ${JENKINS_HTTP_PORT}
    ports:
      - "${JENKINS_HOST_PORT}:${JENKINS_HTTP_PORT}"
    restart: no
    networks:
      - ${DOCKER_NETWORK}
    environment:
      - JENKINS_OPTS=--httpPort=${JENKINS_HTTP_PORT}
    volumes:
      - ${JENKINS_DATA_DIR}:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock

networks:
  network_dev:
    external: true
    name: ${DOCKER_NETWORK}
```

## Usando DockerFile

### File dockerfile
```
FROM jenkins/jenkins:lts

USER root

# Instala Docker CLI
RUN apt-get update && \
    apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release && \
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && \
    apt-get install -y docker-ce-cli && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Da permisos al usuario jenkins para usar Docker
RUN groupadd -g 999 docker && \
    usermod -aG docker jenkins

USER jenkins
```

### File docker-compose.yaml
```
version: "3"

services:
  service-jenkins-server:
    container_name: service-jenkins-server
    build: .
    image: jenkins-custom:latest
    user: root
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
      - JENKINS_OPTS=--httpPort=9080
    volumes:
      - ./jenkins_data:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
      - /usr/bin/docker:/usr/bin/docker

networks:
  network_dev:
    external: true

```

### Verificacion de grupo
Asegúrate de que el grupo docker en tu host tenga el GID 999, o ajusta el groupadd -g en el Dockerfile para que coincida con el GID real del grupo docker en tu sistema. Puedes verificarlo con:
```
getent group docker
```

### File .dockerignore
```
# Ignorar archivos y carpetas innecesarios
.git
.gitignore
*.md
*.log
*.env
*.DS_Store
node_modules
tmp/
*.swp
*.bak
docker-compose.yml
*.iml
.idea/
.vscode/
```
