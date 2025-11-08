# FLUTTER WEB

## snippets

### snippets MateApp
```
	"Flutter Material App": {
		"prefix": "mateapp",
		"body": [
			"import 'package:flutter/material.dart';",
			"",
			"void main() => runApp(MyApp());",
			"",
			"class MyApp extends StatelessWidget {",
			"  @override",
			"  Widget build(BuildContext context) {",
			"    return MaterialApp(",
			"      title: 'Material App',",
			"      theme: ThemeData(primarySwatch: Colors.blue),",
			"      home: Scaffold(",
			"        appBar: AppBar(title: Text('Material App Bar')),",
			"        body: Center(child: Text('Hello, World!')),",
			"      ),",
			"    );",
			"  }",
			"}"
		],
		"description": "Crea una página/AppMaterial de flutter fácilmente"
	}
```

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
