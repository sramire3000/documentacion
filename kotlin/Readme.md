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

### Adicionar en settings.gradle
```
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
    }
```

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
