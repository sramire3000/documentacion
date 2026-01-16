# Generar Llaves RSA 4096

### Command
```
# Generar llave privada
openssl genrsa -out private_key.pem 4096

# Extraer llave pública de la privada
openssl rsa -in private_key.pem -pubout -out public_key.pem
```
