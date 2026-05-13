# Configuración para WORKSPACE

### Configuracion
```bash
{
  // WINDOWS
  "window.zoomLevel": 0,

  // WORKBENCH MEJORADO
  "workbench.colorCustomizations": {
    // Color de fondo de la barra de estado
    "statusBar.background": "#121016",
    // Color de fondo de la barra de estado en modo depuración
    "statusBar.debuggingBackground": "#121016",
    // Color de primer plano de la barra de estado en modo depuración
    "statusBar.debuggingForeground": "#525156",
    // Color de fondo de la barra de herramientas de depuración
    "debugToolBar.background": "#121016",
    // Color de fondo de la barra de actividad
    "activityBar.background": "#1a1620",
    // Color de fondo de la barra de título activa
    "titleBar.activeBackground": "#1a1620",
    // Color de fondo del resaltado de línea
    "editor.lineHighlightBackground": "#1e1a25",
    // Color del borde del resaltado de línea
    "editor.lineHighlightBorder": "#1e1a25",
    // Color de fondo de la selección en todo el editor
    "selection.background": "#2a2438",
    // Color de fondo de la selección en el editor
    "editor.selectionBackground": "#302f34",
    // Color de fondo del editor
    "editor.background": "#000000",
  },
  // No mostrar la página de bienvenida al iniciar VS Code
  "workbench.startupEditor": "none",
  // Mantener la barra lateral a la izquierda para una navegación más rápida
  "workbench.sideBar.location": "left",
  // Mostrar pestañas solo cuando hay más de un editor abierto para reducir el desorden visual
  "workbench.editor.showTabs": "multiple",
  // Mantener la barra de estado visible para información rápida sin ocupar espacio innecesario
  "workbench.statusBar.visible": true,
  // Cambia el tema de iconos a tu preferencia (puedes probar con "material-icon-theme" para un estilo moderno o elegir otro tema que te guste)
  "workbench.iconTheme": "material-icon-theme",
  // Cambia el tema de color a tu preferencia (puedes probar con "Default High Contrast" para un contraste máximo o elegir otro tema que te guste)
  "workbench.colorTheme": "Default High Contrast",
  // Desactiva el desplazamiento pegajoso en los árboles para una navegación más fluida
  "workbench.tree.enableStickyScroll": false,

  // EDITOR OPTIMIZADO PARA FLUTTER/DART
  // Desactiva el minimapa para reducir distracciones
  "editor.minimap.enabled": false,
  // Oculta la barra de desplazamiento vertical para una apariencia más limpia
  "editor.scrollbar.vertical": "hidden",
  // Elimina el borde del área de la regla de visión general para un diseño más minimalista
  "editor.overviewRulerBorder": false,
  // Oculta el cursor en la regla de visión general para reducir distracciones
  "editor.hideCursorInOverviewRuler": true,
  // Desactiva las guías de indentación para un aspecto más limpio
  "editor.guides.indentation": false,
  // Desactiva el margen de glifos para reducir el desorden visual
  "editor.glyphMargin": false,
  // Ajusta el tamaño de fuente a tu preferencia para una mejor legibilidad
  "editor.fontSize": 14,
  // Ajusta la altura de línea para mejorar la legibilidad sin ocupar demasiado espacio
  "editor.lineHeight": 1.3,
  // Activa el ajuste de línea para evitar desplazamientos horizontales y mejorar la legibilidad
  "editor.wordWrap": "on",
  // Desactiva el resaltado de corchetes para reducir distracciones visuales
  "editor.matchBrackets": "never",
  // Habilita el zoom con la rueda del mouse para ajustar rápidamente el tamaño de fuente según tus necesidades
  "editor.mouseWheelZoom": true,
  // Establece el tamaño de tabulación a 2 espacios para un código más compacto y legible
  "editor.tabSize": 3,
  // Usa espacios en lugar de tabulaciones para mantener la consistencia en diferentes entornos
  "editor.insertSpaces": true,
  // Permite que el editor detecte automáticamente la configuración de indentación del proyecto para mantener la coherencia
  "editor.detectIndentation": true,
  // Usa una fuente con ligaduras para mejorar la legibilidad del código
  "editor.fontFamily": "'Fira Code', 'Cascadia Code', Consolas, 'Courier New', monospace",
  // Habilita las ligaduras de fuente para mejorar la apariencia de los operadores y símbolos en el código
  "editor.fontLigatures": true,
  // Habilita la colorización de pares de corchetes para mejorar la legibilidad del código
  "editor.bracketPairColorization.enabled": true,
  // Habilita las guías de pares de corchetes para una mejor visualización de la estructura del código
  "editor.guides.bracketPairs": true,
  // Habilita el resaltado semántico para mejorar la legibilidad del código al diferenciar visualmente los elementos según su función
  "editor.semanticHighlighting.enabled": true,
  // Habilita las sugerencias en línea para obtener recomendaciones de código sin interrumpir el flujo de escritura
  "editor.inlineSuggest.enabled": true,
  // Permite que las sugerencias rápidas se muestren incluso cuando hay fragmentos disponibles para una experiencia de autocompletado más fluida
  "editor.suggest.snippetsPreventQuickSuggestions": false,
  // Resalta la línea actual solo en el margen para reducir distracciones visuales
  "editor.renderLineHighlight": "gutter",
  // Desactiva el resaltado de selección para reducir distracciones visuales
  "editor.selectionHighlight": false,

  // FORMATO Y ORGANIZACIÓN
  // Formatea el código automáticamente al guardar para mantener la consistencia
  "editor.formatOnSave": true,
  // Formatea el código automáticamente al pegar para mantener la consistencia
  "editor.formatOnPaste": true,
  // Configura las acciones de código para organizar importaciones, aplicar correcciones y ordenar miembros automáticamente al guarda
  "editor.codeActionsOnSave": {
    // Organiza las importaciones automáticamente al guardar para mantener el código limpio
    "source.organizeImports": "always",
    // Aplica todas las correcciones disponibles al guardar para mantener el código sin errores
    "source.fixAll": "always",
    // Ordena los miembros de las clases automáticamente al guardar para mejorar la legibilidad
    "source.sortMembers": "always",
  },

  // GIT
  // Evita que VS Code busque repositorios Git en carpetas superiores para mejorar el rendimiento
  "git.openRepositoryInParentFolders": "never",
  // Desactiva la confirmación de sincronización para agilizar el flujo de trabajo de Git
  "git.confirmSync": false,
  // Habilita los commits inteligentes para permitir realizar commits sin mensajes cuando no hay cambios o solo cambios sin seguimiento
  "git.enableSmartCommit": true,

  // BREADCRUMBS
  // Desactiva los breadcrumbs para una interfaz más limpia
  "breadcrumbs.enabled": false,

  // TERMINAL OPTIMIZADO
  // Ajusta el tamaño de fuente del terminal a tu preferencia
  "terminal.integrated.fontSize": 11.0,
  // Habilita el parpadeo del cursor para mejorar la visibilidad
  "terminal.integrated.cursorBlinking": true,
  // Establece PowerShell como el perfil predeterminado en Windows
  "terminal.integrated.defaultProfile.windows": "PowerShell",

  // IGNORE RECOMENDACIONES
  "extensions.ignoreRecommendations": true,

}

```
