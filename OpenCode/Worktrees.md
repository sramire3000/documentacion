## Worktrees manualmente

1. Crear directorio `.worktrees/`
2. Ejecutar:
```
git worktree add .worktrees/<nombre-del-worktree>
```

## Ahora necesitamos 3 features:

- implementemos un **triple shot**: Por 5 segundos, el personaje dispara 3 veces en línea recta.
- implementemos un **sistema de skins**: Poder cambiar la apariencia de la nave.
- implementemos un **escudo**: Un escudo que protege a la nave de los proyectiles enemigos.

## Otros comandos

### Listar
```
git worktree list
```

### Eliminar
```
git worktree remove .worktrees/<nombre-del-worktree>
```

### Nota: Si Git te da un error de que hay cambios sin guardar o archivos modificados y estás seguro de que no los necesitas, puedes forzar la eliminación agregando la bandera --force:
```
git worktree remove --force .worktrees/<nombre-del-worktree>
```

### Limpiar referencias obsoletas (Opcional):
Si la carpeta del worktree fue borrada manualmente desde el explorador de archivos o la terminal (usando rm -rf) en lugar de usar git worktree remove, Git mantendrá una referencia huérfana. Puedes limpiarla ejecutando:
```
git worktree prune
```
