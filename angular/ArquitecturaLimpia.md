# ARQUITECTURA LIMPIA

### 📁 Estructura Profesional Recomendada
```bash
src/
│
├── app/
│   │
│   ├── core/                        # Solo una vez en toda la app
│   │   ├── auth/
│   │   │   ├── auth.service.ts
│   │   │   ├── auth.facade.ts
│   │   │   └── auth.model.ts
│   │   │
│   │   ├── guards/
│   │   │   ├── auth.guard.ts
│   │   │   └── role.guard.ts
│   │   │
│   │   ├── interceptors/
│   │   │   ├── auth.interceptor.ts
│   │   │   ├── error.interceptor.ts
│   │   │   └── tenant.interceptor.ts
│   │   │
│   │   ├── config/
│   │   │   ├── api.config.ts
│   │   │   └── app.config.ts
│   │   │
│   │   ├── layout/
│   │   │   ├── main-layout/
│   │   │   └── auth-layout/
│   │   │
│   │   └── core.providers.ts
│   │
│   ├── shared/                      # Reutilizable y genérico
│   │   ├── components/
│   │   │   ├── data-table/
│   │   │   ├── confirm-dialog/
│   │   │   ├── modal/
│   │   │   └── loading-spinner/
│   │   │
│   │   ├── ui/                      # Wrappers de librerías UI
│   │   ├── pipes/
│   │   ├── directives/
│   │   ├── validators/
│   │   └── shared.providers.ts
│   │
│   ├── features/                    # Aquí vive el negocio real
│   │
│   │   ├── productos/
│   │   │   ├── domain/
│   │   │   │   ├── models/
│   │   │   │   │   └── producto.model.ts
│   │   │   │   ├── repositories/
│   │   │   │   │   └── producto.repository.ts
│   │   │   │   └── mappers/
│   │   │   │       └── producto.mapper.ts
│   │   │   │
│   │   │   ├── application/
│   │   │   │   ├── use-cases/
│   │   │   │   │   ├── crear-producto.usecase.ts
│   │   │   │   │   ├── listar-productos.usecase.ts
│   │   │   │   │   └── actualizar-producto.usecase.ts
│   │   │   │   └── dto/
│   │   │   │       ├── producto-request.dto.ts
│   │   │   │       └── producto-response.dto.ts
│   │   │   │
│   │   │   ├── infrastructure/
│   │   │   │   ├── producto-http.repository.ts
│   │   │   │   └── producto.api.ts
│   │   │   │
│   │   │   ├── presentation/
│   │   │   │   ├── pages/
│   │   │   │   │   ├── productos-list.page.ts
│   │   │   │   │   └── producto-form.page.ts
│   │   │   │   ├── components/
│   │   │   │   │   └── producto-form.component.ts
│   │   │   │   └── state/
│   │   │   │       └── productos.store.ts
│   │   │   │
│   │   │   └── productos.routes.ts
│   │   │
│   │   ├── ventas/
│   │   ├── compras/
│   │   ├── inventario/
│   │   ├── contabilidad/
│   │   └── administracion/
│   │
│   ├── app.routes.ts
│   ├── app.config.ts
│   └── main.ts
│
├── environments/
│   ├── environment.ts
│   └── environment.prod.ts
│
├── assets/
└── styles/
```

### 🔵 1️⃣ Core (Solo una vez en la app)
```bash
core/
 ├── interceptors/   → JWT, errores
 ├── guards/         → auth, permisos
 ├── services/       → auth.service.ts
 ├── config/         → api.config.ts
```

Ejemplo:
```bash
// core/interceptors/auth.interceptor.ts
```
Aquí va el token JWT para todas las requests.

⚠ Nunca poner lógica de negocio aquí.

### 🟢 2️⃣ Shared (Reutilizable)

Componentes genéricos:
```bash
shared/
 ├── components/
 │     ├── data-table/
 │     ├── confirm-dialog/
 │
 ├── pipes/
 ├── directives/
```
Ejemplo:
```
<app-data-table />
```

### 🟡 3️⃣ Features (Arquitectura por módulo funcional)

Aquí vive el negocio real.
Ejemplo: productos
```bash
features/productos/
 ├── pages/
 │     ├── productos-list.page.ts
 │     ├── producto-form.page.ts
 │
 ├── components/
 │     ├── producto-form.component.ts
 │
 ├── services/
 │     └── productos.service.ts
 │
 ├── models/
 │     └── producto.model.ts
 │
 ├── dto/
 │     └── producto-response.dto.ts
 │
 ├── state/
 │     └── productos.signal-store.ts
 │
 └── productos.routes.ts
```

## 🧠 Cómo debe estar dividido internamente

📄 Modelo (Dominio frontend)
```bash
export interface Producto {
  id: string;
  nombre: string;
  precio: number;
  stock: number;
}
```

📄 DTO (Lo que viene del backend)
```bash
export interface ProductoResponseDTO {
  id: string;
  nombre: string;
  precio: number;
  stock: number;
}
```

📄 Servicio (Caso de uso)
```bash
@Injectable({ providedIn: 'root' })
export class ProductosService {

  private http = inject(HttpClient);

  obtenerTodos() {
    return this.http.get<ProductoResponseDTO[]>('/api/productos');
  }
}
```

📄 Página (Solo UI + coordinación)
```bash
@Component({
  standalone: true,
  templateUrl: './productos-list.page.html'
})
export class ProductosListPage {

  private service = inject(ProductosService);

  productos = signal<Producto[]>([]);

  ngOnInit() {
    this.service.obtenerTodos().subscribe(data => {
      this.productos.set(data);
    });
  }
}
```

🚀 Rutas Lazy Loading
```
// app.routes.ts
export const routes: Routes = [
  {
    path: 'productos',
    loadChildren: () =>
      import('./features/productos/productos.routes')
        .then(m => m.PRODUCTOS_ROUTES)
  }
];
```

## 🎯 Reglas de Código Limpio
- Componentes pequeños
- Servicios con responsabilidad única
- Nada de any
- DTO ≠ Modelo
- No mezclar lógica de negocio con UI
- Nombres claros
- Métodos cortos

## 🔥 Versión Enterprise (si quieres subir nivel)
Puedes agregar:
- state/ con signals
- domain/ para lógica compleja
- mappers/
- facades/
- Arquitectura tipo Clean frontend

## 📌 Si lo aplicas a tu SaaS

Tu sistema quedaría:
```bash
features/
 ├── contabilidad/
 ├── inventario/
 ├── compras/
 ├── ventas/
 └── administracion/
```
