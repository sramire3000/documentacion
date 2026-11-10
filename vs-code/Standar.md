# Configuración para WORKSPACE

## Plugins standar x Id (No requiere tema)

- shd101wyy.markdown-preview-enhanced
- yzane.markdown-pdf
- pomdtr.excalidraw-editor
- nadomani.excalidraw-copilot
- redhat.vscode-xml
- thang-nm.flow-icons
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

## Git
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
// ============================================================================
// settings.json — PERFIL "NOCHE" (tema oscuro, gris azulado, sin negro puro)
// ============================================================================
// Base #1B1D23 (evita la halación del #000000) · texto #C9D1D9 10.9:1
// Comentarios #7A8595 4.5:1 · acentos 6:1–9:1 · todo ≥ WCAG AA.
//
// Uso:
//   1. Copia TODO el objeto de abajo en tu settings.json de usuario.
//   2. Para alternar día/noche sin pisar colores, crea dos perfiles de VS Code
//      (Ctrl+Shift+P > "Profiles: Create Profile"). Los colorCustomizations
//      son por perfil.
//   3. Los colores de SINTAXIS dependen del tema: instala "Tokyo Night",
//      "One Dark Pro", "GitHub Dark Dimmed" o "Catppuccin Mocha".
// ============================================================================
{
  // ---------- Ventana ----------
  "window.zoomLevel": 0,
  "workbench.startupEditor": "none",
  "extensions.ignoreRecommendations": false,
  "workbench.sideBar.location": "left",
  "workbench.editor.showTabs": "multiple",
  "workbench.statusBar.visible": true,
  "workbench.iconTheme": "flow-deep",
  "breadcrumbs.enabled": true,
  "breadcrumbs.filePath": "on",
  // ---------- Editor ----------
  "editor.minimap.enabled": false,
  "editor.scrollbar.vertical": "hidden",
  "editor.overviewRulerBorder": false,
  "editor.hideCursorInOverviewRuler": true,
  "editor.guides.indentation": true,
  "editor.guides.bracketPairs": "active",
  "editor.glyphMargin": true,
  "editor.fontSize": 15,
  "editor.lineHeight": 1.5,
  "editor.fontFamily": "'JetBrains Mono', 'Cascadia Code', Consolas, monospace",
  "editor.fontLigatures": true,
  "editor.letterSpacing": 0.3,
  "editor.cursorBlinking": "smooth",
  "editor.cursorSmoothCaretAnimation": "on",
  "editor.smoothScrolling": true,
  "editor.stickyScroll.enabled": true,
  "editor.wordWrap": "off",
  "editor.wordWrapColumn": 120,
  "editor.matchBrackets": "always",
  "editor.mouseWheelZoom": true,
  "editor.tabSize": 2,
  "editor.insertSpaces": true,
  "editor.detectIndentation": false,
  "editor.bracketPairColorization.enabled": true,
  "editor.semanticHighlighting.enabled": true,
  "editor.inlineSuggest.enabled": true,
  "editor.suggest.snippetsPreventQuickSuggestions": false,
  "editor.renderLineHighlight": "all",
  "editor.selectionHighlight": true,
  "editor.renderWhitespace": "selection",
  // ---------- Formato ----------
  "editor.formatOnSave": true,
  "editor.formatOnPaste": false,
  "editor.codeActionsOnSave": {
    "source.organizeImports": "explicit",
    "source.fixAll": "explicit"
  },
  // ---------- Archivos ----------
  "files.autoSave": "onFocusChange",
  "files.eol": "\n",
  "files.trimTrailingWhitespace": true,
  "files.insertFinalNewline": true,
  "files.trimFinalNewlines": true,
  "explorer.confirmDelete": false,
  "explorer.confirmDragAndDrop": false,
  // ---------- Git ----------
  "git.openRepositoryInParentFolders": "never",
  "git.confirmSync": false,
  "git.enableSmartCommit": false,
  "git.autofetch": true,
  // ---------- Terminal ----------
  "terminal.integrated.fontSize": 13,
  "terminal.integrated.fontFamily": "'JetBrains Mono', 'Cascadia Code', monospace",
  "terminal.integrated.lineHeight": 1.2,
  "terminal.integrated.cursorBlinking": true,
  "terminal.integrated.scrollback": 10000,
  "terminal.integrated.defaultProfile.windows": "Git Bash",
  "terminal.integrated.defaultProfile.linux": "bash",
  // ---------- Por lenguaje ----------
  "[xml]": {
    "editor.tabSize": 4
  },
  "[yaml]": {
    "editor.tabSize": 2
  },
  "[json][jsonc]": {
    "editor.tabSize": 2
  },
  "[markdown]": {
    "editor.wordWrap": "on",
    "editor.tabSize": 2
  },
  // ---------- Colores ----------
  "workbench.colorCustomizations": {
    // Base
    "editor.background": "#1B1D23",
    "editor.foreground": "#C9D1D9",
    "editor.lineHighlightBackground": "#22252D",
    "editor.lineHighlightBorder": "#00000000",
    "editor.selectionBackground": "#2F3540",
    "editor.inactiveSelectionBackground": "#272B33",
    "editor.selectionHighlightBackground": "#2F354080",
    "selection.background": "#3A4150",
    "editorCursor.foreground": "#7DCFFF",
    "editorWhitespace.foreground": "#2A2E37",
    "editorIndentGuide.background1": "#24272F",
    "editorIndentGuide.activeBackground1": "#3B404D",
    // Colores de brackets
    "editorBracketHighlight.foreground1": "#7AA2F7",
    "editorBracketHighlight.foreground2": "#BB9AF7",
    "editorBracketHighlight.foreground3": "#73DACA",
    "editorBracketHighlight.foreground4": "#E0AF68",
    "editorBracketHighlight.foreground5": "#F7768E",
    "editorBracketHighlight.foreground6": "#9ECE6A",
    "editorBracketHighlight.unexpectedBracket.foreground": "#F7768E",
    // Diagnóstico y gutter
    "editorGutter.background": "#1B1D23",
    "editorGutter.addedBackground": "#73DACA",
    "editorGutter.modifiedBackground": "#7AA2F7",
    "editorGutter.deletedBackground": "#F7768E",
    "editorError.foreground": "#F7768E",
    "editorWarning.foreground": "#E0AF68",
    "editorInfo.foreground": "#7DCFFF",
    // Chrome de la ventana
    "activityBar.background": "#15171D",
    "activityBar.foreground": "#C9D1D9",
    "activityBar.inactiveForeground": "#6B7684",
    "activityBarBadge.background": "#7AA2F7",
    "activityBarBadge.foreground": "#15171D",
    "titleBar.activeBackground": "#15171D",
    "titleBar.activeForeground": "#C9D1D9",
    "titleBar.inactiveBackground": "#15171D",
    "titleBar.inactiveForeground": "#6B7684",
    "statusBar.background": "#15171D",
    "statusBar.foreground": "#C8CDD8",
    "statusBar.noFolderBackground": "#15171D",
    "statusBar.debuggingBackground": "#2A1F33",
    "statusBar.debuggingForeground": "#E5DEFF",
    "statusBarItem.remoteBackground": "#7AA2F7",
    "statusBarItem.remoteForeground": "#15171D",
    "debugToolBar.background": "#1B1D23",
    "debugToolBar.border": "#2A2E37",
    // Barra lateral y panel
    "sideBar.background": "#17191F",
    "sideBar.foreground": "#C0C8D4",
    "sideBar.border": "#24272F",
    "sideBarTitle.foreground": "#9AA4B2",
    "sideBarSectionHeader.background": "#15171D",
    "sideBarSectionHeader.foreground": "#9AA4B2",
    "panel.background": "#17191F",
    "panel.border": "#24272F",
    "terminal.background": "#1B1D23",
    "terminal.foreground": "#C9D1D9",
    "terminalCursor.foreground": "#7DCFFF",
    // Pestañas y widgets
    "editorGroupHeader.tabsBackground": "#15171D",
    "editorGroup.border": "#24272F",
    "tab.activeBackground": "#1B1D23",
    "tab.activeForeground": "#C9D1D9",
    "tab.inactiveBackground": "#15171D",
    "tab.inactiveForeground": "#7A8595",
    "tab.activeBorderTop": "#7AA2F7",
    "tab.border": "#15171D",
    "editorWidget.background": "#20232B",
    "editorWidget.border": "#2A2E37",
    "editorSuggestWidget.background": "#20232B",
    "editorSuggestWidget.selectedBackground": "#2F3540",
    "editorHoverWidget.background": "#20232B",
    "editorHoverWidget.border": "#2A2E37",
    "editor.findMatchBackground": "#3E4B6B",
    "editor.findMatchHighlightBackground": "#2F354080",
    "list.activeSelectionBackground": "#2A2F3A",
    "list.activeSelectionForeground": "#C9D1D9",
    "list.hoverBackground": "#22252D",
    "list.inactiveSelectionBackground": "#22252D",
    "focusBorder": "#3B404D",
    // Git en el explorador
    "gitDecoration.modifiedResourceForeground": "#E0AF68",
    "gitDecoration.untrackedResourceForeground": "#73DACA",
    "gitDecoration.deletedResourceForeground": "#F7768E",
    "gitDecoration.conflictingResourceForeground": "#FF9E64",
    "gitDecoration.ignoredResourceForeground": "#5C6674",
    // Scrollbar
    "scrollbarSlider.background": "#3B404D60",
    "scrollbarSlider.hoverBackground": "#3B404D90",
    "scrollbarSlider.activeBackground": "#4A5060A0"
  },
  // ---------- Sintaxis manual (XML / properties / JSON; en Java manda el language server) ----------
  "editor.tokenColorCustomizations": {
    "textMateRules": [
      {
        "scope": [
          "comment",
          "punctuation.definition.comment"
        ],
        "settings": {
          "foreground": "#7A8595",
          "fontStyle": "italic"
        }
      },
      {
        "scope": [
          "keyword",
          "storage"
        ],
        "settings": {
          "foreground": "#BB9AF7"
        }
      },
      {
        "scope": [
          "string"
        ],
        "settings": {
          "foreground": "#9ECE6A"
        }
      },
      {
        "scope": [
          "constant.numeric",
          "constant.language"
        ],
        "settings": {
          "foreground": "#FF9E64"
        }
      },
      {
        "scope": [
          "entity.name.function",
          "support.function"
        ],
        "settings": {
          "foreground": "#7AA2F7"
        }
      },
      {
        "scope": [
          "entity.name.type",
          "entity.name.class",
          "support.class"
        ],
        "settings": {
          "foreground": "#E0AF68"
        }
      },
      {
        "scope": [
          "entity.name.tag",
          "meta.tag"
        ],
        "settings": {
          "foreground": "#F7768E"
        }
      },
      {
        "scope": [
          "meta.decorator",
          "entity.name.function.decorator"
        ],
        "settings": {
          "foreground": "#E0AF68"
        }
      }
    ]
  },
  "flow-icons.licenseKey": ""
}

```
