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
    errorMessage: String? = null, // Mensaje de error (opcional)
    // Configuración de colores con valores estándar
    primaryColor: Color = Color(0xFF1B365D), // Azul oscuro
    borderColor: Color = Color(0xFFC4C4C4),
    errorColor: Color = MaterialTheme.colorScheme.error, // Color de error por defecto
    backgroundColor: Color = Color(0xFFFAFAFA)
) {
    val isError = !errorMessage.isNullOrEmpty()

    Column(modifier = modifier) {
        // Label superior
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isError) errorColor else Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Campo de entrada
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("""^\d*\.?\d{0,2}$"""))) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = if (isError) errorColor else primaryColor
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
                    color = if (isError) errorColor else primaryColor
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                errorContainerColor = backgroundColor,
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = borderColor,
                errorBorderColor = errorColor,
                cursorColor = if (isError) errorColor else primaryColor
            )
        )

        // Texto descriptivo del error (si existe)
        if (isError) {
            Text(
                text = errorMessage!!,
                color = errorColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}
```
### Ejemplo de uso

import's
```
// El componente propiamente
import com.example.kmpcurso.components.CurrencyInputField

// Para el manejo de estado (el "by remember")
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// Para el diseño y modificadores
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

// Base de Compose
import androidx.compose.runtime.Composable
```

1. Sin error (Estado normal)
```
CurrencyInputField(
    value = amount,
    onValueChange = { amount = it }
)
```
   
