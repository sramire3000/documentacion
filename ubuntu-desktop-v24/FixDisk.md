# El SSD puede estar en modo ahorro de energía agresivo (muy común en Dell)

## Algunos modelos Dell activan APST / power saving en NVMe, lo que hace que el disco “despierte” lento cuando abres un programa.

Primero verifica si tu disco es NVMe:
```
lsblk -o name,rota
```

Si rota dice 0, es SSD.
Si el nombre es tipo nvme0n1, es NVMe.

Luego prueba desactivar ahorro NVMe temporalmente:

Edita:
```
sudo nano /etc/default/grub
```

Busca esta línea:
```
GRUB_CMDLINE_LINUX_DEFAULT="quiet splash"
```
Y cámbiala por:
```
GRUB_CMDLINE_LINUX_DEFAULT="quiet splash nvme_core.default_ps_max_latency_us=0"
```
Luego:
```
sudo update-grub
sudo reboot
```
