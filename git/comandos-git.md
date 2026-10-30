# GIT + GITHUB

## Comandos

### Basicos

| Comando          																			| Description											|	 
| :---                  																			|    :----             										|
| git --version      																			| Version de git       								|
| git --help commit																			| Ayuda para el comando commit			|
| git config --global user.name "nombre del usuario" 					| Configuracion del usuario 					|
| git config --global user.email "email del usuario"							| Configuracion del email						|
| git config --global  core.editor "nano"                      					| Editor en uso nano								|
| git config --global  core.editor "code --wait"            						| Editor en uso VsCode							|
| git config --global -e                                               						| Editar configuracion							|
| git config --global init.defaultBranch main             						| Define por default la rama main			|
| git config --global core.autocrlf input                       					| Convierte los CRLF a LF					|
| git config --global alias.s 'status -sb'											| Creando un alias "s" uso => "git s"		|
| git config --global alias.lg 'log --oneline --graph --all --decorate'	| Creando un alias "lg"uso => "git lg"	|
| git config --global alias.ast '!git add . && git status -sb'				| Comandos encadenados 					|


### Repositorios
| Comando          														| Description																	| 
| :---                  														|    :----             																|
| git branch -M main 													| Renombra la rama														|
| git init      	      														| Inicializa el repo local  												|
| git status                                                          		| Estado del repositorio													|
| git add index.html													| Adiciona al stage el archivo index.html						|
| git add .																	| Adiciona al stage el todos los archivos						|
| git rm --cached index.html										| Eliminar del stage														|
| git commit -m "Initial code"										| Comprometer con los cambios									|
| git commit -m "feat:"												| Funcionalidad nueva													|
| git commit -m "fix:"													| Correccion de un bug													|
| git commit -m "docs:"												| Cambios en documentacion										|
| git commit -m "refactor:"											| Cambios que no agrega funcion ni corrige bug			|
| git commit -m "test:"												| Adicion o correccion de pruebas									|
| git commit -m "chore:"											| Mantenimiento (dependencias, config)						|
| git restore .                                                            	| descartar los cambios al ultimo commit						|
| git restore index.html                                             	| descartar los cambios sobre el archivo index.html		|
| git log                                                                    	| Historial de commits													|
| git log -p                                                                	| Historial de commits con detalle de cambios				|
| git log --stat                                                           	| Historial de cambios con respectos como estabamos	|
| git log --grep="chore"                                            	| Busca todos los chore												|
| git log oneline                                                        	| Forma corta																|

Git diff – cómo ver los cambios exactos entre archivos antes de
Git commit --amend – actualizar mensajes de commits ya realizados
Git reset – cómo retroceder y deshacer cambios de forma segura
Git reflog – recuperar el historial de cambios incluso después de un reset
Git rm y git mv – eliminar y renombrar archivos manualmente dentro de Git

### Archivo ".gitignore"

