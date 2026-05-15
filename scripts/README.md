# Scripts

## Script para limpiar FVM

Fedora fvmClean.sh
```
#!/bin/bash

# Desactiva el fvm
dart pub global deactivate fvm

# Activa el fvm
dart pub global activate fvm

echo "✅ ¡Listo!"
```

PowerShell fvmClean.ps1
```
# Desactiva el fvm
dart pub global deactivate fvm

# Activa el fvm
dart pub global activate fvm

Write-Host "✅ ¡Listo!" -ForegroundColor Green
```

Batch CMD fvmClean.bat
```
@echo off

:: Desactiva el fvm
dart pub global deactivate fvm

:: Activa el fvm
dart pub global activate fvm

echo ✅ ¡Listo!
```

## Script para crear proyecto de flutter con Fvm

Fedora fcreate.sh
```
#!/bin/bash

# --- Configuración ---
DEFAULT_VERSION="3.22.0"

# Recibir parámetros
RAW_NAME=$1
# El comando ${var//-/_} reemplaza todos los "-" por "_"
PROJECT_NAME=${RAW_NAME//-/_}
SELECTED_VERSION=${2:-$DEFAULT_VERSION}

# --- Validación ---
if [ -z "$RAW_NAME" ]; then
    echo "❌ Error: Falta el nombre del proyecto."
    exit 1
fi

# Avisar si se cambió el nombre
if [ "$RAW_NAME" != "$PROJECT_NAME" ]; then
    echo "⚠️  Nota: Se cambió el nombre a '$PROJECT_NAME' (Dart no permite guiones medios)."
fi

echo "🚀 Creando proyecto '$PROJECT_NAME' con Flutter $SELECTED_VERSION..."

# 1. Crear el proyecto
if fvm spawn $SELECTED_VERSION create "$PROJECT_NAME"; then
    cd "$PROJECT_NAME" || exit
    fvm use $SELECTED_VERSION --pin
    echo "✅ ¡Listo! Proyecto configurado."
else
    echo "❌ Error al crear el proyecto."
    exit 1
fi

```
