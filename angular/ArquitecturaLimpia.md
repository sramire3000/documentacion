# ARQUITECTURA LIMPIA

### 📁 Estructura Profesional Recomendada
```bash
src/
│
├── app/
│   ├── core/
│   │   ├── config/
│   │   ├── guards/
│   │   ├── interceptors/
│   │   ├── services/
│   │   └── core.providers.ts
│   │
│   ├── shared/
│   │   ├── components/
│   │   ├── directives/
│   │   ├── pipes/
│   │   ├── ui/
│   │   └── shared.providers.ts
│   │
│   ├── layout/
│   │   ├── main-layout/
│   │   └── auth-layout/
│   │
│   ├── features/
│   │   ├── productos/
│   │   │   ├── pages/
│   │   │   ├── components/
│   │   │   ├── services/
│   │   │   ├── models/
│   │   │   ├── dto/
│   │   │   ├── state/
│   │   │   └── productos.routes.ts
│   │   │
│   │   ├── ventas/
│   │   ├── compras/
│   │   ├── inventario/
│   │   └── contabilidad/
│   │
│   ├── app.routes.ts
│   └── app.config.ts
│
├── environments/
│
└── main.ts
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




