# Spec Driven Development

## Diagrama
```mermaid
graph LR
A(1. Describir) --> B(2. Plan Mode)
B --> C(3. Refinar)
C -- Iteración hasta aprobar --> A
C --> D(4. Guardar)
D --> E(5. Ejecutar)
E --> F(6. Revisar)
```

## Pasos
1. Describir (El problema no la solución)
2. Plan mode (OpenCode propone no edita)
3. Refinar (Desiciones no sugerencias)
4. Guardar (specs/auth/NN-Feature.md)
5. Ejecutar (Paso a paso)
6. Revisar (Diff por paso, comprobar )

Reglas que nadie casi sigue:
- En el paso 1 describe el problema, deja que tu LLMs proponga la solución.
- En el paso 3 da soluciones concretas  no "creo que... tal-vez seria bueno"
- En el paso 5 pide pausas entre fases del plan, revisa diffs pequeños.
- Si a la mitad del 5 quieres cambiar algo, vuelve al paso 2 no improvises

# Bloquear rama en la opcion branch
- Branch name pattern = main
- [X] Require a pull request before merging
   - [X] Require approvals (2)
- [X] Require status checks to pass before merging
- [X] Do not allow bypassing the above settings 

# Inicio

## Paso 01 en opencode
```
/init
```

## Configuracion

## Ejemplo de creacion de spec
```
/spec Ocupamos 4 fantasmas en el juego cd pacman, cada uno debe de temer su propia forma de actuar pata qye se comporte de forma diferente y uno de ellos debe perseguir agresivamente a pacman
```
