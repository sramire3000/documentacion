# Visual Studio Code FLUTTER

### Extensions Globales

- [Bracket Pair Color DLW](https://marketplace.visualstudio.com/items?itemName=BracketPairColorDLW.bracket-pair-color-dlw)
- [Activitus Bar](https://marketplace.visualstudio.com/items?itemName=Gruntfuggly.activitusbar)
- [Better Comments](https://marketplace.visualstudio.com/items?itemName=aaron-bond.better-comments)
- [Error Lens](https://marketplace.visualstudio.com/items?itemName=usernamehw.errorlens)
- [JSON Crack](https://marketplace.visualstudio.com/items?itemName=AykutSarac.jsoncrack-vscode)
- [Lorem ipsum](https://marketplace.visualstudio.com/items?itemName=Tyriar.lorem-ipsum)
- [Paste JSON as Code](https://marketplace.visualstudio.com/items?itemName=quicktype.quicktype)
- [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode)
- [Pubspec Assist](https://marketplace.visualstudio.com/items?itemName=jeroen-meijer.pubspec-assist)
- [TODO Highlight](https://marketplace.visualstudio.com/items?itemName=wayou.vscode-todo-highlight)
- [Auto Rename Tag](https://marketplace.visualstudio.com/items?itemName=formulahendry.auto-rename-tag)

### TypeScript
- [JavaScript and TypeScript Nightly](https://marketplace.visualstudio.com/items?itemName=ms-vscode.vscode-typescript-next)

### Thema
- [Monokai Night Theme](https://marketplace.visualstudio.com/items?itemName=fabiospampinato.vscode-monokai-night)
- [Material Icon Theme](https://marketplace.visualstudio.com/items?itemName=PKief.material-icon-theme)


### Extenciones de GIT
- [GitHub Copilot app modernization](https://marketplace.visualstudio.com/items?itemName=vscjava.migrate-java-to-azure)
- [Git Graph](https://marketplace.visualstudio.com/items?itemName=mhutchie.git-graph)
- [GitHub Copilot](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot)
- [GitHub Copilot Chat](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot-chat)


### Extenciones de Flutter
- [Dart](https://marketplace.visualstudio.com/items?itemName=Dart-Code.dart-code)
- [Dart Import Sorter](https://marketplace.visualstudio.com/items?itemName=aziznal.dart-import-sorter)
- [bloc](https://marketplace.visualstudio.com/items?itemName=FelixAngelov.bloc)
- [Flutter](https://marketplace.visualstudio.com/items?itemName=Dart-Code.flutter)
- [Flutter Riverpod Snippets](https://marketplace.visualstudio.com/items?itemName=robert-brunhage.flutter-riverpod-snippets)
- [Flutter Widget Snippets](https://marketplace.visualstudio.com/items?itemName=alexisvt.flutter-snippets)
- [Flutter Tree](https://marketplace.visualstudio.com/items?itemName=marcelovelasquez.flutter-tree)
- [Awesome Flutter Snippets](https://marketplace.visualstudio.com/items?itemName=Nash.awesome-flutter-snippets)
- [Flutter Color](https://marketplace.visualstudio.com/items?itemName=circlecodesolution.ccs-flutter-color)
- [Flutter Files](https://marketplace.visualstudio.com/items?itemName=gornivv.vscode-flutter-files)


### File settings.json
```
{
  // Windows
  "window.zoomLevel": 1,
  
  // ignore recomendaciones
  "extensions.ignoreRecommendations": true,
  
  // Terminal optimizado para Flutter
  "terminal.integrated.defaultProfile.windows": "PowerShell",
  "terminal.integrated.fontSize": 12,
  "terminal.integrated.cursorBlinking": true,
  
  // Workbench mejorado
  "workbench.colorCustomizations": {
    "statusBar.background": "#121016",
    "statusBar.debuggingBackground": "#121016",
    "statusBar.debuggingForeground": "#525156",
    "debugToolBar.background": "#121016",
    "activityBar.background": "#1a1620",
    "titleBar.activeBackground": "#1a1620",
    "editor.lineHighlightBackground": "#1e1a25",
    "editor.lineHighlightBorder": "#1e1a25",
    "editor.selectionBackground": "#2a2438",
    "selection.background": "#2a2438"
  },
  "workbench.startupEditor": "none",
  "workbench.sideBar.location": "left",
  "workbench.editor.showTabs": "multiple",
  "workbench.statusBar.visible": true,
  "workbench.iconTheme": "material-icon-theme",
  
  // Editor optimizado para Flutter/Dart
  "editor.minimap.enabled": false,
  "editor.scrollbar.vertical": "hidden",
  "editor.overviewRulerBorder": false,
  "editor.hideCursorInOverviewRuler": true,
  "editor.guides.indentation": false,
  "editor.glyphMargin": false,
  "editor.fontSize": 14,
  "editor.lineHeight": 1.3,
  "editor.wordWrap": "on",
  "editor.matchBrackets": "never",
  "editor.mouseWheelZoom": true,
  "editor.tabSize": 2,
  "editor.insertSpaces": true,
  "editor.detectIndentation": true,
  "editor.fontFamily": "'Fira Code', 'Cascadia Code', Consolas, 'Courier New', monospace",
  "editor.fontLigatures": true,
  "editor.bracketPairColorization.enabled": true,
  "editor.guides.bracketPairs": true,
  "editor.semanticHighlighting.enabled": true,
  "editor.inlineSuggest.enabled": true,
  "editor.suggest.snippetsPreventQuickSuggestions": false,
  "editor.renderLineHighlight": "gutter",
  "editor.selectionHighlight": false,
  
  // Formato y organización
  "editor.formatOnSave": true,
  "editor.formatOnPaste": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": "always",
    "source.fixAll": "always",
    "source.sortMembers": "always"
  },
  
  // Git
  "git.openRepositoryInParentFolders": "never",
  "git.confirmSync": false,
  "git.enableSmartCommit": true,
  
  // Breadcrumbs
  "breadcrumbs.enabled": false,
  
  // Files
  "files.autoSave": "afterDelay",
  "files.autoSaveDelay": 1000,
  "files.exclude": {
    "**/*.o": true,
    "**/.dart_tool": true,
    "**/.pub": true,
    "**/build": true,
    "**/.DS_Store": true,
    "**/thumbs.db": true
  },
  "files.watcherExclude": {
    "**/.dart_tool": true,
    "**/.pub": true,
    "**/build": true
  },
  
  // Dart específico optimizado (configuraciones actuales)
  "dart.debugExternalPackageLibraries": false,
  "dart.debugSdkLibraries": false,
  "dart.previewFlutterUiGuides": true,
  "dart.previewFlutterUiGuidesCustomTracking": true,
  "dart.showInspectorNotificationsForWidgetErrors": false,
  "dart.checkForSdkUpdates": false,
  "dart.pubOutdatedPrompt": "never",
  "dart.runPubGetOnPubspecChanges": "always",
  "dart.flutterHotReloadOnSave": "never",
  "dart.flutterCreateOrganization": "com.example",
  "dart.analyzeAngularTemplates": true,
  
  "[dart]": {
    "editor.formatOnSave": true,
    "editor.formatOnType": true,
    "editor.selectionHighlight": false,
    "editor.suggest.snippetsPreventQuickSuggestions": false,
    "editor.suggestSelection": "first",
    "editor.tabCompletion": "onlySnippets",
    "editor.wordBasedSuggestions": "off",
    "editor.defaultFormatter": "Dart-Code.dart-code",
    "editor.foldingStrategy": "indentation",
    "editor.rulers": [80],
    "editor.wordSeparators": "`!%&()=+[{}];:''\"\"\\|,.<>/?"
  },
  
  // Theme
  "workbench.colorTheme": "Monokai Night",
  
  // Configuraciones adicionales para Flutter
  "emmet.includeLanguages": {
    "dart": "html"
  },
  
  // Configuración de explorador
  "explorer.confirmDelete": false,
  "explorer.confirmDragAndDrop": false,
  "explorer.compactFolders": false,
  
  // Search
  "search.exclude": {
    "**/node_modules": true,
    "**/bower_components": true,
    "**/*.code-search": true,
    "**/build": true,
    "**/.dart_tool": true,
    "**/.pub": true
  },
  
  // Configuración de snippets
  "editor.quickSuggestions": {
    "strings": true
  },
  
  // Debug
  "debug.onTaskErrors": "showErrors",
  "debug.internalConsoleOptions": "openOnSessionStart"
}
```















