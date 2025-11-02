# Angular
The framework for building scalable web apps with confidence

### Versiones comunes y compatibilidad
```

Angular	Node.js	             TypeScript	npm	   Fecha Lanzamiento	Estado
v20	    18.19+ / 20.10+ / 22.0+	5.6+	8.11+  Mayo 2025	        PRÓXIMA VERSIÓN
v19	    18.19+ / 20.10+ / 22.0+	5.4+	8.11+  Nov 2024	            ÚLTIMA ESTABLE
v18	    18.19+ / 20.10+	        5.4+	8.11+  Mayo 2024	        LTS (hasta 2026)
v17	    18.13+ / 20.9+	        5.2+	8.11+  Nov 2023	            LTS (hasta 2025)
v16	    16.14+ / 18.10+	        4.9+	8.11+  Mayo 2023	        Fin soporte
```

### Install Angular CLI
To install or update nvm, you should run the install script. To do that, you may either download and run the script manually, or use the following cURL or Wget command:

### Install ultima version
```bash
npm install -g @angular/cli
ng version
```

### Install version especifica
```bash
npm install -g @angular/cli@<version>

Ejemplo:
  npm install -g @angular/cli@14.2.0
  npm install -g @angular/cli@15.1.0
  npm install -g @angular/cli@16.0.0
```

### Create Proyect
```bash
ng new <project-name>
```
### Install Dependencias
```bash
npm install
```

### Run
```bash
npm start
```

# Angular + PrimeNg + Tailwind
```bash
info:
  Angular CLI: 20.3.8
  Node: 24.3.0
  Package Manager: npm 11.4.2
  OS: linux x64
```
### Install Node / Angular
```bash
npm unistall -g @angular/cli
nvm list
nvm install v24.3.0 
nvm use v24.3.0
npm install -g @angular/cli@20.3.8
```
## Crear proyecto Angular
```bash 
ng new angular20-primeng-tallwind4
   StyleSheet format : CSS
   Prerendering          	: No
   Zoneless                 : Yes
   Practices					: claude, github copilot, Geminit 
   
cd angular20-primeng-tallwind4
ng serve --open
```

## Install Tallwind Css 4
### Install paquete de TailwindCss4
```bash
npm install tailwindcss @tailwindcss/postcss postcss --force
```

### Crear archivo en raiz  .postcssrc.json
```bash
{
  "plugins": {
    "@tailwindcss/postcss": {}
  }
}
```

### Add an @import to ./src/styles.css that imports Tailwind CSS.
```bash
@import "tailwindcss";
```

### Test good install tallwind ./src/app/app.html
```
<router-outlet />
<h1 class="text-3xl font-bold underline bg-amber-500">
  Hello world!
</h1>
```
### Test Run Tallwind
```bash
ng serve --open
```





