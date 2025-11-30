### Recursos Para Desarrollo
```
# Para entornos de desarrollo con recursos limitados
    deploy:
       resources:
           limits:
             cpus: '0.2'
             memory: 512M
           reservations:
             cpus: '0.05'
             memory: 256M    
```
