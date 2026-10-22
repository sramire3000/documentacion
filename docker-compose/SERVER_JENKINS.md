# SERVIDOR JENKINGS

## Archivo ".env"
```
# ==========================================================
# Jenkins - Entorno de desarrollo
# Toda la configuración ajustable vive aquí; docker-compose.yml
# únicamente consume estas variables.
# ==========================================================

# ---- Proyecto / contenedor / imagen ----
COMPOSE_PROJECT_NAME=jenkins-dev
JENKINS_IMAGE=jenkins/jenkins:lts-jdk21
JENKINS_LOCAL_IMAGE=jenkins-dev:local
JENKINS_CONTAINER_NAME=jenkins-dev
JENKINS_RESTART_POLICY=unless-stopped
TZ=America/El_Salvador

# ---- Red ----
# 0.0.0.0 = accesible desde la red local; 127.0.0.1 = solo esta laptop.
JENKINS_BIND_IP=0.0.0.0
# Puerto de la interfaz web en el host (8090 si 8080 está ocupado).
JENKINS_HTTP_PORT=8080
# Puerto publicado en el host para agentes remotos.
JENKINS_AGENT_PORT=50000
# Puerto interno del agente (debe coincidir con el destino del mapeo).
JENKINS_AGENT_PORT_INTERNAL=50000
JENKINS_NETWORK_NAME=jenkins_dev_net

# ---- Persistencia (volumen físico en el host, NO volumen de Docker) ----
# Ruta relativa usada por Compose; se crea automáticamente.
JENKINS_HOME_HOST_PATH=./data/jenkins_home
# Ruta ABSOLUTA equivalente (obligatoria). Si mueves la carpeta del proyecto
# debes actualizarla. Se monta también dentro del contenedor con la misma
# ruta para que `docker run -v` de los pipelines funcione contra el daemon
# del host.
JENKINS_HOME_HOST_ABS_PATH=/home/hsr/docker-compose/jenkins/data/jenkins_home

# ---- Permisos ----
# UID/GID del usuario del host (hsr = 1000). Los archivos creados por Jenkins
# en ./data/jenkins_home quedarán a tu nombre.
JENKINS_UID=1000
JENKINS_GID=1000
# GID del grupo "docker" del host (ver: getent group docker). Permite usar
# /var/run/docker.sock sin ejecutar Jenkins como root.
DOCKER_GID=969

# ---- Usuario administrador inicial ----
# Se crea solo la primera vez que arranca (JENKINS_HOME vacío). Cambia la
# contraseña antes de exponer el servicio fuera de tu red local.
JENKINS_ADMIN_ID=admin
JENKINS_ADMIN_PASSWORD=jenkins-dev-2026
JENKINS_ADMIN_NAME=Jenkins Admin
JENKINS_ADMIN_EMAIL=admin@localhost
# Ejecuciones concurrentes en el nodo controlador.
JENKINS_EXECUTORS=2

# ---- JVM ----
# Porcentaje del límite de memoria del contenedor dedicado al heap de Java.
JENKINS_JAVA_MAX_RAM_PERCENTAGE=60
JENKINS_JAVA_INITIAL_RAM_PERCENTAGE=20
# Opciones extra para el WAR (ej. --prefix=/jenkins). Vacío = sin extra.
JENKINS_OPTS=

# ---- Límites de recursos (desarrollo) ----
# cpus: 1.0 = un núcleo completo.
JENKINS_CPUS_LIMIT=2.0
JENKINS_CPUS_RESERVATION=0.5
# El heap (60%) queda holgado dentro de este límite.
JENKINS_MEM_LIMIT=4g
JENKINS_MEM_RESERVATION=512m

# ---- Healthcheck / apagado ----
JENKINS_HEALTHCHECK_INTERVAL=30s
JENKINS_HEALTHCHECK_TIMEOUT=10s
JENKINS_HEALTHCHECK_RETRIES=10
JENKINS_HEALTHCHECK_START_PERIOD=120s
JENKINS_STOP_GRACE_PERIOD=60s
```

## Archivo "docker-compose.yml"
```
name: ${COMPOSE_PROJECT_NAME:-jenkins-dev}

services:
  jenkins:
    build:
      context: .
      dockerfile: Dockerfile
      args:
        JENKINS_BASE_IMAGE: ${JENKINS_IMAGE:-jenkins/jenkins:lts-jdk21}
    image: ${JENKINS_LOCAL_IMAGE:-jenkins-dev:local}
    container_name: ${JENKINS_CONTAINER_NAME:-jenkins-dev}
    restart: ${JENKINS_RESTART_POLICY:-unless-stopped}

    # UID/GID del usuario del host (1000 = hsr). Así los archivos del volumen
    # físico quedan a nombre del usuario y no de root.
    user: "${JENKINS_UID:-1000}:${JENKINS_GID:-1000}"
    # GID del grupo "docker" del host: permite usar /var/run/docker.sock.
    group_add:
      - "${DOCKER_GID:-969}"

    environment:
      TZ: ${TZ:-America/Mexico_City}
      # Heap calculado como porcentaje del límite de memoria del contenedor.
      JAVA_OPTS: >-
        -XX:MaxRAMPercentage=${JENKINS_JAVA_MAX_RAM_PERCENTAGE:-60}
        -XX:InitialRAMPercentage=${JENKINS_JAVA_INITIAL_RAM_PERCENTAGE:-20}
        -Djava.awt.headless=true
        -Duser.timezone=${TZ:-America/Mexico_City}
        -Djenkins.install.runSetupWizard=false
      JENKINS_OPTS: ${JENKINS_OPTS:-}
      JENKINS_SLAVE_AGENT_PORT: ${JENKINS_AGENT_PORT_INTERNAL:-50000}
      # Usuario inicial creado por init.groovy.d/01-security.groovy
      JENKINS_ADMIN_ID: ${JENKINS_ADMIN_ID:-admin}
      JENKINS_ADMIN_PASSWORD: ${JENKINS_ADMIN_PASSWORD:-admin}
      JENKINS_ADMIN_NAME: ${JENKINS_ADMIN_NAME:-Jenkins Admin}
      JENKINS_ADMIN_EMAIL: ${JENKINS_ADMIN_EMAIL:-admin@localhost}
      JENKINS_EXECUTORS: ${JENKINS_EXECUTORS:-2}

    ports:
      # UI web. 0.0.0.0 para acceder desde otros equipos de la red local.
      - "${JENKINS_BIND_IP:-0.0.0.0}:${JENKINS_HTTP_PORT:-8080}:8080"
      # Puerto de agentes (solo necesario si agregas agentes remotos).
      - "${JENKINS_BIND_IP:-0.0.0.0}:${JENKINS_AGENT_PORT:-50000}:${JENKINS_AGENT_PORT_INTERNAL:-50000}"

    volumes:
      # Volumen PERSISTIDO FÍSICO en el host (bind mount), no volumen de Docker.
      - ${JENKINS_HOME_HOST_PATH:-./data/jenkins_home}:/var/jenkins_home:Z
      # El mismo directorio montado también en su ruta absoluta del host. Así,
      # si un pipeline ejecuta "docker run -v <ruta del workspace>", el daemon
      # del host (al que apunta el socket) encuentra la ruta real. Debe ser la
      # ruta absoluta verdadera de JENKINS_HOME_HOST_PATH.
      - ${JENKINS_HOME_HOST_ABS_PATH}:${JENKINS_HOME_HOST_ABS_PATH}:Z
      # Docker del host (docker-outside-of-docker). Sin :Z para no romper el
      # etiquetado SELinux del socket.
      - /var/run/docker.sock:/var/run/docker.sock

    # Límites para desarrollo (Compose v2 los aplica al contenedor).
    cpus: "${JENKINS_CPUS_LIMIT:-2.0}"
    mem_limit: "${JENKINS_MEM_LIMIT:-4g}"
    mem_reservation: "${JENKINS_MEM_RESERVATION:-512m}"
    # Equivalentes para Swarm / compatibilidad con deploy.
    deploy:
      resources:
        limits:
          cpus: "${JENKINS_CPUS_LIMIT:-2.0}"
          memory: "${JENKINS_MEM_LIMIT:-4g}"
        reservations:
          cpus: "${JENKINS_CPUS_RESERVATION:-0.5}"
          memory: "${JENKINS_MEM_RESERVATION:-512m}"

    ulimits:
      nofile:
        soft: 65536
        hard: 65536

    healthcheck:
      test: ["CMD-SHELL", "curl -fsS http://127.0.0.1:8080/login >/dev/null || exit 1"]
      interval: ${JENKINS_HEALTHCHECK_INTERVAL:-30s}
      timeout: ${JENKINS_HEALTHCHECK_TIMEOUT:-10s}
      retries: ${JENKINS_HEALTHCHECK_RETRIES:-10}
      start_period: ${JENKINS_HEALTHCHECK_START_PERIOD:-120s}

    stop_grace_period: ${JENKINS_STOP_GRACE_PERIOD:-60s}

networks:
  default:
    name: ${JENKINS_NETWORK_NAME:-jenkins_dev_net}
```

## Archivo "Dockerfile"
```
ARG JENKINS_BASE_IMAGE=jenkins/jenkins:lts-jdk21
FROM ${JENKINS_BASE_IMAGE}

# Cliente de Docker + plugin de Compose para poder construir/levantar
# contenedores en el Docker del host (socket montado por docker-compose.yml).
USER root

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        ca-certificates \
        curl \
        git \
        gnupg \
        jq \
        unzip \
    && install -m 0755 -d /etc/apt/keyrings \
    && curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc \
    && chmod a+r /etc/apt/keyrings/docker.asc \
    && . /etc/os-release \
    && echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian ${VERSION_CODENAME} stable" \
        > /etc/apt/sources.list.d/docker.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
        docker-ce-cli \
        docker-buildx-plugin \
        docker-compose-plugin \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Plugins base. Se instalan en /usr/share/jenkins/ref y el entrypoint oficial
# los copia a JENKINS_HOME la primera vez que arranca el contenedor.
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Scripts de inicialización (usuario admin, executors) desde variables de .env.
COPY init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/

USER jenkins
```

## Archivo "plugins.txt"
```
git
workflow-aggregator
pipeline-stage-view
docker-workflow
docker-plugin
credentials-binding
build-timeout
timestamper
ws-cleanup
ssh-slaves
matrix-auth
job-dsl
configuration-as-code
```

## Archivo "init.groovy.d/01-security.groovy"
```
import hudson.model.User
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy
import hudson.security.HudsonPrivateSecurityRealm
import hudson.tasks.Mailer
import jenkins.model.Jenkins

// Crea el usuario administrador y los executors a partir de las variables
// definidas en .env. Solo actúa la primera vez (cuando aún no hay securityRealm).
def env = System.getenv()
def jenkins = Jenkins.get()

def adminId = env['JENKINS_ADMIN_ID']?.trim()
def adminPassword = env['JENKINS_ADMIN_PASSWORD']

if (!(jenkins.getSecurityRealm() instanceof HudsonPrivateSecurityRealm) && adminId && adminPassword) {
    def realm = new HudsonPrivateSecurityRealm(false)
    jenkins.setSecurityRealm(realm)

    def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
    strategy.setAllowAnonymousRead(false)
    jenkins.setAuthorizationStrategy(strategy)

    def user = realm.createAccount(adminId, adminPassword)
    user.setFullName(env['JENKINS_ADMIN_NAME'] ?: 'Jenkins Admin')
    def email = env['JENKINS_ADMIN_EMAIL']
    if (email) {
        user.addProperty(new Mailer.UserProperty(email))
    }
    user.save()

    jenkins.save()
    println "[init] Usuario administrador '${adminId}' creado."
}

def executors = (env['JENKINS_EXECUTORS'] ?: '').trim()
if (executors.isInteger() && executors.toInteger() > 0) {
    jenkins.setNumExecutors(executors.toInteger())
    jenkins.save()
    println "[init] Executors configurados en ${executors}."
}
```

Ajustes obligatorios en el .env de destino
1. JENKINS_HOME_HOST_ABS_PATH → ruta absoluta real en la nueva máquina.
2. DOCKER_GID → getent group docker (cambia entre distros).
3. JENKINS_UID / JENKINS_GID → id -u / id -g.
4. JENKINS_HTTP_PORT si 8080 está ocupado.

Antes de arrancar
mkdir -p data/jenkins_home          # que quede a tu nombre, no de root
docker compose up -d --build        # la imagen jenkins-dev:local se reconstruye aquí
La imagen no se puede "llevar": se reconstruye con el Dockerfile (necesita internet la primera vez).
Si en cambio quieres migrar la instalación existente
Entonces sí copia también data/jenkins_home/ (con permisos 1000:1000). Es lo único que contiene jobs, credenciales y plugins instalados. Todo lo demás es reproducible desde los 5 archivos.


## Configuracion global
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
