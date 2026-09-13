# StandardTextField

## Source
```
package com.example.kmpcurso.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StandardTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    errorMessage: String? = null,
    helperText: String? = null,
    maxLength: Int? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    // Paleta de colores estándar (idéntica a CurrencyInputField)
    primaryColor: Color = Color(0xFF1B365D), // Azul oscuro de la marca
    borderColor: Color = Color(0xFFC4C4C4),
    errorColor: Color = MaterialTheme.colorScheme.error,
    backgroundColor: Color = Color(0xFFFAFAFA)
) {
    val isError = !errorMessage.isNullOrEmpty()

    Column(modifier = modifier) {
        // Label superior opcional
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = if (isError) errorColor else Color.Gray,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        // Campo de entrada principal
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (maxLength == null || newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            isError = isError,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = if (isError) errorColor else primaryColor
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                disabledContainerColor = backgroundColor.copy(alpha = 0.5f),
                errorContainerColor = backgroundColor,
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = borderColor,
                errorBorderColor = errorColor,
                cursorColor = if (isError) errorColor else primaryColor,
                focusedLeadingIconColor = primaryColor,
                unfocusedLeadingIconColor = Color.Gray,
                errorLeadingIconColor = errorColor,
                focusedTrailingIconColor = primaryColor,
                unfocusedTrailingIconColor = Color.Gray,
                errorTrailingIconColor = errorColor
            )
        )

        // Fila inferior para mensaje de error / helper text y contador de caracteres
        if (isError || !helperText.isNullOrEmpty() || maxLength != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
            ) {
                // Mensaje de error o texto de ayuda
                if (isError) {
                    Text(
                        text = errorMessage!!,
                        color = errorColor,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                } else if (!helperText.isNullOrEmpty()) {
                    Text(
                        text = helperText,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                // Contador de caracteres (opcional)
                if (maxLength != null) {
                    Text(
                        text = "${value.length}/$maxLength",
                        color = if (isError) errorColor else Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
```
