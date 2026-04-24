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

### Clase de servicio "EncryptionService.java"
````
package com.example.demo_encrypt.app.service;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.demo_encrypt.app.config.CryptoProperties;
import com.example.demo_encrypt.app.util.CryptoUtils;

/**
 * Servicio de encriptación que integra CryptoUtils con la configuración de
 * Spring.
 *
 * Proporciona métodos de alto nivel para cifrar/descifrar datos usando la
 * configuración
 * cargada desde application.properties.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EncryptionService {

  /**
   * DTO con información sobre las llaves (no expone la clave privada).
   */
  @lombok.Data
  @lombok.Builder
  public static class KeyInfo {
    private String keyName;
    private String algorithm;
    private String dataEncryption;
    private String status;
  }

  private final CryptoProperties cryptoProperties;

  /**
   * Cifra un texto plano usando encriptación híbrida (ECC + AES-256).
   *
   * La clave pública se carga desde las propiedades configuradas.
   *
   * @param plaintext texto a cifrar
   * @return datos cifrados en formato Base64
   * @throws RuntimeException si hay error en la encriptación
   */
  public String encrypt(String plaintext) {
    try {
      log.debug("Iniciando encriptación de datos");

      // Cargar clave pública desde propiedades
      PublicKey publicKey = CryptoUtils.base64ToPublicKey(
          cryptoProperties.getEcc().getPublicKey());

      // Cifrar con método híbrido
      byte[] encryptedBytes = CryptoUtils.encryptHybrid(plaintext, publicKey);

      // Convertir a Base64 para transmisión
      String encrypted = CryptoUtils.bytesToBase64(encryptedBytes);
      log.info("Encriptación completada exitosamente");

      return encrypted;
    } catch (Exception e) {
      log.error("Error en encriptación", e);
      throw new RuntimeException("Error en encriptación: " + e.getMessage(), e);
    }
  }

  /**
   * Descifra datos que fueron cifrados con el método encrypt().
   *
   * La clave privada se carga desde las propiedades configuradas.
   *
   * @param encryptedBase64 datos cifrados en formato Base64
   * @return texto plano descifrado
   * @throws RuntimeException si hay error en la desencriptación
   */
  public String decrypt(String encryptedBase64) {
    try {
      log.debug("Iniciando desencriptación de datos");

      // Cargar clave privada desde propiedades
      PrivateKey privateKey = CryptoUtils.base64ToPrivateKey(
          cryptoProperties.getEcc().getPrivateKey());

      // Convertir de Base64
      byte[] encryptedBytes = CryptoUtils.base64ToBytes(encryptedBase64);

      // Descifrar con método híbrido
      String decrypted = CryptoUtils.decryptHybrid(encryptedBytes, privateKey);
      log.info("Desencriptación completada exitosamente");

      return decrypted;
    } catch (Exception e) {
      log.error("Error en desencriptación", e);
      throw new RuntimeException("Error en desencriptación: " + e.getMessage(), e);
    }
  }

  /**
   * Obtiene información sobre las llaves configuradas.
   *
   * @return objeto con información de las llaves (sin exponer la privada)
   */
  public KeyInfo getKeyInfo() {
    return KeyInfo.builder()
        .keyName(cryptoProperties.getEcc().getKeyName())
        .algorithm("ECC - secp256r1 (256 bits)")
        .dataEncryption("AES-256-CBC")
        .status("Configurado y listo")
        .build();
  }
}
````

### Clase "EncryptionController.java" Controller de Ejemplo
````
package com.example.demo_encrypt.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.demo_encrypt.app.service.EncryptionService;

/**
 * Controlador REST que demuestra el uso de encriptación híbrida.
 *
 * Endpoints:
 * - POST /api/encrypt -> Cifra un mensaje
 * - POST /api/decrypt -> Descifra un mensaje
 * - GET /api/key-info -> Información sobre las llaves
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class EncryptionController {

  /**
   * DTO para solicitud de encriptación.
   */
  @lombok.Data
  public static class EncryptRequest {
    private String message;
  }

  /**
   * DTO para solicitud de desencriptación.
   */
  @lombok.Data
  public static class DecryptRequest {
    private String encrypted;
  }

  private final EncryptionService encryptionService;

  /**
   * Cifra un mensaje usando encriptación híbrida.
   *
   * Ejemplo de uso:
   * POST http://localhost:8080/api/encrypt
   * Content-Type: application/json
   *
   * {
   * "message": "Este es un mensaje secreto"
   * }
   *
   * @param request con el campo "message" a cifrar
   * @return objeto con "encrypted" (datos cifrados en Base64)
   */
  @PostMapping("/encrypt")
  public ResponseEntity<Map<String, Object>> encrypt(@RequestBody EncryptRequest request) {
    log.info("Solicitud de encriptación recibida");

    if (request.getMessage() == null || request.getMessage().isBlank()) {
      return ResponseEntity.badRequest().body(Map.of(
          "error", "El campo 'message' es obligatorio"));
    }

    try {
      String encrypted = encryptionService.encrypt(request.getMessage());

      Map<String, Object> response = new HashMap<>();
      response.put("original", request.getMessage());
      response.put("encrypted", encrypted);
      response.put("algorithm", "ECC-4096 + AES-256-CBC");
      response.put("status", "Éxito");

      log.info("Encriptación completada exitosamente");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error en encriptación", e);
      return ResponseEntity.internalServerError().body(Map.of(
          "error", "Error en encriptación: " + e.getMessage()));
    }
  }

  /**
   * Descifra un mensaje que fue cifrado con el endpoint /encrypt.
   *
   * Ejemplo de uso:
   * POST http://localhost:8080/api/decrypt
   * Content-Type: application/json
   *
   * {
   * "encrypted": "BASE64_ENCRYPTED_DATA_HERE"
   * }
   *
   * @param request con el campo "encrypted" (datos en Base64)
   * @return objeto con "decrypted" (mensaje original)
   */
  @PostMapping("/decrypt")
  public ResponseEntity<Map<String, Object>> decrypt(@RequestBody DecryptRequest request) {
    log.info("Solicitud de desencriptación recibida");

    if (request.getEncrypted() == null || request.getEncrypted().isBlank()) {
      return ResponseEntity.badRequest().body(Map.of(
          "error", "El campo 'encrypted' es obligatorio"));
    }

    try {
      String decrypted = encryptionService.decrypt(request.getEncrypted());

      Map<String, Object> response = new HashMap<>();
      response.put("decrypted", decrypted);
      response.put("algorithm", "ECC-4096 + AES-256-CBC");
      response.put("status", "Éxito");

      log.info("Desencriptación completada exitosamente");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error en desencriptación", e);
      return ResponseEntity.internalServerError().body(Map.of(
          "error", "Error en desencriptación: " + e.getMessage()));
    }
  }

  // ==================== DTOs de Solicitud ====================

  /**
   * Obtiene información sobre la configuración criptográfica.
   *
   * Ejemplo de uso:
   * GET http://localhost:8080/api/key-info
   *
   * @return información sobre las llaves y algoritmos configurados
   */
  @GetMapping("/key-info")
  public ResponseEntity<Map<String, Object>> getKeyInfo() {
    log.info("Solicitud de información de llaves");

    try {
      EncryptionService.KeyInfo keyInfo = encryptionService.getKeyInfo();

      Map<String, Object> response = new HashMap<>();
      response.put("keyName", keyInfo.getKeyName());
      response.put("keyAlgorithm", keyInfo.getAlgorithm());
      response.put("dataEncryption", keyInfo.getDataEncryption());
      response.put("status", keyInfo.getStatus());
      response.put("security", "Nivel de seguridad: 128 bits (equivalente)");

      log.info("Información de llaves obtenida");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error al obtener información de llaves", e);
      return ResponseEntity.internalServerError().body(Map.of(
          "error", "Error: " + e.getMessage()));
    }
  }

  /**
   * Ejemplo de cifrado de un JSON/objeto más complejo.
   *
   * Ejemplo de uso:
   * POST http://localhost:8080/api/encrypt-json
   * Content-Type: application/json
   *
   * {
   * "message": "{\"usuario\": \"admin\", \"password\": \"secreta123\"}"
   * }
   *
   * @param request con JSON a cifrar
   * @return datos cifrados
   */
  @PostMapping("/encrypt-json")
  public ResponseEntity<Map<String, Object>> encryptJson(@RequestBody EncryptRequest request) {
    log.info("Solicitud de encriptación de JSON recibida");

    try {
      String encrypted = encryptionService.encrypt(request.getMessage());

      Map<String, Object> response = new HashMap<>();
      response.put("originalJson", request.getMessage());
      response.put("encrypted", encrypted);
      response.put("note", "Cualquier tipo de datos (texto, JSON, XML, etc.) se puede cifrar");
      response.put("status", "Éxito");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error en encriptación", e);
      return ResponseEntity.internalServerError().body(Map.of(
          "error", "Error: " + e.getMessage()));
    }
  }
}
````

### La clase "GenerateKeysApp.java" debe estar ubicada donde esta la clase de inicio de la aplicación
````
package com.example.demo_encrypt.app;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo_encrypt.app.util.CryptoUtils;

/**
 * Aplicación utilitaria para generar nuevas llaves ECC.
 *
 * USO:
 * mvn exec:java -Dexec.mainClass="com.example.demo_encrypt.app.GenerateKeysApp"
 *
 * O ejecutar directamente esta clase desde el IDE.
 *
 * SALIDA:
 * Genera un par de llaves ECC (Elliptic Curve Cryptography) con curva
 * secp256r1.
 * Las llaves se imprimen en formato Base64 y también se guardan en
 * generated-keys/ como archivos .properties y .b64.
 */
public class GenerateKeysApp {

  private static final DateTimeFormatter FILE_TS_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

  public static void main(String[] args) {
    System.out.println("═════════════════════════════════════════════════════════════");
    System.out.println("   GENERADOR DE LLAVES ECC PARA ENCRIPTACIÓN SEGURA");
    System.out.println("═════════════════════════════════════════════════════════════\n");

    try {
      // Generar par de llaves ECC
      System.out.println("⚙️  Generando par de llaves ECC (secp256r1)...");
      KeyPair keyPair = CryptoUtils.generateECCKeyPair();

      PublicKey publicKey = keyPair.getPublic();
      PrivateKey privateKey = keyPair.getPrivate();

      // Convertir a Base64
      String publicKeyBase64 = CryptoUtils.publicKeyToBase64(publicKey);
      String privateKeyBase64 = CryptoUtils.privateKeyToBase64(privateKey);
      String keyName = "production-key-" + System.currentTimeMillis();

      System.out.println("✅ Llaves generadas correctamente\n");

      // Mostrar información sobre la encriptación
      printEncryptionInfo();

      // Mostrar las llaves en formato para application.properties
      printKeys(publicKeyBase64, privateKeyBase64, keyName);

      // Guardar llaves en archivos locales para evitar errores de copia manual
      saveKeysToFiles(publicKeyBase64, privateKeyBase64, keyName);

    } catch (Exception e) {
      System.err.println("❌ Error al generar las llaves: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Imprime información sobre los algoritmos de encriptación utilizados.
   */
  private static void printEncryptionInfo() {
    System.out.println("📋 INFORMACIÓN DE ENCRIPTACIÓN:");
    System.out.println("┌─────────────────────────────────────────────────────────┐");
    System.out.println("│ Intercambio de Claves:        ECC (secp256r1)           │");
    System.out.println("│ Nivel de Seguridad:           256 bits                  │");
    System.out.println("│ Cifrado de Datos:             AES-256-CBC               │");
    System.out.println("│ Vector de Inicialización:     Aleatorio (16 bytes)      │");
    System.out.println("│ Relleno:                      PKCS5                     │");
    System.out.println("│ Método:                       Híbrido (Asimétrico+Simé) │");
    System.out.println("│                                                         │");
    System.out.println("│ ✅ Aprobado por gobiernos e instituciones internacionales");
    System.out.println("│ ✅ Resistente a ataques de fuerza bruta modernos        │");
    System.out.println("│ ✅ Usado en: HTTPS, VPN, Discos cifrados, Banca digital │");
    System.out.println("└─────────────────────────────────────────────────────────┘\n");
  }

  /**
   * Imprime las llaves generadas en formato listo para application.properties.
   */
  private static void printKeys(String publicKeyBase64, String privateKeyBase64, String keyName) {
    System.out.println("🔑 COPIAR ESTAS LÍNEAS A application.properties:\n");
    System.out.println("─────────────────────────────────────────────────────────────");

    System.out.println("# Configuración de llaves ECC (Generadas el " + LocalDateTime.now() + ")");
    System.out.println("crypto.ecc.key-name=" + keyName);
    System.out.println("crypto.ecc.public-key=" + publicKeyBase64);
    System.out.println("crypto.ecc.private-key=" + privateKeyBase64);

    System.out.println("─────────────────────────────────────────────────────────────\n");

    System.out.println("⚠️  ADVERTENCIAS IMPORTANTES:\n");
    System.out.println("1. ✋ NUNCA compartir la clave privada");
    System.out.println("2. 🔐 Guardar la clave privada en un lugar seguro");
    System.out.println("3. 🔑 La clave pública se puede compartir sin riesgos");
    System.out.println("4. 📝 Para PRODUCCIÓN usar un vault/gestor de secretos");
    System.out.println("5. 🔄 Usar diferentes llaves para dev/staging/producción");
    System.out.println("6. 💾 Hacer backup seguro de las llaves privadas");
    System.out.println("\n");
  }

  /**
   * Guarda llaves en archivos locales para evitar errores de copia manual.
   */
  private static void saveKeysToFiles(String publicKeyBase64, String privateKeyBase64, String keyName)
      throws Exception {
    String timestamp = LocalDateTime.now().format(FILE_TS_FORMAT);
    Path outputDir = Path.of("generated-keys");
    Files.createDirectories(outputDir);

    Path propertiesFile = outputDir.resolve("crypto-ecc-" + timestamp + ".properties");
    Path publicFile = outputDir.resolve("public-key-" + timestamp + ".b64");
    Path privateFile = outputDir.resolve("private-key-" + timestamp + ".b64");

    String propertiesContent = String.join(System.lineSeparator(),
        "# Configuración de llaves ECC (Generadas el " + LocalDateTime.now() + ")",
        "crypto.ecc.key-name=" + keyName,
        "crypto.ecc.public-key=" + publicKeyBase64,
        "crypto.ecc.private-key=" + privateKeyBase64,
        "");

    Files.writeString(propertiesFile, propertiesContent, StandardCharsets.UTF_8);
    Files.writeString(publicFile, publicKeyBase64 + System.lineSeparator(), StandardCharsets.UTF_8);
    Files.writeString(privateFile, privateKeyBase64 + System.lineSeparator(), StandardCharsets.UTF_8);

    System.out.println("📁 ARCHIVOS GENERADOS:");
    System.out.println("   - " + propertiesFile.toAbsolutePath());
    System.out.println("   - " + publicFile.toAbsolutePath());
    System.out.println("   - " + privateFile.toAbsolutePath());
    System.out.println();
  }
}
````



