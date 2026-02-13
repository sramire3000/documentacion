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

## Crear Modulo
```
go mod init test
```


## Compilación WASM

### Windows
````
 $env:GOOS="js"; $env:GOARCH="wasm"; go build -o main.wasm main.go
````

### Linux
````
GOOS=js GOARCH=wasm go build -o test.wasm
````
