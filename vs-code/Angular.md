# Visual Studio Code ANGULAR


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
- [Material Icon Theme](https://marketplace.visualstudio.com/items?itemName=PKief.material-icon-theme)
- [Monokai Night Theme](https://marketplace.visualstudio.com/items?itemName=fabiospampinato.vscode-monokai-night)

### Extenciones de GIT
- [GitHub Copilot app modernization](https://marketplace.visualstudio.com/items?itemName=vscjava.migrate-java-to-azure)
- [Git Graph](https://marketplace.visualstudio.com/items?itemName=mhutchie.git-graph)
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
  

  // Angular & TypeScript
  "typescript.preferences.autoImportFileExcludePatterns": [
    "@angular/compiler",
    "rxjs/internal/**"
  ],
  "typescript.updateImportsOnFileMove.enabled": "always",
  "typescript.suggest.autoImports": true,
  
  // Prettier
  "prettier.singleQuote": true,
  "prettier.trailingComma": "es5",
  "prettier.semi": true,
  "prettier.printWidth": 100,
  "prettier.tabWidth": 2,
  "[typescript]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
  },
  "[html]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
  },
  "[scss]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
  },
  
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

  // Formato
  "editor.formatOnSave": true,
  "editor.formatOnPaste": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": "always",
    "source.fixAll.eslint": "always"
  },

  // Git
  "git.openRepositoryInParentFolders": "never",
  "git.confirmSync": false,
  "git.enableSmartCommit": true,

  // Breadcrumbs
  "breadcrumbs.enabled": false,

  // Archivos
  "files.autoSave": "onFocusChange",
  "files.exclude": {
    "**/node_modules": true,
    "**/dist": true,
    "**/.angular": true
  },


  // Theme
  "workbench.colorTheme": "Monokai Night",

  
  // ESLint
  "eslint.validate": [
    "typescript",
    "javascript"
  ],
  
  // Extensiones específicas
  "angular.enable-strict-mode-prompt": false,
  "angular.view-engine": false,
  "angular.suggest.includeAutocompleteOptionalChain": true
}
```






























