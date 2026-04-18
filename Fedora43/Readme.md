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






