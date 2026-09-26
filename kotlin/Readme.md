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
dependencies {
    /* Adicionar librerias */

    //Layaout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")

    //Icons
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    //consumir APIs REST Retrofit peticiones (POST, GET, PUT)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Convert Gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // cargar y mostrar imágenes
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")

    // Se encarga de la comunicación HTTP para descargar las imágenes.
    // Permite personalizar timeouts, headers, autenticación, interceptores, etc.
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")

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

@Preview(showSystemUi = true)
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
package com.example.myfirstbankingapp.screens.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstbankingapp.screens.login.model.LoginUiState
import com.example.myfirstbankingapp.screens.login.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class viewModel {
    class LoginViewModel(
        private val repository: LoginRepository
    ) : ViewModel() {
        // Creando un estado mutable que solo va a ser modificado por el view model
        private val _uiState = MutableStateFlow(LoginUiState())

        // Un estado que lo va a observar la vista, este estado no sera modificado por la vista
        val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

        // Función que actualiza el usuario dentro del ui state
        fun onUsernameChange(username: String) {
            _uiState.update {
                it.copy(
                    username = username,
                    errorMessage = null
                )
            }
        }

        // Funcion que actualiza la contraseña dentro del ui state
        fun onPasswordChange(password: String) {
            _uiState.update {
                it.copy(
                    password = password,
                    errorMessage = null
                )
            }
        }

        fun login() {
            val currentState = _uiState.value

            if (currentState.username.isBlank() || currentState.password.isBlank()) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Ingresa usario y contraseña"
                    )
                }

                return
            }

            // 1. Mostrar cargando
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }

                // 2. Llamar API a través del repositorio
                val result = repository.login(
                    username = currentState.username,
                    password = currentState.password
                )

                // 3. Procesar resultado
                result
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                isLoginSuccessful = true,
                                token = response.token
                            )
                        }
                    }
                    .onFailure {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Usuario y/o contraseña incorrectos"
                            )
                        }
                    }
            }
        }
    }
}
```
