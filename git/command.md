# COMANDOS DE GIT

# Download
-[Download Windows](https://git-scm.com/)

## Version de git
```
git --version
```

## Git config
```
git config --global user.name "John Doe"
git config --global user.email johndoe@example.com
```

## Guarda las credenciales en el gestor de windows
```
git config --global credential.helper manager
```

## Guarda las credenciales en el gestor de linnux
```
git config --global credential.helper manager-core
```


## TRABAJAR CON RAMAS

### Muestra la rama donde estoy parado
```
git branch
```

### Clona la rama apartir de donde estoy parado y crea la nueva
```
git branch [rama_nueva]
git push -u origin ramita1 
````

### Cambiar el nombre a una rama
```
git branch -m [rama_old] [rama_new]
```

### cambiarse de rama
```
git checkout [nombre de la rama]
```

### Eliminar una rama
```
git branch -d [nombre de la rama]
```

### Respaldo de rama
```
git archive --format zip --output respaldo_rama.zip nombre_de_tu_rama
```

###Crear un "Bundle" (El respaldo profesional de Git)
Si quieres un solo archivo que contenga absolutamente todo (todas las ramas, todos los commits y todo el historial) para poder restaurarlo después en otra computadora como un repositorio completo, lo ideal es usar un bundle.

Ejecuta esto en tu consola:
```
git bundle create respaldo_total.bundle --all
```

### Respaldar todas las ramas en archivos ZIP individuales 
PowerShell
```
git branch -r | ForEach-Object { 
    $branch = $_.Trim().Replace("origin/", ""); 
    if ($branch -notmatch "HEAD") {
        git archive --format zip --output "backup_$($branch.Replace('/', '_')).zip" "origin/$branch"
        Write-Host "Respaldada rama: $branch"
    }
}
```

Linux
```
for branch in $(git branch -r | grep -v 'HEAD'); do
    # Limpiamos el nombre de la rama (quitamos 'origin/')
    clean_name=$(echo $branch | sed 's/origin\///')
    
    # Reemplazamos barras inclinadas por guiones bajos para el nombre del archivo
    file_name=$(echo $clean_name | tr '/' '_')
    
    echo "Respaldando rama: $clean_name..."
    git archive --format zip --output "backup_${file_name}.zip" "$branch"
done
```



## Repositorio 

### …or create a new repository on the command line
```
echo "# prueba" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M master
git remote add origin [url_git]
git push -u origin master
```

### Clonar repo
|                 Comando               |           Descripción                 |
|---------------------------------------|---------------------------------------|
|git clone [url_git]                    | Clonar un repositorio                 |
|git clone -b [nombre_branch] [url_git] | Clonar un repositorio rama especifico |               |

…or push an existing repository from the command line
```
git remote add origin [url_git]
git branch -M master
git push -u origin master
```

## Comandos

|             Comando             |           Descripción                              |
|---------------------------------|----------------------------------------------------|
|git init                         | Inicaliza un repositorio                           |
|git status                       | Estado del repo local                              |
|git add .                        | Sube todos los archivos repo local                 |
|git comit -m "Texto del cambio"  | Comitea los cambios                                |
|git push                         | Guarda los cambios                                 |
|git pull origin master           | Actualiza los cambios de git alocal                |
|git reset --hard origin/master   | Actualiza la rama a ultimo estado(pierden cambios) |
|git diff master [tu_branch_trabajo]   | busca las diferencias |
|git merge [ramaOrigen] [ramaDestino]  | Hacer un merge entre ramas |



## Solución de conflictos

### Resolver un conflicto pero quieres que tu cambio prevalezca totalmente tu cambio "README.md"
```
# Traer los cambios del servidor (Fetch)
git fetch origin

# Sobrescribir el archivo con tu versión (Checkout)
git checkout --ours README.md

# Luego marcas el conflicto como resuelto
git add README.md
git commit -m "Solucionando conflicto: prevalece mi versión de README.md"

# Subir tus cambios cambia "tu-rama" por tu rama
git push origin tu-rama --force
```

### Resolver el conflicto pero quieres hacer un merge
```
# Guardar cambios en local
git add README.md
git commit -m "Mis cambios temporales antes del merge"

# Sincroniza tu repositorio
git fetch origin

# Intenta el Merge
git merge origin/master

#. Resuelve el conflicto manualmente
Como ambos tocaron el mismo archivo (README.md), Git se detendrá y te dirá: "Automatic merge failed; fix conflicts and then commit the result".

Abre el archivo README.md en tu editor (puedes usar nano que recién instalamos). Verás unas marcas extrañas que dividen ambos cambios:

<<<<<<< HEAD: Todo lo que esté debajo de esto es tu cambio local.

=======: Esta es la línea divisoria.

>>>>>>> origin/master: Todo lo que esté arriba de esto es el cambio que estaba en GitHub.

Lo que debes hacer:

Borra las marcas (<<<<, ====, >>>>).

Acomoda el texto para que ambos cambios queden como tú quieras (uno arriba del otro, o mezclados).

Guarda el archivo.


# Finaliza el proceso
git add README.md
git commit -m "Merge de cambios en README: integrando ambas versiones"
git push origin master
```



-[Stack Edit](https://stackedit.io/app#)
