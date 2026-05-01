# SERVIDOR MYSQL VER 8

## Archivo ".env"
```
# Password del root
MYSQL_ROOT_PASSWORD=TuPasswordRaiz123!
MYSQL_DATABASE=mi_proyecto_db
MYSQL_USER=development
MYSQL_PASSWORD=TuPassword
MYSQL_PORT=3306

MYSQL_CONTAINER_NAME=mysql8
MYSQL_CONTAINER_MEM_LIMIT=2g
MYSQL_CONTAINER_MEM_RESERV=1g
```

## Archivo "docker-compose.yml"
```
services:
  mysql_db:
    image: mysql:8.0
    container_name: ${MYSQL_CONTAINER_NAME}
    restart: unless-stopped
    ports:
      - "${MYSQL_PORT}:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
      - MYSQL_DATABASE=${MYSQL_DATABASE}
      - MYSQL_USER=${MYSQL_USER}
      - MYSQL_PASSWORD=${MYSQL_PASSWORD}
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: ${MYSQL_CONTAINER_MEM_LIMIT}
        reservations:
          cpus: "0.1"
          memory: ${MYSQL_CONTAINER_MEM_RESERV}
    volumes:
      - mysql_data:/var/lib/mysql
    # Importante para compatibilidad con algunos clientes antiguos
    command: --default-authentication-plugin=mysql_native_password

volumes:
  mysql_data:
    driver: local
```
