# Ubutnu Versión 26


## Cambiar Password root
```
sudo passwd root
```

## Install SDKMAN! (Recomendado para desarrolladores Multiples JDK)

### Install unzip
```
sudo apt update && sudo apt install curl zip unzip
```

### Ejecuta el script oficial de instalación en tu terminal:
```
curl -s "https://get.sdkman.io" | bash
```

### Una vez termine, inicializa SDKMAN! ejecutando:
```
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

### Comandos
```
# Lista todos los jdk online
sdk list java

# Lista los instalados
sdk list java | grep -E "installed|local"

# Install jdk
sdk install java 27.0.0.0+35-tem
sdk install java 26.0.2+1.1-tem
sdk install java 25.0.4.1+1-tem
sdk install java 21.0.12+1.1-tem
sdk install java 17.0.20-tem

# Usar un jdk
sdk use java 17.0.11.tem

# Default jdk
sdk default java 21.0.3.tem

# Jdk en uso
sdk current java

# Eliminar un jdk
sdk uninstall java 17.0.11.tem
```

## Instalar Docker

### Paso 1: Instalar dependencias y configurar el repositorio de Docker

1. Actualiza el sistema e instala prerrequisitos:
```
sudo apt update
sudo apt install ca-certificates curl
```

2. Añade la clave GPG oficial de Docker:
```
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
```

3. Configura el repositorio oficial:
```
sudo tee /etc/apt/sources.list.d/docker.sources <<EOF
Types: deb
URIs: https://download.docker.com/linux/ubuntu
Suites: $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}")
Components: stable
Architectures: $(dpkg --print-architecture)
Signed-By: /etc/apt/keyrings/docker.asc
EOF
```

### Paso 2: Instalar Docker Engine y Docker Compose
```
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

### Paso 3: Verificar la instalación de Docker Compose
```
docker compose version
```

### Paso 4: (Opcional) Permisos de usuario sin sudo
```
sudo apt install util-linux-extra
sudo usermod -aG docker $USER
newgrp docker
sudo usermod -aG docker $USER
docker logs portainer
```

## Install NVM

Paso 1: Instalar dependencias previas (curl)
```
sudo apt update
sudo apt install curl -y
```

Paso 2: Ejecutar el script de instalación de NVM
```
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.8/install.sh | bash
```

Paso 3: Cargar NVM en tu sesión actual
```
export NVM_DIR="$([ -z "${XDG_CONFIG_HOME-}" ] && printf %s "${HOME}/.nvm" || printf %s "${XDG_CONFIG_HOME}/nvm")"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
```

Paso 4: Verificar la instalación
```
command -v nvm
```

Paso 5: Instalar Node.js usando NVM
```
# Imstala la version mas reciente recoemndada
nvm install --lts

# Instala la mas reciente
nvm install node

# Instala una version especifica
nvm install 22
nvm use 22

# Listado de las instaladas
nvm list

# Eliminar
nvm uninstall <version>
```

## Install github Desktop

### Paquetes
```
wget -qO - https://mirror.mwt.me/shiftkey-desktop/gpgkey | gpg --dearmor | sudo tee /usr/share/keyrings/mwt-desktop.gpg > /dev/null
sudo sh -c 'echo "deb [arch=amd64 signed-by=/usr/share/keyrings/mwt-desktop.gpg] https://mirror.mwt.me/shiftkey-desktop/deb/ any main" > /etc/apt/sources.list.d/mwt-desktop.list'
```

### Install
```
sudo apt update && sudo apt install github-desktop
```
