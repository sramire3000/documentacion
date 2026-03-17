# Configuraciones de Visual Studio Code

### Goland



## Copilot

### Crear carpeta ".github"
### crear archivo "copilot-instructions.md"
### Ejemplo contenido
```bash
# GitHub Copilot Instructions - Sistema SaaS Multi-Empresas

## Descripción del Proyecto

Este es un sistema SaaS multi-cliente con módulos como:

- Contabilidad
- Inventario
- Compras
- Ventas
- Reportes
- Administración

El sistema está diseñado para ser escalable, seguro y fácil de usar, permitiendo a las empresas gestionar sus operaciones de manera eficiente.

---

# Frontend

- Angular CLI : 21.1.4
- Angular : 21.1.5
- Node.js : 22.22.0
- Package Manager : npm 11.10.1
- Standalone Components (NO usar NgModule)
- Signals preferidos sobre RxJS cuando sea posible
- Lazy loading obligatorio en módulos principales
- Arquitectura modular por feature

## Estructura

- feature/
- core/
- shared/
- layout/
- guards/
- services/
- models/
- dtos/

## Reglas estrictas

- NO usar any
- Siempre tipar interfaces
- Usar inject() en lugar de constructor injection cuando sea posible
- Separar DTOs de modelos de dominio
- Usar interceptores para JWT
- Guards para permisos por rol
- Manejo centralizado de errores
- Código limpio y SOLID

## Estilo

- SCSS
- ESLint estricto
- No duplicar lógica
- Métodos pequeños y reutilizables

## Comentarios adicionales

- Proyecto SaaS multi-Empresas
- Arquitectura limpia
- Angular standalone
- Código empresarial escalable

```

- [Agents](https://agents.md/)
