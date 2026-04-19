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
### Install Angular
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



