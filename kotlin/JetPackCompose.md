# Jet Pack Compose

### Surface
```
Surface(
	// Utiliza todo el espacio Ancho como alto
	modifier = Modifier.fillMaxSize(),
    // Color de fondo
	color = MaterialTheme.colorScheme.background,
){}
```

### Column
````
Column(
	// alineado a la Izquiera o derecha
	horizontalAlignment = Alignment.CenterHorizontally,
	// Alineado Abajo hacia Arriba
	verticalArrangement = Arrangement.Center
) {}
````

### Row
```
Row(
	// Centrado pero espacio entre cada uno
	horizontalArrangement = Arrangement.SpaceEvenly,
	// Centrado de Arriba hacia Abajo
	verticalAlignment = Alignment.CenterVertically
){}
```
