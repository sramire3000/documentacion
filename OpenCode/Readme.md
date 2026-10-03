# Open Code

## Install
```
npm install -g --allow-scripts=@opencode/cli @opencode/cli
```

## Version
```
opencode --version
```

## Ejecutar
```
opencode
```

## Run the following command in a project that is in a GitHub repo:

[opencode github install](https://github.com/apps/opencode-agent)

### crear el archivo ".github/workflows/opencode.yml"
```
name: opencode
on:
  issue_comment:
    types: [created]
  pull_request_review_comment:
    types: [created]
jobs:
  opencode:
    if: |
      contains(github.event.comment.body, '/oc') || 
      contains(github.event.comment.body, '/opencode')
    runs-on: ubuntu-latest
    permissions:
      id-token: write
    steps:
      - name: Checkout repository
        uses: actions/checkout@v6
        with:
          fetch-depth: 1
          persist-credentials: false
      - name: Run OpenCode
        uses: anomalyco/opencode/github@latest
        env:
          GEMINI_API_KEY: ${{ secrets.GEMINI_API_KEY }}
        with:
          model: google/gemini-2.5-pro
```


### URL's
- [OpenCode](https://opencode.ai)
- [OpenCode Go](https://opencode.ai/go)
- [AGENTS.md](https://agents.md/)
- [Skills](https://www.skills.sh/)


### Utilizar modelos
```
/connect
```

### IA MAI-Code-1.1-Flash

## Listado de Ficheros
- README.md (Objetivo del proyecto)
- AGENTS.md (Instrucciones princiaples)
- ideas-revision.md (Nuevas ideas)
- references/file-system.md (Estructura deseada)
