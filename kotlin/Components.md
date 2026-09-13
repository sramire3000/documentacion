# Componentes

## CurrencyInputField

### Source
```
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Monto",
    currencySymbol: String = "$",
    placeholder: String = "0.00",
    // Configuración de colores con valores por defecto (modificables)
    primaryColor: Color = Color(0xFF1B365D), // Azul oscuro similar a la imagen
    borderColor: Color = Color(0xFFC4C4C4),
    backgroundColor: Color = Color(0xFFFAFAFA)
) {
    Column(modifier = modifier) {
        // Label superior
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Campo de entrada
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                // Permite solo números y hasta un punto decimal con 2 decimales
                if (newValue.isEmpty() || newValue.matches(Regex("""^\d*\.?\d{0,2}$"""))) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = primaryColor
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 20.sp,
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Text(
                    text = currencySymbol,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = borderColor,
                cursorColor = primaryColor
            )
        )
    }
}
```
### Example
```
@Composable
fun ExampleScreen() {
    var amount by remember { mutableStateOf("") }

    // 1. Uso estándar (con los colores por defecto)
    CurrencyInputField(
        value = amount,
        onValueChange = { amount = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    // 2. Ejemplo personalizando el color si es necesario
    /*
    CurrencyInputField(
        value = amount,
        onValueChange = { amount = it },
        primaryColor = Color(0xFF0066CC), // Azul personalizado
        borderColor = Color.Blue
    )
    */
}
```
