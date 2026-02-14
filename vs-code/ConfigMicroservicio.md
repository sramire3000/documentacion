# Configuracion de WorkSpace

## 1. Crear Archivo ".code-workspace" en la ruta raiz

### Contendio archivo ".code-workspace"
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
      "javaExec": "/usr/lib/jvm/java-17-openjdk-amd64/bin/java",
      "mainClass": "com.example.spring_boot_compras.SpringBootComprasApplication",
      "projectName": "spring-boot-compras",
      "vmArgs": "-Xms128m -Xmx384m -XX:MaxMetaspaceSize=96m -XX:MaxDirectMemorySize=32m -XX:+UseG1GC -XX:+UseStringDeduplication",
      "args": "",
      "envFile": "${workspaceFolder}/.env"
    },
    {
      "type": "java",
      "name": "Spring Boot-SpringBootVentasApplication<spring-boot-ventas>",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "javaExec": "/usr/lib/jvm/java-17-openjdk-amd64/bin/java",
      "mainClass": "com.example.spring_boot_ventas.SpringBootVentasApplication",
      "projectName": "spring-boot-ventas",
      "vmArgs": "-Xms128m -Xmx384m -XX:MaxMetaspaceSize=96m -XX:MaxDirectMemorySize=32m -XX:+UseG1GC -XX:+UseStringDeduplication",
      "args": "",
      "envFile": "${workspaceFolder}/.env"
    }
  ]
}
```

## 4. En cada proyecto crear la carpeta ".vscode"

## 5. Crear archivo "tasks.json" en la carpeta ".vscode"
```
{
  "version": "2.0.0",
  "tasks": [
    {
      "label": "Run Compras (dev)",
      "type": "shell",
      "command": "./mvnw",
      "args": [
        "spring-boot:run"
      ],
      "options": {
        "cwd": "${workspaceFolder}",
        "env": {
          "JAVA_TOOL_OPTIONS": "-Xms128m -Xmx384m -XX:MaxMetaspaceSize=96m -XX:MaxDirectMemorySize=32m -XX:+UseG1GC -XX:+UseStringDeduplication"
        }
      },
      "group": "build",
      "isBackground": true,
      "problemMatcher": []
    }
  ]
}
```

## 6. Crear archivo "settings.json" en la carpeta ".vscode"
```
{
  "java.configuration.runtimes": [
    {
      "name": "JavaSE-17",
      "path": "${env:JAVA_HOME}",
      "default": true
    }
  ],
  "java.jdt.ls.java.home": "${env:JAVA_HOME}",
  "maven.terminal.useJavaHome": true,
  "java.debug.settings.vmArgs": "-Xms128m -Xmx384m -XX:MaxMetaspaceSize=96m -XX:MaxDirectMemorySize=32m -XX:+UseG1GC -XX:+UseStringDeduplication"
}
```
