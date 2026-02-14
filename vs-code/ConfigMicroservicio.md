# Configuracion de WorkSpace

## 1. Crear Archivo ".code-workspace" en la ruta raiz

### Contendio archivo .code-workspace
```
{
  "folders": [
    {
      "path": "spring-boot-compras",
      "name": "ackend API de compras"
    },
    {
      "path": "spring-boot-ventas",
      "name": "Backend API de ventas"
    }
  ]
}

```

## 2. Crear la carpeta ".vscode"

## 3. Crear archivo "launch.json"

```
{
  "configurations": [
    
    {
      "type": "java",
      "name": "Spring Boot-SpringBootComprasApplication<spring-boot-compras>",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "mainClass": "com.example.spring_boot_compras.SpringBootComprasApplication",
      "projectName": "spring-boot-compras",
      "args": "",
      "envFile": "${workspaceFolder}/.env"
    },
    {
      "type": "java",
      "name": "Spring Boot-SpringBootVentasApplication<spring-boot-ventas>",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "mainClass": "com.example.spring_boot_ventas.SpringBootVentasApplication",
      "projectName": "spring-boot-ventas",
      "args": "",
      "envFile": "${workspaceFolder}/.env"
    }
  ]
}
```
