# Comandos personalizados

## Como crear el comando con opencode

En modo plan
```
Necesito crear un comando personalizado de OpenCode que corra a nivel local. que se ejecute /worktree y lo que necesito que haga es simplemente el siguiente comando

git worktree add .worktrees/<nombre-del-worktree>

No hagas nada más. no te cambies de directorio. No hagas nada adicional. Simplemente ejecuta el comando de la creación del World drink
```


## Archivo ".opencode/commands/worktree.md"
```
---
description: Crea un git worktree en .worktress/ con un nombre derivado del contexto dado.
agent: build
---

El usuario invocó `/worktree` con el siguiente argumento:

$ARGUMENTS

Instrucciones:

1. Analiza el argumento (puede contener espacion) y deriva un nombre corto en kebab-case (minúscula, sin espacios ni acentetos) que represente el contesto.
2. Ejecuta exactamente este comando con la tool bash, sin cambiar de directori y sin pasos adicionales:
git worktree add .worktrees/<nombre-derivado>
3. No hagas nada más: no uses `cd`, no corras otros comando, no edites archivos, no confirmes con el usuario, no hagas commit ni push.
4 Reporta únicamente el resultado del comando (stdout/stderr y código de salida).
5. Si el argumento es muy largo, simplifícalo a un nombre significativo.
```
