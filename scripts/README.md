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
