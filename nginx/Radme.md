# NgInx

### Install NgInx in ubuntu

```bash
sudo apt-get install nginx
```

### Verificar
```bash
sudo systemctl status nginx
```

### Archivos de configuraciones Globales
```bash
cd /etc/nginx
nano nginx.conf
```
### Recargar cambios de configuracion
```bash
sudo nginx -s reload
```

### Configuraciones de cada proyecto
```bash
cd /etc/nginx/sites-available
```

### Configuraciones de enlaces
```bash
cd /etc/nginx/sites-enabled
```

### Ubiacion de paginas Web
```bash
cd /var/www
```
## Test

### Crear carpeta en www para test
```bash
sudo mkdir -p /var/www/hola
echo "<h1>Hola desde Nginx</h1>" | sudo tee /var/www/hola/index.html
```
### Crear archivo de configuracion
```bash
sudo nano /etc/nginx/sites-available/hola.conf
```
### Contenido de hola.conf
```bash
server {
}
```
