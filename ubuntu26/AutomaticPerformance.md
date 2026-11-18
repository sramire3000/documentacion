# Automatic Perfomance

### 1.Crear el script de automatización:Paso 1.Abre tu terminal y crea un archivo de script con el siguiente comando: 
```
sudo nano /usr/local/bin/power-profile-auto.sh
```

Copia y pega el siguiente contenido en el archivo:
```
#!/bin/bash
AC_PATH="/sys/class/power_supply/AC/online"

if [[ -f "$AC_PATH" ]]; then
    AC_STATE=$(cat "$AC_PATH")
    case "$AC_STATE" in
        "1")
            powerprofilesctl set performance
            ;;
        "0")
            powerprofilesctl set power-saver
            ;;
    esac
fi
```


### 2.Dar permisos de ejecución al script:Paso 2.Haz que el sistema operativo tenga permisos para ejecutar este script escribiendo:
```
sudo chmod +x /usr/local/bin/power-profile-auto.sh
```

### 3.Configurar la regla Udev (Detección por Hardware):
    Crea una regla de udev para que el sistema detecte inmediatamente cuando conectes o desconectes el cargador:
```
sudo nano /etc/udev/rules.d/99-power-profile.rules
```

Pega la siguiente línea (asegúrate de que el nombre del subsistema coincida, normalmente es AC*):
```
SUBSYSTEM=="power_supply", KERNEL=="AC*", ACTION=="change", RUN+="/usr/local/bin/power-profile-auto.sh"
```

### 4.Crear un servicio systemd para el inicio:Paso
Para asegurarte de que también aplique el perfil correcto apenas enciendas el equipo según cómo esté conectado, crea un servicio systemd:
```
sudo nano /etc/systemd/system/power-profile-auto.service
```
Pega lo siguiente:
```
[Unit]
Description=Set power profile based on AC state at boot
After=multi-user.target

[Service]
Type=oneshot
ExecStart=/usr/local/bin/power-profile-auto.sh

[Install]
WantedBy=multi-user.target
```

### 5.Activar el servicio y recargar reglas
```
sudo systemctl daemon-reload
sudo systemctl enable power-profile-auto.service
sudo udevadm control --reload-rules
sudo udevadm trigger --subsystem-match=power_supply
```


