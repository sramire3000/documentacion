# Generacion de llaves con Spring boot

## Clase "CryptoUtils.java" en el paquete utils
```
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

  private static final byte[] MAGIC = new byte[] { 0x46, 0x45, 0x56, 0x32 }; // FEV2
  private static final int MAGIC_LEN = 4;
  private static final int EPH_RAW_LEN = 65; // uncompressed EC point: 0x04 + X(32) + Y(32)
  private static final int IV_LEN = 16;

  public static String decryptHybridV2(byte[] encryptedData, PrivateKey serverPrivateKey) throws Exception {
    if (encryptedData == null || encryptedData.length < (MAGIC_LEN + EPH_RAW_LEN + IV_LEN + 16)) {
      throw new IllegalArgumentException("Payload V2 invalido: longitud insuficiente");
    }

    // 1) Validar magic FEV2
    for (int i = 0; i < MAGIC_LEN; i++) {
      if (encryptedData[i] != MAGIC[i]) {
        throw new IllegalArgumentException("Payload no es V2 (magic mismatch)");
      }
    }

    // 2) Extraer ephPubRaw, iv, ciphertext
    int offset = MAGIC_LEN;
    byte[] ephRaw = Arrays.copyOfRange(encryptedData, offset, offset + EPH_RAW_LEN);
    offset += EPH_RAW_LEN;

    byte[] iv = Arrays.copyOfRange(encryptedData, offset, offset + IV_LEN);
    offset += IV_LEN;

    byte[] ciphertext = Arrays.copyOfRange(encryptedData, offset, encryptedData.length);

    // 3) Reconstruir PublicKey efimera desde punto raw (secp256r1)
    PublicKey ephPublicKey = rawP256ToPublicKey(ephRaw, serverPrivateKey);

    // 4) ECDH + SHA-256(sharedSecret) => AES-256
    KeyAgreement ka = KeyAgreement.getInstance("ECDH");
    ka.init(serverPrivateKey);
    ka.doPhase(ephPublicKey, true);
    byte[] sharedSecret = ka.generateSecret();

    byte[] aesKeyBytes = java.security.MessageDigest.getInstance("SHA-256").digest(sharedSecret);
    SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

    // 5) AES/CBC/PKCS5Padding decrypt
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));
    byte[] plain = cipher.doFinal(ciphertext);

    return new String(plain, StandardCharsets.UTF_8);
  }

  public static byte[] encryptHybridV2(String plaintext, PublicKey recipientPublicKey) throws Exception {
    // 1) Ephemeral EC keypair P-256
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
    kpg.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
    KeyPair eph = kpg.generateKeyPair();

    // 2) ECDH shared secret
    KeyAgreement ka = KeyAgreement.getInstance("ECDH");
    ka.init(eph.getPrivate());
    ka.doPhase(recipientPublicKey, true);
    byte[] sharedSecret = ka.generateSecret();

    // 3) AES-256 key = SHA-256(sharedSecret)
    byte[] aesKeyBytes = MessageDigest.getInstance("SHA-256").digest(sharedSecret);
    SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

    // 4) AES/CBC/PKCS5Padding
    byte[] iv = new byte[IV_LEN];
    new SecureRandom().nextBytes(iv);

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));
    byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

    // 5) Export ephemeral public key as raw uncompressed point (65 bytes)
    byte[] ephRaw = ecPublicKeyToRaw((ECPublicKey) eph.getPublic());

    // 6) Build payload: MAGIC(4) + ephRaw(65) + iv(16) + ciphertext
    byte[] out = new byte[MAGIC.length + ephRaw.length + iv.length + ciphertext.length];
    int o = 0;
    System.arraycopy(MAGIC, 0, out, o, MAGIC.length);
    o += MAGIC.length;
    System.arraycopy(ephRaw, 0, out, o, ephRaw.length);
    o += ephRaw.length;
    System.arraycopy(iv, 0, out, o, iv.length);
    o += iv.length;
    System.arraycopy(ciphertext, 0, out, o, ciphertext.length);

    return out;
  }

  public static String encryptHybridV2Base64(String plaintext, PublicKey recipientPublicKey) throws Exception {
    return java.util.Base64.getEncoder().encodeToString(encryptHybridV2(plaintext, recipientPublicKey));
  }

  // ==================== MÉTODOS AUXILIARES ====================

  public static KeyPair generateECCKeyPair() throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
    kpg.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
    return kpg.generateKeyPair();
  }

  public static String publicKeyToBase64(PublicKey publicKey) {
    return java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
  }

  public static String privateKeyToBase64(PrivateKey privateKey) {
    return java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());
  }

  public static PublicKey base64ToPublicKey(String base64Key) throws Exception {
    if (base64Key == null || base64Key.isBlank()) {
      throw new IllegalArgumentException("La clave pública ECC está vacía o no configurada");
    }
    byte[] decodedKey = java.util.Base64.getDecoder().decode(base64Key);
    KeyFactory kf = KeyFactory.getInstance("EC");
    return kf.generatePublic(new X509EncodedKeySpec(decodedKey));
  }

  public static PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
    if (base64Key == null || base64Key.isBlank()) {
      throw new IllegalArgumentException("La clave privada ECC está vacía o no configurada");
    }
    byte[] decodedKey = java.util.Base64.getDecoder().decode(base64Key);
    KeyFactory kf = KeyFactory.getInstance("EC");
    return kf.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
  }

  public static byte[] base64ToBytes(String base64String) {
    return java.util.Base64.getDecoder().decode(base64String);
  }

  private static PublicKey rawP256ToPublicKey(byte[] ephRaw, PrivateKey serverPrivateKey) throws Exception {
    if (ephRaw.length != EPH_RAW_LEN || ephRaw[0] != 0x04) {
      throw new IllegalArgumentException("Clave publica efimera invalida");
    }

    java.security.interfaces.ECPrivateKey ecPriv = (java.security.interfaces.ECPrivateKey) serverPrivateKey;
    java.security.spec.ECParameterSpec params = ecPriv.getParams();

    byte[] xBytes = Arrays.copyOfRange(ephRaw, 1, 33);
    byte[] yBytes = Arrays.copyOfRange(ephRaw, 33, 65);

    java.math.BigInteger x = new java.math.BigInteger(1, xBytes);
    java.math.BigInteger y = new java.math.BigInteger(1, yBytes);

    ECPoint point = new ECPoint(x, y);
    ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, params);
    KeyFactory kf = KeyFactory.getInstance("EC");
    return kf.generatePublic(pubSpec);
  }

  private static byte[] ecPublicKeyToRaw(ECPublicKey pub) {
    byte[] x = toFixed(pub.getW().getAffineX().toByteArray(), 32);
    byte[] y = toFixed(pub.getW().getAffineY().toByteArray(), 32);
    byte[] raw = new byte[EPH_RAW_LEN];
    raw[0] = 0x04;
    System.arraycopy(x, 0, raw, 1, 32);
    System.arraycopy(y, 0, raw, 33, 32);
    return raw;
  }

  private static byte[] toFixed(byte[] v, int len) {
    byte[] out = new byte[len];
    int srcPos = Math.max(0, v.length - len);
    int copyLen = Math.min(v.length, len);
    System.arraycopy(v, srcPos, out, len - copyLen, copyLen);
    return out;
  }
}
```

## Clase "GenerateKeysApp.java" en la raiz donde esta la clase main
```
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import sv.com.arreconsa.springboot.app.logistica.utils.CryptoUtils;

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
```
