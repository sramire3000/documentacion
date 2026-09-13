# CurrencyInputField

### Source
```
package com.example.kmpcurso.components


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
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CurrencyInputField(
    rawInput: String, // String que solo contiene dígitos (ej: "500" para $5.00)
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Monto",
    currencySymbol: String = "$",
    errorMessage: String? = null,
    primaryColor: Color = Color(0xFF1B365D),
    borderColor: Color = Color(0xFFC4C4C4),
    errorColor: Color = MaterialTheme.colorScheme.error,
    backgroundColor: Color = Color(0xFFFAFAFA)
) {
    val isError = !errorMessage.isNullOrEmpty()

    // Formatear los dígitos ingresados a moneda decimal
    val formattedDisplayValue = formatCurrency(rawInput)

    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isError) errorColor else Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = formattedDisplayValue,
            onValueChange = { newValue ->
                // Filtrar para conservar solo números y limitar a 9 dígitos max
                val digitsOnly = newValue.filter { it.isDigit() }
                if (digitsOnly.length <= 9) {
                    onValueChange(digitsOnly)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = if (isError) errorColor else primaryColor
            ),
            leadingIcon = {
                Text(
                    text = currencySymbol,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isError) errorColor else primaryColor
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

// Función auxiliar para formatear centavos a String decimal (0.00)
private fun formatCurrency(rawDigits: String): String {
    if (rawDigits.isEmpty()) return "0.00"
    val parsed = rawDigits.toDoubleOrNull() ?: 0.0
    val amount = parsed / 100.0
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return formatter.format(amount)
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

var rawAmount by remember { mutableStateOf("") } // Guardará solo dígitos: ej "500"

// Convertimos a Double real para las validaciones (500 -> 5.00)
val numericAmount = (rawAmount.toDoubleOrNull() ?: 0.0) / 100.0

val errorMessage = when {
    rawAmount.isNotEmpty() && numericAmount < 1.0 -> "El monto mínimo es $1.00"
    else -> null
}

CurrencyInputField(
    rawInput = rawAmount,
    onValueChange = { rawAmount = it },
    errorMessage = errorMessage
)
```
