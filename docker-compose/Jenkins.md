# Docker Composer JENKINS

## Jenkins JDK17

### Crear el folder "jenkins_data"
```
mkdir -p jenkins_data
sudo chmod 777 jenkins_data
```

### File docker-compose.yaml
```
version: '3.8'

services:
  jenkins-lts-17:
    image: jenkins/jenkins:lts-jdk17
    container_name: jenkins-server
    restart: unless-stopped
    user: root
    privileged: true
    deploy:
       resources:
           limits:
             cpus: '0.2'
             memory: 2048M
           reservations:
             cpus: '0.05'
             memory: 1024M
    ports:
      - "9060:8080"
      - "50000:50000"
    volumes:
      # Persistencia de TODAS las carpetas de Jenkins
      - jenkins-data:/var/jenkins_home
     
      # Docker integration
      - /var/run/docker.sock:/var/run/docker.sock
      - /usr/bin/docker:/usr/bin/docker:ro
      - /usr/local/bin/docker-compose:/usr/local/bin/docker-compose:ro
     
      # Timezone
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
   
    environment:
      - JAVA_OPTS=-Djava.awt.headless=true -Duser.timezone=America/El_Salvador
      - JENKINS_OPTS=--webroot=/var/cache/jenkins/war
      - DOCKER_HOST=unix:///var/run/docker.sock
     
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/login"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

volumes:
  jenkins-data:
    driver: local
    driver_opts:
      type: none
      o: bind
      device: ./jenkins_data  # Carpeta específica en el host
```

### Configuracion
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
