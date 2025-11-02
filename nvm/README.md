# Node Version Manager
nvm es un gestor de versiones para Node.js, diseñado para instalarse por usuario e invocarse por shell. nvm funciona en cualquier shell compatible con POSIX (sh, dash, ksh, zsh, bash), en particular en estas plataformas: Unix, macOS y Windows WSL.



### Install & Update Script
To install or update nvm, you should run the install script. To do that, you may either download and run the script manually, or use the following cURL or Wget command:



```bash
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh | bash
```

```bash
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh | bash
```

### Version
```bash
nvm --version
```

### Listado de versiones en linea
```bash
nvm ls-remote
nvm list available 
```

### LIstado de versiones en el Sistema
```bash
nvm ls
nvm list
```

### Install version example
```bash
nvm install v18.19.1
```

### Use
```bash
nvm use [version node]
```

### Uninstall version
```bash
nvm uninstall v4.9.1
```



