# COMANDOS DE GIT

# Download
-[Download Windows](https://git-scm.com/)
-[Stack Edit](https://stackedit.io/app#)


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


## Comandos

SmartyPants converts ASCII punctuation characters into "smart" typographic punctuation HTML entities. For example:


|             Comando            |           Descripción                |
|--------------------------------|--------------------------------------|
|git init                        | Inicaliza un repositorio             |
|git status                      | Estado del repo local                |
|git add .                       | Guarda todos los archivos repo local |
|git remote add origin "URL_GIT" | Setear el repsitorio git             |
------------------------------------------------------------------------|

You can render UML diagrams using [Mermaid](https://mermaidjs.github.io/). For example, this will produce a sequence diagram:

```mermaid
sequenceDiagram
Alice ->> Bob: Hello Bob, how are you?
Bob-->>John: How about you John?
Bob--x Alice: I am good thanks!
Bob-x John: I am good thanks!
Note right of John: Bob thinks a long<br/>long time, so long<br/>that the text does<br/>not fit on a row.

Bob-->Alice: Checking with John...
Alice->John: Yes... John, how are you?
```

And this will produce a flow chart:

```mermaid
graph LR
A[Square Rect] -- Link text --> B((Circle))
A --> C(Round Rect)
B --> D{Rhombus}
C --> D
```
