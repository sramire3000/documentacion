## Actualizar Ubuntu

### 1. Actualizar la lista de paquetes disponibles
```bash
sudo apt update
```

### 2. Actualizar los paquetes instalados a sus últimas versiones
```
sudo apt upgrade -y
```
### 3. Actualizar también los paquetes que requieren cambios de dependencias o nuevas instalaciones
```
sudo apt full-upgrade -y
```

### 4. Eliminar paquetes y dependencias que ya no se usan
```
sudo apt autoremove -y
```

### 5. Opcional) Actualizar la distribución completa  
Si quieres pasar de una versión a otra (por ejemplo, de Ubuntu 23.10 a 24.04), usa:
```
sudo do-release-upgrade
```

