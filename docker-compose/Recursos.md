### Recursos Para Desarrollo
```
# Para entornos de desarrollo con recursos limitados
deploy:
  resources:
    limits:
      cpus: '0.2'
      memory: 384M
    reservations:
      cpus: '0.05'
      memory: 128M
```
