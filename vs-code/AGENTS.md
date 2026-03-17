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

- User `StateForm` y `sharedflow` para que la comunicación entre ViewModel y UI.
- En la UI, observar el estado usando `collectAsStateWhithLifecycle()` para una gestión efciente del ciclo de vida.
- Evitar pasar ViewModels a componentes hijos; pasar estados y lambdas para eventos (State Hoisting)

## 5. Recursos

- Utilizar el sistema de recursos de compose Mutiplataform(`composeResources`)
- Imagenes, Strings y fuentes deben deficnirse en `commonMain/composeResources`
- Acceder a ellos mediante la clase generada `Res`
  
## 6. Navegación

- Utilizar `navigation-compose` en `commonMain`
- Definir las rutas y el `NavHost` en un punto central de la aplicación compartida.

## 7. Convenciones de Código

## 8. Gestión de dependencias

## 9. Workflow de Desarrollo

## 10. Organización de Paquetes

Se debe seguir una estructura de paquetes clara y modular dentro de `commonMain`:

- `com.pilbeo.pilbeo:android` (para mantener compatibilidad con el paquete de la aplicacion original)
  - `ui`: Componentes visuales, pantallas(`screens`) y navegación
  - `viewmodel`: Lógica de presentacion y gestión de estado
  - `domain`: Modelos de dominio y casos de uso
  - `data`: Respositorios y fuentes de datos
  - `di`: Configuración de inyección de dependencias
  - `util`: Funciones de utilidad y extenciones.


