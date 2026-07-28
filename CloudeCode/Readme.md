# Cloude Clode

## Install

macOS, Linux, WSL:
```
curl -fsSL https://claude.ai/install.sh | bash
```

Windows PowerShell:
```
irm https://claude.ai/install.ps1 | iex
```

Windows CMD:
```
curl -fsSL https://claude.ai/install.cmd -o install.cmd && install.cmd && del install.cmd
```

To confirm the installation worked, run:
```
claude --version
```

## Install claude extension VsCode
- (Claude Code for VS Code)[https://marketplace.visualstudio.com/items?itemName=anthropic.claude-code]


## Login Auth

```
claude auth login
```

## Consola claude
```
claude
```

### Ver modelos disponibles consola
```
/model
```

### Cambiarse de modelo consola
```
/model opusplan
```

### Diferencia en modelos

- Opus   = Tareas son pesadas, complejas y maximo razonamiento
- Sonnet = Equilibrado entre calidad y precio
- Haiku  = Modelo Rapido flash, mas barato, util tareas sencillas, razonamiento sencillo

## Vaciar el contexto en la consola
```
/clear
```

## Ver limites en consola
```
/usage
```

