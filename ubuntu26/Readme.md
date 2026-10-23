# Ubutnu Versión 26


## Cambiar Password root
```
sudo passwd root
```

## Install ananicy-cpp

1. Instalar las dependencias de compilación y librerías necesarias:
```
sudo apt update
sudo apt install build-essential cmake git libsystemd-dev libspdlog-dev nlohmann-json3-dev libfmt-dev libbpf-dev libelf-dev   
```

2. Clonar el repositorio oficial:
```
git clone https://gitlab.com/ananicy-cpp/ananicy-cpp.git
cd ananicy-cpp   
```

3. Compilar e instalar:
```
mkdir build && cd build
cmake -DCMAKE_BUILD_TYPE=Release -DENABLE_SYSTEMD=ON ..
make -j$(nproc)
sudo make install
```

4. Instalar las reglas de la comunidad (esenciales para que funcione):
Ananicy-cpp necesita un conjunto de reglas para saber a qué aplicaciones priorizar o despriorizar. Se suelen clonar las reglas de CachyOS en su directorio correspondiente:
```
sudo mkdir -p /etc/ananicy.d
sudo git clone https://github.com/CachyOS/ananicy-rules.git /etc/ananicy.d/rules
```

5. Iniciar y activar el servicio en systemd:
```
sudo systemctl enable --now ananicy-cpp
```

Aunque requiere esos pasitos extra de compilación en comparación con Fedora, una vez instalado en Ubuntu funciona exactamente igual, ofreciendo la misma gestión ligera de prioridades para evitar que los equipos con pocos núcleos se Congelen.

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
