# Integracion con Angular

### Instalar paquetes
```
npm install tweetnacl tweetnacl-util
```

### Archivo "crypto.service.ts"
```
import { Injectable } from '@angular/core';
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
  private serverPublicKey: CryptoKey | null = null;
  private readonly encoder = new TextEncoder();
  private readonly initPromise: Promise<void>;

  constructor() {
    this.initPromise = this.inicializarClavePublica();
  }

  private async inicializarClavePublica(): Promise<void> {
    const publicKeyBase64 = environment.cryptoEccPublicKey
      ?.trim()
      .replace(/^=+/, '');
    if (!publicKeyBase64) {
      return;
    }

    try {
      const spkiBytes = this.base64ToBytes(publicKeyBase64);
      const spkiBuffer = new ArrayBuffer(spkiBytes.byteLength);
      new Uint8Array(spkiBuffer).set(spkiBytes);
      this.serverPublicKey = await crypto.subtle.importKey(
        'spki',
        spkiBuffer,
        { name: 'ECDH', namedCurve: 'P-256' },
        true,
        []
      );
    } catch (error) {
      console.error('No se pudo cargar cryptoEccPublicKey', error);
      this.serverPublicKey = null;
    }
  }

  async encrypt(plaintext: string): Promise<string> {
    await this.initPromise;

    if (!this.serverPublicKey) {
      throw new Error('Clave publica del servidor no disponible');
    }

    const ephemeralKeyPair = await crypto.subtle.generateKey(
      { name: 'ECDH', namedCurve: 'P-256' },
      true,
      ['deriveBits']
    );

    const sharedSecret = await crypto.subtle.deriveBits(
      { name: 'ECDH', public: this.serverPublicKey },
      ephemeralKeyPair.privateKey,
      256
    );

    const aesMaterial = await crypto.subtle.digest('SHA-256', sharedSecret);
    const aesKey = await crypto.subtle.importKey(
      'raw',
      aesMaterial,
      { name: 'AES-CBC', length: 256 },
      false,
      ['encrypt']
    );

    const iv = crypto.getRandomValues(new Uint8Array(16));
    const cipherBuffer = await crypto.subtle.encrypt(
      { name: 'AES-CBC', iv },
      aesKey,
      this.encoder.encode(plaintext)
    );
    const ephPubRaw = new Uint8Array(
      await crypto.subtle.exportKey('raw', ephemeralKeyPair.publicKey)
    );
    const cipherBytes = new Uint8Array(cipherBuffer);

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
      status: this.serverPublicKey
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

### Modo de Uso
```

  //Método Constructor
  constructor(
    private readonly _cryptoService: CryptoService,
  ) {}

    const usuarioCodigo = String(this._sessionStorage.getValue('user') ?? '');
    const usuarioCodigoCifrado = await this._cryptoService.encriptar(
      usuarioCodigo
    );
```

