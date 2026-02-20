# Visual Studio Code ANGULAR

## Configuracion de ".vscode"

### Crea la carpeta en la terminal
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
    "nrwl.angular-console",
    "formulahendry.auto-close-tag",
    "bracketpaircolordlw.bracket-pair-color-dlw",
    "gruntfuggly.activitusbar",
    "aaron-bond.better-comments",
    "aykutsarac.jsoncrack-vscode",
    "tyriar.lorem-ipsum",
    "quicktype.quicktype",
    "jeroen-meijer.pubspec-assist",
    "wayou.vscode-todo-highlight",
    "formulahendry.auto-rename-tag",
    "yoavbls.pretty-ts-errors",
    "pkief.material-icon-theme",
    "github.copilot-chat"
  ]
}
````

### crear el archivo de ejecución
````
nano launch.json
````


### Contenidode  Archivo "launch.json"
````
{
  // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
  "version": "0.2.0",
  "configurations": [
    {
      "name": "ng serve",
      "type": "chrome",
      "request": "launch",
      "preLaunchTask": "npm: start",
      "url": "http://localhost:4200/"
    },
    {
      "name": "ng test",
      "type": "chrome",
      "request": "launch",
      "preLaunchTask": "npm: test",
      "url": "http://localhost:9876/debug.html"
    }
  ]
}
````

### Creación del archivo de tareas
````
nano tasks.json
````

### Contenido del archivo de tareas
````
{
  // For more information, visit: https://go.microsoft.com/fwlink/?LinkId=733558
  "version": "2.0.0",
  "tasks": [
    {
      "type": "npm",
      "script": "start",
      "isBackground": true,
      "problemMatcher": {
        "owner": "typescript",
        "pattern": "$tsc",
        "background": {
          "activeOnStart": true,
          "beginsPattern": {
            "regexp": "(.*?)"
          },
          "endsPattern": {
            "regexp": "bundle generation complete"
          }
        }
      }
    },
    {
      "type": "npm",
      "script": "test",
      "isBackground": true,
      "problemMatcher": {
        "owner": "typescript",
        "pattern": "$tsc",
        "background": {
          "activeOnStart": true,
          "beginsPattern": {
            "regexp": "(.*?)"
          },
          "endsPattern": {
            "regexp": "bundle generation complete"
          }
        }
      }
    }
  ]
}
````

## Archivo de configuracion Settings

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









































