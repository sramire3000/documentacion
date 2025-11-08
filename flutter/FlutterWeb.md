# FLUTTER WEB

### Crear un nuevo proyecto Flutter
```bash
# Elimina cualquier configuracion
rm -rf .fvm .fvmrc

# Crear Proyecto
fvm flutter create mi_proyecto

# Navegar al directorio del proyecto
cd mi_proyecto

# Ver versiones instaladas
fvm list

# Usar version de flutter (Esto creará un archivo .fvm/flutter_sdk con un enlace simbólico)
fvm use [Version Flutter] 

# Usar FVM para ejecutar comandos Flutter
fvm flutter pub get
fvm flutter run
fvm flutter build apk

# O puedes navegar al directorio y usar flutter directamente
cd mi_proyecto
flutter pub get  # Usará la versión configurada por FVM
```

### Ejecutar la app
```bash
fvm flutter run
```
