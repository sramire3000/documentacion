# Spec Driven Development SDD


## Diagrama
```mermaid
graph LR
A(1. Describir) --> B(2. Plan Mode)
B --> C(3. Refinar)
C -- Iteración hasta aprobar --> A
C --> D(4. Guardar)
D --> E(5. Ejecutar)
E --> F(6. Revisar)
```

## Pasos
1. Describir (El problema no la solución)
2. Plan mode (OpenCode propone no edita)
3. Refinar (Desiciones no sugerencias)
4. Guardar (specs/auth/NN-Feature.md)
5. Ejecutar (Paso a paso)
6. Revisar (Diff por paso, comprobar )

Reglas que nadie casi sigue:
- En el paso 1 describe el problema, deja que tu LLMs proponga la solución.
- En el paso 3 da soluciones concretas  no "creo que... tal-vez seria bueno"
- En el paso 5 pide pausas entre fases del plan, revisa diffs pequeños.
- Si a la mitad del 5 quieres cambiar algo, vuelve al paso 2 no improvises

# Bloquear rama en la opcion branch
Repo > Settings > branches >add clasic branch proteccion rule
- Branch name pattern = main
- [X] Require a pull request before merging
   - [X] Require approvals (2)
- [X] Require status checks to pass before merging
- [X] Do not allow bypassing the above settings 

# Inicio

## Install Skill formato Raw
- [.agents/skills/spec-impl/SKILL.md](https://github.com/sramire3000/documentacion/blob/master/OpenCode/InstallSkillSpec/.agents/skills/spec-impl/SKILL.md)
- [.agents/skills/spec/template.md](https://github.com/sramire3000/documentacion/blob/master/OpenCode/InstallSkillSpec/.agents/skills/spec/template.md)
- [.agents/skills/spec/SKILL.md](https://github.com/sramire3000/documentacion/blob/master/OpenCode/InstallSkillSpec/.agents/skills/spec/SKILL.md)


### Add opencode.json
```
"command": {
  "spec": {
    "description": "Crea especificaciones de pantallas y funcionalidades",
    "template": "Carga y sigue la skill spec en .agents/skills/spec/SKILL.md.\n\nFeature: $ARGUMENTS"
  },
  "spec-impl": {
    "description": "Implementa especificaciones de pantallas y funcionalidades",
    "template": "Carga y sigue la skill spec-impl en .agents/skills/spec-impl/SKILL.md.\n\nFeature: $ARGUMENTS"
  }
}
```

## Install MCP Play wright

### URL
- [Playwright Test](https://playwright.dev)

### Abrir opencode
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

### Abrir opencode
```
Puedes tomar una Screeshot?
```

## Install MCP Conext7

### URL
-[Context7](https://context7.com)

### install
```
npx ctx7 setup
```
Nota: hacer login para que genere el api key

### Example in opencode
```
Necesito que investigues cual es la manera de proteccion de rutas en Next.js usa context7 MCP
```



## Paso 01 en opencode
```
/init
```

## Crear un agente perosnalizado
Opencode => Necesito crear un agente perosnalizado
```
Eres un agente verificador de los criterios de aceptación de un archivo de especificación (spec).

Tu labor es revisar, corregir y marcar los checks del "Acceptance criteria" de un spec.

Usa Context7 Para asegurarte de que se usaron las recomendaciones de next.js

Usa el MCP de Playwright para verificar cuando tiene que ver con pantallas creadas.

Debe de funcionar a nivel de proyecto y usar el modelo con visión [ejemplo: Qwen3.6 Plus], ya que soporta visión para comparar screenshots.

Este agente tiene que estar a nivel de proyecto, no global.
```

## Adicionar al archivo "AGENTS.md"
```
## Flujo de specs (features grandes)

Las skills `/spec` y `/spec-impl` viven en `.agents/skills/`.
`/spec` diseña la spec y la guarda en `specs/` (todavía no existe); `/spec-impl <NN-nombre>` implementa una spec en estado Approved.
Respeta sus fases: no escribas código antes de que la spec esté aprobada.
`AutoCreateBranch` en `specs/.spec-config.yml` (por defecto `true`) controla la creación de rama.

El comando `/verify-spec` (`.opencode/commands/verify-spec.md`) delega en el subagente `spec-verifier`
(`.opencode/agents/spec-verifier.md`, modo `all`). Ese agente clasifica cada criterio (código,
comando, Next.js o UI), valida las recomendaciones del framework con Context7, compara las pantallas
contra los prototipos con Playwright + visión y marca los checks solo con evidencia. Si todos pasan,
actualiza el `**Status:**` del spec (en este repo, `Implementado`); si alguno falla, no lo cambia y
lista los bloqueos.

## MCPs

- Playwright está configurado en `opencode.json`. Cualquier screenshot o salida relacionada con Playwright debe ir en `.playwright-mcp/` (ignorada por git).
- Usa Context7 MCP para traer la documentación actualizada del framework en vez de fiarte del entrenamiento.

## Reglas de codigo
- Usar codigo limpio, nombres, funciones, variables, ect. en ingles.
```

## Configuracion

## Ejemplos
### Creacion de spec
```
/spec Ocupamos 4 fantasmas en el juego cd pacman, cada uno debe de temer su propia forma de actuar pata qye se comporte de forma diferente y uno de ellos debe perseguir agresivamente a pacman
```

### Ejecucion
```
/spec-impl @01-test
```
