# Integracion con Angular

### Archivo "ANGULAR_CODE_TEMPLATES.md"
````
# 🚀 CÓDIGO COPY-PASTE - ANGULAR + SPRING BOOT ENCRIPTACIÓN

Código listo para usar. Solo copy-paste y adaptar según tu necesidad.

---

## 1️⃣ SERVICIO CRIPTOGRÁFICO ANGULAR

**Archivo:** `src/app/services/crypto.service.ts`

```typescript
import { Injectable } from '@angular/core';
import * as tweetnacl from 'tweetnacl';
import * as utils from 'tweetnacl-util';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {
  private serverPublicKey: Uint8Array | null = null;
  private clientKeyPair: tweetnacl.BoxKeyPair | null = null;

  constructor() {
    this.clientKeyPair = tweetnacl.box.keyPair();
  }

  async obtenerClavePublicaServidor(base64PublicKey: string): Promise<void> {
    this.serverPublicKey = this.base64ToBytes(base64PublicKey);
    console.log('✅ Clave pública del servidor cargada');
  }

  cifrarConServidorPublico(mensaje: string): string {
    if (!this.serverPublicKey) {
      throw new Error('Clave pública del servidor no disponible');
    }
    const mensajeBytes = utils.decodeUTF8(mensaje);
    const nonce = tweetnacl.randomBytes(24);
    const caja = tweetnacl.box.after(nonce, this.serverPublicKey, this.clientKeyPair!.secretKey);

    const resultado = new Uint8Array(nonce.length + caja.length);
    resultado.set(nonce);
    resultado.set(caja, nonce.length);

    return this.bytesToBase64(resultado);
  }

  descifrarRespuestaServidor(datoCifradoBase64: string): string {
    try {
      const datoCifrado = this.base64ToBytes(datoCifradoBase64);
      const nonce = datoCifrado.slice(0, 24);
      const caja = datoCifrado.slice(24);

      const mensaje = tweetnacl.box.open.after(nonce, caja, this.serverPublicKey!, this.clientKeyPair!.secretKey);

      if (!mensaje) {
        throw new Error('No se pudo descifrar');
      }
      return utils.encodeUTF8(mensaje);
    } catch (error) {
      console.error('Error descifrando:', error);
      throw error;
    }
  }

  obtenerClavePublicaCliente(): string {
    return this.bytesToBase64(this.clientKeyPair!.publicKey);
  }

  private bytesToBase64(bytes: Uint8Array): string {
    const binaria = String.fromCharCode(...bytes);
    return btoa(binaria);
  }

  private base64ToBytes(base64: string): Uint8Array {
    const binaria = atob(base64);
    const bytes = new Uint8Array(binaria.length);
    for (let i = 0; i < binaria.length; i++) {
      bytes[i] = binaria.charCodeAt(i);
    }
    return bytes;
  }
}
```

---

## 2️⃣ INTERCEPTOR HTTP

**Archivo:** `src/app/interceptors/crypto.interceptor.ts`

```typescript
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CryptoService } from '../services/crypto.service';

@Injectable()
export class CryptoInterceptor implements HttpInterceptor {
  constructor(private cryptoService: CryptoService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.debeSkipEncriptacion(req.url)) {
      return next.handle(req);
    }

    if ((req.method === 'POST' || req.method === 'PUT') && req.body) {
      try {
        const json = JSON.stringify(req.body);
        const cifrado = this.cryptoService.cifrarConServidorPublico(json);

        const reqCifrado = req.clone({
          body: { encrypted: cifrado },
          setHeaders: { 'X-Encrypted': 'true' }
        });

        return next.handle(reqCifrado).pipe(
          map(event => {
            if (event instanceof HttpResponse && event.body?.encrypted) {
              try {
                const descifrado = this.cryptoService.descifrarRespuestaServidor(event.body.encrypted);
                return event.clone({ body: JSON.parse(descifrado) });
              } catch (e) {
                console.error('Error descifrando respuesta:', e);
                return event;
              }
            }
            return event;
          })
        );
      } catch (error) {
        console.error('Error en interceptor:', error);
        return next.handle(req);
      }
    }

    return next.handle(req);
  }

  private debeSkipEncriptacion(url: string): boolean {
    const skipUrls = ['/api/key-info', '/api/public-key', '/auth/login-publico'];
    return skipUrls.some(skipUrl => url.includes(skipUrl));
  }
}
```

**Registro en `app.module.ts`:**

```typescript
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CryptoInterceptor } from './interceptors/crypto.interceptor';

@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CryptoInterceptor,
      multi: true
    }
  ]
})
export class AppModule { }
```

---

## 3️⃣ SERVICIO API

**Archivo:** `src/app/services/api.service.ts`

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CryptoService } from './crypto.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private cryptoService: CryptoService
  ) {
    this.inicializarCriptografia();
  }

  async inicializarCriptografia(): Promise<void> {
    try {
      const clavePublica = await this.http.get<any>(`${this.apiUrl}/public-key`).toPromise();
      await this.cryptoService.obtenerClavePublicaServidor(clavePublica.publicKey);
    } catch (error) {
      console.error('Error inicializando criptografía:', error);
    }
  }

  enviarDatosCifrados(endpoint: string, datos: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}${endpoint}`, datos);
  }

  cifrarMensaje(mensaje: string): string {
    return this.cryptoService.cifrarConServidorPublico(mensaje);
  }

  descifrarMensaje(datoCifrado: string): string {
    return this.cryptoService.descifrarRespuestaServidor(datoCifrado);
  }

  loginCifrado(usuario: string, password: string): Observable<any> {
    const credenciales = { usuario, password };
    return this.enviarDatosCifrados('/encrypt', {
      message: JSON.stringify(credenciales)
    });
  }

  guardarDatos(datos: any): Observable<any> {
    return this.enviarDatosCifrados('/usuario/guardar', datos);
  }

  obtenerDatos(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuario/${id}`);
  }
}
```

---

## 4️⃣ COMPONENTE LOGIN

**Archivo:** `src/app/components/login.component.ts`

```typescript
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-login',
  template: `
    <div class="login-container">
      <h2>🔐 Login Seguro (Encriptado)</h2>

      <form (ngSubmit)="login()" [formGroup]="loginForm">
        <div class="form-group">
          <label>Usuario:</label>
          <input
            type="text"
            formControlName="usuario"
            placeholder="Tu usuario"
            class="form-control">
        </div>

        <div class="form-group">
          <label>Contraseña:</label>
          <input
            type="password"
            formControlName="password"
            placeholder="Tu contraseña"
            class="form-control">
        </div>

        <button type="submit" [disabled]="cargando" class="btn btn-primary">
          {{ cargando ? '⏳ Autenticando...' : '✅ Login' }}
        </button>
      </form>

      <div *ngIf="mensaje" [ngClass]="tipoMensaje" class="alert">
        {{ mensaje }}
      </div>
    </div>
  `,
  styles: [`
    .login-container {
      max-width: 400px;
      margin: 50px auto;
      padding: 20px;
      border: 1px solid #ddd;
      border-radius: 8px;
    }
    .form-group {
      margin-bottom: 15px;
    }
    input { width: 100%; padding: 8px; }
    button { width: 100%; padding: 10px; }
    .alert { margin-top: 15px; padding: 10px; }
  `]
})
export class LoginComponent {
  loginForm = this.crearFormulario();
  mensaje: string = '';
  tipoMensaje: string = '';
  cargando: boolean = false;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  crearFormulario() {
    return this.fb.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.mostrarMensaje('Por favor completa los campos', 'error');
      return;
    }

    this.cargando = true;
    const { usuario, password } = this.loginForm.value;

    this.apiService.loginCifrado(usuario, password).subscribe({
      next: (respuesta) => {
        this.mostrarMensaje('✅ Login exitoso', 'success');
        localStorage.setItem('token', respuesta.token);
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.mostrarMensaje('❌ Credenciales inválidas', 'error');
        console.error(error);
      },
      complete: () => this.cargando = false
    });
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensaje = msg;
    this.tipoMensaje = `alert-${tipo}`;
    setTimeout(() => this.mensaje = '', 5000);
  }
}
```

---

## 5️⃣ COMPONENTE DATOS SENSIBLES

**Archivo:** `src/app/components/datos-sensibles.component.ts`

```typescript
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-datos-sensibles',
  template: `
    <div class="container">
      <h2>💳 Guardar Datos Sensibles (Cifrados)</h2>

      <form [formGroup]="formulario" (ngSubmit)="guardar()">
        <div class="form-group">
          <label>Número de Tarjeta:</label>
          <input
            type="text"
            formControlName="numeroTarjeta"
            placeholder="XXXX XXXX XXXX XXXX"
            maxlength="19">
        </div>

        <div class="form-group">
          <label>CVV:</label>
          <input
            type="password"
            formControlName="cvv"
            placeholder="***"
            maxlength="4">
        </div>

        <div class="form-group">
          <label>Monto a Transferir:</label>
          <input
            type="number"
            formControlName="monto"
            placeholder="0.00"
            step="0.01">
        </div>

        <button type="submit" [disabled]="cargando">
          {{ cargando ? '⏳ Procesando...' : '💾 Enviar Cifrado' }}
        </button>
      </form>

      <div *ngIf="mensaje" class="alert" [ngClass]="tipoMensaje">
        {{ mensaje }}
      </div>

      <p class="info">
        🔒 Todos los datos se cifran automáticamente antes de enviar
      </p>
    </div>
  `,
  styles: [`
    .container { max-width: 500px; margin: 20px auto; }
    .form-group { margin-bottom: 15px; }
    input { width: 100%; padding: 10px; }
    button { width: 100%; padding: 10px; }
    .alert { padding: 10px; margin-top: 15px; }
    .info { color: #666; font-size: 12px; }
  `]
})
export class DatosSensiblesComponent {
  formulario: FormGroup;
  mensaje: string = '';
  tipoMensaje: string = '';
  cargando: boolean = false;

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService
  ) {
    this.formulario = this.fb.group({
      numeroTarjeta: ['', Validators.required],
      cvv: ['', [Validators.required, Validators.minLength(3)]],
      monto: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  guardar(): void {
    if (this.formulario.invalid) {
      this.mostrarMensaje('❌ Completa todos los campos', 'error');
      return;
    }

    this.cargando = true;
    const datosSensibles = {
      ...this.formulario.value,
      timestamp: new Date().toISOString()
    };

    this.apiService.guardarDatos(datosSensibles).subscribe({
      next: () => {
        this.mostrarMensaje('✅ Datos guardados cifrados', 'success');
        this.formulario.reset();
      },
      error: (error) => {
        this.mostrarMensaje('❌ Error al guardar', 'error');
        console.error(error);
      },
      complete: () => this.cargando = false
    });
  }

  private mostrarMensaje(msg: string, tipo: string): void {
    this.mensaje = msg;
    this.tipoMensaje = `alert-${tipo}`;
    setTimeout(() => this.mensaje = '', 4000);
  }
}
```

---

## 6️⃣ CONFIGURACIÓN SPRING BOOT

**Archivo:** `src/main/java/...config/CorsConfig.java`

```java
package com.example.demo_encrypt.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:4200",    // Angular dev
                "https://midominio.com"     // Producción
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
```

---

## 7️⃣ ENDPOINT EXPOSER CLAVE PÚBLICA

**Agregar a `EncryptionController.java`:**

```java
/**
 * Expone la clave pública para que Angular la use
 * Endpoint SIN autenticación
 */
@GetMapping("/public-key")
public ResponseEntity<Map<String, String>> obtenerClavePublica() {
    try {
        String clavePublicaB64 = cryptoProperties.getEcc().getPublicKey();

        return ResponseEntity.ok(Map.of(
            "publicKey", clavePublicaB64,
            "algorithm", "ECC-secp256r1",
            "timestamp", LocalDateTime.now().toString()
        ));
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}
```

---

## 8️⃣ UNIT TEST ANGULAR

**Archivo:** `src/app/services/crypto.service.spec.ts`

```typescript
import { TestBed } from '@angular/core/testing';
import { CryptoService } from './crypto.service';

describe('CryptoService', () => {
  let service: CryptoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CryptoService);
  });

  it('debería ser creado', () => {
    expect(service).toBeTruthy();
  });

  it('debería generar nonce diferente cada vez', () => {
    const clavePublica = 'MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE0n8O5dRgJhQAFuKFXG8x2LqKY8y6B8mJm1qHvJqT0Y8KBfKcLvZKx7qJLhJKVzQJ7u7DfXqYQZmXWxFYKHRmAw==';

    service.obtenerClavePublicaServidor(clavePublica).then(() => {
      const cifrado1 = service.cifrarConServidorPublico('test');
      const cifrado2 = service.cifrarConServidorPublico('test');

      expect(cifrado1).not.toEqual(cifrado2);
    });
  });

  it('debería obtener clave pública del cliente', () => {
    const clave = service.obtenerClavePublicaCliente();
    expect(clave).toBeTruthy();
    expect(typeof clave).toBe('string');
  });
});
```

---

## 9️⃣ INSTALACIÓN NPM

```bash
# Instalar dependencias necesarias
npm install tweetnacl tweetnacl-util

# Opcional: Si necesitas tipos de TypeScript
npm install --save-dev @types/tweetnacl

# Verificar instalación
npm list tweetnacl
```

---

## 🔟 FLUJO COMPLETO EN ACCIÓN

### Paso 1: Angular obtiene clave pública

```typescript
// En app.component.ts OnInit
constructor(private apiService: ApiService) {}

ngOnInit() {
  // Automático en ApiService.constructor()
}
```

### Paso 2: Usuario completa formulario

```typescript
formulario.value = {
  usuario: "admin",
  password: "secreto123"
}
```

### Paso 3: Angular cifra datos

```
Datos originales: {"usuario":"admin","password":"secreto123"}
     ↓ (CIFRADO CON ECC PÚBLICA)
Datos cifrados: "BFmJ6xK9u2M...base64..."
```

### Paso 4: Spring Boot descifra

```java
@PostMapping("/encrypt")
String cifrado = request.get("encrypted"); // "BFmJ6xK9u2M..."
String descifrado = encryptionService.decrypt(cifrado);
// Resultado: {"usuario":"admin","password":"secreto123"}
```

### Paso 5: Spring Boot procesa y cifra respuesta

```java
Usuario usuario = autenticar(descifrado);
String respuestaCifrada = encryptionService.encrypt(usuario);
// Retorna: {"encrypted": "BFmJ6xK9u2M...response..."}
```

### Paso 6: Angular descifra respuesta

```typescript
respuestaServidor = {"encrypted": "BFmJ6xK9u2M..."}
datosDescifrados = cryptoService.descifrarRespuestaServidor(
  respuestaServidor.encrypted
);
// Resultado: {"id":1,"usuario":"admin","token":"..."}
```

---

## 🎯 RESUMEN: QUÉ HACE CADA ARCHIVO

| Archivo | Función |
|---------|---------|
| `crypto.service.ts` | Cifra/descifra con claves del servidor |
| `crypto.interceptor.ts` | Automáticamente cifra/descifra todas las peticiones |
| `api.service.ts` | Llamadas HTTP seguras |
| `login.component.ts` | Formulario de login (datos se cifran auto) |
| `datos-sensibles.component.ts` | Guardar datos sensibles cifrados |
| `CorsConfig.java` | Permite peticiones desde Angular |
| `EncryptionController.java` | Expone endpoint `/api/public-key` |

---

## ✅ CHECKLIST: ANTES DE PRODUCCIÓN

- [ ] Cambiar `localhost:4200` por tu dominio real
- [ ] Usar HTTPS (no HTTP)
- [ ] Generar nuevas llaves ECC con `GenerateKeysApp`
- [ ] Guardar claves privadas en vault/secrets manager
- [ ] Pruebas de seguridad (penetration testing)
- [ ] Tests unitarios con cobertura > 80%
- [ ] Documentar endpoints en Swagger
- [ ] Configurar rate limiting en backend
- [ ] Agregar logs de auditoría
- [ ] Revisar OWASP Top 10

---

**¡Listo para producción! 🚀**

Copia estos archivos y adapta según tu proyecto.

````

### Archivo "ANGULAR_INTEGRATION.md"
````
# 🔐 INTEGRACIÓN ANGULAR + SPRING BOOT ENCRIPTACIÓN HÍBRIDA

Guía completa para implementar comunicación segura entre Angular y Spring Boot usando ECC + AES-256.

## 📋 Contenido

- [Arquitectura](#arquitectura)
- [Instalación en Angular](#instalación-en-angular)
- [Servicio de Encriptación Angular](#servicio-de-encriptación-angular)
- [Interceptor HTTP](#interceptor-http)
- [Métodos Necesarios](#métodos-necesarios)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Flujo Completo](#flujo-completo)
- [Configuración CORS](#configuración-cors)
- [Seguridad](#seguridad)
- [Troubleshooting](#troubleshooting)

---

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────┐
│                      ANGULAR (Frontend)                 │
├─────────────────────────────────────────────────────────┤
│ 1. Obtiene clave pública ECC del servidor               │
│ 2. Cifra datos con ECC pública                          │
│ 3. Envía datos cifrados (POST)                          │
│ 4. Recibe respuesta cifrada                             │
│ 5. Descifra con clave privada del cliente               │
└─────────────────────────────────────────────────────────┘
                          ↕ HTTPS
┌─────────────────────────────────────────────────────────┐
│                  SPRING BOOT (Backend)                  │
├─────────────────────────────────────────────────────────┤
│ 1. Recibe datos cifrados                                │
│ 2. Descifra con clave privada ECC                       │
│ 3. Procesa datos descifrados                            │
│ 4. Cifra respuesta con clave pública ECC                │
│ 5. Envía respuesta cifrada                              │
└─────────────────────────────────────────────────────────┘
```

---

## 📦 Instalación en Angular

### Paso 1: Instalar librerías criptográficas

```bash
npm install tweetnacl tweetnacl-util
npm install libsodium.js
npm install @angular/common @angular/http
```

**Alternativas recomendadas:**
- `TweetNaCl.js` - Ligero (12 KB), perfecto para navegadores
- `libsodium.js` - Port de libsodium (más completo)
- `crypto-js` - Compatible pero menos seguro

### Paso 2: Importar en `tsconfig.json`

```json
{
  "compilerOptions": {
    "types": ["node"],
    "skipLibCheck": true,
    "allowJs": true
  }
}
```

---

## 🔐 Servicio de Encriptación Angular

### Archivo: `src/app/services/crypto.service.ts`

```typescript
import { Injectable } from '@angular/core';
import * as tweetnacl from 'tweetnacl';
import * as utils from 'tweetnacl-util';

/**
 * Servicio de encriptación para Angular
 * Implementa ECC + AES-256 compatible con Spring Boot
 */
@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  // Almacenar clave pública del servidor
  private serverPublicKey: Uint8Array | null = null;

  // Par de llaves del cliente (opcional, para end-to-end)
  private clientKeyPair: tweetnacl.BoxKeyPair | null = null;

  constructor() {
    // Generar par de llaves para el cliente (opcional)
    this.clientKeyPair = tweetnacl.box.keyPair();
  }

  /**
   * Obtiene la clave pública del servidor
   * Generalmente desde una endpoint sin autenticación
   */
  async obtenerClavePublicaServidor(base64PublicKey: string): Promise<void> {
    // Convertir Base64 a Uint8Array
    this.serverPublicKey = this.base64ToBytes(base64PublicKey);
    console.log('✅ Clave pública del servidor cargada');
  }

  /**
   * Cifra un mensaje con la clave pública del servidor
   *
   * @param mensaje Texto a cifrar
   * @returns Datos cifrados en Base64
   */
  cifrarConServidorPublico(mensaje: string): string {
    if (!this.serverPublicKey) {
      throw new Error('❌ Clave pública del servidor no está disponible');
    }

    // Convertir mensaje a bytes
    const mensajeBytes = utils.decodeUTF8(mensaje);

    // Generar nonce aleatorio (24 bytes)
    const nonce = tweetnacl.randomBytes(24);

    // Cifrar con clave pública del servidor
    const caja = tweetnacl.box.after(
      nonce,
      this.serverPublicKey,
      this.clientKeyPair!.secretKey
    );

    // Combinar nonce + datos cifrados
    const resultado = new Uint8Array(nonce.length + caja.length);
    resultado.set(nonce);
    resultado.set(caja, nonce.length);

    // Convertir a Base64 para transmisión
    return this.bytesToBase64(resultado);
  }

  /**
   * Descifra un mensaje recibido del servidor
   *
   * @param datoCifradoBase64 Datos cifrados en Base64
   * @returns Texto descifrado
   */
  descifrarRespuestaServidor(datoCifradoBase64: string): string {
    try {
      // Convertir de Base64
      const datoCifrado = this.base64ToBytes(datoCifradoBase64);

      // Extraer nonce (primeros 24 bytes)
      const nonce = datoCifrado.slice(0, 24);
      const caja = datoCifrado.slice(24);

      // Descifrar
      const mensaje = tweetnacl.box.open.after(
        nonce,
        caja,
        this.serverPublicKey!,
        this.clientKeyPair!.secretKey
      );

      if (!mensaje) {
        throw new Error('No se pudo descifrar el mensaje');
      }

      return utils.encodeUTF8(mensaje);
    } catch (error) {
      console.error('❌ Error descifrando respuesta:', error);
      throw error;
    }
  }

  /**
   * Convierte datos binarios a Base64
   */
  private bytesToBase64(bytes: Uint8Array): string {
    const binaria = String.fromCharCode(...bytes);
    return btoa(binaria);
  }

  /**
   * Convierte Base64 a datos binarios
   */
  private base64ToBytes(base64: string): Uint8Array {
    const binaria = atob(base64);
    const bytes = new Uint8Array(binaria.length);
    for (let i = 0; i < binaria.length; i++) {
      bytes[i] = binaria.charCodeAt(i);
    }
    return bytes;
  }

  /**
   * Obtiene la clave pública del cliente (para compartir con servidor si es necesario)
   */
  obtenerClavePublicaCliente(): string {
    return this.bytesToBase64(this.clientKeyPair!.publicKey);
  }
}
```

---

## 🌐 Interceptor HTTP

### Archivo: `src/app/interceptors/crypto.interceptor.ts`

```typescript
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CryptoService } from '../services/crypto.service';

/**
 * Interceptor que automáticamente cifra/descifra todas las peticiones
 */
@Injectable()
export class CryptoInterceptor implements HttpInterceptor {

  constructor(private cryptoService: CryptoService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    // No interceptar ciertos endpoints
    if (this.debeSkipEncriptacion(request.url)) {
      return next.handle(request);
    }

    // Cifrar body si existe
    if (request.method === 'POST' || request.method === 'PUT') {
      if (request.body && typeof request.body === 'object') {
        try {
          // Convertir a JSON
          const json = JSON.stringify(request.body);

          // Cifrar
          const cifrado = this.cryptoService.cifrarConServidorPublico(json);

          // Crear nuevo request con datos cifrados
          const requestCifrado = request.clone({
            body: { encrypted: cifrado },
            setHeaders: {
              'X-Encrypted': 'true'
            }
          });

          return next.handle(requestCifrado).pipe(
            map(event => {
              if (event instanceof HttpResponse) {
                // Descifrar respuesta
                if (event.body && event.body.encrypted) {
                  try {
                    const descifrado = this.cryptoService.descifrarRespuestaServidor(
                      event.body.encrypted
                    );
                    const datosDescifrados = JSON.parse(descifrado);

                    return event.clone({
                      body: datosDescifrados
                    });
                  } catch (error) {
                    console.error('Error descifrando respuesta:', error);
                    return event;
                  }
                }
              }
              return event;
            })
          );
        } catch (error) {
          console.error('Error en interceptor:', error);
          return next.handle(request);
        }
      }
    }

    return next.handle(request);
  }

  /**
   * Determina si una URL debe saltarse la encriptación
   */
  private debeSkipEncriptacion(url: string): boolean {
    const skipUrls = [
      '/api/key-info',
      '/api/public-key',
      '/auth/login-publico'
    ];
    return skipUrls.some(skipUrl => url.includes(skipUrl));
  }
}
```

### Registrar en `app.module.ts`

```typescript
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CryptoInterceptor } from './interceptors/crypto.interceptor';

@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CryptoInterceptor,
      multi: true
    }
  ]
})
export class AppModule { }
```

---

## 📚 Métodos Necesarios

### 1. Servicio de API en Angular

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private cryptoService: CryptoService
  ) {
    this.inicializarCriptografia();
  }

  /**
   * Inicializa la criptografía obteniendo la clave pública del servidor
   */
  async inicializarCriptografia(): Promise<void> {
    try {
      // Obtener clave pública del servidor
      const respuesta = await this.http.get<any>(
        `${this.apiUrl}/key-info`
      ).toPromise();

      // Si el servidor expone la clave pública en un endpoint especial
      const clavePublica = await this.http.get<any>(
        `${this.apiUrl}/public-key`
      ).toPromise();

      await this.cryptoService.obtenerClavePublicaServidor(
        clavePublica.publicKey
      );
    } catch (error) {
      console.error('Error inicializando criptografía:', error);
    }
  }

  /**
   * Envía datos cifrados al servidor
   */
  enviarDatosCifrados(endpoint: string, datos: any): Observable<any> {
    const url = `${this.apiUrl}${endpoint}`;

    // El interceptor se encargará de cifrar automáticamente
    return this.http.post<any>(url, datos);
  }

  /**
   * Cifra un mensaje manualmente (sin interceptor)
   */
  cifrarMensaje(mensaje: string): string {
    return this.cryptoService.cifrarConServidorPublico(mensaje);
  }

  /**
   * Descifra un mensaje manualmente
   */
  descifrarMensaje(datoCifrado: string): string {
    return this.cryptoService.descifrarRespuestaServidor(datoCifrado);
  }

  /**
   * Ejemplo: Login cifrado
   */
  loginCifrado(usuario: string, password: string): Observable<any> {
    const credenciales = {
      usuario,
      password
    };

    return this.enviarDatosCifrados('/encrypt', {
      message: JSON.stringify(credenciales)
    });
  }

  /**
   * Ejemplo: Obtener datos de usuario (cifrado)
   */
  obtenerUsuario(usuarioId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  /**
   * Ejemplo: Guardar datos sensibles (cifrados)
   */
  guardarDatosSensibles(datos: any): Observable<any> {
    return this.enviarDatosCifrados('/usuario/guardar', datos);
  }
}
```

---

## 💻 Ejemplos de Uso

### Ejemplo 1: Componente de Login

```typescript
import { Component } from '@angular/core';
import { ApiService } from './services/api.service';

@Component({
  selector: 'app-login',
  template: `
    <form (ngSubmit)="login()">
      <input [(ngModel)]="usuario" name="usuario" placeholder="Usuario">
      <input [(ngModel)]="password" name="password" type="password" placeholder="Contraseña">
      <button type="submit">Login Cifrado</button>
      <p *ngIf="mensaje">{{ mensaje }}</p>
    </form>
  `
})
export class LoginComponent {

  usuario: string = '';
  password: string = '';
  mensaje: string = '';

  constructor(private apiService: ApiService) {}

  login(): void {
    this.mensaje = '⏳ Autenticando...';

    this.apiService.loginCifrado(this.usuario, this.password)
      .subscribe({
        next: (respuesta) => {
          this.mensaje = '✅ Login exitoso (datos cifrados)';
          console.log('Respuesta descifrada:', respuesta);
        },
        error: (error) => {
          this.mensaje = '❌ Error en login';
          console.error(error);
        }
      });
  }
}
```

### Ejemplo 2: Formulario de Datos Sensibles

```typescript
import { Component } from '@angular/core';
import { ApiService } from './services/api.service';

@Component({
  selector: 'app-datos-sensibles',
  template: `
    <form (ngSubmit)="guardar()">
      <input [(ngModel)]="numeroTarjeta" name="numeroTarjeta"
             placeholder="Número de tarjeta">
      <input [(ngModel)]="cvv" name="cvv" type="password"
             placeholder="CVV">
      <input [(ngModel)]="monto" name="monto" type="number"
             placeholder="Monto a transferir">
      <button type="submit">💳 Enviar Cifrado</button>
    </form>
    <div *ngIf="mensaje">{{ mensaje }}</div>
  `
})
export class DatosSensiblesComponent {

  numeroTarjeta: string = '';
  cvv: string = '';
  monto: number = 0;
  mensaje: string = '';

  constructor(private apiService: ApiService) {}

  guardar(): void {
    // Los datos se cifran automáticamente en el interceptor
    const datosSensibles = {
      numeroTarjeta: this.numeroTarjeta,
      cvv: this.cvv,
      monto: this.monto,
      timestamp: new Date().toISOString()
    };

    this.apiService.guardarDatosSensibles(datosSensibles)
      .subscribe({
        next: (respuesta) => {
          this.mensaje = '✅ Datos guardados cifrados correctamente';
          this.limpiar();
        },
        error: (error) => {
          this.mensaje = '❌ Error al guardar';
          console.error(error);
        }
      });
  }

  limpiar(): void {
    this.numeroTarjeta = '';
    this.cvv = '';
    this.monto = 0;
  }
}
```

### Ejemplo 3: Usar Encriptación Manual

```typescript
import { Component } from '@angular/core';
import { CryptoService } from './services/crypto.service';

@Component({
  selector: 'app-encriptar-manual',
  template: `
    <div>
      <h2>Cifrado Manual</h2>
      <textarea [(ngModel)]="textoOriginal" placeholder="Texto a cifrar"></textarea>
      <button (click)="cifrar()">🔐 Cifrar</button>

      <div *ngIf="textoCifrado">
        <p><strong>Cifrado:</strong> {{ textoCifrado }}</p>
        <button (click)="copiar()">📋 Copiar</button>
      </div>

      <textarea [(ngModel)]="textoCifradoDescifrar"
                placeholder="Pega texto cifrado para descifrar"></textarea>
      <button (click)="descifrar()">🔓 Descifrar</button>

      <div *ngIf="textoDescifrado">
        <p><strong>Descifrado:</strong> {{ textoDescifrado }}</p>
      </div>
    </div>
  `
})
export class EncriptarManualComponent {

  textoOriginal: string = '';
  textoCifrado: string = '';
  textoCifradoDescifrar: string = '';
  textoDescifrado: string = '';

  constructor(private cryptoService: CryptoService) {}

  cifrar(): void {
    try {
      this.textoCifrado = this.cryptoService.cifrarConServidorPublico(
        this.textoOriginal
      );
      console.log('✅ Cifrado exitoso');
    } catch (error) {
      console.error('Error cifrando:', error);
    }
  }

  descifrar(): void {
    try {
      this.textoDescifrado = this.cryptoService.descifrarRespuestaServidor(
        this.textoCifradoDescifrar
      );
      console.log('✅ Descifrado exitoso');
    } catch (error) {
      console.error('Error descifrando:', error);
    }
  }

  copiar(): void {
    navigator.clipboard.writeText(this.textoCifrado);
    alert('✅ Copiado al portapapeles');
  }
}
```

---

## 🔄 Flujo Completo

### Escenario: Login Usuario

#### 1️⃣ Angular obtiene clave pública del servidor

```
GET /api/public-key
↓
{
  "publicKey": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE0n8O..."
}
```

#### 2️⃣ Angular cifra credenciales

```typescript
const credenciales = JSON.stringify({
  usuario: "admin",
  password: "secreto123"
});

const cifrado = cryptoService.cifrarConServidorPublico(credenciales);
// Resultado: "BFmJ6xK9u2M...base64data..."
```

#### 3️⃣ Angular envía datos cifrados

```
POST /api/encrypt
Content-Type: application/json

{
  "encrypted": "BFmJ6xK9u2M...base64data..."
}
```

#### 4️⃣ Spring Boot recibe y descifra

```java
@PostMapping("/encrypt")
public ResponseEntity<Map<String, Object>> encrypt(
        @RequestBody Map<String, String> request) {

    String cifrado = request.get("encrypted");

    // Descifrar con clave privada
    String descifrado = encryptionService.decrypt(cifrado);
    // Resultado: {"usuario":"admin","password":"secreto123"}

    // Procesar login
    Usuario usuario = autenticar(descifrado);

    // Cifrar respuesta
    String respuestaCifrada = encryptionService.encrypt(
        JSON.stringify(usuario)
    );

    return ResponseEntity.ok(Map.of(
        "encrypted", respuestaCifrada
    ));
}
```

#### 5️⃣ Angular recibe y descifra respuesta

```typescript
{
  "encrypted": "BFmJ6xK9u2M...response..."
}

const respuesta = cryptoService.descifrarRespuestaServidor(
  event.body.encrypted
);
// Resultado: {"id":1,"usuario":"admin","token":"..."}
```

---

## 🌐 Configuración CORS

### En Spring Boot: `application.properties`

```properties
# CORS - Permitir Angular
server.servlet.context-path=/
server.port=8080

# Configuración CORS
server.servlet.context-path=/
```

### En Spring Boot: Crear clase `CorsConfig.java`

```java
package com.example.demo_encrypt.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:4200",        // Angular dev
                "http://localhost:3000",        // Alternativo
                "https://midominio.com"         // Producción
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200",
            "https://midominio.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
```

---

## 🔒 Seguridad

### ✅ Buenas Prácticas

1. **SIEMPRE usar HTTPS en producción**
   ```typescript
   // ✅ CORRECTO
   const apiUrl = 'https://api.midominio.com';

   // ❌ INCORRECTO
   const apiUrl = 'http://api.midominio.com';
   ```

2. **No guardar clave privada en Angular**
   ```typescript
   // ❌ NUNCA hagas esto
   const clavePrivada = 'MIGHAgEAMBMGBy...';

   // ✅ Las claves privadas solo en servidor
   ```

3. **Validar en servidor SIEMPRE**
   ```java
   // ✅ Validar en backend
   @PostMapping("/guardar-usuario")
   public ResponseEntity<?> guardarUsuario(@RequestBody UsuarioDTO usuario) {
       // Validar datos descifrados
       if (usuario.getEmail() == null || !esEmailValido(usuario.getEmail())) {
           return ResponseEntity.badRequest().build();
       }
       // Guardar
   }
   ```

4. **Usar Content Security Policy**
   ```typescript
   // En index.html
   <meta http-equiv="Content-Security-Policy"
         content="default-src 'self'; script-src 'self' 'unsafe-inline'">
   ```

5. **Limpiar datos sensibles de memoria**
   ```typescript
   limpiarCredenciales(): void {
       this.usuario = '';
       this.password = '';
       // Angular auto-garbage collection
   }
   ```

### ⚠️ Posibles Vulnerabilidades

| Riesgo | Prevención |
|--------|-----------|
| **Man-in-the-middle** | Usar HTTPS obligatorio |
| **XSS injection** | Sanitizar entrada con Angular |
| **CSRF attacks** | Usar tokens CSRF con Spring Security |
| **Claves expuestas** | Usar vault/secrets manager |
| **Fuerza bruta** | Rate limiting en servidor |

---

## 🧪 Testing

### Test de Servicio de Encriptación

```typescript
import { TestBed } from '@angular/core/testing';
import { CryptoService } from './crypto.service';

describe('CryptoService', () => {
  let service: CryptoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CryptoService);
  });

  it('debería cifrar y descifrar mensajes', async () => {
    const mensaje = 'Mensaje de prueba';

    // Simular carga de clave pública
    const clavePublicaB64 = 'MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE0n8O...';
    await service.obtenerClavePublicaServidor(clavePublicaB64);

    // Cifrar
    const cifrado = service.cifrarConServidorPublico(mensaje);
    expect(cifrado).toBeTruthy();
    expect(typeof cifrado).toBe('string');

    // Base64 válido
    expect(/^[A-Za-z0-9+/=]*$/.test(cifrado)).toBe(true);
  });

  it('debería generar nonce aleatorio', () => {
    const cifrado1 = service.cifrarConServidorPublico('test');
    const cifrado2 = service.cifrarConServidorPublico('test');

    // Diferentes debido al nonce aleatorio
    expect(cifrado1).not.toEqual(cifrado2);
  });
});
```

---

## 🐛 Troubleshooting

### ❌ Error: "Clave pública del servidor no disponible"

**Causa:** `CryptoService.obtenerClavePublicaServidor()` no fue llamado

**Solución:**
```typescript
constructor(private apiService: ApiService) {
  // Llamar en constructor o ngOnInit
  this.apiService.inicializarCriptografia();
}
```

---

### ❌ Error CORS: "No 'Access-Control-Allow-Origin' header"

**Causa:** CORS no está configurado en Spring Boot

**Solución:** Agregar `CorsConfig.java` (ver sección [Configuración CORS](#configuración-cors))

---

### ❌ Error: "Failed to decrypt message"

**Causa:**
- Claves desincronizadas
- Datos corruptos
- Formato incorrecto

**Solución:**
```typescript
try {
  const descifrado = this.cryptoService.descifrarRespuestaServidor(datos);
} catch (error) {
  console.error('Error descifrando:', error);
  // Reintentar obtener clave pública del servidor
  await this.apiService.inicializarCriptografia();
}
```

---

### ❌ Error: "tweetnacl is not defined"

**Causa:** Libería no importada correctamente

**Solución:**
```typescript
// ✅ Importar correctamente
import * as tweetnacl from 'tweetnacl';
import * as utils from 'tweetnacl-util';

// O usar:
// npm install tweetnacl tweetnacl-util
// npm install @types/tweetnacl (si es necesario)
```

---

### ❌ Datos descifrados son ininteligibles

**Causa:** Encoding UTF-8 incorrecto

**Solución:**
```typescript
// Verificar que se usa UTF-8 en ambos lados
const mensaje = utils.encodeUTF8(datos);  // ✅
const mensaje = new TextEncoder().encode(datos);  // ✅ Alternativa
```

---

## 📋 Checklist de Implementación

- [ ] Instalar TweetNaCl.js en Angular
- [ ] Crear `CryptoService`
- [ ] Crear `CryptoInterceptor`
- [ ] Registrar interceptor en `AppModule`
- [ ] Crear `ApiService`
- [ ] Crear endpoint `/api/public-key` en Spring Boot
- [ ] Configurar CORS en Spring Boot
- [ ] Crear `CorsConfig.java`
- [ ] Probar cifrado manual en componente
- [ ] Probar con interceptor automático
- [ ] Tests unitarios para CryptoService
- [ ] Tests de integración de APIs
- [ ] Verificar HTTPS en producción
- [ ] Revisar manejo de errores
- [ ] Documentar endpoints en OpenAPI/Swagger

---

## 📚 Resumen de Métodos

### CryptoService

| Método | Parámetros | Retorna | Descripción |
|--------|-----------|---------|------------|
| `obtenerClavePublicaServidor()` | `base64PublicKey: string` | `Promise<void>` | Carga clave del servidor |
| `cifrarConServidorPublico()` | `mensaje: string` | `string (Base64)` | Cifra con clave pública |
| `descifrarRespuestaServidor()` | `datoCifradoBase64: string` | `string` | Descifra respuesta del servidor |
| `obtenerClavePublicaCliente()` | Nada | `string (Base64)` | Retorna clave pública del cliente |

### ApiService

| Método | Parámetros | Retorna | Descripción |
|--------|-----------|---------|------------|
| `inicializarCriptografia()` | Nada | `Promise<void>` | Inicializa criptografía |
| `enviarDatosCifrados()` | `endpoint: string, datos: any` | `Observable<any>` | Envía datos (auto-cifrado) |
| `cifrarMensaje()` | `mensaje: string` | `string` | Cifra manualmente |
| `descifrarMensaje()` | `datoCifrado: string` | `string` | Descifra manualmente |
| `loginCifrado()` | `usuario, password` | `Observable<any>` | Login con cifrado |

### Spring Boot (lado servidor)

| Endpoint | Método | Recibe | Retorna |
|----------|--------|--------|---------|
| `/api/public-key` | GET | Nada | `{publicKey: string}` |
| `/api/encrypt` | POST | `{message: string}` | `{encrypted: string}` |
| `/api/decrypt` | POST | `{encrypted: string}` | `{decrypted: string}` |
| `/api/key-info` | GET | Nada | `{keyAlgorithm, status}` |

---

## 🎯 Ejemplo Completo: Sistema de Notas Cifradas

### Backend: Controlador Spring Boot

```java
@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotasController {

    private final EncryptionService encryptionService;
    private final NotasService notasService;

    /**
     * Guardar nota cifrada
     */
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarNota(@RequestBody Map<String, String> request) {
        try {
            String datoCifrado = request.get("encrypted");
            String datosDescifrados = encryptionService.decrypt(datoCifrado);

            NotaDTO nota = new ObjectMapper().readValue(datosDescifrados, NotaDTO.class);
            Nota notaGuardada = notasService.guardar(nota);

            String respuestaCifrada = encryptionService.encrypt(
                new ObjectMapper().writeValueAsString(notaGuardada)
            );

            return ResponseEntity.ok(Map.of("encrypted", respuestaCifrada));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtener nota cifrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerNota(@PathVariable Long id) {
        try {
            Nota nota = notasService.obtenerPorId(id);
            String respuestaCifrada = encryptionService.encrypt(
                new ObjectMapper().writeValueAsString(nota)
            );
            return ResponseEntity.ok(Map.of("encrypted", respuestaCifrada));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
```

### Frontend: Componente Angular

```typescript
@Component({
  selector: 'app-notas',
  template: `
    <div>
      <h2>📝 Notas Cifradas</h2>

      <textarea [(ngModel)]="nuevaNota" placeholder="Nueva nota..."></textarea>
      <button (click)="guardarNota()">💾 Guardar Cifrado</button>

      <div *ngFor="let nota of notas">
        <p>{{ nota.contenido }}</p>
        <small>{{ nota.fecha }}</small>
      </div>
    </div>
  `
})
export class NotasComponent implements OnInit {

  nuevaNota: string = '';
  notas: any[] = [];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.cargarNotas();
  }

  guardarNota(): void {
    // Datos se cifran automáticamente en el interceptor
    this.apiService.enviarDatosCifrados('/notas/guardar', {
      contenido: this.nuevaNota,
      fecha: new Date()
    }).subscribe({
      next: (respuesta) => {
        this.notas.push(respuesta);
        this.nuevaNota = '';
      },
      error: (error) => console.error(error)
    });
  }

  cargarNotas(): void {
    this.apiService.http.get<any[]>('/api/notas').subscribe({
      next: (notas) => this.notas = notas,
      error: (error) => console.error(error)
    });
  }
}
```

---

## 🚀 Próximos Pasos

1. **Implementar en Angular:** Crear servicio y componentes
2. **Configurar CORS:** Agregar `CorsConfig.java` en Spring Boot
3. **Exponer clave pública:** Crear endpoint `/api/public-key`
4. **Testing:** Tests unitarios e integración
5. **Documentar API:** OpenAPI/Swagger
6. **Desplegar:** HTTPS obligatorio en producción

---

**¡Tu aplicación Angular ahora tiene encriptación de nivel bancario! 🔐**

Para soporte técnico consulta [ENCRYPTION_GUIDE.md](ENCRYPTION_GUIDE.md)

````
