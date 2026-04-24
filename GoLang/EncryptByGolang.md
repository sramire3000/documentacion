# Encriptacón con Golang

## archivo "NCRIPTACION_GOLANG.md"
````
# ENCRIPTACION GOLANG

Si, hay posibilidad de llevar este proyecto a Go.

Pero hay que separar dos escenarios porque no son equivalentes:

1. Usar Go para consumir el backend actual de Spring Boot.
2. Reescribir el backend completo en Go.

## Conclusión corta

Si necesitas algo funcional hoy, Go puede integrarse sin problema consumiendo estos endpoints actuales:

- `POST /api/encrypt`
- `POST /api/decrypt`
- `POST /api/encrypt-json`
- `GET /api/key-info`

Si lo que quieres es migrar toda la implementación criptográfica a Go, tambien es posible, pero hay una limitación importante:

- El proyecto Java actual usa `ECIES` de Bouncy Castle sobre `secp256r1`.
- Ese formato no tiene una equivalencia simple y estandarizada en la libreria estandar de Go.
- Por eso, una migración a Go puede mantener el mismo contrato HTTP, pero no necesariamente la compatibilidad binaria exacta con los ciphertexts generados hoy por Java.

La ruta más sana en Go suele ser redefinir el cifrado híbrido con un formato portable y explícito, por ejemplo:

- ECDH sobre `P-256`
- derivación de clave con `SHA-256`
- cifrado simétrico con `AES-256-GCM`

## Opción A: Go como cliente del backend actual

Esta opción es totalmente viable hoy y no exige cambiar nada en Spring Boot.

### Qué hace

- Envía texto plano o JSON al backend.
- El backend cifra y descifra usando sus llaves configuradas.
- Go solo consume la API.

### Código Go de ejemplo

```go
package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"time"
)

type EncryptRequest struct {
	Message string `json:"message"`
}

type DecryptRequest struct {
	Encrypted string `json:"encrypted"`
}

type EncryptResponse struct {
	Original  string `json:"original"`
	Encrypted string `json:"encrypted"`
	Algorithm string `json:"algorithm"`
	Status    string `json:"status"`
}

type DecryptResponse struct {
	Decrypted string `json:"decrypted"`
	Algorithm string `json:"algorithm"`
	Status    string `json:"status"`
}

type KeyInfoResponse struct {
	KeyName        string `json:"keyName"`
	KeyAlgorithm   string `json:"keyAlgorithm"`
	DataEncryption string `json:"dataEncryption"`
	Status         string `json:"status"`
	Security       string `json:"security"`
}

type APIClient struct {
	BaseURL string
	Client  *http.Client
}

func NewAPIClient(baseURL string) *APIClient {
	return &APIClient{
		BaseURL: baseURL,
		Client: &http.Client{
			Timeout: 15 * time.Second,
		},
	}
}

func (c *APIClient) GetKeyInfo() (*KeyInfoResponse, error) {
	req, err := http.NewRequest(http.MethodGet, c.BaseURL+"/api/key-info", nil)
	if err != nil {
		return nil, err
	}

	resp, err := c.Client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode < 200 || resp.StatusCode >= 300 {
		body, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("error HTTP %d: %s", resp.StatusCode, string(body))
	}

	var result KeyInfoResponse
	if err := json.NewDecoder(resp.Body).Decode(&result); err != nil {
		return nil, err
	}

	return &result, nil
}

func (c *APIClient) Encrypt(message string) (*EncryptResponse, error) {
	payload := EncryptRequest{Message: message}
	body, err := json.Marshal(payload)
	if err != nil {
		return nil, err
	}

	req, err := http.NewRequest(http.MethodPost, c.BaseURL+"/api/encrypt", bytes.NewReader(body))
	if err != nil {
		return nil, err
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := c.Client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode < 200 || resp.StatusCode >= 300 {
		responseBody, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("error HTTP %d: %s", resp.StatusCode, string(responseBody))
	}

	var result EncryptResponse
	if err := json.NewDecoder(resp.Body).Decode(&result); err != nil {
		return nil, err
	}

	return &result, nil
}

func (c *APIClient) EncryptJSON(data any) (*EncryptResponse, error) {
	jsonPayload, err := json.Marshal(data)
	if err != nil {
		return nil, err
	}

	payload := EncryptRequest{Message: string(jsonPayload)}
	body, err := json.Marshal(payload)
	if err != nil {
		return nil, err
	}

	req, err := http.NewRequest(http.MethodPost, c.BaseURL+"/api/encrypt-json", bytes.NewReader(body))
	if err != nil {
		return nil, err
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := c.Client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode < 200 || resp.StatusCode >= 300 {
		responseBody, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("error HTTP %d: %s", resp.StatusCode, string(responseBody))
	}

	var raw map[string]any
	if err := json.NewDecoder(resp.Body).Decode(&raw); err != nil {
		return nil, err
	}

	result := &EncryptResponse{
		Original:  string(jsonPayload),
		Encrypted: stringValue(raw["encrypted"]),
		Algorithm: stringValue(raw["algorithm"]),
		Status:    stringValue(raw["status"]),
	}

	return result, nil
}

func (c *APIClient) Decrypt(encrypted string) (*DecryptResponse, error) {
	payload := DecryptRequest{Encrypted: encrypted}
	body, err := json.Marshal(payload)
	if err != nil {
		return nil, err
	}

	req, err := http.NewRequest(http.MethodPost, c.BaseURL+"/api/decrypt", bytes.NewReader(body))
	if err != nil {
		return nil, err
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := c.Client.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode < 200 || resp.StatusCode >= 300 {
		responseBody, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("error HTTP %d: %s", resp.StatusCode, string(responseBody))
	}

	var result DecryptResponse
	if err := json.NewDecoder(resp.Body).Decode(&result); err != nil {
		return nil, err
	}

	return &result, nil
}

func stringValue(value any) string {
	if value == nil {
		return ""
	}
	if text, ok := value.(string); ok {
		return text
	}
	return fmt.Sprintf("%v", value)
}

func main() {
	client := NewAPIClient("http://localhost:8080")

	keyInfo, err := client.GetKeyInfo()
	if err != nil {
		panic(err)
	}
	fmt.Printf("Key info: %+v\n", *keyInfo)

	encryptResp, err := client.Encrypt("Hola desde Go")
	if err != nil {
		panic(err)
	}
	fmt.Printf("Cifrado: %s\n", encryptResp.Encrypted)

	decryptResp, err := client.Decrypt(encryptResp.Encrypted)
	if err != nil {
		panic(err)
	}
	fmt.Printf("Descifrado: %s\n", decryptResp.Decrypted)

	jsonResp, err := client.EncryptJSON(map[string]any{
		"usuario":  "admin",
		"password": "secreta123",
		"canal":    "golang",
	})
	if err != nil {
		panic(err)
	}
	fmt.Printf("JSON cifrado: %s\n", jsonResp.Encrypted)
}
```

### Cuándo usar esta opción

Úsala si tu objetivo es:

- escribir un cliente o microservicio en Go
- consumir el backend actual sin reescribir la criptografía
- mantener la lógica sensible en Spring Boot

## Opción B: migrar el backend a Go

Tambien es posible, pero aqui hay que ser riguroso con la compatibilidad.

### Lo que sí puedes mantener

- los mismos endpoints HTTP
- la misma estructura JSON de request/response
- la idea de cifrado híbrido

### Lo que probablemente debes cambiar

- la implementación exacta de `ECIES` de Bouncy Castle
- el formato binario del ciphertext
- el algoritmo simétrico, idealmente a `AES-GCM`

## Propuesta de implementación en Go

La siguiente implementación usa solo librerías estándar y define un cifrado híbrido portable:

- curva `P-256`
- clave efímera por mensaje
- secreto compartido por ECDH
- derivación de clave con `SHA-256`
- cifrado con `AES-256-GCM`

Esto es adecuado para una migración real a Go, pero no es byte a byte compatible con los datos ya generados por el backend Java actual.

### Estructura del payload cifrado en esta propuesta

```text
[ 65 bytes clave pública efímera ][ 12 bytes nonce ][ N bytes ciphertext+tag ]
```

Todo se serializa luego en Base64.

### Código completo de ejemplo en Go

```go
package main

import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/ecdsa"
	"crypto/elliptic"
	"crypto/rand"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/json"
	"encoding/pem"
	"errors"
	"fmt"
	"io"
	"log"
	"math/big"
	"net/http"
	"os"
)

type EncryptRequest struct {
	Message string `json:"message"`
}

type DecryptRequest struct {
	Encrypted string `json:"encrypted"`
}

type Server struct {
	PrivateKey *ecdsa.PrivateKey
	PublicKey  *ecdsa.PublicKey
}

func main() {
	privateKey, publicKey, err := loadOrGenerateKeys()
	if err != nil {
		log.Fatal(err)
	}

	server := &Server{
		PrivateKey: privateKey,
		PublicKey:  publicKey,
	}

	http.HandleFunc("/api/encrypt", server.handleEncrypt)
	http.HandleFunc("/api/decrypt", server.handleDecrypt)
	http.HandleFunc("/api/key-info", server.handleKeyInfo)

	log.Println("Servidor Go escuchando en :8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func (s *Server) handleEncrypt(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var req EncryptRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		writeJSON(w, http.StatusBadRequest, map[string]any{"error": "JSON invalido"})
		return
	}

	if req.Message == "" {
		writeJSON(w, http.StatusBadRequest, map[string]any{"error": "El campo 'message' es obligatorio"})
		return
	}

	encrypted, err := encryptHybrid(req.Message, s.PublicKey)
	if err != nil {
		writeJSON(w, http.StatusInternalServerError, map[string]any{"error": err.Error()})
		return
	}

	writeJSON(w, http.StatusOK, map[string]any{
		"original":  req.Message,
		"encrypted": encrypted,
		"algorithm": "ECDH P-256 + AES-256-GCM",
		"status":    "Éxito",
	})
}

func (s *Server) handleDecrypt(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var req DecryptRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		writeJSON(w, http.StatusBadRequest, map[string]any{"error": "JSON invalido"})
		return
	}

	if req.Encrypted == "" {
		writeJSON(w, http.StatusBadRequest, map[string]any{"error": "El campo 'encrypted' es obligatorio"})
		return
	}

	decrypted, err := decryptHybrid(req.Encrypted, s.PrivateKey)
	if err != nil {
		writeJSON(w, http.StatusInternalServerError, map[string]any{"error": err.Error()})
		return
	}

	writeJSON(w, http.StatusOK, map[string]any{
		"decrypted": decrypted,
		"algorithm": "ECDH P-256 + AES-256-GCM",
		"status":    "Éxito",
	})
}

func (s *Server) handleKeyInfo(w http.ResponseWriter, r *http.Request) {
	writeJSON(w, http.StatusOK, map[string]any{
		"keyName":        "go-demo-key",
		"keyAlgorithm":   "ECC - P-256",
		"dataEncryption": "AES-256-GCM",
		"status":         "Configurado y listo",
		"security":       "Nivel de seguridad equivalente a 128 bits",
	})
}

func encryptHybrid(plaintext string, recipientPublicKey *ecdsa.PublicKey) (string, error) {
	ephemeralPrivateKey, err := ecdsa.GenerateKey(elliptic.P256(), rand.Reader)
	if err != nil {
		return "", err
	}

	sharedSecret, err := deriveSharedSecret(ephemeralPrivateKey, recipientPublicKey)
	if err != nil {
		return "", err
	}

	aesKey := sha256.Sum256(sharedSecret)
	block, err := aes.NewCipher(aesKey[:])
	if err != nil {
		return "", err
	}

	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return "", err
	}

	nonce := make([]byte, gcm.NonceSize())
	if _, err := io.ReadFull(rand.Reader, nonce); err != nil {
		return "", err
	}

	ciphertext := gcm.Seal(nil, nonce, []byte(plaintext), nil)
	ephemeralPublicKey := elliptic.Marshal(elliptic.P256(), ephemeralPrivateKey.PublicKey.X, ephemeralPrivateKey.PublicKey.Y)

	payload := make([]byte, 0, len(ephemeralPublicKey)+len(nonce)+len(ciphertext))
	payload = append(payload, ephemeralPublicKey...)
	payload = append(payload, nonce...)
	payload = append(payload, ciphertext...)

	return base64.StdEncoding.EncodeToString(payload), nil
}

func decryptHybrid(encryptedBase64 string, recipientPrivateKey *ecdsa.PrivateKey) (string, error) {
	payload, err := base64.StdEncoding.DecodeString(encryptedBase64)
	if err != nil {
		return "", err
	}

	const ephemeralPublicKeyLength = 65
	const nonceLength = 12

	if len(payload) <= ephemeralPublicKeyLength+nonceLength {
		return "", errors.New("payload cifrado invalido")
	}

	ephemeralPublicKeyBytes := payload[:ephemeralPublicKeyLength]
	nonce := payload[ephemeralPublicKeyLength : ephemeralPublicKeyLength+nonceLength]
	ciphertext := payload[ephemeralPublicKeyLength+nonceLength:]

	x, y := elliptic.Unmarshal(elliptic.P256(), ephemeralPublicKeyBytes)
	if x == nil || y == nil {
		return "", errors.New("clave publica efimera invalida")
	}

	ephemeralPublicKey := &ecdsa.PublicKey{
		Curve: elliptic.P256(),
		X:     x,
		Y:     y,
	}

	sharedSecret, err := deriveSharedSecret(recipientPrivateKey, ephemeralPublicKey)
	if err != nil {
		return "", err
	}

	aesKey := sha256.Sum256(sharedSecret)
	block, err := aes.NewCipher(aesKey[:])
	if err != nil {
		return "", err
	}

	gcm, err := cipher.NewGCM(block)
	if err != nil {
		return "", err
	}

	plaintext, err := gcm.Open(nil, nonce, ciphertext, nil)
	if err != nil {
		return "", err
	}

	return string(plaintext), nil
}

func deriveSharedSecret(privateKey *ecdsa.PrivateKey, publicKey *ecdsa.PublicKey) ([]byte, error) {
	if privateKey == nil || publicKey == nil {
		return nil, errors.New("llave privada o publica no disponible")
	}

	x, _ := publicKey.Curve.ScalarMult(publicKey.X, publicKey.Y, privateKey.D.Bytes())
	if x == nil {
		return nil, errors.New("no se pudo derivar el secreto compartido")
	}

	shared := x.Bytes()
	if len(shared) == 0 {
		return nil, errors.New("secreto compartido vacio")
	}

	return shared, nil
}

func loadOrGenerateKeys() (*ecdsa.PrivateKey, *ecdsa.PublicKey, error) {
	privateKeyPath := os.Getenv("ECC_PRIVATE_KEY_PEM")
	publicKeyPath := os.Getenv("ECC_PUBLIC_KEY_PEM")

	if privateKeyPath != "" && publicKeyPath != "" {
		privateKey, err := loadPrivateKeyPEM(privateKeyPath)
		if err != nil {
			return nil, nil, err
		}

		publicKey, err := loadPublicKeyPEM(publicKeyPath)
		if err != nil {
			return nil, nil, err
		}

		return privateKey, publicKey, nil
	}

	privateKey, err := ecdsa.GenerateKey(elliptic.P256(), rand.Reader)
	if err != nil {
		return nil, nil, err
	}

	return privateKey, &privateKey.PublicKey, nil
}

func loadPrivateKeyPEM(path string) (*ecdsa.PrivateKey, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}

	block, _ := pem.Decode(data)
	if block == nil {
		return nil, errors.New("PEM privado invalido")
	}

	key, err := x509.ParsePKCS8PrivateKey(block.Bytes)
	if err != nil {
		return nil, err
	}

	privateKey, ok := key.(*ecdsa.PrivateKey)
	if !ok {
		return nil, errors.New("la llave privada no es ECDSA")
	}

	return privateKey, nil
}

func loadPublicKeyPEM(path string) (*ecdsa.PublicKey, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}

	block, _ := pem.Decode(data)
	if block == nil {
		return nil, errors.New("PEM publico invalido")
	}

	key, err := x509.ParsePKIXPublicKey(block.Bytes)
	if err != nil {
		return nil, err
	}

	publicKey, ok := key.(*ecdsa.PublicKey)
	if !ok {
		return nil, errors.New("la llave publica no es ECDSA")
	}

	return publicKey, nil
}

func writeJSON(w http.ResponseWriter, status int, payload map[string]any) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	_ = json.NewEncoder(w).Encode(payload)
}
```

## Diferencias clave con el backend Java actual

El backend Java de este repo hace esto:

- genera una clave AES aleatoria
- cifra el mensaje con `AES/CBC/PKCS5Padding`
- cifra la clave AES con `ECIES` de Bouncy Castle
- arma el payload como:

```text
[ 4 bytes longitud ][ clave AES cifrada con ECIES ][ datos cifrados con AES ]
```

La implementación Go propuesta arriba hace esto:

- genera una clave efímera ECC por mensaje
- deriva una clave simétrica desde ECDH
- cifra con `AES-256-GCM`
- arma el payload como:

```text
[ clave publica efimera ][ nonce ][ ciphertext+tag ]
```

Eso significa que:

- no puedes tomar un ciphertext del Java actual y esperar que el Go de ejemplo lo descifre
- no puedes mezclar ambas implementaciones sin definir interoperabilidad exacta

## Cuándo sí conviene reescribir a Go

Conviene si buscas:

- un binario más simple de desplegar
- menor consumo de memoria que una app Spring Boot
- una API de cifrado más portable entre servicios
- un stack homogéneo en backend y tooling

## Cuándo no conviene hacerlo aún

No conviene si necesitas inmediatamente:

- compatibilidad exacta con ciphertexts ya emitidos por Java
- conservar `ECIES` exactamente como Bouncy Castle lo está serializando
- mover solo unas pocas llamadas HTTP, porque ahi es mejor dejar Java y consumirlo desde Go

## Recomendación práctica

La decisión correcta depende de tu objetivo:

1. Si solo quieres usar Go con este sistema, usa la Opción A.
2. Si quieres sustituir Spring Boot por Go, usa la Opción B y redefine el esquema criptográfico de forma explícita.
3. Si exiges compatibilidad exacta Java <-> Go a nivel de ciphertext, hay que diseñar y testear esa compatibilidad como una tarea separada.

## Compatibilidad exacta Java <-> Go

Si el objetivo es que Java y Go puedan cifrar y descifrar exactamente el mismo payload, el trabajo correcto no es "traducir el código" sino congelar primero el contrato criptográfico.

## Qué significa realmente "dar los mismos resultados"

Si los dos microservicios, Spring Boot y Go, deben dar los mismos resultados, hay que definir ese requisito con precisión.

Con el esquema actual, el requisito correcto es este:

1. Ambos deben aceptar el mismo contrato HTTP.
2. Ambos deben descifrar correctamente los ciphertexts emitidos por el otro.
3. Ambos deben devolver el mismo plaintext para la misma entrada descifrada.
4. Ambos deben exponer el mismo esquema JSON de respuesta.
5. Ambos deben reportar errores equivalentes para payloads inválidos.

Lo que no debes exigir como criterio de éxito es esto:

1. Que ambos devuelvan el mismo valor exacto en `encrypted` para el mismo plaintext.

Eso no es un objetivo válido con el diseño actual porque el cifrado incorpora aleatoriedad criptográfica.

### Por qué el campo `encrypted` no será idéntico entre ejecuciones sanas

En Java hoy existe aleatoriedad en dos capas:

- `encryptAES()` genera un IV aleatorio de 16 bytes.
- `encryptECC()` inicializa `ECIES` con `new SecureRandom()`.

Por eso, incluso dentro del mismo microservicio Spring Boot, dos llamadas consecutivas con el mismo plaintext pueden y deben producir Base64 distintos.

Si obligas a que Spring Boot y Go generen exactamente el mismo ciphertext para el mismo mensaje, tendrías que introducir determinismo artificial en una parte del cifrado. Eso degrada el diseño y no es un requisito correcto para producción.

### Contrato interoperable correcto

Si el objetivo es interoperabilidad real entre microservicios, este es el contrato que sí debes congelar:

```text
Mismo plaintext de entrada
-> ciphertext potencialmente distinto
-> descifrado cruzado exitoso
-> mismo plaintext de salida
-> mismo schema JSON
```

En otras palabras:

- igualdad semántica: sí
- igualdad binaria del ciphertext: no

### Casos donde sí puedes pedir igualdad exacta

Solo en pruebas controladas con vectores deterministas, por ejemplo si introduces:

- IV fijo
- aleatoriedad ECIES controlada
- llaves fijas

Eso sirve para test vectors, pero no debe ser el comportamiento normal del microservicio.

### Estado real del contrato actual en Java

Hoy `CryptoUtils.encryptHybrid()` hace esto:

1. Genera una clave AES-256 aleatoria.
2. Cifra el plaintext con `AES/CBC/PKCS5Padding`.
3. Cifra la clave AES con `ECIES` de Bouncy Castle usando una clave pública `EC` sobre `secp256r1`.
4. Construye un payload binario con esta estructura:

```text
[ 4 bytes big-endian ][ encryptedAESKey ][ iv + aesCiphertext ]
```

Detalle del formato:

- `4 bytes big-endian`: longitud de `encryptedAESKey`
- `encryptedAESKey`: salida directa de `Cipher.getInstance("ECIES", "BC")`
- `iv + aesCiphertext`: salida de `encryptAES()`, donde los primeros 16 bytes son el IV de AES-CBC

### Qué significa para Go

Para que Go sea compatible exactamente con Java, Go debe poder:

1. Parsear llaves públicas X.509 `EC` y llaves privadas PKCS#8 exportadas por Java.
2. Reproducir el comportamiento de `ECIES` de Bouncy Castle sobre `secp256r1`.
3. Cifrar y descifrar `AES/CBC/PKCS5Padding` con IV de 16 bytes prefijado al ciphertext.
4. Construir y leer el payload binario con el prefijo de longitud en big-endian.

El punto difícil no es AES-CBC ni el prefijo binario. El punto difícil es `ECIES` exactamente como lo serializa y procesa Bouncy Castle.

### Riesgo principal

Aunque dos librerías digan soportar `ECIES`, eso no garantiza compatibilidad binaria entre implementaciones.

La compatibilidad depende de detalles como:

- KDF usada internamente
- MAC usada por la implementación
- encoding del ephemeral public key
- orden y layout binario del ciphertext ECIES
- parámetros de `IESParameterSpec`

En este proyecto Java, `IESParameterSpec(null, null, 128)` deja parte del comportamiento delegado a defaults de Bouncy Castle. Eso hace todavía más importante fijar tests de contrato antes de implementar Go.

## Estrategia recomendada para compatibilidad

La estrategia correcta tiene dos fases.

### Fase 1: congelar el contrato Java actual

Antes de escribir Go, hay que fijar con tests estas invariantes:

- `encryptHybrid()` siempre serializa `[4 bytes length][encryptedAESKey][encryptedData]`
- `encryptedData` siempre es `[16 bytes IV][AES-CBC ciphertext]`
- `decryptHybrid()` puede descifrar un payload reconstruido manualmente a partir de `encryptECC()` y `encryptAES()`
- las llaves exportadas en Base64 desde Java se pueden reimportar sin pérdida

Ese paso deja una especificación verificable del lado Java.

### Fase 2: implementar un adaptador Go contra ese contrato

La implementación Go debe atacarse por capas:

1. parseo de llaves Java en Go
2. parseo del envelope binario híbrido
3. descifrado AES-CBC del segmento simétrico
4. soporte ECIES compatible con Bouncy Castle
5. tests cruzados Java->Go y Go->Java con vectores fijos

### Qué pasa si ECIES exacto resulta inviable en Go

Si no aparece una librería Go realmente compatible con el `ECIES` de Bouncy Castle usado aquí, hay dos alternativas sanas:

1. Mantener Java como servicio de cifrado y dejar Go como cliente HTTP.
2. Redefinir el esquema criptográfico en ambos lados con un formato interoperable explícito, por ejemplo `ECDH + HKDF + AES-GCM`.

## Vectores y tests cruzados que debes tener

Si quieres compatibilidad real, los tests mínimos deben cubrir esto:

### Java -> Go

- Java cifra un plaintext conocido con un keypair fijo.
- Go recibe el Base64 generado por Java.
- Go parsea el prefijo de longitud.
- Go separa `encryptedAESKey` y `encryptedData`.
- Go descifra y recupera exactamente el plaintext original.

### Go -> Java

- Go cifra un plaintext conocido con la clave pública Java.
- Java recibe el Base64 generado por Go.
- Java llama `decryptHybrid()`.
- Java recupera exactamente el plaintext original.

### Casos de borde

- mensaje vacío
- JSON con UTF-8 y caracteres especiales
- payload manipulado en un byte
- llave privada incorrecta
- Base64 inválido
- prefijo de longitud corrupto

## Formato de fixture recomendado

Para no depender solo de ejecución en vivo, conviene guardar fixtures así:

```json
{
	"case": "basic-text",
	"plaintext": "Hola Java Go",
	"publicKeyBase64": "...",
	"privateKeyBase64": "...",
	"encryptedBase64": "..."
}
```

Importante: un fixture completo con `encryptedBase64` solo será estable si controlas la aleatoriedad. Como el esquema actual usa IV aleatorio y ECIES con aleatoriedad interna, los tests más realistas para el contrato actual deben validar round-trip y estructura, no igualdad exacta de ciphertext entre ejecuciones.

## Recomendación concreta para este repo

Si yo tuviera que ejecutar esta migración aquí, haría esto en orden:

1. Congelar ahora el envelope binario del Java actual con tests.
2. Exponer, si hace falta, un endpoint o utilidad para exportar claves y fixtures de prueba.
3. Intentar una PoC en Go solo para parseo y AES.
4. Evaluar si existe una implementación ECIES realmente compatible con Bouncy Castle.
5. Si no existe o es frágil, abandonar compatibilidad binaria exacta y migrar a un esquema nuevo documentado para ambos lados.

Ese orden minimiza trabajo desperdiciado.

## Siguiente paso recomendado

Si decides migrar de verdad a Go, mi recomendación es esta:

1. Congelar primero el contrato HTTP.
2. Definir un formato de payload criptográfico documentado.
3. Implementar tests cruzados Java/Go con vectores conocidos.
4. Solo después reemplazar el backend Java.

Ese orden evita que la migración parezca correcta mientras en realidad rompe la compatibilidad criptográfica.

````
