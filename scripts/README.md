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

PowerShell fcreate.ps1
```
# --- Configuración ---
$DEFAULT_VERSION = "3.22.0"

# Recibir parámetros ($args[0] es el primer parámetro, $args[1] el segundo)
$RAW_NAME = $args[0]
$SELECTED_VERSION = if ($args[1]) { $args[1] } else { $DEFAULT_VERSION }

# --- Validación ---
if (-not $RAW_NAME) {
    Write-Host "❌ Error: Falta el nombre del proyecto." -ForegroundColor Red
    exit 1
}

# El reemplazo de "-" por "_" (Equivalente a ${var//-/_})
$PROJECT_NAME = $RAW_NAME -replace '-', '_'

# Avisar si se cambió el nombre
if ($RAW_NAME -ne $PROJECT_NAME) {
    Write-Host "⚠️  Nota: Se cambió el nombre a '$PROJECT_NAME' (Dart no permite guiones medios)." -ForegroundColor Yellow
}

Write-Host "🚀 Creando proyecto '$PROJECT_NAME' con Flutter $SELECTED_VERSION..." -ForegroundColor Cyan

# 1. Crear el proyecto
fvm spawn $SELECTED_VERSION create "$PROJECT_NAME"

if ($LASTEXITCODE -eq 0) {
    Set-Location "$PROJECT_NAME"
    fvm use $SELECTED_VERSION --pin
    Write-Host "✅ ¡Listo! Proyecto configurado." -ForegroundColor Green
} else {
    Write-Host "❌ Error al crear el proyecto." -ForegroundColor Red
    exit 1
}
```

CMD fcreate.bat
```
@echo off
setlocal enabledelayedexpansion

set "DEFAULT_VERSION=3.22.0"
set "RAW_NAME=%~1"
set "SELECTED_VERSION=%~2"

if "%RAW_NAME%"=="" (
    echo ❌ Error: Falta el nombre del proyecto.
    exit /b 1
)

if "%SELECTED_VERSION%"=="" set "SELECTED_VERSION=%DEFAULT_VERSION%"

:: Reemplazar guiones por guiones bajos
set "PROJECT_NAME=%RAW_NAME:-=_%"

if not "%RAW_NAME%"=="%PROJECT_NAME%" (
    echo ⚠️  Nota: Se cambió el nombre a "%PROJECT_NAME%"
)

echo 🚀 Creando proyecto "%PROJECT_NAME%" con Flutter %SELECTED_VERSION%...

fvm spawn %SELECTED_VERSION% create "%PROJECT_NAME%"
if %ERRORLEVEL% EQU 0 (
    cd "%PROJECT_NAME%"
    fvm use %SELECTED_VERSION% --pin
    echo ✅ ¡Listo! Proyecto configurado.
) else (
    echo ❌ Error al crear el proyecto.
    exit /b 1
)
```
