# Encriptación con Spring Boot JDK21

## 🔐 Encriptación más segura según el propósito
- ✅ 1. AES‑256 (simétrica) — la más segura en la práctica
   AES (Advanced Encryption Standard) con claves de 256 bits es el estándar más utilizado y confiable.
   Por qué es segura:

   Aprobada por gobiernos y entidades internacionales
   Extremadamente resistente a ataques de fuerza bruta
   Muy rápida y eficiente

   Se usa para:

   Archivos
   Bases de datos
   Comunicaciones (HTTPS, VPN, discos cifrados)

   👉 Hoy en día, AES‑256 es considerado prácticamente irrompible con la tecnología actual.

- ✅ 2. RSA‑4096 o ECC (asimétrica) — para intercambio de claves
   Estos algoritmos no suelen cifrar datos grandes, sino que se usan para intercambiar claves de forma segura.
   🔹 RSA‑4096

   Muy seguro pero más lento
   Usado ampliamente en TLS/SSL

  🔹 ECC (Elliptic Curve Cryptography)

  Igual de segura con claves más pequeñas
  Más rápida y eficiente
  Preferida en sistemas modernos

  👉 Actualmente ECC es considerada más robusta y eficiente que RSA.

- ✅ 3. Combinación híbrida (la mejor práctica)
   La forma más segura real es la que se usa en HTTPS y otros sistemas modernos:
   Flujo típico:

   ECC o RSA → intercambio seguro de clave
   AES‑256 → cifrado del contenido

✅ Este enfoque es considerado el más seguro en la industria

## Códificado para el método 3

### Adicionando dependencias al archivo "pom.xml"
````
<!-- Bouncy Castle para ECC y otras operaciones criptográficas -->
<dependency>
   <groupId>org.bouncycastle</groupId>
   <artifactId>bcprov-jdk18on</artifactId>
   <version>1.76</version>
</dependency>
<!-- Conversión Base64 y utilidades -->
<dependency>
   <groupId>commons-codec</groupId>
   <artifactId>commons-codec</artifactId>
   <version>1.16.0</version>
</dependency>
````

### Archivo "application.properties"
```
# crypto.ecc.key-name -> Identificador de las llaves
# crypto.ecc.public-key -> Se usa para CIFRAR (puede ser compartida públicamente)
# crypto.ecc.private-key -> Se usa para DESCIFRAR (CONFIDENCIAL - no compartir)

crypto.ecc.key-name=
crypto.ecc.public-key=
crypto.ecc.private-key=
```
### Clase de configuracion "CryptoProperties.java"
````
package com.example.demo_encrypt.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuración de propiedades criptográficas cargadas desde
 * application.properties.
 *
 * Permite almacenar y gestionar las llaves de forma segura a través de
 * propiedades.
 *
 * Ejemplo en application.properties:
 * crypto.ecc.public-key=BASE64_ENCODED_PUBLIC_KEY
 * crypto.ecc.private-key=BASE64_ENCODED_PRIVATE_KEY
 */
@Configuration
@ConfigurationProperties(prefix = "crypto")
@Getter
@Setter
public class CryptoProperties {

  @Getter
  @Setter
  public static class Ecc {
    /**
     * Clave pública ECC en formato Base64.
     * Se usa para cifrar.
     */
    private String publicKey;

    /**
     * Clave privada ECC en formato Base64.
     * Se usa para descifrar.
     * ⚠️ CONFIDENCIAL - nunca compartir o exponer
     */
    private String privateKey;

    /**
     * Nombre descriptivo del keypair (opcional).
     */
    private String keyName = "default";
  }

  private Ecc ecc = new Ecc();
}

````

