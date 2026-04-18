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

# Install Soporte de video
```
sudo dnf install https://mirrors.rpmfusion.org/ -E %fedora).noarch.rpm https://mirrors.rpmfusion.org/ -E %fedora).noarch.rpm -y
sudo dnf update -y
sudo dnf install intel-media-driver -y
```




