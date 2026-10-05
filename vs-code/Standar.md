# Configuración para WORKSPACE

## Plugins standar x Id 

- shd101wyy.markdown-preview-enhanced
- yzane.markdown-pdf
- pomdtr.excalidraw-editor
- nadomani.excalidraw-copilot
- redhat.vscode-xml
- PKief.material-icon-theme
- quicktype.quicktype
- Gruntfuggly.activitusbar
- NarasimaPandiyan.jetbrainsmono
- Tyriar.lorem-ipsum
- christian-kohler.path-intellisense
- wayou.vscode-todo-highlight
- formulahendry.auto-rename-tag
- formulahendry.auto-close-tag
- BracketPairColorDLW.bracket-pair-color-dlw
- aaron-bond.better-comments
- mhutchie.git-graph
  
Nota: en la tuerquita marcar para todos los perfiles

## IA Id
- google.geminicodeassist
- Anthropic.claude-code
- tunakite03.codebase-memory-mcp

## Docker Id
- ms-azuretools.vscode-containers
- ms-vscode-remote.remote-containers
- ms-azuretools.vscode-docker
- formulahendry.docker-explorer

### Sprint Boot
- shengchen.vscode-checkstyle
- naumovs.color-highlight
- ryanluker.vscode-coverage-gutters
- vscjava.vscode-java-debug
- usernamehw.errorlens
- vscjava.vscode-java-pack
- vscjava.vscode-gradle
- oderwat.indent-rainbow
- redhat.java
- vscjava.vscode-lombok
- vscjava.vscode-maven
- vscjava.vscode-java-dependency
- vscjava.vscode-spring-boot-dashboard
- vmware.vscode-boot-dev-pack
- vmware.vscode-spring-boot
- vscjava.vscode-spring-initializr
- vscjava.vscode-java-test
- redhat.vscode-yaml

## Flutter
- gornivv.vscode-flutter-files
- steoates.autoimport
- Nash.awesome-flutter-snippets
- FelixAngelov.bloc
- Dart-Code.dart-code
- aziznal.dart-import-sorter
- usernamehw.errorlens
- Dart-Code.flutter
- circlecodesolution.ccs-flutter-color
- robert-brunhage.flutter-riverpod-snippets
- marcelovelasquez.flutter-tree
- alexisvt.flutter-snippets
- ms-vscode.vscode-typescript-next
- esbenp.prettier-vscode
- jeroen-meijer.pubspec-assist

## Golang
- golang.go
- casualjim.gotemplate

## Angular
- Angular.ng-template
- cyrilletuzi.angular-schematics
- JMGomes.angular-latest-snippets
- natewallace.angular2-inline
- steoates.autoimport
- usernamehw.errorlens
- dbaeumer.vscode-eslint
- xabikos.JavaScriptSnippets
- AykutSarac.jsoncrack-vscode
- esbenp.prettier-vscode
- YoavBls.pretty-ts-errors
- jeroen-meijer.pubspec-assist
- bradlc.vscode-tailwindcss
- pmneo.tsimporter


## Configuracion
```bash
{
  // Windows
  "window.zoomLevel": 0,
  // ignore recomendaciones
  "extensions.ignoreRecommendations": true,
  // Deshabilitar la pantalla de inicio
  "workbench.startupEditor": "none",
  // Breadcrumbs
  "breadcrumbs.enabled": false,
  // Editor
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
  // Git
  "git.openRepositoryInParentFolders": "never",
  "git.confirmSync": false,
  "git.enableSmartCommit": true,
  // Workbench mejorado
  //"workbench.iconTheme": "material-icon-theme",
  "vsicons.dontShowNewVersionMessage": true,
  "workbench.iconTheme": "material-icon-theme",
  "workbench.sideBar.location": "left",
  "workbench.editor.showTabs": "multiple",
  "workbench.statusBar.visible": true,
  "workbench.colorCustomizations": {
    "statusBar.background": "#121016",
    "statusBar.debuggingBackground": "#121016",
    "statusBar.debuggingForeground": "#525156",
    "debugToolBar.background": "#121016",
    "activityBar.background": "#1a1620",
    "titleBar.activeBackground": "#1a1620",
    "editor.lineHighlightBackground": "#1e1a25",
    "editor.lineHighlightBorder": "#1e1a25",
    "selection.background": "#2a2438",
    "editor.selectionBackground": "#302f34",
    "editor.background": "#000000"
  },
  // Terminal optimizado
  "terminal.integrated.fontSize": 12,
  "terminal.integrated.cursorBlinking": true,
  "terminal.integrated.defaultProfile.windows": "PowerShell",
  // Formato y Organización de Código
  "editor.formatOnSave": true,
  "editor.formatOnPaste": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": "always",
    "source.fixAll.eslint": "always",
    "source.fixAll": "always",
    "source.sortMembers": "always"
  },
  // Files y Explorador
  "files.autoSave": "afterDelay",
  "files.autoSaveDelay": 1000,
  "explorer.confirmDelete": false,
  "explorer.confirmDragAndDrop": false,
  "github.copilot.nextEditSuggestions.enabled": true,
  "workbench.colorTheme": "Tokyo Night",
}
```
