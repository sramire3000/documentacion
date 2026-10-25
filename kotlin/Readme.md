# KOTLIN

### Urls dependencias

- [Constraint Layaut](https://developer.android.com/develop/ui/views/layout/constraint-layout)
- [Retrofit (GET,POST, DELETE, PUT)](https://lysine.dev/retrofit/)
- [Coil images network](https://github.com/coil-kt/coil)
- [Lottie Animation lib](https://lottie.airbnb.tech/#/)
- [Bajar Animaciones LottieFile](https://lottiefiles.com/)

## Settings

### Refresh automatic
<img width="981" height="717" alt="image" src="https://github.com/user-attachments/assets/82146d35-ed37-464f-8b23-1fb60d6447db" />

### Emulador
<img width="990" height="722" alt="image" src="https://github.com/user-attachments/assets/da6c1e68-3d90-42e0-935d-89c6703adbc1" />


## Add Libs

### Permisos "AndroidManifest.xml"
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        ......

    </application>

    <!-- Permiso de acceso al internet -->
    <uses-permission android:name="android.permission.INTERNET" />

</manifest>
```

### En el archivo "\app\build.gradle.kts" add
```
plugins {
    alias(libs.plugins.ksp)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
dependencies {
    /* Adicionar librerias */

    //Icons
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // Retrofit + Gson ver. 3.0
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Retrofit + Gson ver. 2.9.0
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // okhttp3
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Coil cargar y mostrar imágenes
    implementation("io.coil-kt.coil3:coil-compose:3.6.3")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.6.3")


    // Koin for dependency injection
    implementation(platform("io.insert-koin:koin-bom:4.2.2"))
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-compose")
    implementation("io.insert-koin:koin-compose-viewmodel")

    //Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")

    // SharedPreferences for storing user data
    implementation("androidx.datastore:datastore-preferences:1.3.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")

    // Extras

    //Layaout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")

    // Navegaciom diferentes pantallas
    implementation("androidx.navigation:navigation-compose:2.10.2")

    // Animaciones
    implementation("com.airbnb.android:lottie-compose:6.6.6")

    //Go Rutinas
    implementation("androidx.lifecycle.lifecycle-runtime-ktx:lifecycle-runtime-ktx")
}
 ```

### Add animaciones en "settings.gradle.kts"
```
dependencyResolutionManagement {
    repositories {
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
```

### Preview
```
@Preview(showSystemUi = true)
@Composable
fun MyProgressPreview(modifier: Modifier = Modifier.padding(top = 30.dp)) {
    MyProgress(modifier = modifier)
}
```

### Platilla Composable
```
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun My[Nombre](modifier: Modifier = Modifier){

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun My[Nombre]Preview(modifier: Modifier = Modifier.padding(top = 30.dp)){
    My[Nombre](modifier)
}
```

### Invocacion Composable
```
My[Nombre](modifier = Modifier.padding(innerPadding))
```

```
package com.example.myfirstbankingapp.screens.product.model

data class ProductFormUiState(
        val idProduct: Int = 0,
        val name: String = "",
        val price: String = "",
        val category: String = "",
        val nameError: String? = null,
        val priceError: String? = null,
        val categoryError: String? = null
) {
    fun validate(): ProductFormUiState {
        return copy(
                nameError = if (name.isBlank()) "El nombre es requerido" else null,
                categoryError = if (category.isBlank()) "La categoría es requerida" else null,
                priceError =
                        when {
                            price.isBlank() -> "El precio es requerido"
                            price.toDoubleOrNull() == null ->
                                    "Precio debe ser un número decimal válido"
                            price.toDouble() < 0 -> "El precio no puede ser negativo"
                            else -> null
                        }
        )
    }

    companion object {
        fun validatePriceInput(value: String): String {
            // Permitir solo números y un punto decimal
            val filtered = StringBuilder()
            var hasDecimal = false

            for (char in value) {
                when {
                    char.isDigit() -> filtered.append(char)
                    char == '.' && !hasDecimal && filtered.isNotEmpty() -> {
                        filtered.append(char)
                        hasDecimal = true
                    }
                }
            }

            return filtered.toString()
        }
    }
}

```

