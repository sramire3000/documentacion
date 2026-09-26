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

## Drawable

### Archivo "baseline_account_circle_24.xml"
<img width="67" height="59" alt="image" src="https://github.com/user-attachments/assets/79c59524-bbed-4f93-a0ac-59e7384c722c" />

```
<!--
  ~ Copyright (C) 2026 The Android Open Source Project
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<vector xmlns:android="http://schemas.android.com/apk/res/android" android:height="24dp" android:tint="#000000" android:viewportHeight="24" android:viewportWidth="24" android:width="24dp">
      
    <path android:fillColor="@android:color/white" android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10s10,-4.48 10,-10S17.52,2 12,2zM12,6c1.93,0 3.5,1.57 3.5,3.5S13.93,13 12,13s-3.5,-1.57 -3.5,-3.5S10.07,6 12,6zM12,20c-2.03,0 -4.43,-0.82 -6.14,-2.88C7.55,15.8 9.68,15 12,15s4.45,0.8 6.14,2.12C16.43,19.18 14.03,20 12,20z"/>
    
</vector>
```

### Archivo "baseline_account_box_24.xml"
<img width="67" height="59" alt="image" src="https://github.com/user-attachments/assets/bac96125-a9e6-4a27-9b44-bc5a51bf50e7" />

```
<!--
  ~ Copyright (C) 2026 The Android Open Source Project
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<vector xmlns:android="http://schemas.android.com/apk/res/android" android:height="24dp" android:tint="#000000" android:viewportHeight="24" android:viewportWidth="24" android:width="24dp">
      
    <path android:fillColor="@android:color/white" android:pathData="M19,3H5C3.9,3 3,3.9 3,5v14c0,1.1 0.9,2 2,2h14c1.1,0 2,-0.9 2,-2V5C21,3.9 20.1,3 19,3zM12,6c1.93,0 3.5,1.57 3.5,3.5c0,1.93 -1.57,3.5 -3.5,3.5s-3.5,-1.57 -3.5,-3.5C8.5,7.57 10.07,6 12,6zM19,19H5v-0.23c0,-0.62 0.28,-1.2 0.76,-1.58C7.47,15.82 9.64,15 12,15s4.53,0.82 6.24,2.19c0.48,0.38 0.76,0.97 0.76,1.58V19z"/>
    
</vector>
```

### Archivo "baseline_circle_notifications_24.xml"
<img width="67" height="59" alt="image" src="https://github.com/user-attachments/assets/49de046b-bf0e-4b1c-958a-cfd1d99c085a" />

```
<!--
  ~ Copyright (C) 2026 The Android Open Source Project
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<vector xmlns:android="http://schemas.android.com/apk/res/android" android:height="24dp" android:tint="#000000" android:viewportHeight="24" android:viewportWidth="24" android:width="24dp">
      
    <path android:fillColor="@android:color/white" android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zM12,18.5c-0.83,0 -1.5,-0.67 -1.5,-1.5h3c0,0.83 -0.67,1.5 -1.5,1.5zM17,16L7,16v-1l1,-1v-2.61C8,9.27 9.03,7.47 11,7v-0.5c0,-0.57 0.43,-1 1,-1s1,0.43 1,1L13,7c1.97,0.47 3,2.28 3,4.39L16,14l1,1v1z"/>
    
</vector>
```

### Archivo "ic_outline_add.xml"
<img width="67" height="59" alt="image" src="https://github.com/user-attachments/assets/cbc5a650-42b5-4e8b-81c4-11868e6dfa19" />

```
<!--
  ~ Copyright (C) 2026 The Android Open Source Project
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<vector xmlns:android="http://schemas.android.com/apk/res/android" android:height="24dp" android:tint="#000000" android:viewportHeight="960" android:viewportWidth="960" android:width="24dp">
      
    <path android:fillColor="@android:color/white" android:pathData="M440,520L200,520L200,440L440,440L440,200L520,200L520,440L760,440L760,520L520,520L520,760L440,760L440,520Z"/>
    
</vector>
```

### Archivo "outline_account_circle_24.xml"
<img width="67" height="59" alt="image" src="https://github.com/user-attachments/assets/4df392df-6df9-4ec0-acb5-d269b360d652" />

```
<!--
  ~ Copyright (C) 2026 The Android Open Source Project
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<vector xmlns:android="http://schemas.android.com/apk/res/android" android:height="24dp" android:tint="#000000" android:viewportHeight="960" android:viewportWidth="960" android:width="24dp">
      
    <path android:fillColor="@android:color/white" android:pathData="M234,684Q285,645 348,622.5Q411,600 480,600Q549,600 612,622.5Q675,645 726,684Q761,643 780.5,591Q800,539 800,480Q800,347 706.5,253.5Q613,160 480,160Q347,160 253.5,253.5Q160,347 160,480Q160,539 179.5,591Q199,643 234,684ZM380.5,479.5Q340,439 340,380Q340,321 380.5,280.5Q421,240 480,240Q539,240 579.5,280.5Q620,321 620,380Q620,439 579.5,479.5Q539,520 480,520Q421,520 380.5,479.5ZM480,880Q397,880 324,848.5Q251,817 197,763Q143,709 111.5,636Q80,563 80,480Q80,397 111.5,324Q143,251 197,197Q251,143 324,111.5Q397,80 480,80Q563,80 636,111.5Q709,143 763,197Q817,251 848.5,324Q880,397 880,480Q880,563 848.5,636Q817,709 763,763Q709,817 636,848.5Q563,880 480,880ZM580,784.5Q627,769 666,740Q627,711 580,695.5Q533,680 480,680Q427,680 380,695.5Q333,711 294,740Q333,769 380,784.5Q427,800 480,800Q533,800 580,784.5ZM523,423Q540,406 540,380Q540,354 523,337Q506,320 480,320Q454,320 437,337Q420,354 420,380Q420,406 437,423Q454,440 480,440Q506,440 523,423ZM480,380Q480,380 480,380Q480,380 480,380Q480,380 480,380Q480,380 480,380Q480,380 480,380Q480,380 480,380Q480,380 480,380Q480,380 480,380ZM480,740Q480,740 480,740Q480,740 480,740Q480,740 480,740Q480,740 480,740Q480,740 480,740Q480,740 480,740Q480,740 480,740Q480,740 480,740Z"/>
    
</vector>
```




