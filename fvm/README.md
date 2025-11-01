# Simple Flutter Version Management
FVM simplifica la gestión de versiones de Flutter. Permite versiones del SDK por proyecto, lo que garantiza compilaciones de aplicaciones consistentes y facilita las pruebas de nuevas versiones, aumentando así la eficiencia de las tareas de tu proyecto Flutter.



### Instalación de FVM (si no lo tienes) Non-Administrative install
```bash
1. Save the script below as ChocolateyInstallNonAdmin.ps1.
2. Use the script below, determine where you might want Chocolatey installed if it is not to C:\ProgramData\chocoportable.
3. Open PowerShell.exe.
4. Run the following Set-ExecutionPolicy Bypass -Scope Process -Force;
5. Run .\ChocolateyInstallNonAdmin.ps1.


# Set directory for installation - Chocolatey does not lock
# down the directory if not the default
$InstallDir='C:\ProgramData\chocoportable'
$env:ChocolateyInstall="$InstallDir"

# If your PowerShell Execution policy is restrictive, you may
# not be able to get around that. Try setting your session to
# Bypass.
Set-ExecutionPolicy Bypass -Scope Process -Force;

# All install options - offline, proxy, etc at
# https://chocolatey.org/install
iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```


### Configurar una versión de Flutter con FVM
```bash
# Listar versiones disponibles de Flutter
fvm releases

# Instalar una versión específica de Flutter
fvm install stable

# O instalar la última versión
fvm install latest

# Configurar la versión por defecto
fvm global stable
```

### Instalar version de flutter ejemplo
```bash
# Instalar versión
fvm install 3.7.2 --setup

# Usar versión
fvm use 3.7.2
```

### Crear un nuevo proyecto Flutter
```bash

# Crear Proyecto
fvm flutter create mi_proyecto

# Navegar al directorio del proyecto
cd mi_proyecto

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


