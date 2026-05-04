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



-[Stack Edit](https://stackedit.io/app#)
