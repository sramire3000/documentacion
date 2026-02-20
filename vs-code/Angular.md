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

### Archivo "settings.json"
```bash
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

## Instalar Paquetes
```bash
npm install @angular/cdk --save (Error)
npm install @angular/animations --save

npm install font-awesome --save
npm install moment --save
npm install jwt-decode --save
npm install sweetalert2 --save

npm install chart.js --save
npm install quill --save

ng add @angular-eslint/schematics
npm i prettier prettier-eslint eslint-config-prettier eslint-plugin-prettier -D
npm install --save-dev webpack-bundle-analyzer

# Angular 15.0.1
npm install primeng@15.0.0 --save (error)

#Para la version Ng 19.2.20
npm install primeng@^19 --save 
npm install primeicons @primeng/themes --save 

npm install primeicons --save 
npm install primeflex --save
```

## Configuraciones de Archivo en raiz


### Archivo ".prettierignore"
```bash
dist 
node_modules 
```

### Archivo ".gitignore"
```bash
# See https://docs.github.com/get-started/getting-started-with-git/ignoring-files for more about ignoring files.

# Compiled output
/dist
/tmp
/out-tsc
/bazel-out

# Node
/node_modules
npm-debug.log
yarn-error.log

# IDEs and editors
.idea/
.project
.classpath
.c9/
*.launch
.settings/
*.sublime-workspace

# Visual Studio Code
.vscode/*
!.vscode/settings.json
!.vscode/tasks.json
!.vscode/launch.json
!.vscode/extensions.json
.history/*

# Miscellaneous
/.angular/cache
.sass-cache/
/connect.lock
/coverage
/libpeerconnection.log
testem.log
/typings

# System files
.DS_Store
Thumbs.db
```



### Configuraciob de archivo "angular.json"
Solo adiciona "allowedCommonJsDependencies"
```bash
{
  "$schema": "./node_modules/@angular/cli/lib/config/schema.json",
  "version": 1,
  "newProjectRoot": "projects",
  "projects": {
    "angular-proyect1": {
      "projectType": "application",
      "schematics": {},
      "root": "",
      "sourceRoot": "src",
      "prefix": "app",
      "architect": {
        "build": {
          "builder": "@angular-devkit/build-angular:application",
          "options": {
            =======>
            "allowedCommonJsDependencies": ["sweetalert2", "crypto-js"],
            <=======
          }
        }
      }
    }
  }
}
```

### Archivo "tsconfig.app.json"
```bash
{
"compilerOptions": {
=======>
"resolveJsonModule": true,
"esModuleInterop": true,
<=======
},
```

### Configuracion del archivo "tsconfig.json"
solo adiciona la propiedad "resolveJsonModule"
```bash
{
"compilerOptions": {
  =======>
  "resolveJsonModule": true,
  <=======
  },
  "angularCompilerOptions": {
  =======>
  "strictDomEventTypes": false,
  <=======
  }
}
```

### Configuracion del archivo "package.json"
```bash
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build --configuration production --base-href ./",
    "watch": "ng build --watch --configuration development",
    "test": "ng test",
    "lint": "ng lint",
    "lint:fix": "ng lint --fix",
    "stats": "ng build --stats-json & webpack-bundle-analyzer dist/my-app/stats.json",
    "format": "prettier --write \"src/**/*.ts\" \"test/**/*.ts\"",
    "build:github": "npm run delete:docs && npm run build:href && npm run copy:dist",
    "delete:docs": "del docs",
    "copy:dist": "copyfiles dist/bases/* ./docs -f"
  },
```

## Despliegue Docker

### Bajar imagen Node
```bash
docker pull node:24.7.0-alpine
```

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

### Configuración del arcivo "Dockerfile.dev" para DEV
```bash
# =========================================
# Stage 1: Development - Angular Application
# =========================================

# Define the Node.js version to use (Alpine for a small footprint)
ARG NODE_VERSION=24.7.0-alpine

# Set the base image for development
FROM node:${NODE_VERSION} AS dev

# Set environment variable to indicate development mode
ENV NODE_ENV=development

# Set the working directory inside the container
WORKDIR /app

# Copy only the dependency files first to optimize Docker caching
COPY package.json package-lock.json ./

# Install dependencies using npm with caching to speed up subsequent builds
RUN --mount=type=cache,target=/root/.npm npm install

# Copy all application source files into the container
COPY . .

# Expose the port Angular uses for the dev server (default is 4200)
EXPOSE 4200

# Start the Angular dev server and bind it to all network interfaces
CMD ["npm", "start", "--", "--host=0.0.0.0"]

```

### Archivo docker-compose.yml
```
services:
  angular-prod:
    build:
      context: .
      dockerfile: Dockerfile
    image: docker-angular-arreconsa
    ports:
      - '8080:8080'

  angular-dev:
    build:
      context: .
      dockerfile: Dockerfile.dev
    deploy:
      resources:
        limits:
          memory: 2048M
        reservations:
          memory: 1024M
    ports:
      - '4200:4200'
    develop:
      watch:
        - action: sync
          path: .
          target: /app
```

### Ejecutar con docker compose dev container
```bash
docker compose watch angular-dev
```

### URL Dev
```bash
http://localhost:4200
```

### Configuración del arcivo "Dockerfile" para PRD
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

### URL Docker
```bash
http://200.31.171.18:9090/ArreconsaWeb/api/security/oauth/token
```

## Install librerias
```bash
npm install
```

### Run Proyect dev
```bash
npm run start
```

### Crear archivos de compilación para publicar
```bash
npm run build
```

















