# Comandos

### PASO 1 — Instalar Cloudflare Tunnel en tu Windows 10
Descarga cloudflared Windows 64-bit
```
https://developers.cloudflare.com/cloudflare-one/connections/connect-apps/install-and-setup/installation/
```

Crea esta carpeta:
Copia cloudflared.exe dentro de esa carpeta.
```
C:\cloudflared
```

### PASO 2 — Autenticar tu servidor con Cloudflare
```
cd C:\cloudflared
cloudflared tunnel login
```

### PASO 3 — Crear el túnel
```
cloudflared tunnel create produccion-tunel
```
Te mostrará algo como
```
Created tunnel produccion-tunel with id xxxxxxxx-xxxx-xxxx
```

### PASO 4 — Crear archivo de configuración
Crea este archivo:
```
C:\cloudflared\config.yml
```

Contenido (ajústalo según tus puertos):
```
tunnel: ID_DEL_TUNEL
credentials-file: C:\Users\TU_USUARIO\.cloudflared\ID_DEL_TUNEL.json

ingress:
  - hostname: tudominio.com
    service: http://localhost:80

  - hostname: api.tudominio.com
    service: http://localhost:8080

  - service: http_status:404
```
🔹 localhost:80 → Nginx (Angular)
🔹 localhost:8080 → Tus microservicios



### Ejecuta este comando y dime EXACTAMENTE qué muestra:
```
cloudflared tunnel list
```

