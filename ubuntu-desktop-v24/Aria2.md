#aria2 (línea de comandos avanzada)
Tipo: Gestor de descargas ligero y potente.

Características:

Descargas multi-fuente (HTTP, FTP, BitTorrent, Metalink).

Divide archivos en múltiples conexiones para máxima velocidad.

Muy eficiente en descargas grandes

### Install
```
sudo apt install aria2
```

### 📥 Ejemplo básico de descarga
```
aria2c https://ejemplo.com/archivo.iso
```

### 🚀 Descarga acelerada con múltiples conexiones
```
aria2c -x 16 https://ejemplo.com/archivo.iso
```

### 🔄 Reanudar descargas interrumpidas
```
aria2c -c https://ejemplo.com/archivo.iso
```
