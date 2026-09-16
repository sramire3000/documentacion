# Jet Pack Compose

### Preview
```
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstComposeAppTheme {
        Greeting("Android")
    }
}
```

### Modificadores
```
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background

modifier = Modifier
          .fillMaxWidth()							// Utiliza todo el ancho
          .fillMaxHeight()          				// Utiliza todo el alto
          .fillMaxSize()       						// Utiliza todo el espacio Ancho como alto
          .padding(30.dp)           				// Padding al rededor de todo el componente
          .wrapContentSize()   						// Ocupe solo el espacio exacto que su contenido necesita
          .background(Color.Gray), 					// Color
          .clickable{},             				// Para hacer click
          .verticalScroll(rememberScrollState())   	// Scroll vertical
          .horizontalScroll(rememberScrollState())  // Scroll horizotal
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

### Box
```
Box(
	modifier = Modifier
		.fillMaxSize(),
	contentAlignment = Alignment.Center
){
	Box(
		modifier = Modifier
			.size(50.dp)
			.background(Color.Red),
		contentAlignment = Alignment.Center
	){
		Text("Hola")
	}
}
```

### Column
````
Column(
    modifier = modifier
               .fillMaxSize(),
               .verticalScroll(rememberScrollState())
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

### My Complex Layout
<img width="228" height="530" alt="image" src="https://github.com/user-attachments/assets/4610ea24-ce63-47bf-8af3-88b64969b4db" />

```
Column(modifier = Modifier) {
	Box(Modifier.weight(1f).fillMaxWidth().background(Color.Red)) { }
	Box(Modifier.weight(1f).fillMaxWidth().background(Color.Cyan)) {
		Row() {
			Box(
				modifier = Modifier
					.weight(1f)
					.height(125.dp)
					.background(Color.Gray)
			) { }
			Box(
				modifier = Modifier
					.weight(1f)
					.height(125.dp)
					.background(Color.Green)
			) { }
		}
	}
	Box(Modifier.weight(1f).fillMaxWidth().background(Color.Green)) { }
}
```

### Layaout Tarea 1
<img width="236" height="513" alt="image" src="https://github.com/user-attachments/assets/e687563d-3964-421b-91eb-66704f1866db" />

```
@Preview(showBackground = true)
@Composable
fun MyExerciseOne(modifier: Modifier = Modifier){
    Column(modifier = Modifier) {
        Box(Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Text("Ejemplo 1")
        }
        Box(Modifier.weight(1f).fillMaxWidth()) {
            Row() {
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Red)
                    , contentAlignment = Alignment.Center
                ) {
                    Text("Ejemplo 2")
                }
                Box(Modifier
                    .weight(1f)
                    .background(Color.Green)
                    .fillMaxHeight()
                    , contentAlignment = Alignment.Center
                ) {
                    Text("Ejemplo 3")
                }
            }
        }
        Box(Modifier.weight(1f).fillMaxWidth().background(Color.Magenta), contentAlignment = Alignment.BottomCenter) {
            Text("Ejemplo 3")
        }
    }
}
```

### Spacer
```
Spacer(Modifier.height(20.dp))
Spacer(Modifier.width(20.dp))
```

### MyBasicConstraintLayout
<img width="238" height="530" alt="image" src="https://github.com/user-attachments/assets/9a71146c-efc6-4481-a7c9-76e6de3afefb" />

```
@Preview(showBackground = true)
@Composable
fun MyBasicConstraintLayout(modifier: Modifier = Modifier){

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        // Id para el layaout
        val(boxRed, boxGray, boxGreen, boxMagenta, boxYellow) = createRefs()

        Box(Modifier.size(150.dp).background(Color.Red).constrainAs(boxRed){
            top.linkTo(boxYellow.bottom)
            start.linkTo(boxYellow.end)
        }) { }

        Box(Modifier.size(150.dp).background(Color.Gray).constrainAs(boxGray){
            top.linkTo(boxYellow.bottom)
            end.linkTo(boxYellow.start)
        }){}

        Box(Modifier.size(150.dp).background(Color.Green).constrainAs(boxGreen){
            bottom.linkTo(boxYellow.top)
            start.linkTo(boxYellow.end)
        }) { }

        Box(Modifier.size(150.dp).background(Color.Magenta).constrainAs(boxMagenta){
            bottom.linkTo(boxYellow.top)
            end.linkTo(boxYellow.start)
        }) { }
        Box(Modifier.size(150.dp).background(Color.Yellow).constrainAs(boxYellow){
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }) { }
    }
}
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

### Variables recomposición
```
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

 var isTextClicked by remember { mutableStateOf(false) }


```
