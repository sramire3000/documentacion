# Comandos de Docker


## Respaldo de una imagen
```
docker save -o nombre_del_respaldo.tar nombre_de_la_imagen:tag
```

## Como restaurar imagen
```
docker load -i nombre_del_respaldo.tar
```

## Comparativa de limpieza

| Comando | Qué elimina | Riesgo |
| :--- | :--- | :--- |
| `docker image prune` | Solo imágenes sin nombre (`<none>`) | **Bajo**: Son residuos de builds. |
| `docker image prune -a` | Imágenes sin nombre + imágenes no usadas | **Medio**: Borra imágenes útiles pero inactivas. |
| `docker system prune` | Contenedores parados, redes y dangling images | **Bajo**: Solo borra lo que no se usa. |

### Limpiar volumenes Huerfanos
```
docker volume prune
```

### Limpiar imágenes huérfanas (Dangling)
```
docker images -f "dangling=true"
docker image prune 
```
