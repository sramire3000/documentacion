# Angular
The framework for building scalable web apps with confidence

### Versiones comunes y compatibilidad
```
Angular 16: Node.js 18.13+ o 20.9+
Angular 15: Node.js 14.20+, 16.13+, o 18.10+
Angular 14: Node.js 14.15+ o 16.10+
Angular 13: Node.js 12.20+ o 14.15+, o 16.10+
```

### Install Angular CLI
To install or update nvm, you should run the install script. To do that, you may either download and run the script manually, or use the following cURL or Wget command:

```bash
npm install -g @angular/cli
ng version
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
````bash
info:
  Node Version		: v22.14.0
  Angular CLI 		: 19.2.0
  Packet Manager	: 10.0.2
````

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







