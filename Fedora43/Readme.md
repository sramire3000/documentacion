# INSTALL FEDORA 43


- [Download](https://download.fedoraproject.org/pub/fedora/linux/releases/43/Workstation/x86_64/iso/Fedora-Workstation-Live-43-1.6.x86_64.iso)

## Configuracion

### Cambiar el Password Root
```
sudo passwd root
```

### Update fedora
```
sudo dnf upgrade –refresh
```

### Update paquetes flatpak
```
flatpak update
```

### Verfifica actualizaciones de firmware
```
fwipdmgr get-updates
```

### Install htop
```
sudo dnf install htop
```

### Install nano
```
sudo dnf install nano
```

### Install Soporte de video
```
sudo dnf install https://mirrors.rpmfusion.org/ -E %fedora).noarch.rpm https://mirrors.rpmfusion.org/ -E %fedora).noarch.rpm -y
sudo dnf update -y
sudo dnf install intel-media-driver -y
```

## Install Software

### Install Visual Studio Code
- [Download](https://vscode.download.prss.microsoft.com/dbazure/download/stable/560a9dba96f961efea7b1612916f89e5d5d4d679/code-1.116.0-1776214233.el8.x86_64.rpm)
```
sudo dnf install ./code*.rmp
```

### Install Golang
```
sudo dnf install golang -y
go version
```

### Install Brave Navegador
```
sudo dnf install -y dnf-plugins-core
sudo dnf config-manager addrepo --from-repofile=https://brave-browser-rpm-release.s3.brave.com/brave-browser.repo
sudo dnf install brave-browser -y
```

### Install git
```
sudo dnf install git -y
git config --global user.name "tu_nombre"
git config --global user.email "tu_correo@ejemplo.com"
git config --global credential.helper store

```

### Install GithubDesktop
```
curl -L https://github.com/shiftkey/desktop/releases/download/release-3.4.9-linux1/GitHubDesktop-linux-x86_64-3.4.9-linux1.rpm -o github-desktop.rpm
sudo dnf install ./github-desktop.rpm -y
sudo rm /etc/yum.repos.d/github-desktop.repo
rm github-desktop.rpm
```


### Instalar SDKMAN! Para Uso del JDK
```
# Install SDKMAN
curl -s "https://get.sdkman.io" | bash

# Reinicia terminal y ejecuta:
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Ver lista disponibles
sdk list java

# Instalar Java 17 (Temurin es muy estable)
sdk install java 17.0.10-tem

# Instalar Java 21
sdk install java 21.0.2-tem

# Para las versiones 25 y 26, te recomiendo usar las que ya bajaste con DNF 
# para no duplicar espacio, pero si quieres manejarlas todas con SDKMAN:
sdk install java 25-open

# Ver la version en uso
sdk current java

# Uso
Para usar la 17: sdk use java 17.0.10-tem
Para usar la 21: sdk use java 21.0.2-tem
Para que una sea la fija siempre: sdk default java 17.0.10-tem
```
## Install Maven
```
sdk install maven
```

## Install Angular
```
# 1. Instalar NVM (Node Version Manager)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash

# 2. Instalar las diferentes versiones de Node
nvm install 18  # Para proyectos de Angular un poco más antiguos
nvm install 20  # Versión LTS actual (Recomendada para Angular 17/18)
nvm install 22  # Versión más reciente

# 3. Cambiarse de version de Node
nvm use 14
npm install -g @angular/cli@14

# Cuando lances el servidor de desarrollo, puedes limitar el uso de memoria para que no compita con tus 32GB de RAM y deje aire al CPU:
node --max-old-space-size=4096 ./node_modules/@angular/cli/bin/ng serve
```

## Install Angular 15
```
nvm install 18.10
nvm use 18.10
npm install -g @angular/cli@15.0.1
```

### Instalar dependencias
```
# Instalar dependencias
npm install
```

### Crear Archivo para el uso de NVM
```
# Crear el archivo ".nvmrc" dentro de tu proyecto
# Adicion la version
v18.10.0
# Comando para el uso
nvm use
```

### Optimizacion de Angular Runtime
```
# Edite el archivo "package.json" y haga el siguiente cambio
  "scripts": {
    "start": "node --max-old-space-size=4096 ./node_modules/@angular/cli/bin/ng serve",
  },
```

### Install Android Studio
```
# 1. Descarga de Android Studio
 Ve al sitio oficial: developer.android.com/studio.
 Descarga el archivo .tar.gz para Linux.

# 2. Instalación en el sistema
# Crear la carpeta en /opt (estándar para apps externas)
sudo mkdir /opt/android-studio

# Descomprimir el archivo ahí
sudo tar -zxvf ~/Downloads/android-studio-*.tar.gz -C /opt/

# Darle permisos a tu usuario para que pueda actualizarse solo
sudo chown -R $USER:$USER /opt/android-studio

3. Ejecuta el instalador por primera vez:
opt/android-studio/bin/studio.sh
```

### Insall Flutter
```
4. Configurar un proyecto con una versión específica
cd ~/tu-proyecto-flutter
fvm use stable

Configuración para Flutter (Paso Crítico)
flutter config --android-studio-dir /opt/android-studio
flutter config --android-sdk ~/Android/Sdk

Esto creará una carpeta oculta .fvm en tu proyecto con un enlace simbólico a la versión elegida.

# Ver versiones instaladas:
fvm list

# Ver versiones disponibles para bajar:
fvm releases
```

### Install librerias compilacion Flutter
```
sudo dnf install clang cmake ninja-build gtk3-devel -y
```

### Dejar por defecto
```
fvm global 3.19.3
```

## Instalar FVM despues de flutter

### Install
```
dart pub global activate fvm
echo 'export PATH="$PATH:$HOME/.pub-cache/bin"' >> ~/.bashrc
source ~/.bashrc
fvm --version
```

### Install la version mas estable
```
fvm install stable
fvm use stable
```

### Instalar la version 3.22.0 
```
fvm install 3.22.0
fvm use 3.22.0
```

### Ver Instalados
```
fvm list
```
### Ver disponibles
```
fvm releases
```

## Install DBeaver (Gestión de Bases de Datos)
```
# Asegurarnos de que flathub esté configurado
flatpak remote-add --if-not-exists flathub https://flathub.org/repo/flathub.flatpakrepo

# Instalar DBeaver Community Edition
flatpak install flathub io.dbeaver.DBeaverCommunity -y
```

## Install Postman
```
flatpak install flathub com.getpostman.Postman -y
```
## Instalar PostgreSQL

```
sudo dnf install -y postgresql-server postgresql-contrib
sudo postgresql-setup --initdb
sudo systemctl start postgresql
sudo systemctl enable postgresql
sudo systemctl status postgresql
```
### Crear el usuario
```
# Entrar al entorno del superusuario de Postgres
sudo -i -u postgres

# Crear tu usuario (usa el nombre 'hsr' para que coincida con tu Linux)
createuser --interactive
# Nombre del rol: hsr
# ¿Será superusuario? y

# Crear una base de datos inicial para tus pruebas
createdb hsr

# Entrar a la consola de Postgres con tu usuario
psql

# Ejecutar este comando dentro de la consola (cambia 'tu_clave' por la que quieras)
ALTER USER hsr PASSWORD 'tu_clave';

# Salir de la consola
\q

# Salir del entorno de postgres
exit
```

### Install PgAdmin4
```
# 1. Asegurar que flathub esté activo
flatpak remote-add --if-not-exists flathub https://flathub.org/repo/flathub.flatpakrepo

# 2. Instalar pgAdmin 4
flatpak install flathub org.pgadmin.pgadmin4 -y
```

## Install Docker engine

1. Desinstalar versiones viejas
Fedora a veces trae podman o versiones antiguas. Vamos a limpiar:
```
sudo dnf remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
```
2. Configurar el repositorio oficial
```
sudo dnf -y install dnf-plugins-core
sudo dnf-3 config-manager --add-repo https://download.docker.com/linux/fedora/docker-ce.repo
```
3. Instalar Docker y Docker Compose
```
sudo dnf install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
```
4. Configuración de Post-Instalación (Vital)
```
sudo systemctl start docker
sudo systemctl enable docker

sudo groupadd docker
sudo usermod -aG docker $USER
```

### Test
```
docker run hello-world
```

## Install Chrome
```
wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
sudo dnf install ./google-chrome-stable_current_x86_64.rpm -y
```

## Install p7zip
```
sudo dnf install p7zip p7zip-plugins -y
```

## Install onlyoffice
```
flatpak install flathub org.onlyoffice.desktopeditors -y
```

## Install Keepass
```
flatpak install flathub org.keepassxc.KeePassXC -y
```

# Un tip de oro para tu Dell i7
Como vas a consumir APIs, asegúrate de que cuando corras tu app por primera vez, el firewall de Fedora no bloquee las peticiones. Si notas que la app en el emulador no llega a tu API local, puedes darle permiso temporalmente:

```Bash
sudo firewall-cmd --permanent --add-port=8080/tcp
sudo firewall-cmd --reload
```

## Notepad ++
```
flatpak install flathub com.github.dail8859.NotepadNext -y
```
## Install RustDesk
```
# Download
https://github.com/rustdesk/rustdesk/releases/download/1.4.6/rustdesk-1.4.6-0.x86_64.rpm

#Install
sudo dnf install ./rustdesk-1.x.x-x86_64.rpm
```
