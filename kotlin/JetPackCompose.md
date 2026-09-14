# Jet Pack Compose

### Modificadores
```
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background

modifier = Modifier
          .fillMaxSize()       		// Utiliza todo el espacio Ancho como alto
          .wrapContentSize()   		// Ocupe solo el espacio exacto que su contenido necesita
          .background(Color.Gray), 	// Color
          .clickable{},             // Para dar click
```

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
import androidx.compose.foundation.layout.Row

Row(
	// Centrado pero espacio entre cada uno
	horizontalArrangement = Arrangement.SpaceEvenly,
	// Centrado de Arriba hacia Abajo
	verticalAlignment = Alignment.CenterVertically
){}
```

### Imagen
```
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

Image(
	painter = painterResource(id = R.drawable.baseline_person_24),
	contentDescription = "Perfil"
)
```

### Card
```
Card(
	// redondez de las esquinas del componente (en este caso, de la tarjeta Card).
	shape = RoundedCornerShape(80),
	// Sombra o elevación visual que proyecta un componente Card
	elevation = CardDefaults.cardElevation(15.dp),
	//Border ancho y color
	border = BorderStroke(1.dp, Color.Red)
) {}
```
