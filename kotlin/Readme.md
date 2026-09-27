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
package com.example.myfirstbankingapp.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myfirstbankingapp.R
import com.example.myfirstbankingapp.components.AppTextField
import com.example.myfirstbankingapp.components.PrimaryButton
import com.example.myfirstbankingapp.screens.login.viewModel.viewModelLogin.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.welcome),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                stringResource(R.string.login_description),
                fontSize = 16.sp,
            )

            // Username
            AppTextField(
                value = uiState.username,
                onValueChange = viewModel::onUsernameChange,
                label = stringResource(R.string.username),
                isSecured = false
            )

            // Password
            AppTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = stringResource(R.string.password),
                isSecured = true
            )

            uiState.errorMessage?.let { message ->

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Button
            PrimaryButton(
                stringResource(R.string.login),
                isEnabled = !uiState.isLoading && uiState.username.isNotBlank() && uiState.password.isNotBlank()
            ) {
                viewModel.login()
            }

            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
```

