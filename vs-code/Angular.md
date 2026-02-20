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
  // Angular & TypeScript
  "typescript.preferences.autoImportFileExcludePatterns": [
    "@angular/compiler",
    "rxjs/internal/**"
  ],
  "typescript.updateImportsOnFileMove.enabled": "always", // Actualiza las importaciones automáticamente al mover archivos
  "typescript.suggest.autoImports": true, // Habilita las sugerencias de auto-importación

  // Prettier
  "prettier.singleQuote": true, //  Usar comillas simples
  "prettier.trailingComma": "es5", // Agregar comas al final de objetos, arrays, etc. (compatible con ES5)
  "prettier.semi": true, // Agregar punto y coma al final de las declaraciones
  "prettier.printWidth": 100, // Ancho máximo de línea antes de hacer un salto de línea
  "prettier.tabWidth": 2, // Número de espacios por tabulación
  "[typescript]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode" // Usar Prettier como formateador predeterminado para TypeScript
  },
  "[html]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode" // Usar Prettier como formateador predeterminado para HTML
  },
  "[scss]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode" // Usar Prettier como formateador predeterminado para SCSS
  },

  // Archivos angular
  "files.exclude": {
    "**/node_modules": true, // Excluir la carpeta node_modules
    "**/dist": true, // Excluir la carpeta dist
    "**/.angular": true // Excluir la carpeta .angular
  },

  // ESLint
  "eslint.validate": ["typescript", "javascript"] // Validar archivos TypeScript y JavaScript con ESLint
}

```

## Despliegue Docker

### Configuracion del archivo "nginx.conf"
```bash
worker_processes  1;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    
    server {
        listen       9090;
        server_name  localhost;
        
        # Configuración para servir la aplicación Angular con contexto /ArreconsaWeb
        location /ArreconsaWeb {
            # Directorio donde está la aplicación Angular
            alias /usr/share/nginx/html/angular-web-arreconsa;
            
            # Es importante establecer el tipo MIME para archivos estáticos
            index  index.html index.htm;
            
            # Reglas para el enrutamiento de Angular (SPA)
            try_files $uri $uri/ /ArreconsaWeb/index.html;
            
            # Opcional: configuración de caché para mejor rendimiento
            location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
                expires 1y;
                add_header Cache-Control "public, immutable";
            }
        }
        
        # Redirección de la raíz a la aplicación (opcional)
        location = / {
            return 301 /ArreconsaWeb/;
        }
        
        # Si necesitas acceder a la aplicación también desde la raíz (adicional)
        location / {
            root   /usr/share/nginx/html/angular-web-arreconsa;
            index  index.html index.htm;
            try_files $uri $uri/ /index.html;
        }
    }
}
```

### Configuración del arcivo "Dockerfile"
```bash
FROM nginx:1.27.2-alpine-slim
ENV TZ=America/El_Salvador

#Elimina el archivo de confguracion del nginx
RUN rm -fv /etc/nginx/nginx.conf

#Elimina html de distribucion
RUN rm -frv /usr/share/nginx/html/* && ls /usr/share/nginx/html/

#Copia la nueva configuracion del nginx
COPY nginx.conf /etc/nginx/nginx.conf

#Copy compilados Angular
COPY ./dist /usr/share/nginx/html

EXPOSE 9090

CMD ["nginx", "-g", "daemon off;"]
```










































