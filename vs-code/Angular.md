# Visual Studio Code

## ANGULAR

### Extensions
- [Angular Language Service](https://marketplace.visualstudio.com/items?itemName=Angular.ng-template)
- [Angular Schematics](https://marketplace.visualstudio.com/items?itemName=cyrilletuzi.angular-schematics)
- [Angular Snippets (Version 18)](https://marketplace.visualstudio.com/items?itemName=johnpapa.Angular2)
- [Angular Snippets 2025 - v19](https://marketplace.visualstudio.com/items?itemName=JMGomes.angular-latest-snippets)
- [angular2-inline](https://marketplace.visualstudio.com/items?itemName=natewallace.angular2-inline)
- [Auto Close Tag](https://marketplace.visualstudio.com/items?itemName=formulahendry.auto-close-tag)
- [Auto Import](https://marketplace.visualstudio.com/items?itemName=steoates.autoimport)
- [Auto Rename Tag](https://marketplace.visualstudio.com/items?itemName=formulahendry.auto-rename-tag)
- [Better Comments](https://marketplace.visualstudio.com/items?itemName=aaron-bond.better-comments)
- [EditorConfig for VS Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig)
- [Error Lens](https://marketplace.visualstudio.com/items?itemName=usernamehw.errorlens)
- [GitHub Copilot](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot)
- [GitHub Copilot Chat](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot-chat)
- [JavaScript (ES6) code snippets](https://marketplace.visualstudio.com/items?itemName=xabikos.JavaScriptSnippets)
- [Lorem ipsum](https://marketplace.visualstudio.com/items?itemName=Tyriar.lorem-ipsum)
- [Material Icon Theme](https://marketplace.visualstudio.com/items?itemName=PKief.material-icon-theme)
- [Monokai Night Theme](https://marketplace.visualstudio.com/items?itemName=fabiospampinato.vscode-monokai-night)
- [Paste JSON as Code](https://marketplace.visualstudio.com/items?itemName=quicktype.quicktype)
- [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode)
- [Pretty TypeScript Errors](https://marketplace.visualstudio.com/items?itemName=yoavbls.pretty-ts-errors)
- [Tailwind CSS IntelliSense](https://marketplace.visualstudio.com/items?itemName=bradlc.vscode-tailwindcss)
- [Tokyo Night](https://marketplace.visualstudio.com/items?itemName=enkia.tokyo-night)
- [TypeScript Importer](https://marketplace.visualstudio.com/items?itemName=pmneo.tsimporter)
- [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)


### File settings.json
```
{
  // Angular & TypeScript
  "typescript.preferences.autoImportFileExcludePatterns": [
    "@angular/compiler",
    "rxjs/internal/**"
  ],
  "typescript.updateImportsOnFileMove.enabled": "always",
  "typescript.suggest.autoImports": true,
  
  // Formato
  "editor.formatOnSave": true,
  "editor.formatOnPaste": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": "always",
    "source.fixAll.eslint": "always"
  },
  
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
  "editor.tabSize": 2,
  "editor.insertSpaces": true,
  "editor.detectIndentation": true,
  "editor.wordWrap": "on",
  "editor.minimap.enabled": false,
  "editor.fontSize": 14,
  "editor.fontFamily": "'Fira Code', 'Cascadia Code', Consolas, 'Courier New', monospace",
  "editor.fontLigatures": true,
  "editor.mouseWheelZoom": true,
  "window.zoomLevel": 0,
  
  // Archivos
  "files.autoSave": "onFocusChange",
  "files.exclude": {
    "**/node_modules": true,
    "**/dist": true,
    "**/.angular": true
  },
  
  // Terminal
  "terminal.integrated.shellArgs.windows": ["-NoExit", "-Command", "ng version"],
  "terminal.integrated.fontSize": 14,
  
  // Workbench
  "workbench.iconTheme": "material-icon-theme",
  "workbench.colorTheme": "Default Dark Modern",
  
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














