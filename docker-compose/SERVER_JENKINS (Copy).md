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

## Configuracion
```
+Instalar Plugin (Panel de Control>Administrar Jenkins>Plugins)
	+LOCALE
	+Maven Integration
	+Nexus Artifact Uploader
	+Pipeline Utility Steps
	+SonarQube Scanner
    +Nodejs
	+Reiniciar

+Configuracion Idioma (System)
	+Administrar Jenkins > Configuración del sistema 
		+LOCALE	
			+Default Language = en_US
			+Ignore browser preference and force this language to all users = Check


+Add Credentials
	+Manage Jenkins>Credentials>System>Global credentials
		+New credentials global
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
        +instalaciones de NodeJS
            +name = node_js
            +version = nodejs 24.3.0
            +global npm = @angular/cli

	
+Configurar Sonar (System)
	+SonarQube installations
		+Sonar Add
			-Name = SonarQube
			-URL  = http://host.docker.internal:9000
            -Server authentication token = sonar_credentials
```
