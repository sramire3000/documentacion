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
```
core/
 ├── interceptors/   → JWT, errores
 ├── guards/         → auth, permisos
 ├── services/       → auth.service.ts
 ├── config/         → api.config.ts
```

Ejemplo:
``` TypeScript
// core/interceptors/auth.interceptor.ts
```
