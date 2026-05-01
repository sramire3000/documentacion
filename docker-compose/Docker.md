# Comandos de Docker


## Respaldo de una imagen
```
docker save -o nombre_del_respaldo.tar nombre_de_la_imagen:tag
```

## Como restaurar imagen
```
docker load -i nombre_del_respaldo.tar
```

### Limpiar volumenes Huerfanos
```
docker volume prune
```

### Imagenes
```
docker images -f "dangling=true"
docker image prune -a
```
