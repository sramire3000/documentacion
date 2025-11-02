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

# Install Btop monitor
```bash
sudo snap install btop
btop
```




