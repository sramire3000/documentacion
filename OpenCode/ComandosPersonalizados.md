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
# /worktree

Uso:
`/worktree <nombre-del-worktree>`

Ejemplo:
`/worktree feature-login`

Ejecuta exactamente:
`git worktree add ".worktrees/$ARGUMENTS"`

git worktree add ".worktrees/$ARGUMENTS"

```
