# Lenguaje GO

## Configuración WASM

### Crear carpeta .vscode
### crear archivo settings.json
````
{
  "go.toolsEnvVars": {
    "GOOS": "js",
    "GOARCH": "wasm"
  }
}
````


## Compilación WASM

### Windows
````
 $env:GOOS="js"; $env:GOARCH="wasm"; go build -o main.wasm main.go
````

### Linux
````
````
