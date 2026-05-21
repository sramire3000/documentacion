# Generacion de llaves con Spring boot

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
