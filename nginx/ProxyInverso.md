# Proxy Inverso

## Configuración Básica
```bash
server {
    # El puerto donde Nginx "escucha" (la puerta de entrada)
    listen 8080;
    
    server_name localhost; # O la IP de tu servidor

    location / {
        # El puerto a donde Nginx envía la solicitud (el destino)
        proxy_pass http://127.0.0.1:8090;
        
        # Buenas prácticas para mantener la información del cliente
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```
