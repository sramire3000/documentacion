# Install software Ubuntu 24 Desktop

## Software Administracion

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


## Utilidades

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
## SOFTWARE DE DESARROLLO

### Install JDK's
```bash
sudo apt -y install openjdk-17-jdk
java --version
sudo apt install openjdk-21-jdk 
java --version
```









