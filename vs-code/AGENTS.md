# Guía de mejores Prácticas - NombreProyecto (Kotlin Multiplataforma) 

## 1. General

- Utiliza buenas practicas de desarrollo de software y aplicaciones móviles Amdroid e IOS.
- Sigue la guía de estilo más acual de Kotlin.
- La App soporta idioma español e inglés. Siendo el inglés el idioma por defecto.
  - Los textos deben estar estructurados por pantalla en su fichero de `string.xml` 

## 2. Arquitectura

Se sigue el patrón **MVVM (Model-View-ViewModel)** adaptado a KMP:

- **UI (Compose):** Reside en `commonMain`. Debe ser declarativa y reactiva.
- **ViewModel:** Reside en `commonMain` utilizando la librería de Lifecycle de Jetpack compatible con KMP
- **Repository/UseCase:** Lógica de negocio y acceso a datos en `commonMain`.
- **Platform Specific:** Solo cuando sea estrictamente necesario, utilizar `expect/actual` en `androidMain` e `iosMain`.

## 3. Gestión de UI y Compose

- **Componentes Compartidos:** toda la UI debe intentarse construir en `commonMain` usando compose Multiplataforma.
- **Material 3:** Utilizar `MaterialTheme` y componentes de Material 3 para mantener....
- **Adaptabilidad:** Diseñar interfaces que funciones bien en diferentes tamaños
- **Safe Areas:** Utilizar `safeContentPadding()` o modificaciones similares para
- **Componentes:** Toda la UI debe intentar utilizar componentes del paquete `components` y tender a crear componentes comunes y reutilizables

## 4. Estado y Reactividad
