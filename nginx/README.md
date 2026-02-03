# NgInx

## Install NgInx en ubuntu

### Install 
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

## Comando para el manejo de Nginx con Systemctl

### Iniciar servicio de Nginx
```bash
sudo systemctl start nginx
```

### Status de Nginx
```bash
sudo systemctl status nginx
```

### Detener el servicio
```bash
sudo systemctl stop nginx
```

### Reiniciar el servicio
```bash
sudo systemctl restart nginx
```

### No habilitar servicio de Nginx en el boot
```bash
sudo systemctl disable nginx
```

### Habilitar servicio de Nginx en el boot
```bash
sudo systemctl enable nginx
```

## Configuraciones de proyectos

### Configuraciones sitios disponibles
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
   # puerto de escucha
   listen 80;
   # Nombre del sitio
   server_name hola.local;
   # Ruta de sitio o paginas web
   root /var/www/hola;
   # Archivo inicial solicitado
   index index.html;

   # Archivo solicitado
   location / {
      try_files $uri $uri/ =404;
   }

}
```
### Activa el sito
```bash
sudo ln -s /etc/nginx/sites-available/hola.conf /etc/nginx/sites-enabled/
```

### Comprobar configuracion
```bash
sudo nginx -t
```
### Recargar Nginx
```bash
sudo nginx -s reload
```

### Adicionar el host
```bash
sudo nano /etc/hosts
127.0.1.1 hola.local
```

## Configuracion de un Sitio Web Basico

### 1. Crear carpeta sitio web
```bash
sudo mkdir -p /var/www/mi-sitio
```
### 2. crear index.html
```bash
sudo nano /var/www/mi-sitio/index.html
```

### 3. contenido index.html
```bash
<!DOCTYPE html>
<html>
   <head>
      <title>Mi sitio web</title>
      <link rel="stylesheet" href="style.css">
   </head>
   <body>
      <h1>Bien venido a mi sitio web con Nginx</h1>
      <p>Este es un ejemplo de contenido estatico</p>
      <script src="script.js"></script>
   </body>
</html>
```

### 4. crear archivo de estilos
```bash
sudo nano /var/www/mi-sitio/style.css
```

### 5. contenido style.css
```bash
body {
   font-family: Arial, sans-serif;
   background-color: #f0f0f0;
   text-align: center;
}

h1 {
   color: #333;
}
```
### 6. crear archivo script.js
```bash
sudo nano /var/www/mi-sitio/script.js
```

### 7. Contenido de script.js
```bash
console.log("NGINX Sirve este archivo jajascript");
alert("Hola Js desde Nginx");
```

### 8. Dar permiso de acceso al sitio si no hay
```bash
sudo chown -R www-data:www-data /var/www/mi-sitio
sudo chown -R 775 /var/www/mi-sitio
```
### 9. Revision de archivos
```bash
ls -l /var/www/mi-sitio/
```

### 10. Crear archivo mi-sitio.conf de configuracion del sitio disponible
```bash
sudo nano /etc/nginx/sites-available/mi-sitio.conf
```

### 11. Contenido de mi-sitio.conf
```bash
server {
   # Puerto de escucha
   listen 80;
   # Nombre del dominio
   server_name mi_sitio.local;
   # Directorio raiz
   root /var/www/mi-sitio;
   # Archivo que se ejecuta por primera vez
   index index.html;

   location / {
      # Localizacion del sitio
      try_files $uri $uri/ /index.html;
   }
}
```

### 12. configurar el nombre del dominio
```bash
sudo nano /etc/hosts
```


## Produccion

### Reiniciar Servicio sin interrupcion
```bash
sudo nginx -t && sudo nginx -s reload
```
