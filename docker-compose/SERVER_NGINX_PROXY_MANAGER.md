# Docker Composer NGINX PROXY MANAGER

### User
```
Email       : admin@example.com
Password	: changeme
```

### Create folder
```
mkdir -p data
sudo chmod 777 data

mkdir -p letsencrypt
sudo chmod 777 letsencrypt
```

## Crear archivo ".env"
```
NGINX_CONTAINER_NAME=nginx_proxy
NGINX_CONTAINER_MEM_LIMIT=2g
NGINX_CONTAINER_MEM_RESERV=1g
```

### File docker-compose.yaml
```
services:
  app:
    image: 'jc21/nginx-proxy-manager:latest'
    container_name: ${NGINX_CONTAINER_NAME}
    restart: unless-stopped
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: ${NGINX_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: "0.1"
          memory: ${NGINX_CONTAINER_MEM_RESERV}    
    ports:
      # These ports are in format <host-port>:<container-port>
      - '80:80' # Public HTTP Port
      - '443:443' # Public HTTPS Port
      - '81:81' # Admin Web Port
      # Add any other Stream port you want to expose
      # - '21:21' # FTP

    #environment:
      # Uncomment this if you want to change the location of
      # the SQLite DB file within the container
      # DB_SQLITE_FILE: "/data/database.sqlite"

      # Uncomment this if IPv6 is not enabled on your host
      # DISABLE_IPV6: 'true'

    volumes:
      - ./data:/data
      - ./letsencrypt:/etc/letsencrypt
```

### URL
- [Url Admin](http://localhost:81/login)







