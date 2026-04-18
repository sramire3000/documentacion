# INSTALL FEDORA 43


- [Download](https://download.fedoraproject.org/pub/fedora/linux/releases/43/Workstation/x86_64/iso/Fedora-Workstation-Live-43-1.6.x86_64.iso)

## Configuracion

### Cambiar el Password Root
```
sudo passwd root
```

# Update fedora
```
sudo dnf upgrade –refresh
```

# Update paquetes flatpak
```
flatpak update
```

# Verfifica actualizaciones de firmware
```
fwipdmgr get-updates
```

# Install htop
```
sudo dnf install htop
```

# Install nano
```
sudo dnf install nano
```
