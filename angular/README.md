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
  Node Version		: v22.14.0
  Angular CLI 		: 19.2.0
  Packet Manager	: 10.0.2
```
### Si se esta usando nvm
```bash
nvm use v22.14.0
```

### Create project
```bash
ng new angular-primeng
	  stylesheet format	: Sass
	  Server Renderin   : no
```
### Install dependencia
```bash
npm install primeng @primeuix/themes
```

### Edit "src/app/app.config.ts" add
```bash
	import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
	import { providePrimeNG } from 'primeng/config';
	import Aura from '@primeuix/themes/aura';

    providers: [
    	provideAnimationsAsync(),
        providePrimeNG({
            theme: {
                preset: Aura,
                options: {
                  cssLayer: {
                      name: 'primeng',
                      order: 'tailwind-base, primeng, tailwind-utilities'
                  }
              }				
            }
        })
    ]
```

### Probar PrimeNg
```bash
  ng g c components/pruebas --skip-tests
```

### Modificar src/app/app.routes.ts
```bash
export const routes: Routes = [
  {
    path : '',
    redirectTo: 'pruebas',
    pathMatch: 'full'
  },
  {
    path: 'pruebas',
    component: Pruebas
  }
];
```

### edit src/app/components/pruebas/pruebas.html
```bash
<div class="card flex justify-center">
  <p-button label="Check" />
</div>
```

### edit src/app/components/pruebas/pruebas.ts
```bash
import { ButtonModule } from 'primeng/button';

@Component({
    imports: [ButtonModule]
})
```

### edit src/app/app.html
```bash
<router-outlet />
```

### Subir server
```bash
  ng serve -o
```

### install icons
```bash
npm install primeicons
```

### Install plugin
```bash
npm i tailwindcss-primeui
```

### Install Tailwind css
```bash
npm install tailwindcss @tailwindcss/postcss postcss --force
```

### Create .postcssrc.json en raiz del proyecto
```bash
{
  "plugins": {
    "@tailwindcss/postcss": {}
  }
}
```

### edit src/styles.scss
```bash
@use "tailwindcss";
@use "primeicons/primeicons.css";
@plugin "tailwindcss-primeui";
@layer tailwind, primeng;
```

### edit src/app/components/pruebas/pruebas.html
```bash
<h1 class="text-3xl font-bold underline">
  Hello world!
</h1>
```









