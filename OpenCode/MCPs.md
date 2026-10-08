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
