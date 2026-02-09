# Install software Ubuntu 24 Desktop

## SOFTWARE ADMINISTRACION

### Cambiar Password root
```bash
sudo passwd root
```

### Swap agrresivo
```bash
para hacerlo permanente, edita /etc/sysctl.conf y agrega:
vm.swappiness=60

#Temporal
sudo sysctl vm.swappiness=60
```

### Cambiar nombre al equipo
```bash
sudo hostnamectl set-hostname hsr.com.sv
```

### Actualizar repositorios Ubuntu
```bash
sudo apt update

```

### Actualizar ubuntu
```bash
sudo apt upgrade
```

### Instalar un .deb
```bash
sudo dpkg -i nombre_del_archivo.deb
```

### Forzar reinstalación ignorando conflictos
```bash
sudo dpkg -i --force-all nombre_del_paquete.deb
```



## UTILIDADES

### Check Bateria
```bash
upower -i /org/freedesktop/UPower/devices/battery_BAT0
sudo apt install acpi
acpi -V
```

### Install Gparted
```bash
sudo apt install gparted
```

### Install Btop monitor
```bash
sudo snap install btop
btop
```

### Install Stacer monitoreo
```bash
sudo apt install stacer
```

### Install Wget
```bash
sudo apt install wget
```

### Install curl
```bash
sudo apt  install curl 
```

### shutter
```bash
sudo apt install shutter
sudo apt install flameshot
```
### Install fastfetch informacion global
```bash
sudo add-apt-repository ppa:zhangsongcui3371/fastfetch
sudo apt update
sudo apt install fastfetch
```

### install libnss3
```bash
sudo apt-get install libnss3-tools
```

### cmake
```bash
sudo apt install cmake
sudo apt install ninja-build
sudo apt install pkg-config
sudo apt install libgtk-3-dev
Reboot system

#Install convert de RPM a DEB
sudo apt install alien -y

#Convert rpm a deb
sudo alien [Archivo RPM]
sudo alien -k archivo.tar.xz
```

### Install dbeaver
```bash
sudo snap install dbeaver-ce
```

### Set Path Chrome
```bash
sudo nano /etc/environment.
CHROME_EXECUTABLE=/snap/bin/chromium
```

### Install Zeal Documentacion lenguajes
```bash
sudo apt-get install zeal
```

### Istall Bootles
```bash
sudo apt install flatpak -y
reboot
sudo flatpak remote-add --if-not-exists flathub https://flathub.org/repo/flathub.flatpakrepo
flatpak install flathub com.usebottles.bottles -y
flatpak run com.usebottles.bottles
```

## SOFTWARE DE DESARROLLO

### Buscar Jdks
```bash
sudo apt search openjdk | grep -E 'openjdk-.*-jdk/'
```

### Install JDK's
```bash
sudo apt -y install openjdk-17-jdk
sudo apt install openjdk-21-jdk
sudo apt install openjdk-25-jdk
java --version
```

### Cambiarse de Jdk
```bash
sudo update-alternatives --config java
```

### Install GIT
```bash
sudo apt install git
git config --global user.email "sramire3000@gmail.com"
git config --global user.name "Hector Ramirez"
```

### Install Maven
```bash
udo apt install maven
```

### Install FVM
```bash
wget https://github.com/leoafarias/fvm/releases/download/3.2.1/fvm-3.2.1-linux-x64.tar.gz
sudo tar -xvf fvm-3.2.1-linux-x64.tar.gz
mkdir .fvm_flutter
cd .fvm_flutter/
mkdir bin
cd bin/
#copiar fvm y src en el bin

#sudo nano /etc/environment
PATH="........:/snap/bin:/home/hector-ramirez/.fvm_flutter/bin"
```

### Cambiar de Jdk con fvm
```bash
fvm flutter config --jdk-dir=/snap/android-studio/current/jbr
fvm flutter config --jdk-dir=//usr/lib/jvm/java-21-openjdk-amd64
fvm flutter config --jdk-dir=/usr/lib/jvm/java-17-openjdk-amd64
```

### Install GitDesktop
```bash
wget https://github.com/shiftkey/desktop/releases/download/release-3.1.7-linux1/GitHubDesktop-linux-3.1.7-linux1.deb
sudo dpkg -i <filename>.deb
sudo dpkg -i GitHubDesktop-linux-3.1.7-linux1.deb
```

### Instal STS
```bash
sudo mv spring-tools-for-eclipse-4.30.0.RELEASE-e4.35.0-linux.gtk.x86_64.tar.gz /opt/
cd /opt/
sudo tar -xvf spring-tools-for-eclipse-4.30.0.RELEASE-e4.35.0-linux.gtk.x86_64.tar.gz
cp sts-4.32.1.RELEASE /opt/sts-4.30.0.RELEASE
cd sts-4.30.0.RELEASE/
sudo nano /usr/share/applications/STS.desktop

#Shorcut
[Desktop Entry]
Name=STST 4.30.0
Comment=SSTST 4.30.0
Exec=/opt/sts-4.30.0.RELEASE/SpringToolSuite4
Icon=/opt/sts-4.30.0.RELEASE/icon.xpm
StartupNotify=true
Terminal=false
Type=Application
Categories=Development;IDE;
```

### Install Putty
```bash
sudo apt install putty putty-tools 
```

### Install DOCKER
Info URL https://docs.docker.com/engine/install/ubuntu/
```bash
#Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo service docker start
sudo docker run hello-world
sudo systemctl status docker
sudo docker run hello-world
sudo groupadd docker
sudo usermod -aG docker $USER

newgrp docker
```

### install Docker compose
```bash
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

### Instalar portainer
```bash
https://voidnull.es/instalar-portainer-en-ubuntu-24-04/
docker volume create portainer_data
docker run -d -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce:latest --restart=always
http://localhost:9000/
```

### Install Node Version Manager
```bash
# URL DOC https://github.com/nvm-sh/nvm?tab=readme-ov-file#installing-and-updating
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh | bash

nvm --version
nvm lits
```

### Install Jmeter
```bash
java --version
Download
https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.tgz

tar -xvzf apache-jmeter-5.6.3.tgz
sudo mv apache-jmeter-5.6.3  /opt/jmeter
cd /opt/jmeter
cd
pwd
sudo open .bashrc
--Add path
     export PATH=$PATH:/opt/jmeter/bin
source .bashrc
jmeter
```

### Apache Bencmark
```bash
sudo apt install apache2-utils
ab -v
```

### Install Sql Server Using docker run:
```bash
docker run -d -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Password.1234" --name my-mssql-server -p 1433:1433 rapidfort/microsoft-sql-server-2019-ib:latest
```

### Install .net
```bash
https://learn.microsoft.com/en-us/dotnet/core/install/linux-ubuntu-install?tabs=dotnet9&pivots=os-linux-ubuntu-2404
```

### Install Bruno
```bash
sudo snap install bruno
```








