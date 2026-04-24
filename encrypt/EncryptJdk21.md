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
### Paquetes
````
- config
- util
- controller
- service
````

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
### Clase de  "CryptoUtils.java"
````
package com.example.demo_encrypt.app.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;

/**
 * Clase de utilidad para operaciones criptográficas seguras.
 * Implementa encriptación híbrida: ECC + AES-256
 */
public class CryptoUtils {

  private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
  private static final int AES_KEY_SIZE = 256;
  private static final int IV_SIZE = 16;
  private static final String ECC_CURVE = "secp256r1";
  private static final int ECIES_MAC_KEY_SIZE = 128;

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  // ==================== GENERACIÓN DE LLAVES ====================

  public static KeyPair generateECCKeyPair() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
    ECGenParameterSpec ecSpec = new ECGenParameterSpec(ECC_CURVE);
    keyPairGenerator.initialize(ecSpec, new SecureRandom());
    return keyPairGenerator.generateKeyPair();
  }

  public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(AES_KEY_SIZE, new SecureRandom());
    return keyGenerator.generateKey();
  }

  public static byte[] generateIV() {
    byte[] iv = new byte[IV_SIZE];
    new SecureRandom().nextBytes(iv);
    return iv;
  }

  // ==================== CIFRADO AES-256 ====================

  public static byte[] encryptAES(String plaintext, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM_AES, "BC");
    byte[] iv = generateIV();
    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
    byte[] encryptedData = cipher.doFinal(plaintext.getBytes("UTF-8"));

    byte[] result = new byte[iv.length + encryptedData.length];
    System.arraycopy(iv, 0, result, 0, iv.length);
    System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);

    return result;
  }

  public static String decryptAES(byte[] encryptedData, SecretKey secretKey) throws Exception {
    byte[] iv = new byte[IV_SIZE];
    System.arraycopy(encryptedData, 0, iv, 0, IV_SIZE);

    byte[] ciphertext = new byte[encryptedData.length - IV_SIZE];
    System.arraycopy(encryptedData, IV_SIZE, ciphertext, 0, ciphertext.length);

    Cipher cipher = Cipher.getInstance(ALGORITHM_AES, "BC");
    IvParameterSpec ivSpec = new IvParameterSpec(iv);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

    byte[] decryptedData = cipher.doFinal(ciphertext);
    return new String(decryptedData, "UTF-8");
  }

  // ==================== CIFRADO ASIMÉTRICO ECC ====================

  public static byte[] encryptECC(byte[] plaintext, PublicKey publicKey) throws Exception {
    Cipher cipher = Cipher.getInstance("ECIES", "BC");
    IESParameterSpec iesSpec = new IESParameterSpec(null, null, ECIES_MAC_KEY_SIZE);
    cipher.init(Cipher.ENCRYPT_MODE, publicKey, iesSpec, new SecureRandom());
    return cipher.doFinal(plaintext);
  }

  public static byte[] decryptECC(byte[] encryptedData, PrivateKey privateKey) throws Exception {
    Cipher cipher = Cipher.getInstance("ECIES", "BC");
    IESParameterSpec iesSpec = new IESParameterSpec(null, null, ECIES_MAC_KEY_SIZE);
    cipher.init(Cipher.DECRYPT_MODE, privateKey, iesSpec);
    return cipher.doFinal(encryptedData);
  }

  // ==================== FLUJO HÍBRIDO COMPLETO ====================

  public static byte[] encryptHybrid(String plaintext, PublicKey eccPublicKey) throws Exception {
    SecretKey aesKey = generateAESKey();
    byte[] encryptedData = encryptAES(plaintext, aesKey);
    byte[] encryptedAESKey = encryptECC(aesKey.getEncoded(), eccPublicKey);

    byte[] result = new byte[4 + encryptedAESKey.length + encryptedData.length];
    result[0] = (byte) ((encryptedAESKey.length >> 24) & 0xFF);
    result[1] = (byte) ((encryptedAESKey.length >> 16) & 0xFF);
    result[2] = (byte) ((encryptedAESKey.length >> 8) & 0xFF);
    result[3] = (byte) (encryptedAESKey.length & 0xFF);

    System.arraycopy(encryptedAESKey, 0, result, 4, encryptedAESKey.length);
    System.arraycopy(encryptedData, 0, result, 4 + encryptedAESKey.length, encryptedData.length);

    return result;
  }

  public static String decryptHybrid(byte[] encryptedData, PrivateKey eccPrivateKey) throws Exception {
    int keyLength = ((encryptedData[0] & 0xFF) << 24) |
        ((encryptedData[1] & 0xFF) << 16) |
        ((encryptedData[2] & 0xFF) << 8) |
        (encryptedData[3] & 0xFF);

    byte[] encryptedAESKey = new byte[keyLength];
    System.arraycopy(encryptedData, 4, encryptedAESKey, 0, keyLength);

    byte[] ciphertext = new byte[encryptedData.length - 4 - keyLength];
    System.arraycopy(encryptedData, 4 + keyLength, ciphertext, 0, ciphertext.length);

    byte[] decryptedAESKeyBytes = decryptECC(encryptedAESKey, eccPrivateKey);
    SecretKey aesKey = new SecretKeySpec(decryptedAESKeyBytes, 0, decryptedAESKeyBytes.length, "AES");

    return decryptAES(ciphertext, aesKey);
  }

  // ==================== CONVERSIÓN DE FORMATOS ====================

  public static String publicKeyToBase64(PublicKey publicKey) {
    return Base64.encodeBase64String(publicKey.getEncoded());
  }

  public static String privateKeyToBase64(PrivateKey privateKey) {
    return Base64.encodeBase64String(privateKey.getEncoded());
  }

  public static PublicKey base64ToPublicKey(String base64Key) throws Exception {
    if (base64Key == null || base64Key.isBlank()) {
      throw new IllegalArgumentException("La clave pública ECC está vacía o no configurada");
    }
    byte[] decodedKey = Base64.decodeBase64(base64Key);
    if (decodedKey == null || decodedKey.length == 0) {
      throw new IllegalArgumentException("La clave pública ECC no tiene un Base64 válido");
    }
    X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
    KeyFactory kf = KeyFactory.getInstance("EC", "BC");
    return kf.generatePublic(spec);
  }

  public static PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
    if (base64Key == null || base64Key.isBlank()) {
      throw new IllegalArgumentException("La clave privada ECC está vacía o no configurada");
    }
    byte[] decodedKey = Base64.decodeBase64(base64Key);
    if (decodedKey == null || decodedKey.length == 0) {
      throw new IllegalArgumentException("La clave privada ECC no tiene un Base64 válido");
    }
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
    KeyFactory kf = KeyFactory.getInstance("EC", "BC");
    return kf.generatePrivate(spec);
  }

  public static String bytesToBase64(byte[] data) {
    return Base64.encodeBase64String(data);
  }

  public static byte[] base64ToBytes(String base64String) {
    return Base64.decodeBase64(base64String);
  }
}
````



