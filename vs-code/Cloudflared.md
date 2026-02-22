# Solución Ideal: Cloudflare + Cloudflare Tunnel

### Ventajas
```
✅ Funciona sin IP pública
✅ SSL automático
✅ Protección contra ataques
✅ No necesitas abrir puertos
✅ Gratis en plan básico
```

### 🔧 Cómo funcionaría en tu caso
En tu servidor Windows:
```
1. Instalas cloudflared
2. Creas túnel hacia tu Angular (ej: localhost:80)
3. Cloudflare conecta tu dominio al túnel
4. Todo tráfico pasa cifrado
```

### 🧠 Arquitectura interna recomendada en tu server
```
Angular → Nginx (80)
Microservicios → puertos internos (8081, 8082, etc)
PostgreSQL → solo localhost
```

### 🥇 PASO 1 — Comprar o usar un dominio
Necesitas un dominio propio.
```
GoDaddy
```

### 🥇 PASO 2 — Crear cuenta en Cloudflare
```
1. Ve a Cloudflare
2. Agrega tu dominio
3. Cambia los DNS en tu registrador por los de Cloudflare
4. Espera propagación (5–30 min normalmente)
```







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
C:\Users\[TU_USUARIO]\.cloudflared\config.yml
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
localhost:80 → Nginx (Angular)
localhost:8080 → Tus microservicios

### PASO 5 — Crear registros DNS automáticos
Ejecuta:
```
cloudflared tunnel route dns produccion-tunel tudominio.com
cloudflared tunnel route dns produccion-tunel api.tudominio.com
```

### PASO 6 — Ejecutar el túnel

```
cloudflared tunnel run produccion-tunel
```
Si todo está bien:
Tu dominio ya será accesible con HTTPS

### PASO 7 — Instalar como servicio (OBLIGATORIO)
```
cloudflared service install
```




### Ejecuta este comando y dime EXACTAMENTE qué muestra:
```
cloudflared tunnel list
```

### Verifica el servicio 
```
sc query cloudflared
```

### Confirma la conexion
```
cloudflared tunnel info produccion-tunel
```

cloudflared service uninstall

cloudflared service install --config C:\Users\TU_USUARIO\.cloudflared\config.yml

```
C:\Windows\System32\config\systemprofile\.cloudflared\
```
