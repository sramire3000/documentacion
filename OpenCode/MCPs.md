# Install MCP's


## Context 7

Context7 incorpora documentación actualizada y ejemplos de código específicos de cada versión directamente a tu asistente de programación con IA. Esto significa que ya no tendrás que lidiar con código obsoleto ni con APIs confusas.

-[Context7](https://context7.com)

### Install

```
npx ctx7 setup
```
Pasos:
- MCP Server
- OpenCode
- Autorizar browser

### Example in open opencode test
```
Necesito que investigues cual es la manera de proteccion de rutas en Next.js usa context7 MCP
```

### Add file configuration "AGENTS.md" in proyect
```
## MCPs
- Usa Context7 MCP para traer la documentación actualizada del framework en vez de fiarte del entrenamiento.
```

## Playwright
Playwright Test es un marco de pruebas integral para aplicaciones web modernas. Incluye ejecutor de pruebas, aserciones, aislamiento, paralelización y herramientas avanzadas. Playwright es compatible con Chromium, WebKit y Firefox en Windows, Linux y macOS, tanto localmente como en integración continua (CI), con o sin interfaz gráfica, y ofrece emulación móvil nativa para Chrome (Android) y Mobile Safari.

- [Playwright Test](https://playwright.dev)


### Install Abrir opencode y colocar
```
Necesitamos instalar el MCP de Playwright localmente
{
  "mcpServers": {
    "playwright": {
      "command": "npx",
      "args": ["@playwright/mcp@latest"]
    }
  }
}
```

### Add a .gitignore
```
# Playwright
.playwright-mcp
.playwright-mcp/*
```

### Version
```
npx playwright --version
```

### Abrir opencode
```
Puedes tomar una Screeshot?
```

### Add file configuration "AGENTS.md" in proyect
```
## MCPs
- Playwright está configurado en `opencode.json`. Cualquier screenshot o salida relacionada con Playwright debe ir en `.playwright-mcp/` (ignorada por git).
```

### Install chromium
```
npx playwright install chromium
npx playwright test
```


## opencode.json Global

### Fedora 44
```
{
  "command": {
    "spec": {
      "description": "Crea especificaciones de pantallas y funcionalidades",
      "template": "Carga y sigue la skill spec en .agents/skills/spec/SKILL.md.\n\nFeature: $ARGUMENTS"
    }
  },
  "mcp": {
    "servers": {
      "playwright": {
        "type": "local",
        "command": [
          "npx",
          "-y",
          "@playwright/mcp@0.0.83",
          "--executable-path",
          "/home/hsr/.cache/ms-playwright/chromium-1243/chrome-linux64/chrome"
        ]
      },
      "context7": {
        "type": "remote",
        "url": "https://mcp.context7.com/mcp",
        "enabled": true,
        "headers": {
          "Authorization": "Bearer ctx7sk-0ecf9a75-ed00-4c21-b346-644427ae5b42"
        }
      },
      "codebase-memo": {
        "type": "local",
        "command": ["/home/hsr/.local/bin/codebase-memory-mcp"],
        "enabled": true
      }
    }
  }
}
```

### Windows
```
{
  "$schema": "https://opencode.ai/config.json",
{
  "command": {
    "spec": {
      "description": "Crea especificaciones de pantallas y funcionalidades",
      "template": "Carga y sigue la skill spec en .agents/skills/spec/SKILL.md.\n\nFeature: $ARGUMENTS"
    }
  },
  "mcp": {
    "playwright": {
        "type": "local",
        "command": [
          "npx",
          "-y",
          "@playwright/mcp@0.0.83"
        ]
    },      
    "context7": {
      "type": "remote",
      "url": "https://mcp.context7.com/mcp",
      "enabled": true,
      "headers": {
        "Authorization": "Bearer ctx7sk-c8074cce-5499-4ac6-87f1-7506c91e61a2"
      }
    },
    "codebase-memo": {
      "type": "local",
      "command": [
        "C:\\Users\\HSR\\.local\\bin\\codebase-memory-mcp.exe"
      ],
      "enabled": true
    }
  }
}
```

## PostgresSQL

### Add user Readonly

```
-- 1. Crear el usuario (si no lo habías creado antes)
CREATE USER ia_writer WITH PASSWORD 'tu_contrasena_super_segura';

-- 2. Dar permisos para conectarse a la base de datos FamilyDB
GRANT CONNECT ON DATABASE "FamilyDB" TO ia_writer;

-- 3. Dar permisos de uso y creación dentro del esquema 'family'
-- (USAGE permite ver el esquema, CREATE permite crear objetos si fuera necesario)
GRANT USAGE, CREATE ON SCHEMA family TO ia_writer;

-- 4. Dar permisos completos (SELECT, INSERT, UPDATE, DELETE) sobre todas las tablas EXISTENTES en el esquema 'family'
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA family TO ia_writer;

-- 5. Dar permisos sobre secuencias (muy importante para los campos ID autoincrementables / SERIAL / IDENTITY al hacer INSERT)
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA family TO ia_writer;

-- 6. Automatizar para que las FUTURAS tablas y secuencias que crees también tengan estos permisos automáticamente
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA family 
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ia_writer;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA family 
GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO ia_writer;
```

### Delete user
```
-- 1. Conéctate a la base de datos FamilyDB y revoca los privilegios
REVOKE ALL PRIVILEGES ON DATABASE "FamilyDB" FROM ia_writer;
REVOKE ALL PRIVILEGES ON SCHEMA family FROM ia_writer;

-- 2. Si el usuario llegó a crear objetos (tablas/secuencias) en el esquema 'family', 
-- reasigna la propiedad al superusuario (ej. postgres) para evitar errores de dependencia:
REASSIGN OWNED BY ia_writer TO postgres;

-- 3. Elimina cualquier otro privilegio remanente que tenga asignado
DROP OWNED BY ia_writer;
```

### Add file "opencode.json"
````
{
  "$schema": "https://opencode.ai/config.json",
  "mcp": {
    "postgres": {
      "type": "local",
      "command": ["npx", "-y", "mcp-postgres-server"],
      "env": {
        "PG_HOST": "localhost",
        "PG_PORT": "5432",
        "PG_USER": "tu_usuario",
        "PG_PASSWORD": "tu_contraseña",
        "PG_DATABASE": "tu_base_de_datos"
      },
      "enabled": true
    }
  }
}
````


