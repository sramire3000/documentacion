# Encriptacion con Angular con la llave publica

## Archivo "package.json" add librerias
```
"dependencies": {
    "@noble/ciphers": "^1.1.3",
    "@noble/curves": "^1.3.0",
    "@noble/hashes": "^1.3.3",
  },
```

## Archivo "nvironment.ts" y "environment.prod.ts"
```
export const environment = {
  cryptoEccKeyName: 'production-key-1777269410477',
  cryptoEccPublicKey:
    'MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE6JEaq3VeDRxlB0PRXalaYncauTiy7AVZqyBwkwcx268sT1ebjpYHnPZmbkuYtAvS5IYu+hOafVIEKM3dTD6cDA==',
};
```

### Class "crypto.service.ts" en el paquete shared.services
```
import { Injectable } from '@angular/core';
import { cbc } from '@noble/ciphers/aes';
import { p256 } from '@noble/curves/p256';
import { sha256 } from '@noble/hashes/sha256';
import { environment } from 'src/environments/environment';

export interface KeyInfo {
  keyName: string;
  algorithm: string;
  dataEncryption: string;
  status: string;
}

@Injectable({
  providedIn: 'root',
})
export class CryptoService {
  private static readonly FRONT_V2_MAGIC = new Uint8Array([
    0x46, 0x45, 0x56, 0x32,
  ]); // FEV2
  // Clave publica en formato raw (65 bytes sin encapsulado SPKI)
  private serverPublicKeyBytes: Uint8Array | null = null;
  private readonly encoder = new TextEncoder();

  constructor() {
    this.inicializarClavePublica();
  }

  private inicializarClavePublica(): void {
    const publicKeyBase64 = environment.cryptoEccPublicKey
      ?.trim()
      .replace(/^=+/, '');
    if (!publicKeyBase64) {
      return;
    }

    try {
      const spkiBytes = this.base64ToBytes(publicKeyBase64);
      // Extrae la clave publica raw (65 bytes) saltando los 26 bytes de cabecera SPKI para P-256
      this.serverPublicKeyBytes = spkiBytes.slice(26);
    } catch (error) {
      console.error('No se pudo cargar cryptoEccPublicKey', error);
      this.serverPublicKeyBytes = null;
    }
  }

  async encrypt(plaintext: string): Promise<string> {
    if (!this.serverPublicKeyBytes) {
      throw new Error('Clave publica del servidor no disponible');
    }

    // Genera par de llaves efimero
    const ephPrivKey = p256.utils.randomPrivateKey();
    const ephPubRaw = p256.getPublicKey(ephPrivKey, false); // 65 bytes sin comprimir

    // ECDH: coordenada X del punto compartido (32 bytes), igual que Web Crypto deriveBits
    const sharedPoint = p256.getSharedSecret(
      ephPrivKey,
      this.serverPublicKeyBytes
    );
    const sharedSecret = sharedPoint.slice(1); // elimina el prefijo 02/03, queda X (32 bytes)

    // Deriva clave AES con SHA-256
    const aesKey = sha256(sharedSecret);

    // IV aleatorio (crypto.getRandomValues funciona en HTTP y HTTPS)
    const iv = crypto.getRandomValues(new Uint8Array(16));

    // Cifra con AES-256-CBC + PKCS7 (compatible con Java AES/CBC/PKCS5Padding)
    const cipherBytes = cbc(aesKey, iv).encrypt(this.encoder.encode(plaintext));

    // Formato V2: [magic(4)=FEV2 | ephPubRaw(65) | iv(16) | ciphertext(N)]
    const output = new Uint8Array(
      CryptoService.FRONT_V2_MAGIC.length +
        ephPubRaw.length +
        iv.length +
        cipherBytes.length
    );
    let offset = 0;
    output.set(CryptoService.FRONT_V2_MAGIC, offset);
    offset += CryptoService.FRONT_V2_MAGIC.length;
    output.set(ephPubRaw, offset);
    offset += ephPubRaw.length;
    output.set(iv, offset);
    offset += iv.length;
    output.set(cipherBytes, offset);

    return this.bytesToBase64(output);
  }

  async decrypt(_encryptedBase64: string): Promise<string> {
    throw new Error(
      'Decrypt no disponible en frontend (requiere clave privada del servidor)'
    );
  }

  async getKeyInfo(): Promise<KeyInfo> {
    return {
      keyName: environment.cryptoEccKeyName,
      algorithm: 'ECC - secp256r1 (256 bits)',
      dataEncryption: 'AES-256-CBC',
      status: this.serverPublicKeyBytes
        ? 'Configurado y listo'
        : 'Clave publica no cargada',
    };
  }

  async encriptar(texto: string): Promise<string> {
    return this.encrypt(texto);
  }

  private bytesToBase64(bytes: Uint8Array): string {
    let binary = '';
    const chunkSize = 0x8000;
    for (let i = 0; i < bytes.length; i += chunkSize) {
      const chunk = bytes.subarray(i, i + chunkSize);
      binary += String.fromCharCode(...chunk);
    }
    return btoa(binary);
  }

  private base64ToBytes(base64: string): Uint8Array {
    const binary = atob(base64);
    const bytes = new Uint8Array(binary.length);
    for (let i = 0; i < binary.length; i++) {
      bytes[i] = binary.charCodeAt(i);
    }
    return bytes;
  }
}
```

## Modo de uso
```
//Método Constructor
constructor(
private readonly _cryptoService: CryptoService,
) {}

//Metodo
//Encriptar el codigo del usuario
const usuarioCodigoCifrado = await this._cryptoService.encriptar(
  usuarioCodigo
);

```
