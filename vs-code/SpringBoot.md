# Visual Studio Code SPRING BOOT

## Estructura de Carpetas
```bash
    PROYECTOS_EJEMPLO
        MICROSERVICIO 1
        MICROSERVICIO 2
```

## Instalar Extenciones 

### Crea un archivo a nivel de tu workspace "spring-boot-extensions.txt" 
```bash
alefragnani.project-manager
christian-kohler.path-intellisense
eamodio.gitlens
github.copilot-chat
gruntfuggly.todo-tree
humao.rest-client
ms-azuretools.vscode-containers
ms-azuretools.vscode-docker
oderwat.indent-rainbow
pkief.material-icon-theme
rangav.vscode-thunder-client
redhat.java
redhat.vscode-xml
redhat.vscode-yaml
ryanluker.vscode-coverage-gutters
shengchen.vscode-checkstyle
sonarsource.sonarlint-vscode
vmware.vscode-boot-dev-pack
vmware.vscode-spring-boot
vscjava.vscode-gradle
vscjava.vscode-java-debug
vscjava.vscode-java-dependency
vscjava.vscode-java-pack
vscjava.vscode-java-test
vscjava.vscode-lombok
vscjava.vscode-maven
vscjava.vscode-spring-boot-dashboard
vscjava.vscode-spring-initializr
```

### Para instalas ejecuta desde el PowerShell en el workspace
```
 cat extensions.txt | xargs -L 1 code --install-extension
```

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
  "editor.formatOnType": true,
  "editor.autoIndent": "full",
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
  "workbench.editor.highlightModifiedTabs": true,
  "workbench.editor.tabSizing": "shrink",
  // ========== EDITOR - TEXTO Y FUENTES ==========
  "editor.letterSpacing": 0.3,
  "editor.fontWeight": "450",
  "editor.cursorBlinking": "phase",
  "editor.cursorSmoothCaretAnimation": "on",
  "editor.cursorWidth": 2,
  "editor.smoothScrolling": true,
  // ========== EDITOR - VISUAL ==========
  "editor.minimap.scale": 2,
  "editor.minimap.showSlider": "always",
  "editor.guides.bracketPairsHorizontal": true,
  "editor.guides.highlightActiveIndentation": true,
  "editor.renderWhitespace": "boundary",
  "editor.renderControlCharacters": true,
  "editor.occurrencesHighlight": "multiFile",
  // ========== COLORES PERSONALIZADOS ==========
  // ========== TOKEN COLORS - SYNTAX HIGHLIGHTING ==========
  "editor.tokenColorCustomizations": {
    "textMateRules": [
      {
        "scope": "comment",
        "settings": {
          "fontStyle": "italic",
          "foreground": "#565f89"
        }
      },
      {
        "scope": "string",
        "settings": {
          "foreground": "#9ece6a"
        }
      },
      {
        "scope": "keyword, storage.type",
        "settings": {
          "foreground": "#bb9af7",
          "fontStyle": "bold"
        }
      },
      {
        "scope": "variable.other.object",
        "settings": {
          "foreground": "#e0af68"
        }
      },
      {
        "scope": "entity.name.type.class, entity.name.type.interface",
        "settings": {
          "foreground": "#7dcfff",
          "fontStyle": "bold"
        }
      },
      {
        "scope": "entity.name.function",
        "settings": {
          "foreground": "#7aa2f7"
        }
      },
      {
        "scope": "constant.numeric",
        "settings": {
          "foreground": "#ff9e64"
        }
      },
      {
        "scope": "annotation",
        "settings": {
          "foreground": "#f7768e",
          "fontStyle": "bold"
        }
      }
    ]
  },
  // ========== SEMANTIC HIGHLIGHTING ==========
  "editor.semanticTokenColorCustomizations": {
    "enabled": true,
    "rules": {
      "class": {
        "foreground": "#7dcfff",
        "fontStyle": "bold"
      },
      "interface": {
        "foreground": "#7dcfff",
        "fontStyle": "italic"
      },
      "enum": {
        "foreground": "#bb9af7"
      },
      "typeParameter": {
        "foreground": "#e0af68"
      },
      "method": {
        "foreground": "#7aa2f7"
      },
      "*.spring": {
        "foreground": "#9ece6a",
        "fontStyle": "bold"
      }
    }
  },
  // ========== TERMINAL VISUAL ==========
  "terminal.integrated.fontFamily": "'Fira Code', 'Cascadia Code', monospace",
  "terminal.integrated.cursorStyle": "line",
  "terminal.integrated.cursorWidth": 2,
  // ========== EXTENSIONES VISUALES ==========
  "errorLens.enabled": true,
  "errorLens.fontSize": "13px",
  "errorLens.enabledDiagnosticLevels": [
    "error",
    "warning",
    "info"
  ],
  "errorLens.gutterIconsEnabled": true,
  "errorLens.messageBackgroundMode": "message",
  "bracketPairColorizer.depreciation-notice": false,
  "indentRainbow.colors": [
    "rgba(255,255,255,0.05)",
    "rgba(255,255,255,0.1)",
    "rgba(255,255,255,0.15)",
    "rgba(255,255,255,0.2)"
  ],
  "todo-tree.general.tags": [
    "TODO",
    "FIXME",
    "BUG",
    "NOTE",
    "OPTIMIZE"
  ],
  "todo-tree.highlights.defaultHighlight": {
    "foreground": "#ff9e64",
    "background": "#ff9e6420",
    "icon": "checklist",
    "iconColor": "#ff9e64"
  },
  // ========== PRODUCTIVIDAD ==========
  "files.trimTrailingWhitespace": true,
  "files.insertFinalNewline": true,
  "files.trimFinalNewlines": true,
  // ========== GIT VISUAL ==========
  "git.decorations.enabled": true,
  "git.autofetch": true,
  "gitlens.hovers.currentLine.over": "line",
  "gitlens.currentLine.enabled": true,
  "gitlens.blame.avatars": true,
  // ========== SPRING BOOT ==========
  "spring.boot.dashboards": [
    {
      "name": "🚀 Local Dev - 8080",
      "port": 8080
    },
    {
      "name": "🧪 Test Env - 8081",
      "port": 8081
    }
  ],
  // ========== DEBUGGING VISUAL ==========
  "debug.onTaskErrors": "showErrors",
  "debug.internalConsoleOptions": "openOnSessionStart",
  "debug.console.fontSize": 14,
  "debug.console.lineHeight": 1.4,
  "maven.terminal.customEnv": [
    {
      "environmentVariable": "JAVA_HOME",
      "value": "/usr/lib/jvm/java-21-openjdk"
    }
  ],
  // ========== TESTING ==========
  "java.test.config": {
    "vmArgs": [
      "-Dspring.profiles.active=test",
      "-Xmx1024m"
    ]
  },
  // ========== CONFIGURACIONES ESPECÍFICAS POR LENGUAJE ==========
  "[java]": {
    "editor.defaultFormatter": "redhat.java",
    "editor.suggest.snippetsPreventQuickSuggestions": false,
    "editor.semanticHighlighting.enabled": true,
    "editor.codeLens": true,
    "editor.foldingImportsByDefault": true
  },
  "[json]": {
    "editor.defaultFormatter": "vscode.json-language-features",
    "editor.quickSuggestions": {
      "strings": true
    }
  },
  "[properties]": {
    "editor.defaultFormatter": "redhat.java",
    "editor.suggest.snippetsPreventQuickSuggestions": false
  },
  "[yaml]": {
    "editor.defaultFormatter": "redhat.vscode-yaml",
    "editor.tabSize": 2,
    "editor.autoIndent": "advanced"
  },
  "[xml]": {
    "editor.defaultFormatter": "redhat.vscode-xml"
  },
  // ========== JAVA CONFIGURATION ==========
  "java.compile.nullAnalysis.mode": "automatic",
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.debug.settings.onBuildFailureProceed": true,
  "java.saveActions.organizeImports": true,
  "java.completion.importOrder": [
    "java",
    "javax",
    "jakarta",
    "org.springframework",
    "org.hibernate",
    "lombok",
    "com",
    "#"
  ],
  "gitlens.ai.model": "vscode",
  "gitlens.ai.vscode.model": "copilot:gpt-4.1",
  "github.copilot.nextEditSuggestions.enabled": true,
}
```


























