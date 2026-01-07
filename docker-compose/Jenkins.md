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
JENKINS_HTTP_PORT=3000
JENKINS_HOST_PORT=3000
JENKINS_CONTAINER_NAME=server-jenkins

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

```
+Instalar Plugin (Panel de Control>Administrar Jenkins>Plugins)
	+LOCALE
	+Maven Integration
	+Nexus Artifact Uploader
	+Pipeline Utility Steps
	+SonarQube Scanner
	+Reiniciar

+Configuracion Idioma (System)
	+Administrar Jenkins > Configuración del sistema 
		+LOCALE	
			+Default Language = en_US
			+Ignore browser preference and force this language to all users = Check


+Add Credentials
	+Manage Jenkins>Credentials>System>Global credentials
		+New credentials
			-username = user-server-config
			-password = 
			-id       = git_credentials	
		+New credentials
			-username = admin
			-password = password
			-id       = nexus_credentials
		+Sonarqube
            -Id         = sonar_credentials
            Secret Text = 

+Configurar Maven
	+Tools
		+Maven installations
		    +Add Maven
		    +Name = "Maven3"
			+Install automatically
			+Version3.9.5
        +SonarQube Scanner installations
		    +Name = SonarQube
            +Install Automatic = check

	
+Configurar Sonar (System)
	+SonarQube installations
		+Sonar Add
			-Name = SonarQube
			-URL  = http://host.docker.internal:9000
            -Server authentication token = sonar_credentials
```