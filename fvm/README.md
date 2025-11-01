# Simple Flutter Version Management
FVM simplifica la gestión de versiones de Flutter. Permite versiones del SDK por proyecto, lo que garantiza compilaciones de aplicaciones consistentes y facilita las pruebas de nuevas versiones, aumentando así la eficiencia de las tareas de tu proyecto Flutter.






### 2. Configurar una versión de Flutter con FVM
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

### 3. Crear un nuevo proyecto Flutter
```bash
# Crear proyecto usando FVM
fvm create mi_proyecto

# O usando el comando tradicional con FVM
fvm flutter create mi_proyecto
```