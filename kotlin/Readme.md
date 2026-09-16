# KOTLIN

## Settings

### Refresh automatic
<img width="981" height="717" alt="image" src="https://github.com/user-attachments/assets/82146d35-ed37-464f-8b23-1fb60d6447db" />

### Emulador
<img width="990" height="722" alt="image" src="https://github.com/user-attachments/assets/da6c1e68-3d90-42e0-935d-89c6703adbc1" />


## Add Libs

### En el archivo "\gradle\libs.versions.toml"
```
[versions]
constraintVersion = "1.1.0"

[libraries]
constraint-layaut = {module = "androidx.contraintlayout:contraintlayaout-compose", version.ref = "constraintVersion" }
```

### En el archivo "\app\build.gradle.kts" add
```
dependencies {
    implementation(libs.constraint.layaut)
}
 ```
