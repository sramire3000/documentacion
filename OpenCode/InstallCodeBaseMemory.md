# Instalar Codebase Memory MCP y usarlo con OpenCode

Guía de instalación de **codebase-memory-mcp** para:

- **Fedora 44** (Linux x86_64)
- **Windows 10** (x86_64)

y su integración con **OpenCode** (configuración V2).

> `codebase-memory-mcp` es un binario nativo que construye un grafo de conocimiento de tu
> código y lo expone por MCP (17 herramientas). No necesita runtime de lenguaje, Docker ni
> API keys, y no envía telemetría: todo se procesa en local.

---

## 1. Requisitos

| Requisito | Fedora 44 | Windows 10 |
|---|---|---|
| Arquitectura | x86_64 o ARM64 | x86_64 |
| OpenCode | Instalado y funcionando | Instalado y funcionando |
| Terminal | `bash` (por defecto) | **PowerShell 5.1+** (o PowerShell 7) |
| Extra | `curl` (`sudo dnf install curl`) | Permiso para ejecutar scripts |

Comprueba la versión de OpenCode:

```bash
opencode --version
```

---

## 2. Instalación en Fedora 44

### 2.1 Opción recomendada — instalador oficial

```bash
curl -fsSL https://raw.githubusercontent.com/DeusData/codebase-memory-mcp/main/install.sh | bash
```

El instalador:

1. Descarga el binario de la última release y verifica su SHA-256.
2. Lo coloca en el PATH (`~/.local/bin`).
3. **Auto-detecta OpenCode** y configura su integración MCP.
4. Añade `~/.local/bin` al PATH en `~/.bashrc`.

Si el PATH no se refresca en la sesión actual:

```bash
source ~/.bashrc        # o: exec $SHELL
```

Opciones útiles:

```bash
# Solo el binario, sin tocar la configuración de agentes
curl -fsSL https://raw.githubusercontent.com/DeusData/codebase-memory-mcp/main/install.sh | bash -s -- --skip-config

# Instalar en una ruta personalizada
curl -fsSL https://raw.githubusercontent.com/DeusData/codebase-memory-mcp/main/install.sh | bash -s -- --dir="$HOME/tools/cbm"
```

### 2.2 GitHub Releases (manual)

1. Descarga desde <https://github.com/DeusData/codebase-memory-mcp/releases/latest>:
   - `codebase-memory-mcp-linux-amd64.tar.gz` (o `-linux-arm64`).
2. Extrae y ejecuta el instalador incluido:

```bash
tar xzf codebase-memory-mcp-linux-amd64.tar.gz
cd codebase-memory-mcp-linux-amd64
./install.sh
```

Verifica la integridad con el `checksums.txt` de la release.

### 2.3 Gestores de paquetes (alternativa)

```bash
# npm
npm install -g codebase-memory-mcp

# PyPI (aislado, recomendado con pipx)
pipx install codebase-memory-mcp
# o: pip install -U codebase-memory-mcp

# Go
go install github.com/DeusData/codebase-memory-mcp@latest
```

> Hay paquete AUR (`codebase-memory-mcp-bin`) para Arch, **no** para Fedora.
> En Fedora usa el instalador oficial o npm/pipx/go.

---

## 3. Instalación en Windows 10

### 3.1 Opción recomendada — instalador PowerShell

En **PowerShell**:

```powershell
# 1) Descargar el instalador
Invoke-WebRequest -Uri https://raw.githubusercontent.com/DeusData/codebase-memory-mcp/main/install.ps1 -OutFile install.ps1

# 2) (Recomendado) Revisar el script antes de ejecutarlo
notepad .\install.ps1

# 3) Quitar la marca de "archivo descargado de internet"
Unblock-File .\install.ps1

# 4) Ejecutar
.\install.ps1
```

Si PowerShell bloquea la ejecución:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\install.ps1
# o directamente:
PowerShell -ExecutionPolicy Bypass -File .\install.ps1
```

Si SmartScreen avisa de software sin firmar: **Más información → Ejecutar de todas formas**.

### 3.2 GitHub Releases (manual)

1. Descarga `codebase-memory-mcp-windows-amd64.zip` desde
   <https://github.com/DeusData/codebase-memory-mcp/releases/latest>.
2. Extrae y ejecuta:

```powershell
Expand-Archive codebase-memory-mcp-windows-amd64.zip -DestinationPath .
Unblock-File .\install.ps1
.\install.ps1
```

### 3.3 Gestores de paquetes (alternativa)

```powershell
# Scoop
scoop install codebase-memory-mcp

# Winget
winget install codebase-memory-mcp

# Chocolatey
choco install codebase-memory-mcp

# npm
npm install -g codebase-memory-mcp
```

> **Antivirus:** Microsoft Defender puede marcar el binario como
> `Trojan:Script/Wacatac.B!ml`. Es un **falso positivo conocido** (típico de
> ejecutables sin firmar). Verifica el SHA-256 con `checksums.txt`.

### 3.4 Abrir una terminal nueva

Tras instalar, **cierra y reabre PowerShell** para que el PATH incluya el binario.
Comprueba:

```powershell
Get-Command codebase-memory-mcp
# o
where.exe codebase-memory-mcp
```

---

## 4. Configuración con OpenCode

OpenCode usa configuración **V2**. Los servidores MCP van bajo `mcp.servers`
(nunca directamente bajo `mcp`) y **no existe el campo `enabled`**: para
desactivar un servidor se usa `"disabled": true`.

### 4.1 Automática (el instalador ya lo hace)

El instalador detecta OpenCode y escribe:

| Elemento | Ruta (Fedora) | Elemento (Windows) |
|---|---|---|
| MCP | `~/.config/opencode/opencode.json` | `%USERPROFILE%\.config\opencode\opencode.json` |
| Instrucciones | `~/.config/opencode/AGENTS.md` | `%USERPROFILE%\.config\opencode\AGENTS.md` |
| Skill | `~/.config/opencode/skills/codebase-memory/SKILL.md` | igual, bajo su config |
| Agentes (3) | `~/.config/opencode/agents/codebase-memory*.md` | igual, bajo su config |
| Plugin | `~/.config/opencode/plugins/cbm-augment.ts` | igual, bajo su config |

> La documentación de OpenCode define la config global como
> `~/.config/opencode/opencode.json(c)`; en Windows se resuelve bajo tu carpeta
> de usuario (`%USERPROFILE%\.config\opencode\`). Si dudas de la ruta exacta,
> **no la escribas a mano**: usa `opencode mcp add` (sección 4.2) y deja que
> OpenCode la cree, o consulta la ruta con `opencode mcp list`.

Para forzar **solo** la integración con OpenCode (con el binario ya instalado):

```bash
codebase-memory-mcp install --clients=opencode
```

Previsualizar sin cambiar nada:

```bash
codebase-memory-mcp install --dry-run -y --clients=opencode
```

Listar todos los clientes soportados:

```bash
codebase-memory-mcp install --clients
```

### 4.2 Manual — vía CLI de OpenCode (recomendado)

Deja que OpenCode escriba el servidor en el sitio correcto:

```bash
# Fedora
opencode mcp add codebase-memory-mcp -- codebase-memory-mcp
```

```powershell
# Windows
opencode mcp add codebase-memory-mcp -- codebase-memory-mcp.exe
```

Añade `--global` para tenerlo en todos los proyectos:

```bash
opencode mcp add codebase-memory-mcp --global -- codebase-memory-mcp
```

### 4.3 Manual — editando el JSON

**Global** (`~/.config/opencode/opencode.json` en Fedora,
`%USERPROFILE%\.config\opencode\opencode.json` en Windows):

```jsonc
{
  "$schema": "https://opencode.ai/config.json",
  "mcp": {
    "servers": {
      "codebase-memory-mcp": {
        "type": "local",
        "command": ["codebase-memory-mcp"]
      }
    }
  }
}
```

Si el binario no está en el PATH, usa la ruta absoluta:

```jsonc
{
  "mcp": {
    "servers": {
      "codebase-memory-mcp": {
        "type": "local",
        "command": ["/home/tu-usuario/.local/bin/codebase-memory-mcp"]
      }
    }
  }
}
```

```jsonc
// Windows (recuerda escapar las barras invertidas)
{
  "mcp": {
    "servers": {
      "codebase-memory-mcp": {
        "type": "local",
        "command": ["C:\\Users\\TuUsuario\\.local\\bin\\codebase-memory-mcp.exe"]
      }
    }
  }
}
```

Campos útiles de un servidor local en V2:

| Campo | Descripción |
|---|---|
| `type` | Obligatorio: `"local"`. |
| `command` | Obligatorio: ejecutable + argumentos (array). |
| `cwd` | Directorio de trabajo. Por defecto, el workspace. |
| `environment` | Variables de entorno añadidas. Usa `{env:NOMBRE}` para secretos. |
| `disabled` | `true` para tenerlo configurado **sin** conectar. Default: `false`. |
| `codemode` | `false` para exponer las herramientas directamente. Default: `true`. |

### 4.4 Ejemplo completo con UI y entorno

```jsonc
{
  "$schema": "https://opencode.ai/config.json",
  "mcp": {
    "servers": {
      "codebase-memory-mcp": {
        "type": "local",
        "command": ["/home/tu-usuario/.local/bin/codebase-memory-mcp"],
        "environment": {
          "CBM_DIAGNOSTICS": "0"
        }
      }
    }
  }
}
```

---

## 5. Verificación

1. **Reinicia OpenCode** para que reconecte los servidores MCP:

   ```bash
   opencode service restart
   ```

2. Lista los servidores:

   ```bash
   opencode mcp list
   ```

   Debe aparecer `✓ codebase-memory-mcp connected`.

3. En el TUI de OpenCode usa el comando **`/mcps`** y comprueba que el servidor
   está conectado y expone sus herramientas.

4. Comprueba la versión del binario:

   ```bash
   codebase-memory-mcp --version
   ```

---

## 6. Primer uso

### 6.1 Indexar el proyecto

Pídeselo al agente en lenguaje natural:

> Index this project

o llama directamente a la herramienta `index_repository` (vía MCP).

Con la configuración de auto-index activada, los proyectos nuevos se indexan solos
al iniciar la sesión (ver 6.3).

### 6.2 UI gráfica del grafo

La UI viene embebida en el binario:

```bash
codebase-memory-mcp --ui=true --port=9749
```

Abre <http://localhost:9749>. La UI la sirve el *daemon* compartido, así que varias
sesiones no levantan servidores HTTP duplicados. La ruta del grafo es:

```
http://127.0.0.1:9749/?tab=graph&project=<nombre-del-proyecto>
```

Para acceder desde otra máquina por SSH:

```bash
ssh -L 9749:127.0.0.1:9749 tu-usuario@servidor
```

### 6.3 Auto-index y watcher

```bash
# Indexar automáticamente proyectos nuevos al iniciar sesión MCP
codebase-memory-mcp config set auto_index true
codebase-memory-mcp config set auto_index_limit 50000

# Mantener frescos los proyectos ya indexados (default: true)
codebase-memory-mcp config set auto_watch true

# Hilo de watcher en background (default: true)
codebase-memory-mcp config set watcher_enabled true
```

Claves de configuración disponibles:

| Clave | Default | Descripción |
|---|---|---|
| `auto_index` | `false` | Indexa proyectos nuevos al iniciar la sesión MCP. |
| `auto_index_limit` | `50000` | Máximo de archivos para auto-indexar. |
| `auto_watch` | `true` | Registra el watcher de git por sesión. |
| `watcher_enabled` | `true` | Hilo de reindexado en background. |
| `ui_enabled` | `false` | Sirve la UI del grafo en un puerto local. |
| `ui_port` | `9749` | Puerto de la UI. |
| `ui-lang` | `auto` | Idioma de la UI (`en`, `zh`, `auto`). |

Ver la configuración actual:

```bash
codebase-memory-mcp config list
```

---

## 7. Actualizar y desinstalar

### Actualizar

En **todas** las plataformas la actualización se hace re-ejecutando el instalador,
no desde el binario en marcha. El propio binario te dice qué comando ejecutar:

```bash
codebase-memory-mcp update
```

Si el `install.sh` sigue junto al binario (como deja el instalador oficial):

```bash
# Fedora
bash "$(dirname "$(command -v codebase-memory-mcp)")/install.sh"
```

```powershell
# Windows
powershell -ExecutionPolicy Bypass -File "$(Split-Path (Get-Command codebase-memory-mcp).Source)\install.ps1"
```

Si el `install.sh` no está (por ejemplo, instalaste por npm/pipx o el archivo se
movió), vuelve a lanzar el instalador de la sección 2.1 / 3.1: es idempotente y
reemplaza el binario por la última versión.

Si instalaste por gestor de paquetes:

```bash
npm install -g codebase-memory-mcp@latest     # npm
pipx upgrade codebase-memory-mcp              # pipx
```

### Desinstalar

```bash
# Conserva los índices (default)
codebase-memory-mcp uninstall

# Borra también todos los índices de proyectos
codebase-memory-mcp uninstall -y --delete-indexes
```

---

## 8. Solución de problemas

| Síntoma | Causa / Solución |
|---|---|
| `opencode mcp list` no muestra el servidor | No reiniciaste OpenCode: `opencode service restart`. |
| El servidor no conecta | Verifica la ruta del binario (`command`). Usa ruta absoluta si no está en el PATH. |
| `Chromium distribution 'chrome' is not found` (Playwright, no CBM) | Falta `--executable-path` en el servidor de Playwright; no afecta a Codebase Memory. |
| La UI no carga | Comprueba `ui_enabled` y el puerto: `codebase-memory-mcp config list`. Solo escucha en `127.0.0.1`. |
| `enabled: true` no hace nada | Es un campo **V1**. En V2 usa `disabled` (o nada para activado). |
| Actualizar no hace nada | `codebase-memory-mcp update` solo imprime el comando del instalador; ejecútalo. |
| Logs con `POLL ERROR` repetidos | Suele venir de la extensión de VS Code invocando `cli list_projects`. Usa la integración nativa de OpenCode o desinstala la extensión. |
| El binario no aparece en Windows | Cierra y reabre PowerShell para recargar el PATH. |

### Ubicaciones de referencia

| Elemento | Fedora | Windows |
|---|---|---|
| Binario | `~/.local/bin/codebase-memory-mcp` | `%USERPROFILE%\.local\bin\codebase-memory-mcp.exe` |
| Caché de índices | `~/.cache/codebase-memory-mcp/` | `%USERPROFILE%\.cache\codebase-memory-mcp\` |
| Logs del daemon | `~/.cache/codebase-memory-mcp/logs/cbm-daemon.log` | ídem bajo tu HOME |
| Config de OpenCode | `~/.config/opencode/opencode.json` | `%USERPROFILE%\.config\opencode\opencode.json` |

---

## 9. Referencias

- Repositorio: <https://github.com/DeusData/codebase-memory-mcp>
- Releases: <https://github.com/DeusData/codebase-memory-mcp/releases/latest>
- Documentación de OpenCode (MCP servers): <https://opencode.ai/v2/docs/mcp-servers>
- Documentación de la configuración de OpenCode: <https://opencode.ai/v2/docs/config>
