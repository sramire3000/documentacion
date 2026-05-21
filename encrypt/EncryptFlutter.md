# CryptoService — Flutter / Dart

Implementación Flutter del servicio de cifrado ECIES, **interoperable** con el
[servicio Angular](src/app/shared/global/services/crypto.service.ts) y con el
mismo backend Java. Ambas implementaciones producen mensajes cifrados con la
misma estructura, por lo que el servidor puede descifrar indistintamente los
mensajes provenientes de cualquiera de los dos clientes.

---

## Esquema de cifrado

```
Plaintext
    │
    ▼  1. Par de llaves efímero P-256 (secp256r1)
    │
    ▼  2. ECDH(ephPriv, serverPub) → punto compartido → coord. X (32 B)
    │
    ▼  3. KDF: SHA-256(coordX) → clave AES (32 B)
    │
    ▼  4. IV aleatorio (16 B)  +  AES-256-CBC / PKCS7
    │
    ▼
Base64( FEV2 magic (4 B) | ephPubRaw (65 B) | IV (16 B) | CipherText (N B) )
```

| Campo        | Tamaño   | Descripción                                                       |
| ------------ | -------- | ----------------------------------------------------------------- |
| `FEV2` magic | 4 bytes  | Identificador de versión (`0x46 0x45 0x56 0x32`)                  |
| `ephPubRaw`  | 65 bytes | Clave pública efímera, punto P-256 sin comprimir (`04 \| X \| Y`) |
| `IV`         | 16 bytes | Vector de inicialización AES aleatorio                            |
| `CipherText` | N bytes  | Texto cifrado (múltiplo de 16 por PKCS7)                          |

---

## Archivos

```
flutter/
└── crypto_service.dart   ← implementación Dart
flutter.md                ← este documento
```

---

## Dependencia requerida

Añadir al `pubspec.yaml` del proyecto Flutter:

```yaml
dependencies:
  pointycastle: ^3.9.1
```

> `pointycastle` es la librería criptográfica estándar de Dart/Flutter.
> Proporciona P-256, SHA-256, AES-CBC y PKCS7 sin depender de plugins nativos.

---

## Uso

```dart
import 'package:mi_app/crypto_service.dart';

// 1. Inicializar con la clave pública del servidor (Base64 SPKI/DER)
final crypto = CryptoService(
  publicKeyBase64: 'MFkwEwYHKoZIzj0CAQY...', // mismo valor que environment.cryptoEccPublicKey
  keyName: 'mi-clave-v1',
);

// 2. Cifrar
final encryptedBase64 = crypto.encrypt('mi texto secreto');

// 3. Alias en español
final encryptedBase64 = crypto.encriptar('mi texto secreto');

// 4. Consultar estado
final info = crypto.getKeyInfo();
print(info.status); // "Configurado y listo"
```

---

## Correspondencia Angular ↔ Flutter

| Angular (`@noble/curves` / `@noble/ciphers`)              | Flutter (`pointycastle`)                             |
| --------------------------------------------------------- | ---------------------------------------------------- |
| `p256.utils.randomPrivateKey()`                           | `ECKeyGenerator` + `FortunaRandom`                   |
| `p256.getPublicKey(priv, false)` → 65 bytes               | `ECPublicKey.Q!.getEncoded(false)` → 65 bytes        |
| `p256.getSharedSecret(priv, pub).slice(1)` → 32 bytes (X) | `ECDHBasicAgreement.calculateAgreement()` → `BigInt` |
| `sha256(sharedSecret)`                                    | `SHA256Digest().process(sharedX)`                    |
| `cbc(aesKey, iv).encrypt(data)`                           | `PaddedBlockCipher('AES/CBC/PKCS7').process(data)`   |
| `crypto.getRandomValues(new Uint8Array(16))`              | `Random.secure().nextInt(256)` × 16                  |
| `spkiBytes.slice(26)`                                     | `base64.decode(key).sublist(26)`                     |

### Nota sobre PKCS5 vs PKCS7

Java usa `AES/CBC/PKCS5Padding`. Para bloques de 128 bits, **PKCS5 y PKCS7
son idénticos**. `pointycastle` expone el padding como `PKCS7`; el resultado
binario es el mismo que Java produce con `PKCS5Padding`.

---

## Notas de seguridad

- La clave privada efímera se genera en cada llamada a `encrypt` y nunca se
  almacena ni se expone.
- El IV se genera con `Random.secure()`, que usa la fuente de entropía del SO.
- El servicio **no implementa `decrypt`**: el descifrado ocurre exclusivamente
  en el backend, que custodia la clave privada del servidor.
- La clave pública del servidor se lee solo desde la configuración; nunca se
  acepta como parámetro de la llamada de cifrado.

---

## Prueba de interoperabilidad

Para verificar que los mensajes cifrados desde Flutter son descifrados
correctamente por el backend Java, se puede ejecutar la siguiente prueba de
integración:

```dart
void main() {
  final crypto = CryptoService(
    publicKeyBase64: '<clave pública del entorno de pruebas>',
  );

  final cipher = crypto.encrypt('{"usuario":"test","clave":"1234"}');
  print('Enviando al backend: $cipher');
  // El backend debe responder con el JSON original descifrado.
}
```
