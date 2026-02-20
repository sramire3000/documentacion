# Configuración para WORKSPACE

### Configuracion
```bash
{
  "folders": [
    {
      "path": "../fuentes-arreconsa/angular-proyect",
      "name": "Proyecto Angular",
    },
    {
      "path": "../fuentes-arreconsa/demo",
      "name": "Demo",
    },
    {
      "path": ".",
      "name": "Workspace",
    },
  ],
  "settings": {
    "window.zoomLevel": 0, // Ajusta el nivel de zoom a tu preferencia
    "workbench.startupEditor": "none", // Evita que se abra un editor al iniciar
    "breadcrumbs.enabled": false, // Desactiva las migas de pan

    //Configuración del editor para mejorar la legibilidad y reducir el desorden visual
    "editor.minimap.enabled": false, // Desactiva el minimapa
    "editor.scrollbar.vertical": "hidden", // Oculta la barra de desplazamiento vertical
    "editor.overviewRulerBorder": false, // Elimina el borde del visor general
    "editor.hideCursorInOverviewRuler": true, // Oculta el cursor en el visor general
    "editor.guides.indentation": true, // Desactiva las guías de indentación
    "editor.glyphMargin": false, // Desactiva el margen de glifos
    "editor.fontSize": 14, // Ajusta el tamaño de fuente a tu preferencia  (Ojo, esto puede afectar la legibilidad si es demasiado pequeño o grande)
    "editor.lineHeight": 1.3, // Ajusta la altura de línea a tu preferencia
    "editor.wordWrap": "off", // Activa el ajuste de línea
    "editor.matchBrackets": "never", // Desactiva el resaltado de corchetes coincidentes
    "editor.mouseWheelZoom": true, // Permite hacer zoom con la rueda del ratón
    "editor.tabSize": 2, // Ajusta el tamaño de tabulación a tu preferencia
    "editor.insertSpaces": true, // Usa espacios en lugar de tabulaciones
    "editor.detectIndentation": true, // Detecta automáticamente la indentación del archivo
    "editor.fontFamily": "Fira Code, Consolas, 'Courier New', monospace", // Cambia la fuente a tu preferencia (Fira Code es una fuente de programación con ligaduras)
    "editor.fontLigatures": true, // Habilita las ligaduras de fuente
    "editor.bracketPairColorization.enabled": true, // Habilita la colorización de pares de corchetes
    "editor.guides.bracketPairs": true, // Habilita las guías de pares de corchetes
    "editor.semanticHighlighting.enabled": true, // Habilita el resaltado semántico
    "editor.inlineSuggest.enabled": true, // Habilita las sugerencias en línea
    "editor.suggest.snippetsPreventQuickSuggestions": false, // Permite sugerencias rápidas incluso cuando hay fragmentos
    "editor.renderLineHighlight": "gutter", // Resalta la línea actual en el margen
    "editor.selectionHighlight": false, // Desactiva el resaltado de selección

    // Git settings
    "git.openRepositoryInParentFolders": "never", // Evita que se abra el repositorio en carpetas padre
    "git.confirmSync": false, // Desactiva la confirmación al sincronizar
    "git.enableSmartCommit": true, // Habilita el commit inteligente
    "gitlens.ai.model": "vscode", // Configura el modelo de IA para GitLens (si estás usando GitLens)
    "gitlens.ai.vscode.model": "copilot:gpt-4.1", // Configura el modelo de IA específico para Visual Studio Code (si estás usando GitLens)
    "github.copilot.nextEditSuggestions.enabled": true, // Habilita las sugerencias de edición de Copilot

    // Workbench mejorado
    "workbench.iconTheme": "material-icon-theme", // Cambia el tema de íconos a Material Icon Theme
    "vsicons.dontShowNewVersionMessage": true, // Evita el mensaje de nueva versión de los íconos
    "workbench.sideBar.location": "left", // Mantiene la barra lateral a la izquierda
    "workbench.editor.showTabs": "multiple", // Muestra las pestañas solo cuando hay más de una
    "workbench.statusBar.visible": true, // Mantiene la barra de estado visible
    "workbench.colorCustomizations": {
      // Personaliza los colores del tema para mejorar la legibilidad y reducir el desorden visual
      "statusBar.background": "#121016", // Color de fondo de la barra de estado
      "statusBar.debuggingBackground": "#121016", // Color de fondo de la barra de estado en modo depuración
      "statusBar.debuggingForeground": "#525156", // Color de primer plano de la barra de estado en modo depuración
      "debugToolBar.background": "#121016", // Color de fondo de la barra de herramientas de depuración
      "activityBar.background": "#1a1620", // Color de fondo de la barra de actividad
      "titleBar.activeBackground": "#1a1620", // Color de fondo de la barra de título activa
      "editor.lineHighlightBackground": "#1e1a25", // Color de fondo del resaltado de línea
      "editor.lineHighlightBorder": "#1e1a25", // Color del borde del resaltado de línea
      "selection.background": "#2a2438", // Color de fondo de la selección en todo el editor
      "editor.selectionBackground": "#302f34", // Color de fondo de la selección en el editor
      "editor.background": "#000000", // Color de fondo del editor
    },

    // Terminal optimizado
    "terminal.integrated.fontSize": 12, // Ajusta el tamaño de fuente del terminal a tu preferencia
    "terminal.integrated.cursorBlinking": true, // Habilita el parpadeo del cursor para mejorar la visibilidad
    "terminal.integrated.defaultProfile.windows": "PowerShell", // Establece PowerShell como el perfil predeterminado en Windows

    // Formato y Organización de Código
    "editor.formatOnSave": true, // Formatea el código automáticamente al guardar
    "editor.formatOnPaste": true, // Formatea el código automáticamente al pegar
    "editor.formatOnType": true, // Formatea el código automáticamente al escribir
    "editor.codeActionsOnSave": {
      // Aplica acciones de código al guardar
      "source.organizeImports": "always", // Organiza las importaciones automáticamente al guardar
      "source.fixAll.eslint": "always", // Aplica las correcciones de ESLint automáticamente al guardar
      "source.fixAll": "always", // Aplica todas las correcciones disponibles automáticamente al guardar
      "source.sortMembers": "always", // Ordena los miembros de las clases automáticamente al guardar
    },

    // Files y Explorador
    "files.autoSave": "afterDelay", // Guarda automáticamente los archivos después de un retraso
    "files.autoSaveDelay": 1000, // Establece el retraso para el guardado automático a 1 segundo
    "explorer.confirmDelete": false, // Evita la confirmación al eliminar archivos
    "explorer.confirmDragAndDrop": false, // Evita la confirmación al arrastrar y soltar archivos
  },
}
```
