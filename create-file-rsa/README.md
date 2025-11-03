# Algoritmo RSA
En criptografía, RSA (Rivest, Shamir y Adleman) es un sistema criptográfico de clave pública desarrollado en 1977, que utiliza factorización de números enteros. Es el primer y más utilizado algoritmo de este tipo y es válido tanto para cifrar como para firmar digitalmente.


### Generar llave privada 2048
```bash
openssl genrsa -out private.pem 2048
```

### Genera la llave publica
```
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
```

### Ejemplo de encriptar archivo
```bash
echo  "Mensaje secreto" > mensajesecreto.txt

#Firmar el archivo
openssl dgst -sign private.pem -keyform PEM -sha256 -out mensajesecreto.txt.sign mensajesecreto.txt

#Verificar Archivo
openssl dgst -verify public.pem -keyform PEM -sha256 -signature mensajesecreto.txt.sign mensajesecreto.txt
```
