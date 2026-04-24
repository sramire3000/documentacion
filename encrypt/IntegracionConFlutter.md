# Integración con Flutter

## Archivo "FLUTTER_INTEGRATION.md"
````
# FLUTTER_INTEGRATION

Guia practica para integrar este backend Spring Boot con una app Flutter.

Esta documentacion esta basada en el comportamiento real del proyecto actual:

- El backend ya expone `POST /api/encrypt`
- El backend ya expone `POST /api/decrypt`
- El backend ya expone `GET /api/key-info`
- El backend no expone todavia `GET /api/public-key`
- El backend cifra usando un esquema hibrido propio: `ECIES (Bouncy Castle sobre secp256r1) + AES-256-CBC`

## Resumen rapido

Si quieres integrarlo con Flutter hoy mismo, la forma correcta es esta:

1. Flutter envia texto o JSON plano al backend.
2. Spring Boot cifra o descifra usando sus llaves privadas/publicas configuradas.
3. Flutter consume la respuesta ya procesada.

Ese flujo funciona de inmediato con el codigo actual.

Si lo que quieres es cifrar en Flutter antes de enviar al backend, eso requiere un paso adicional de arquitectura porque este proyecto usa `ECIES` con Bouncy Castle, y esa compatibilidad exacta no viene resuelta out of the box en Dart/Flutter.

## Arquitectura recomendada hoy

```text
Flutter App
   |
   | HTTP/HTTPS
   v
Spring Boot
   |- POST /api/encrypt
   |- POST /api/decrypt
   |- GET  /api/key-info
```

Flujo:

1. Flutter serializa un objeto a JSON.
2. Flutter envia ese JSON como texto al endpoint `/api/encrypt`.
3. El backend devuelve el campo `encrypted` en Base64.
4. Si necesitas recuperar el contenido original, Flutter envia ese Base64 a `/api/decrypt`.
5. El backend devuelve el campo `decrypted`.

## Prerrequisitos

- Flutter 3.22+
- Dart 3+
- Backend corriendo en `http://localhost:8080`
- Llaves ECC ya configuradas en `src/main/resources/application.properties`

Antes de conectar Flutter, valida el backend:

```bash
mvn spring-boot:run
```

Y prueba que responde:

- `GET http://localhost:8080/api/key-info`
- `POST http://localhost:8080/api/encrypt`
- `POST http://localhost:8080/api/decrypt`

## Opcion 1: Integracion inmediata con Flutter

Esta es la opcion recomendada para este repositorio tal como esta hoy.

### 1. Crear proyecto Flutter

```bash
flutter create secure_demo_app
cd secure_demo_app
```

### 2. Agregar dependencias

En `pubspec.yaml` agrega:

```yaml
dependencies:
  flutter:
    sdk: flutter
  http: ^1.2.1
```

Luego ejecuta:

```bash
flutter pub get
```

### 3. Crear modelos basicos

Crea `lib/models/encryption_models.dart`:

```dart
class EncryptRequest {
  final String message;

  EncryptRequest({required this.message});

  Map<String, dynamic> toJson() => {
        'message': message,
      };
}

class EncryptResponse {
  final String original;
  final String encrypted;
  final String algorithm;
  final String status;

  EncryptResponse({
    required this.original,
    required this.encrypted,
    required this.algorithm,
    required this.status,
  });

  factory EncryptResponse.fromJson(Map<String, dynamic> json) {
    return EncryptResponse(
      original: json['original'] as String? ?? '',
      encrypted: json['encrypted'] as String? ?? '',
      algorithm: json['algorithm'] as String? ?? '',
      status: json['status'] as String? ?? '',
    );
  }
}

class DecryptResponse {
  final String decrypted;
  final String algorithm;
  final String status;

  DecryptResponse({
    required this.decrypted,
    required this.algorithm,
    required this.status,
  });

  factory DecryptResponse.fromJson(Map<String, dynamic> json) {
    return DecryptResponse(
      decrypted: json['decrypted'] as String? ?? '',
      algorithm: json['algorithm'] as String? ?? '',
      status: json['status'] as String? ?? '',
    );
  }
}

class KeyInfoResponse {
  final String keyName;
  final String keyAlgorithm;
  final String dataEncryption;
  final String status;
  final String security;

  KeyInfoResponse({
    required this.keyName,
    required this.keyAlgorithm,
    required this.dataEncryption,
    required this.status,
    required this.security,
  });

  factory KeyInfoResponse.fromJson(Map<String, dynamic> json) {
    return KeyInfoResponse(
      keyName: json['keyName'] as String? ?? '',
      keyAlgorithm: json['keyAlgorithm'] as String? ?? '',
      dataEncryption: json['dataEncryption'] as String? ?? '',
      status: json['status'] as String? ?? '',
      security: json['security'] as String? ?? '',
    );
  }
}
```

### 4. Crear servicio HTTP

Crea `lib/services/encryption_api_service.dart`:

```dart
import 'dart:convert';

import 'package:http/http.dart' as http;

import '../models/encryption_models.dart';

class EncryptionApiService {
  EncryptionApiService({required this.baseUrl});

  final String baseUrl;

  Uri _uri(String path) => Uri.parse('$baseUrl$path');

  Future<KeyInfoResponse> getKeyInfo() async {
    final response = await http.get(
      _uri('/api/key-info'),
      headers: {
        'Content-Type': 'application/json',
      },
    );

    _ensureSuccess(response);
    return KeyInfoResponse.fromJson(jsonDecode(response.body) as Map<String, dynamic>);
  }

  Future<EncryptResponse> encryptText(String plainText) async {
    final response = await http.post(
      _uri('/api/encrypt'),
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        'message': plainText,
      }),
    );

    _ensureSuccess(response);
    return EncryptResponse.fromJson(jsonDecode(response.body) as Map<String, dynamic>);
  }

  Future<EncryptResponse> encryptJson(Map<String, dynamic> data) async {
    final response = await http.post(
      _uri('/api/encrypt-json'),
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        'message': jsonEncode(data),
      }),
    );

    _ensureSuccess(response);
    return EncryptResponse.fromJson({
      'original': jsonEncode(data),
      ...jsonDecode(response.body) as Map<String, dynamic>,
    });
  }

  Future<DecryptResponse> decryptText(String encryptedBase64) async {
    final response = await http.post(
      _uri('/api/decrypt'),
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        'encrypted': encryptedBase64,
      }),
    );

    _ensureSuccess(response);
    return DecryptResponse.fromJson(jsonDecode(response.body) as Map<String, dynamic>);
  }

  void _ensureSuccess(http.Response response) {
    if (response.statusCode >= 200 && response.statusCode < 300) {
      return;
    }

    final body = response.body.isEmpty ? 'Sin detalle' : response.body;
    throw Exception('HTTP ${response.statusCode}: $body');
  }
}
```

### 5. Ejemplo de uso en una pantalla Flutter

Crea `lib/main.dart`:

```dart
import 'dart:convert';

import 'package:flutter/material.dart';

import 'services/encryption_api_service.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Demo Encrypt',
      home: const EncryptionDemoPage(),
    );
  }
}

class EncryptionDemoPage extends StatefulWidget {
  const EncryptionDemoPage({super.key});

  @override
  State<EncryptionDemoPage> createState() => _EncryptionDemoPageState();
}

class _EncryptionDemoPageState extends State<EncryptionDemoPage> {
  final _controller = TextEditingController();
  final _service = EncryptionApiService(baseUrl: 'http://localhost:8080');

  String keyInfo = '';
  String encrypted = '';
  String decrypted = '';
  String error = '';
  bool loading = false;

  Future<void> loadKeyInfo() async {
    setState(() {
      loading = true;
      error = '';
    });

    try {
      final response = await _service.getKeyInfo();
      setState(() {
        keyInfo = jsonEncode({
          'keyName': response.keyName,
          'keyAlgorithm': response.keyAlgorithm,
          'dataEncryption': response.dataEncryption,
          'status': response.status,
          'security': response.security,
        });
      });
    } catch (e) {
      setState(() {
        error = e.toString();
      });
    } finally {
      setState(() {
        loading = false;
      });
    }
  }

  Future<void> encrypt() async {
    setState(() {
      loading = true;
      error = '';
      decrypted = '';
    });

    try {
      final response = await _service.encryptText(_controller.text);
      setState(() {
        encrypted = response.encrypted;
      });
    } catch (e) {
      setState(() {
        error = e.toString();
      });
    } finally {
      setState(() {
        loading = false;
      });
    }
  }

  Future<void> decrypt() async {
    if (encrypted.isEmpty) return;

    setState(() {
      loading = true;
      error = '';
    });

    try {
      final response = await _service.decryptText(encrypted);
      setState(() {
        decrypted = response.decrypted;
      });
    } catch (e) {
      setState(() {
        error = e.toString();
      });
    } finally {
      setState(() {
        loading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Spring Boot + Flutter')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: ListView(
          children: [
            TextField(
              controller: _controller,
              decoration: const InputDecoration(
                labelText: 'Texto a cifrar',
                border: OutlineInputBorder(),
              ),
              minLines: 3,
              maxLines: 5,
            ),
            const SizedBox(height: 12),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: [
                ElevatedButton(
                  onPressed: loading ? null : loadKeyInfo,
                  child: const Text('Ver key-info'),
                ),
                ElevatedButton(
                  onPressed: loading ? null : encrypt,
                  child: const Text('Cifrar'),
                ),
                ElevatedButton(
                  onPressed: loading ? null : decrypt,
                  child: const Text('Descifrar'),
                ),
              ],
            ),
            const SizedBox(height: 16),
            if (loading) const LinearProgressIndicator(),
            if (keyInfo.isNotEmpty) ...[
              const Text('Key Info'),
              SelectableText(keyInfo),
              const SizedBox(height: 16),
            ],
            if (encrypted.isNotEmpty) ...[
              const Text('Encrypted'),
              SelectableText(encrypted),
              const SizedBox(height: 16),
            ],
            if (decrypted.isNotEmpty) ...[
              const Text('Decrypted'),
              SelectableText(decrypted),
              const SizedBox(height: 16),
            ],
            if (error.isNotEmpty) ...[
              const Text('Error'),
              SelectableText(error),
            ],
          ],
        ),
      ),
    );
  }
}
```

### 6. Consideraciones para Android Emulator, iOS Simulator y dispositivo real

No siempre puedes usar `http://localhost:8080` tal cual.

Usa esta referencia:

- Android Emulator: `http://10.0.2.2:8080`
- iOS Simulator: `http://localhost:8080`
- Dispositivo fisico: `http://IP_DE_TU_PC:8080`

Puedes dejarlo asi:

```dart
final service = EncryptionApiService(baseUrl: 'http://10.0.2.2:8080');
```

## Payloads reales del backend

### POST `/api/encrypt`

Request:

```json
{
  "message": "Hola desde Flutter"
}
```

Response:

```json
{
  "original": "Hola desde Flutter",
  "encrypted": "BASE64_AQUI",
  "algorithm": "ECC-4096 + AES-256-CBC",
  "status": "Éxito"
}
```

### POST `/api/decrypt`

Request:

```json
{
  "encrypted": "BASE64_AQUI"
}
```

Response:

```json
{
  "decrypted": "Hola desde Flutter",
  "algorithm": "ECC-4096 + AES-256-CBC",
  "status": "Éxito"
}
```

### GET `/api/key-info`

Response:

```json
{
  "keyName": "demo-key",
  "keyAlgorithm": "ECC - secp256r1 (256 bits)",
  "dataEncryption": "AES-256-CBC",
  "status": "Configurado y listo",
  "security": "Nivel de seguridad: 128 bits (equivalente)"
}
```

## Integrar con formularios Flutter

Ejemplo de login seguro desde Flutter, delegando el cifrado al backend:

```dart
Future<void> sendLogin() async {
  final payload = {
    'username': 'admin',
    'password': 'super-secreto',
  };

  final response = await service.encryptJson(payload);
  debugPrint(response.encrypted);
}
```

En este enfoque:

- Flutter no maneja llaves privadas ECC.
- El backend concentra la responsabilidad criptografica.
- La compatibilidad es inmediata.
- HTTPS sigue siendo obligatorio en produccion.

## Opcion 2: Cifrar en Flutter antes de enviar

Esta opcion no esta lista con el codigo actual del repositorio, pero puedes evolucionar hacia ella.

### Lo que falta en el backend

Necesitas al menos exponer la clave publica ECC:

```java
@GetMapping("/public-key")
public ResponseEntity<Map<String, String>> publicKey() {
  return ResponseEntity.ok(Map.of(
      "publicKey", cryptoProperties.getEcc().getPublicKey(),
      "curve", "secp256r1",
      "algorithm", "ECIES"
  ));
}
```

### El punto importante de compatibilidad

No copies una implementacion basada en `tweetnacl`, `x25519` o `curve25519` esperando que funcione con este backend.

Este proyecto usa:

- curva `secp256r1`
- `ECIES` de Bouncy Castle
- `AES-256-CBC` para el payload

Eso significa que Flutter debe producir exactamente el mismo formato criptografico que espera `CryptoUtils.encryptHybrid()` y `CryptoUtils.decryptHybrid()`.

### Recomendacion tecnica

Si realmente quieres cifrado del lado Flutter, tienes dos caminos:

1. Mantener el backend actual y reproducir en Dart el mismo esquema `ECIES + AES-256-CBC` compatible con Bouncy Castle.
2. Cambiar la arquitectura para usar un esquema mas facil de compartir entre Java y Flutter, por ejemplo ECDH + HKDF + AES-GCM con un formato de payload definido por ti.

La opcion 2 suele ser mas mantenible si frontend y backend deben cifrar y descifrar ambos lados.

## Configuracion CORS para Flutter Web

Si tu app corre como Flutter Web, configura CORS en Spring Boot.

Ejemplo:

```java
package com.example.demo_encrypt.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins("http://localhost:3000", "http://localhost:4200")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }
}
```

Para Flutter Mobile nativo esto normalmente no hace falta, pero para Flutter Web si.

## Buenas practicas

- Usa HTTPS siempre en produccion.
- No embebas claves privadas en Flutter.
- No guardes material criptografico sensible en `SharedPreferences`.
- Si necesitas secretos locales, usa almacenamiento seguro como `flutter_secure_storage`.
- Maneja timeouts y reintentos en tus llamadas HTTP.
- Registra errores sin imprimir datos sensibles en logs.

## Troubleshooting

### 1. `Connection refused`

Verifica que Spring Boot este corriendo en el puerto `8080`.

### 2. En Android Emulator no funciona `localhost`

Usa `10.0.2.2` en lugar de `localhost`.

### 3. `400 Bad Request` con `message` obligatorio

El backend espera exactamente este contrato:

```json
{
  "message": "texto plano"
}
```

### 4. `400 Bad Request` con `encrypted` obligatorio

El endpoint `/api/decrypt` espera:

```json
{
  "encrypted": "BASE64_GENERADO_POR_EL_BACKEND"
}
```

### 5. Error de CORS en Flutter Web

Agrega una configuracion `CorsConfig` en Spring Boot.

### 6. Error al iniciar backend con llaves

Revisa `src/main/resources/application.properties`.

Si ves errores de Base64 o de `encoded key spec`, la configuracion de llaves ECC probablemente esta mal formada.

## Recomendacion final

Para este repositorio, la integracion mas segura y directa con Flutter es:

1. Usar Flutter como cliente HTTP.
2. Delegar el cifrado y descifrado al backend actual.
3. Mantener HTTPS como capa obligatoria de transporte.
4. Solo avanzar a cifrado en cliente cuando definas una compatibilidad criptografica explicita entre Java y Dart.

Con eso evitas una integracion que parezca correcta pero falle por incompatibilidad real de algoritmos.

````
