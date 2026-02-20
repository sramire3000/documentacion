# Visual Studio Code ANGULAR

## Configuracion de ".vscode"

### crea la carpeta en la termnal
````
mkdir .vscode
````

### crear el archivo de configuracion para extenciones
````
nano extensions.json
````

### Contenido del arhivo "extensions.json"
````
{
  // For more information, visit: https://go.microsoft.com/fwlink/?linkid=827846
  "recommendations": [
    "angular.ng-template",
    "johnpapa.angular2",
    "esbenp.prettier-vscode",
    "dbaeumer.vscode-eslint",
    "christian-kohler.path-intellisense",
    "steoates.autoimport",
    "bradlc.vscode-tailwindcss",
    "ecmel.vscode-html-css",
    "eamodio.gitlens",
    "usernamehw.errorlens",
    "rangav.vscode-thunder-client",
    "nrwl.angular-console"
  ]
}
````

### Extensions Generales
- [Auto Close Tag](https://marketplace.visualstudio.com/items?itemName=formulahendry.auto-close-tag)
- [Auto Import](https://marketplace.visualstudio.com/items?itemName=steoates.autoimport)
- [Bracket Pair Color DLW](https://marketplace.visualstudio.com/items?itemName=BracketPairColorDLW.bracket-pair-color-dlw)
- [Activitus Bar](https://marketplace.visualstudio.com/items?itemName=Gruntfuggly.activitusbar)
- [Better Comments](https://marketplace.visualstudio.com/items?itemName=aaron-bond.better-comments)
- [Error Lens](https://marketplace.visualstudio.com/items?itemName=usernamehw.errorlens)
- [JSON Crack](https://marketplace.visualstudio.com/items?itemName=AykutSarac.jsoncrack-vscode)
- [Lorem ipsum](https://marketplace.visualstudio.com/items?itemName=Tyriar.lorem-ipsum)
- [Paste JSON as Code](https://marketplace.visualstudio.com/items?itemName=quicktype.quicktype)
- [Pubspec Assist](https://marketplace.visualstudio.com/items?itemName=jeroen-meijer.pubspec-assist)
- [TODO Highlight](https://marketplace.visualstudio.com/items?itemName=wayou.vscode-todo-highlight)
- [Auto Rename Tag](https://marketplace.visualstudio.com/items?itemName=formulahendry.auto-rename-tag)

### Code Format
- [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode)
- [Pretty TypeScript Errors](https://marketplace.visualstudio.com/items?itemName=yoavbls.pretty-ts-errors)
- [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)

### TypeScript
- [JavaScript (ES6) code snippets](https://marketplace.visualstudio.com/items?itemName=xabikos.JavaScriptSnippets)
- [TypeScript Importer](https://marketplace.visualstudio.com/items?itemName=pmneo.tsimporter)


### Theme
- [visualcode-icons](https://marketplace.visualstudio.com/items?itemName=vscode-icons-team.vscode-icons)

### Extenciones de GIT
- [GitHub Copilot](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot)
- [GitHub Copilot Chat](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot-chat)

### Angular Global
- [Angular Language Service](https://marketplace.visualstudio.com/items?itemName=Angular.ng-template)
- [Angular Schematics](https://marketplace.visualstudio.com/items?itemName=cyrilletuzi.angular-schematics)
- [angular2-inline](https://marketplace.visualstudio.com/items?itemName=natewallace.angular2-inline)

### Angular 17
- [Angular 17 Snippets](https://marketplace.visualstudio.com/items?itemName=Mikael.Angular-BeastCode)

### Angular 18
- [Angular Snippets (Version 18)](https://marketplace.visualstudio.com/items?itemName=johnpapa.Angular2)

### Angular 19
- [Angular Snippets 2025 - v19](https://marketplace.visualstudio.com/items?itemName=JMGomes.angular-latest-snippets)

### Angular 20
- [Tailwind CSS IntelliSense](https://marketplace.visualstudio.com/items?itemName=bradlc.vscode-tailwindcss)

### File settings.json
```
{
  // Windows
  "window.zoomLevel": 1,
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
  "workbench.iconTheme": "vscode-icons",
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
    "editor.background": "#000000",
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
    "source.sortMembers": "always",
  },
  // Files y Explorador
  "files.autoSave": "afterDelay",
  "files.autoSaveDelay": 1000,
  "explorer.confirmDelete": false,
  "explorer.confirmDragAndDrop": false,
  "github.copilot.nextEditSuggestions.enabled": true,

  // Angular & TypeScript
  "typescript.preferences.autoImportFileExcludePatterns": ["@angular/compiler", "rxjs/internal/**"],
  "typescript.updateImportsOnFileMove.enabled": "always",
  "typescript.suggest.autoImports": true,

  // Prettier
  "prettier.singleQuote": true,
  "prettier.trailingComma": "es5",
  "prettier.semi": true,
  "prettier.printWidth": 100,
  "prettier.tabWidth": 2,
  "[typescript]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode",
  },
  "[html]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode",
  },
  "[scss]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode",
  },

  // Archivos angular
  "files.exclude": {
    "**/node_modules": true,
    "**/dist": true,
    "**/.angular": true,
  },

  // ESLint
  "eslint.validate": ["typescript", "javascript"],

  // Extensiones específicas
  "angular.enable-strict-mode-prompt": false,
  "github.copilot.enable": {
    "*": true,
    "plaintext": false,
    "markdown": true,
    "scminput": false,
  },
}
```

































